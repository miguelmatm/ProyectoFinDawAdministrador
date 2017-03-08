package PModelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloCliente {
	
	private Connection conec;
	private ResultSet rs;
	private PreparedStatement ps;
	private String consulta;
	
	private String correo;
	private String passw;
	private String nick;
	private String aleatoria;
	private String token;
	private boolean validado;
	private int idCliente;

	public ModeloCliente(Connection c) {
		conec = c;
		rs = null;
		ps = null;
	}

	public boolean existeCliente(int idClientee) {
		
		boolean existe = false;
		
		consulta = "SELECT * FROM proyecto.cliente where id_client = ?   ";

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setInt(1, idClientee);
			
			rs = ps.executeQuery();		
			
			if (rs.next()) {
				nick = rs.getString("nombre");
				correo = rs.getString("correo");
				passw = rs.getString("pass");
				aleatoria = rs.getString("aleatoria");
				validado = rs.getBoolean("validado");
				token = rs.getString("token");
				idCliente = rs.getInt("id_client");
				existe = true;
			}


		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return existe;
	}
	
	public int cantidad (){
		
		int cuantos = 0;
		
		try {
			
			rs.last();
			cuantos = rs.getRow();
			rs.first();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cuantos;
	}
	
	
	public boolean listarClientes(){
		
		boolean siguiente = false;
		
		consulta = "SELECT * FROM proyecto.cliente where validado = true";

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				nick = rs.getString("nombre");
				correo = rs.getString("correo");
				passw = rs.getString("pass");
				aleatoria = rs.getString("aleatoria");
				validado = rs.getBoolean("validado");
				token = rs.getString("token");
				idCliente = rs.getInt("id_client");
			
				siguiente = true;
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return siguiente;
	}
	
	
	public boolean siguienteCliente(){
		
		boolean siguiente = false;
		
		try {
			
			if (rs.next()) {
				
				nick = rs.getString("nombre");
				correo = rs.getString("correo");
				passw = rs.getString("pass");
				aleatoria = rs.getString("aleatoria");
				validado = rs.getBoolean("validado");
				token = rs.getString("token");
				idCliente = rs.getInt("id_client");
				
				siguiente = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return siguiente;
	}

	public String getCorreo() {
		return correo;
	}

	public String getPassw() {
		return passw;
	}

	public String getNick() {
		return nick;
	}

	public int getIdCliente() {
		return idCliente;
	}
}
