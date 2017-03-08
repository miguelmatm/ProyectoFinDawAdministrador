package PModelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ModeloIncidencia {
	
	private Connection conec;
	private ResultSet rs;
	private PreparedStatement ps;
	private String consulta;

	private int idIncidencia;
	private String descripcion;
	private String estado;
	private int idProyecto;
	

	public ModeloIncidencia(Connection c) {
		conec = c;
		rs = null;
		ps = null;
	}

	
	public boolean buscarIncidencia(int idIncidencia2) {
		
		consulta = "SELECT * FROM proyecto.incidencia where id_incidencia = ?";
		
		boolean encontrado = false;

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ps.setInt(1, idIncidencia2);
			
			rs = ps.executeQuery();		
		
			
			if (rs.next()) {
				
				idIncidencia = rs.getInt("id_incidencia");
				descripcion = rs.getString("descripcion");
				estado = rs.getString("estado");
				idProyecto = rs.getInt("id_proyecto");
				
				encontrado = true;
			}			

		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return encontrado;
		
	}


	public int obtenerSiguienteid() {
		
		consulta = "SELECT * FROM proyecto.incidencia order by id_incidencia";
		
		idIncidencia = -1;

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();		
		
			
			if (rs.next()) {
				rs.last();
				
				idIncidencia = rs.getInt("id_incidencia");
				descripcion = rs.getString("descripcion");
				estado = rs.getString("estado");
				idProyecto = rs.getInt("id_proyecto");
			}			

		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return idIncidencia+1;
	}
	
	public boolean guardarIncidencia(int cadenaIdIncidencia, String estadoIncidencia, int cadenaIdProyecto,
			String descripcionIncidencia) {
		
		boolean registro;

		consulta = "INSERT INTO proyecto.incidencia(id_incidencia, descripcion, estado, id_proyecto) VALUES (?, ?, ?, ?);";

		try {

			ps = conec.prepareStatement(consulta);

			ps.setInt(1, cadenaIdIncidencia);
			ps.setString(2, descripcionIncidencia);
			ps.setString(3, estadoIncidencia);
			ps.setInt(4, cadenaIdProyecto);
			
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			e.printStackTrace();
			registro = false;
			
		}
		return registro;
		
	}
	
	
	public boolean borrarIncidencia(int idIncidencia2) {
		
		 
		boolean registro;

		consulta = "DELETE FROM proyecto.incidencia WHERE id_incidencia = ?;";

		try {

			ps = conec.prepareStatement(consulta);

			ps.setInt(1, idIncidencia2);				
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;
		
	}
	

	public boolean actualizarIncidencia(int idIncidencia2, int idProyecto2, String descripcion2, String estado2) {
		
		

		boolean registro;
		
		Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
		int siguienteId = obtenerSiguienteid();

		consulta = "UPDATE proyecto.incidencia SET descripcion=?, estado=? WHERE id_incidencia = ? and id_proyecto = ? and estado != 'Resuelta';";

		try {
			
			ps = conec.prepareStatement(consulta);

			ps.setString(1, descripcion2);
			ps.setString(2, estado2);
			ps.setInt(3, idIncidencia2);
			ps.setInt(4, idProyecto2);
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;
		
	}

	
	
	public void mostrarTodasLasIncidencias() {
		
		consulta = "SELECT * FROM proyecto.incidencia";		

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();		
		
			
			if (rs.next()) {
				
				idIncidencia = rs.getInt("id_incidencia");
				descripcion = rs.getString("descripcion");
				estado = rs.getString("estado");
				idProyecto = rs.getInt("id_proyecto");

			}			

		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
	}	
	
	
	public boolean haySiguiente(){
		boolean hay = false;
		
			
			try {
				if (rs.next()) {
					
					idIncidencia = rs.getInt("id_incidencia");
					descripcion = rs.getString("descripcion");
					estado = rs.getString("estado");
					idProyecto = rs.getInt("id_proyecto");
					
					hay = true;

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		
		return hay;
	}
	
	
	public boolean borrarIncidenciaPorIdProyecto(int idProyecto2) {
		
		boolean registro;

		consulta = "DELETE FROM proyecto.incidencia WHERE id_proyecto = ?;";

		try {

			ps = conec.prepareStatement(consulta);

			ps.setInt(1, idProyecto2);				
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;
	}
	
	public boolean actualizarEstadoInci(int idIncidencia2, String estado2) {
		
		boolean actualizado = false;
		
		consulta = "UPDATE proyecto.incidencia SET estado=? WHERE id_incidencia = ?;";

		try {
			
			ps = conec.prepareStatement(consulta);

			ps.setString(1, estado2);
			ps.setInt(2, idIncidencia2);

			
			ps.executeUpdate();
		
			actualizado = true;
		

		} catch (SQLException e) {
			
			e.printStackTrace();
			actualizado = false;
			
		}
		
		return actualizado;
	
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



	public int getIdIncidencia() {
		return idIncidencia;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public String getEstado() {
		return estado;
	}



	public int getIdProyecto() {
		return idProyecto;
	}
}
