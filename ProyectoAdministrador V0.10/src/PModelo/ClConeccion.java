package PModelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClConeccion {
	
	private String user;
    private String pass;
//    private String nombreBD = "mtalavera";
//    private final String puerto = "5432";
//    private String host = "ns3034756.ip-91-121-81.eu";
    private String url;
//    private final String esquema = "proyecto";
    
    private Connection connection = null;
    private Statement statement = null;

//    public ClConeccion() {
//        url = "jdbc:postgresql://"+host+"/"+nombreBD + "?currentSchema=" + esquema;
//        
//        System.out.println("Nueva Coneccion"+url);
//        
//        conectarme();
//        
//    }

    public ClConeccion(String dbHostname, String dbDatabase, String dbUsername, String dbPassword) {
    	url = dbHostname;
    	user = dbUsername;
    	pass = dbPassword;
    	
    	System.out.println("Nueva Coneccion : "+dbHostname +dbDatabase +dbUsername +dbPassword);
    	
    	conectarme();
	}

	//Constructor donde vamos a usar todos los atributos predefinidos anteriormente para conectar con la base de datos
    //Este metodo es llamado unicamente al crearse la clase y por el constructor;
    public void conectarme() {
        try {
            Class.forName("org.postgresql.Driver");
            
            connection = DriverManager.getConnection(url,user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
       
            if(connection != null){
                System.out.println("Base De Datos Conectada");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClConeccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClConeccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //devolvera la coneccion ya establecida al crearse
    public Connection getConnection() {
        return connection;
    }
    
    public void cerrarConexion(){
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
