package it.polito.tdp.dizionariograph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int length) {

		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		List<String> parole = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, length);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(res.getString("nome"));
			}

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<String> getSimili(int length, String source) {
		
		String sql = "SELECT nome " + 
					 "FROM parola " + 
					 "WHERE LENGTH(nome) = ? AND " + 
					 "nome LIKE ? ";
		
		List<String> simili = new ArrayList<String>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			
			char [] parolaInserita = source.toCharArray();
			
			for (int i=0;i< parolaInserita.length; i++) {
				
				char temp = parolaInserita[i]; //salvo in una variabile temporanea il carattere che vado a sostituire con la wildcard
			
				parolaInserita[i] = '%';
				
				String parolaModificata = String.copyValueOf(parolaInserita); //modifico la stringa
				
				parolaInserita[i] = temp; //ripristino la parola
			
				PreparedStatement st = conn.prepareStatement(sql);
				
				st.setInt(1, length);
				st.setString(2, parolaModificata);
				
				ResultSet res = st.executeQuery();	
			
	
			while (res.next()) {
				
				if(!res.getString("nome").equals(source)) // controllo per evitare multigrafo su se stesso
				
					simili.add(res.getString("nome"));
			}
		}
			return simili;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}
}
