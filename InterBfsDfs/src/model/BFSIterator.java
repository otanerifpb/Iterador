package model;


import dirigido.Aresta;
import dirigido.Grafo;
import dirigido.VertexState;
import dirigido.Vertice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Interface.InteratorCollection;

public class BFSIterator<T> implements InteratorCollection{
	
	private Grafo<String> grafos;
	private int posicao;
	private T carga;
	private String p;
	private Vertice<T> raiz;
	private List<Vertice<T>> vertices;
	private List<Aresta<T>> arestas;
	private Deque<Vertice<T>> fila;
	

	public BFSIterator(Grafo<String> g, String p) {
		
		this.p = p;
		if (g != null) {
            this.grafos = g;
        } else {
            this.grafos = new Grafo<String>();
        }
    }

    public BFSIterator(String p) {
    	
    }
    
	
	public BFSIterator(List<Vertice<T>> vertices, List<Aresta<T>> arestas, T carga) {
		this.arestas = arestas;
		this.vertices = vertices;
		this.fila = new ArrayDeque<>();
		this.carga = carga;
		this.BFS(carga);
	}

	public String toString() {
		return this.grafos.toString();
	}

	@Override
	public boolean hasNext() {
		//return posicao < this.fila.size() && this.fila != null; 
		return !fila.isEmpty() &&  posicao < (this.fila.size()-1) ;
	}

	@Override
	 public Vertice<T>  getNext() {
		
		if (!hasNext()) {
			throw new IllegalStateException("A lista esta vazia");
		}
		posicao = this.fila.size();
		// raiz = fila.pollFirst();
		this.BFS(this.carga );
		return raiz;
    }
	
	public void BFS( T carga ){
		Queue<Vertice<T>> q = new LinkedList<Vertice<T>>();
		List<Vertice<T>> uAdjacentes = null;
		
		Vertice<T> v = getVertice((T) carga);
					
		if( !exists( v) )
			return;
		
		// Marcando todos os n�s como NAO-VISITADOS
		for(int i=0; i < vertices.size(); i++ ){
			vertices.get(i).setStatus(VertexState.Unvisited);
		}
		
		v.setStatus(VertexState.Visited);
		q.add(v);
		showMarked();
	
		while ( !q.isEmpty()){
			Vertice<T> u = q.remove();
			 System.out.print("\t" + q.toString() + "\n");
			
			uAdjacentes = incidentes(u);
			
			for(Vertice<T> w: uAdjacentes){
				
				if( w.getStatus() == VertexState.Unvisited ) {							
					w.setStatus(VertexState.Visited);
					q.add(w);								
				}
				showMarked();
				System.out.print("\t" + q.toString() +"\n");
			}
			
			u.setStatus(VertexState.Finished);
		}
	}
	
	private void addFila(Vertice<T> u) {
		if(!this.fila.contains(u)) {
			this.fila.add(u);
		}
		
	}

	public Vertice<T> getVertice( T carga){

        for (Vertice<T> w : vertices) {
        	if ( w.getCarga().equals( carga ))
        		return w;
        }
        return null;
	}

	public boolean exists(Vertice<T> w){

        for (Vertice<T> u : vertices) {
        	if ( u.getCarga().equals( w.getCarga() ) )
        		return true;
        }
        return false;
	}
	
	private void showMarked(){
		for(int i=0; i < vertices.size(); i++ ){
			if ( vertices.get(i).getStatus() == VertexState.Visited )
				System.out.print( vertices.get(i).getCarga());
		}
	}
	
	public List<Vertice<T>> incidentes( Vertice<T> u ){
		 List<Vertice<T>> vertex = new ArrayList<Vertice<T>>();
		 for(Aresta<T> arco: arestas){
			if( arco.getDestino().equals(u))
				vertex.add((Vertice<T>) arco.getOrigem());
			else if( arco.getOrigem().equals(u))
				vertex.add((Vertice<T>) arco.getDestino());		
		}
		return vertex;
	 }	
}
