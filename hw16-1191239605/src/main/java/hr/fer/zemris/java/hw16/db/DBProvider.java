package hr.fer.zemris.java.hw16.db;

import java.io.IOException;

/**
 *	Class which provides the {@link Database} used for 
 *	storing information about the pictures. Implements the
 *	simpleton designer pattern.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class DBProvider {

	/**Database which the {@link DBProvider} provides.*/
	private static Database db = null;
	
	/**
	 * Private constructor because this class should only be used through
	 * the static method below.
	 */
	private DBProvider()
	{
	}
	
	/**
	 * 	Returns a {@link Database} object which is used for storing
	 * 	information about pictures.
	 * 	@return {@link Database}
	 * 	@throws IOException if the {@link Database} cannot be created.
	 */
	public static Database getDB() throws IOException {
		if(db == null) {
			db = new Database("src/main/webapp/WEB-INF/opisnik.txt");
		}
		return db;
	}
	
}
