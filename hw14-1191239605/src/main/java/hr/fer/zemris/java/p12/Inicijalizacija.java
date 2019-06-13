package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * 	{@link ServletContextListener} which creates the and/or populates the
 * 	tables POLLS and POLLOPTIONS which this voting-app uses.
 * 
 *	@author Jakov Pucekovic
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties props = new Properties();
		try {
			props.load(Files.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsetting.properties"))));
		} catch (IOException e) {
			System.out.println("Error loading properties.");
			return;
		}
		
		String dbHost = props.getProperty("host");
		String dbPort = props.getProperty("port");
		String dbName = props.getProperty("name");
		String dbUser = props.getProperty("user");
		String dbPass = props.getProperty("password");
		String connectionURL = String.format("jdbc:derby://%s:%s/%s;user=%s;password=%s;create=true", dbHost, dbPort, dbName, dbUser, dbPass);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		try {
			Connection con = cpds.getConnection();
			createTablesIfNotExist(con, sce);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *	Creates and populates the tables if they dont exist.
	 *	@param con {@link Connection}
	 *	@param sce {@link ServletContextEvent} used to resolve path from which the data is loaded.
	 */
	private void createTablesIfNotExist(Connection con, ServletContextEvent sce) {
		try {
			//create tables
			DatabaseMetaData dbmd     = con.getMetaData();
			ResultSet pollsRset 	  = dbmd.getTables(null, null, "POLLS", null);
			ResultSet pollOptionsRset = dbmd.getTables(null, null, "POLLOPTIONS", null);
			
			if(!pollsRset.next()) {
				createPolls(con);
			}
			if(!pollOptionsRset.next()) {	
				createPollOptions(con);	
			}
			
			//populate POLLS
			if(isTableEmpty(con, "POLLS")) {
				populatePolls(con, "Glasanje za omiljeni bend", "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako\n" + 
						"biste glasali!");
				populatePolls(con, "Glasanje za omiljene TV serije", "Od sljedećih TV serija, koja Vam je serija najdraža? Kliknite na link kako\n" + 
						"biste glasali!");
			} 
			
			//populate POLLOPTIONS
			if(isTableEmpty(con, "POLLOPTIONS")) {
				Path bandPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/band-votes.txt"));				
				Path seriesPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/series-votes.txt"));
				
				long poll1Id = 0, poll2Id = 0;
				PreparedStatement pst = con.prepareStatement("select id,title from POLLS");
				ResultSet rset = pst.executeQuery();
				while(rset.next()) {
					if(rset.getString(2).equals("Glasanje za omiljeni bend")) {
						poll1Id = rset.getLong(1);
					} else if(rset.getString(2).equals("Glasanje za omiljene TV serije")) {
						poll2Id = rset.getLong(1);
					}
				}
				rset.close();				
				
				populatePollOptions(con, poll1Id, bandPath);
				populatePollOptions(con, poll2Id, seriesPath);
			}
			
		} catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	/**
	 *	Creates the table POLLS.
	 *	@param con {@link Connection}.
	 */
	private void createPolls(Connection con) {
		try {
			PreparedStatement pst = con.prepareStatement(
				"CREATE TABLE Polls\n" + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
				"title VARCHAR(150) NOT NULL,\n" + 
				"message CLOB(2048) NOT NULL\n" + 
				")");

			pst.executeUpdate();
			pst.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *	Creates the table POLLOPTIONS.
	 *	@param con {@link Connection}.
	 */
	private void createPollOptions(Connection con) {
		try {
			PreparedStatement pst = con.prepareStatement(
				"CREATE TABLE PollOptions\n" + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
				"optionTitle VARCHAR(100) NOT NULL,\n" + 
				"optionLink VARCHAR(150) NOT NULL,\n" + 
				"pollID BIGINT,\n" + 
				"votesCount BIGINT,\n" + 
				"FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + 
				")");

			pst.executeUpdate(); 
			pst.close();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *	Checks if the given table contains any entries.
	 *	@param con {@link Connection}
	 *	@param tableName Name of the table to check.
	 *	@return <code>true</code> if empty, <code>false</code> otherwise.
	 *	@throws SQLException If something goes wrong.
	 */
	private boolean isTableEmpty(Connection con, String tableName) throws SQLException {
		PreparedStatement pst = con.prepareStatement("SELECT * from " + tableName);
		ResultSet rset = pst.executeQuery();
		return !rset.next();
		
	}

	/**
	 *	Inserts the given title and message in the table POLLS.
	 *	@param con {@link Connection}
	 *	@param title Title of the poll to add.
	 *	@param message Message of the poll to add.
	 */
	private void populatePolls(Connection con, String title, String message) {
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)");
			pst.setString(1, title);
			pst.setString(2, message);
			
			pst.executeUpdate();
			pst.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		} 
	}

	/**
	 *	Inserts data read at the {@link Path} into table POLLOPTIONS under the given 
	 *	pollID. Data at the given {@link Path} should be in format [voteOption]:[voteLink]
	 *	where the voteOption and voteLink are separated by 1 or more tabs('\t', not ':').
	 *	@param con {@link Connection}.
	 *	@param pollId ID of the poll for which the options are added.
	 *	@param path {@link Path} to file which contains options.
	 */
	private void populatePollOptions(Connection con, long pollId, Path path) {
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollId, votesCount) values (?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);
		
				List<String> list = Files.readAllLines(path);
			
			for(var el : list) {
				String[] split = el.split("\t+");
				
				pst.setString(1, split[0]);
				pst.setString(2, split[1]);
				pst.setLong(3, pollId);
				pst.setLong(4, 0);
	
				pst.executeUpdate(); 
				
				ResultSet rset = pst.getGeneratedKeys();
				
				try {
					if(rset != null && rset.next()) {
						long noviID = rset.getLong(1);
						System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id=" + noviID);
					}
				} finally {
					try { rset.close(); } catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}