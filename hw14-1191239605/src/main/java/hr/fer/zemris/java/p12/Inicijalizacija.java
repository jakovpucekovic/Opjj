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

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties props = new Properties();
		try {
			props.load(Files.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsetting.properties"))));
		} catch (IOException e) {
			// TODO catch
			System.out.println("properties nisu loadani");
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
		} catch (SQLException e) {
			// TODO catch
			System.out.println(e.getMessage());
		}

	}

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
//TODO linkovi za serije
	//TODO javadoc
	private void createTablesIfNotExist(Connection con, ServletContextEvent sce) {
		try {
			DatabaseMetaData dbmd   = con.getMetaData();
			ResultSet pollsRS 		= dbmd.getTables(null, null, "POLLS", null);
			ResultSet pollOptionsRS = dbmd.getTables(null, null, "POLLOPTIONS", null);
			if(!pollsRS.next() || !pollOptionsRS.next()) {
				createPolls(con);
				createPollOptions(con);

				long poll1Id = populatePolls(con, "Glasanje za omiljeni bend", "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako\n" + 
						"biste glasali!");
				
				long poll2Id = populatePolls(con, "Glasanje za omiljene TV serije", "Od sljedećih TV serija, koja Vam je serija najdraža? Kliknite na link kako\n" + 
						"biste glasali!");
				
				Path bandPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/band-votes.txt"));
				Path seriesPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/series-votes.txt"));
				
				populatePollOptions(con, poll1Id, bandPath);
				populatePollOptions(con, poll2Id, seriesPath);
			
			}
		} catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	private long populatePolls(Connection con, String title, String message) {
		PreparedStatement pst = null;
		long noviID = 0;
		
		try {
			pst = con.prepareStatement(
				"INSERT INTO Polls (title, message) values (?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, message);
			
			pst.executeUpdate();
			
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					noviID = rset.getLong(1);
					System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id=" + noviID);
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} 
		
		return noviID;
	}
	
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
		}		//TODO sto generalno radimo s exceptionima, bacamo stack trace ili nesto drugo?
	}
	//TODO jel ima smisla novu konekciju za svaku od ovih stvari?
	private void createPolls(Connection con) {
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
				"CREATE TABLE Polls\n" + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
				"title VARCHAR(150) NOT NULL,\n" + 
				"message CLOB(2048) NOT NULL\n" + 
				")");

			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	private void createPollOptions(Connection con) {
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
				"CREATE TABLE PollOptions\n" + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
				"optionTitle VARCHAR(100) NOT NULL,\n" + 
				"optionLink VARCHAR(150) NOT NULL,\n" + 
				"pollID BIGINT,\n" + 
				"votesCount BIGINT,\n" + 
				"FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + 
				")");

			pst.executeUpdate(); 
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}