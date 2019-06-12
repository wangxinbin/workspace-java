package br.com.gilson.estudo.informixJDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import br.com.gilson.estudo.informixJDBC.infra.PropertiesInformix;

public class App 
{
    public static void main( String[] args )
    {
    	try {
    		Properties prop = PropertiesInformix.getPropertiesInformix();
    		comand(prop);
    	}catch(ClassNotFoundException | SQLException | IOException e) {
    		System.out.println(e);
    	}
    }
    
    private static String connectionString(Properties prop) {
    	String server = prop.getProperty("prop.informix.server");
        String port = prop.getProperty("prop.informix.port");
        String database = prop.getProperty("prop.informix.database");
        String servername = prop.getProperty("prop.informix.servername");
        
        return "jdbc:informix-sqli://" + server 
                + ":" + port + "/" + database 
                + ":INFORMIXSERVER=" + servername+";";
    }
    
    private static Connection connectionInformix(String connectString, Properties prop) throws ClassNotFoundException, SQLException{
        String driver = "com.informix.jdbc.IfxDriver";
        Class.forName(driver);
        String user = prop.getProperty("prop.informix.user");
        String password = prop.getProperty("prop.informix.password");
        return DriverManager.getConnection(connectString, user, password);
    }
    
    private static void comand(Properties prop) throws ClassNotFoundException, SQLException {
    	String connectString = connectionString(prop);
        Connection conn = connectionInformix(connectString, prop);
        
        PreparedStatement str = conn.prepareStatement("SELECT * FROM SYNONYMS");
        ResultSet resultSet = str.executeQuery();
        
        System.out.println(getHead());
        
        while(resultSet.next()) {
        	for(int i = 1; i <= 4; i++) {
        		
        		System.out.print(String.format(gerFormatter(i), resultSet.getString(i)));
        	}
        	System.out.println("");
        }
        
        str.close();
        conn.close();
    }
    
    private static String gerFormatter(int value) {
    	switch (value) {
		case 1:
			return "%-7s";
		case 2:
			return "%-15s";
		case 3:
			return "%-40s";
		case 4:
			return "%-10s";
		default:
			return "%s";
		}
    	
    }
    
    private static String getHead() {
    	StringBuilder head = new StringBuilder();
    	head.append(String.format(gerFormatter(1), "acd_no"));
    	head.append(String.format(gerFormatter(2), "item_type"));
    	head.append(String.format(gerFormatter(3), "item_name"));
    	head.append(String.format(gerFormatter(4), "value"));
    	
    	return head.toString();
    }
}
