package hr.fer.zemris.java.hw16.servlets;

import java.io.IOException;

/**
 *	DBProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class DBProvider {

	private static Database db = null;
	
	private DBProvider()
	{
	}
	
	//TODO makni apsolutni path
	public static Database getDB() throws IOException {
		if(db == null) {
			db = new Database("/home/jakov/Opjj/hw16-1191239605/src/main/webapp/WEB-INF/opisnik.txt");
		}
		return db;
	}
	
	
}
