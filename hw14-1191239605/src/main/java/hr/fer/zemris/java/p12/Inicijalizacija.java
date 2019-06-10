package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			System.err.println("properties nisu loadani");
		}
		
		String dbHost = props.getProperty("host");
		String dbPort = props.getProperty("port");
		String dbName = props.getProperty("name");
		String dbUser = props.getProperty("user");
		String dbPass = props.getProperty("password");
		String connectionURL = String.format("jdbc:derby://%s:%s/%s;user=%s;password=%s", dbHost, dbPort, dbName, dbUser, dbPass);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		createTablesIfNotExist();

		
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

	//TODO javadoc
	private void createTablesIfNotExist() {
		try {
			Connection dbConnection = DriverManager.getConnection("jdbc:derby:MyDb;create=true");
			DatabaseMetaData dbmd = dbConnection.getMetaData();
			ResultSet pollsRS 		= dbmd.getTables(null, "MYSCHEMA", "POLLS", null);
			ResultSet pollOptionsRS = dbmd.getTables(null, "MYSCHEMA", "POLLOPTIONS", null);
			if(!pollsRS.next())
			{
				createPolls();
			}
			if(!pollOptionsRS.next()) {
				createPollOptions();
			}
		} catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	private void createPolls() {
		
	}
	
	private void createPollOptions() {
		
	}
}