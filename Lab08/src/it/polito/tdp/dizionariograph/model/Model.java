package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	private WordDAO wdao;
	
	private Graph <String, DefaultEdge>  grafo;
	
	private List <String> parole;
	
	public Model() {
		
		wdao= new WordDAO();

	}

	public List<String> createGraph(int numeroLettere) {
					
		parole= wdao.getAllWordsFixedLength(numeroLettere);
		
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo,parole);
		
		for (String source : parole) {
			

			// potrei cercare simili by DB MA LENTO!
			// List<String> paroleSimili = wdao.getSimili(source, numeroLettere);

			
			List<String> paroleSimili = paroleSimilibyJava(parole,source);

			for (String dest : paroleSimili)
			
				grafo.addEdge(source, dest);
		}
		
		return parole;
	}
	

	private List<String> paroleSimilibyJava(List<String> parole, String source) {
	
		List<String> paroleSimili = new ArrayList<>();
		
		for (String p : parole) {
			
			if (simile(source, p))
				
				paroleSimili.add(p);
		}
		
		return paroleSimili;
	}

	private boolean simile(String source, String dest) {
	
		int lettereDiverse = 0;
		
		for (int i = 0; i < source.length(); i++) {
			
			if (source.charAt(i) != dest.charAt(i))
				
				lettereDiverse++;
		}

		if (lettereDiverse == 1)
			
			return true;
		
		else
			return false;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		
		List <String> vicine = new ArrayList<>();
		
		for(String s : grafo.vertexSet()) {

			if(grafo.containsEdge(parolaInserita, s)) {
				
				vicine.add(s);
			}
		}
				
			return vicine;
	}

	public String findMaxDegree() {
		
		int max= 0;
		
		String verticeMax = null;
		
		for (String s : grafo.vertexSet()) {
			
			if (grafo.degreeOf(s) > max) {
				
				max = grafo.degreeOf(s);
				
				verticeMax = s;
			}
		}
		
		if(max!=0) {
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Grado massimo: " +  verticeMax  +"\n" + "grado: "+ max +"\n" + "Vicini: "+ Graphs.neighborListOf(grafo, verticeMax));
			
			return sb.toString();
			
		}
		
		return "Non trovato";
	}
}
