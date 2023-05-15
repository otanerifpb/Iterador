package model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import Interface.InteratorCollection;
import dirigido.Aresta;
import dirigido.VertexState;
import dirigido.Vertice;

public class DFSIterator<T> implements InteratorCollection{
	
	private List<Vertice<T>> vertices;
    private Deque<Vertice<T>> fila;
    private Vertice<T> raiz;

	public DFSIterator(List<Vertice<T>> vertices, T carga) {
		this.vertices = vertices;
		this.fila = new ArrayDeque<>();
		this.DFS(carga);
		
	}
	
	@Override
	public boolean hasNext() {
		return !fila.isEmpty();
	}


	@Override
	public Object getNext() {
		if (!hasNext()) {
			throw new IllegalStateException("A lista esta vazia");
		}
		raiz = fila.pollLast();
		return raiz;
	}
	
	
	public void DFS(T source){
		Vertice<T> u = null;
				
		if ((u = getVertice(source)) == null) {
			System.err.println("vertice nao encontrado em runDFS()");
			return;
		}
		
		for(int i=0; i < vertices.size(); i++ ){
			vertices.get(i).setStatus(VertexState.Unvisited);
		}
		
		runDFS( u );	
	}
	
	private void runDFS(Vertice<T> u){
		Vertice<T> w = null;
		List<Aresta<T>> uAdjacentes = null;
				
		u.setStatus(VertexState.Visited);
		
		uAdjacentes = u.getAdj();
			
		for(Aresta<T> arco: uAdjacentes){
			w = (Vertice<T>) arco.getDestino();
			
			if( w.getStatus() == VertexState.Unvisited )							
				runDFS(w);							
		} 
		
		u.setStatus(VertexState.Finished);
		addFila(u);
	}
	
	public Vertice<T> getVertice( T carga){
      if (!vertices.isEmpty()){
        for (Vertice<T> u : vertices) {
            if ( u.getCarga().equals( carga ))
                return u;
        }
      }
        return null;
    }

    private void addFila(Vertice<T> carga) {
		if(!this.fila.contains(carga)) 
			this.fila.add(carga);
	}

}
