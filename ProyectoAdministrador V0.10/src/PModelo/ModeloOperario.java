package PModelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloOperario {

	
	private Connection conec;
	private ResultSet rs;
	private PreparedStatement ps;
	private String consulta;
	
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String rango;
	private String password;
	private String token;
	private int idOperario;
	


	public ModeloOperario(Connection c) {
		conec = c;
		rs = null;
		ps = null;
	}

	public boolean buscarOperarioXNombre(String nombreOperario) {
		
		boolean resultado = false;
		
		consulta = "SELECT * FROM proyecto.operario where nombre = ?   ";
		

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, nombreOperario);
			
			rs = ps.executeQuery();		
		
			resultado = true;
			
			if (rs.next()) {
				nombre = rs.getString("nombre");
				primerApellido = rs.getString("primerApellido");
				segundoApellido = rs.getString("segundoApellido");
				rango = rs.getString("rangoOperario");
				password = rs.getString("password");
				token = rs.getString("token");
				idOperario = rs.getInt("id_operario");
			}


		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return resultado;
	}
	
	public boolean todosLosOperarios() {
		
		boolean resultado = false;
		
		consulta = "SELECT * FROM proyecto.operario";
		
		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						
			rs = ps.executeQuery();		
					
			if (rs.next()) {
				
				nombre = rs.getString("nombre");
				primerApellido = rs.getString("primerApellido");
				segundoApellido = rs.getString("segundoApellido");
				rango = rs.getString("rangoOperario");
				password = rs.getString("password");
				token = rs.getString("token");
				idOperario = rs.getInt("id_operario");
				
				resultado = true;
			}


		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return resultado;
		
	}
	
	
	public boolean siguienteOperario(){
		
		boolean siguiente = false;
		
		try {
			
			if (rs.next()) {
				
				nombre = rs.getString("nombre");
				primerApellido = rs.getString("primerApellido");
				segundoApellido = rs.getString("segundoApellido");
				rango = rs.getString("rangoOperario");
				password = rs.getString("password");
				token = rs.getString("token");
				idOperario = rs.getInt("id_operario");
				
				siguiente = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return siguiente;
	}
	
	
	
	public void buscarOperarioXId(int idOperarioo){
		consulta = "SELECT * FROM proyecto.operario where id_operario = ?   ";

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setInt(1, idOperarioo);
			
			rs = ps.executeQuery();		
		
			
			
			if (rs.next()) {
				nombre = rs.getString("nombre");
				primerApellido = rs.getString("primerApellido");
				segundoApellido = rs.getString("segundoApellido");
				rango = rs.getString("rangoOperario");
				password = rs.getString("password");
				token = rs.getString("token");
				idOperario = rs.getInt("id_operario");

			}

		} catch (SQLException e) {
			e.printStackTrace();			
		}
	}

	
	public boolean borrarOperario(int idOperario2) {
		
		boolean registro;

		consulta = "DELETE FROM proyecto.operario WHERE id_operario = ?;";

		try {

			ps = conec.prepareStatement(consulta);

			ps.setInt(1, idOperario2);				
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;
	}



	public boolean actualizarTokenPorLogeo(String nombre, String token) {		
			
		consulta = "UPDATE proyecto.operario SET token= ? WHERE nombre =?;";		
		boolean tokenAsignado = false;

		try {
			
			ps = conec.prepareStatement(consulta);
	

			ps.setString(1, token);
			ps.setString(2, nombre);

			ps.executeUpdate();			

			tokenAsignado = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tokenAsignado;
	}
	
	
	public boolean actualizarOperario(int idOperario2, String primerApellido2, String segundoApellido2,
			String rangoOperario) {
		
		boolean actualizado = false;
		
		consulta = "UPDATE proyecto.operario SET \"primerApellido\"=?, \"segundoApellido\"=?, \"rangoOperario\"=? WHERE id_operario = ?;";

		try {
			
			ps = conec.prepareStatement(consulta);

			ps.setString(1, primerApellido2);
			ps.setString(2, segundoApellido2);
			ps.setString(3, rangoOperario);
			ps.setInt(4, idOperario2);

			System.out.println(ps);
			
			ps.executeUpdate();
		
			actualizado = true;
		

		} catch (SQLException e) {
			
			e.printStackTrace();
			actualizado = false;
			
		}
		
		return actualizado;
	}
	
	
	
	public boolean guardarOperario(int idNuevo, String nombre2, String primerApellido2, String segundoApellido2, String rangoOperario,
			String passWord2, String tokenn) {
		
		boolean registro;

		consulta = "INSERT INTO proyecto.operario(id_operario, \"primerApellido\", \"segundoApellido\", \"rangoOperario\", \"password\", \"nombre\", \"token\") VALUES (?, ?, ?, ?, ?, ?, ?);";

		try {

			ps = conec.prepareStatement(consulta);

			ps.setInt(1, idNuevo);
			ps.setString(2, primerApellido2);
			ps.setString(3, segundoApellido2);
			ps.setString(4, rangoOperario);
			ps.setString(5, passWord2);
			ps.setString(6, nombre2);
			ps.setString(7, tokenn);
			
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			e.printStackTrace();
			registro = false;
			
		}
		return registro;
	}
	
	
	
	public void obtenerUltimoId() {

		consulta = "SELECT * FROM proyecto.operario ORDER BY id_operario ";

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();		
		
			
			if (rs.last()) {
				nombre = rs.getString("nombre");
				primerApellido = rs.getString("primerApellido");
				segundoApellido = rs.getString("segundoApellido");
				rango = rs.getString("rangoOperario");
				password = rs.getString("password");
				token = rs.getString("token");
				idOperario = rs.getInt("id_operario");

			}

		} catch (SQLException e) {
			e.printStackTrace();			
		}

		
	}
	
	
	public String getNombre() {
		return nombre;
	}



	public String getPrimerApellido() {
		return primerApellido;
	}



	public String getSegundoApellido() {
		return segundoApellido;
	}



	public String getRango() {
		return rango;
	}



	public String getPassword() {
		return password;
	}



	public String getToken() {
		return token;
	}



	public int getIdOperario() {
		return idOperario;
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


}




	

