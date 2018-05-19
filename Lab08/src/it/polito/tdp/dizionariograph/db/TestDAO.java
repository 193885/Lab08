package it.polito.tdp.dizionariograph.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestDAO {
	
	public static void main(String[] args) {
		
		WordDAO wd = new WordDAO();
		
//		System.out.println(wd.getAllWordsFixedLength(6));
		
		List <String> p = wd.getSimili(4, "cane");
		
		Collections.sort(p);
		
		List <String> pord = new ArrayList <>(p);
		
		System.out.println(pord);
		
		System.out.println(wd.getSimili(4, "cane").size());
	}

}
