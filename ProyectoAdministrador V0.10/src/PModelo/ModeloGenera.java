package PModelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ModeloGenera {

	private Connection conec;
	private ResultSet rs;
	private PreparedStatement ps;
	private String consulta;
	
	private Date fecha;
	private int numero;
	private int idIncidencia;
	private int idProyecto;
	private int idOperario;
	
	

	public ModeloGenera(Connection c) {
		conec = c;
		rs = null;
		ps = null;
	}	
	

	public boolean guardarIncidencia(int idOperario, int idIncidencia, int idProyecto) {
		
		// la incidencia se asignara al administrador que la esta creando.
		
		boolean registro;
		
		Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
		int siguienteId = obtenerSiguienteid();

		consulta = "INSERT INTO proyecto.genera(fecha, numero, id_incidencia, id_proyecto, id_operario) VALUES (?, ?, ?, ?, ?);";

		try {
			
			ps = conec.prepareStatement(consulta);

			ps.setDate(1, (java.sql.Date) fechaActual);
			ps.setInt(2, siguienteId);
			ps.setInt(3, idIncidencia);
			ps.setInt(4, idProyecto);
			ps.setInt(5, idOperario);
			
		
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;

	}
	
	
	
	public int obtenerSiguienteid() {
		
		consulta = "SELECT * FROM proyecto.genera order by numero";
		
		numero = -1;

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();		
		
			
			if (rs.next()) {
				rs.last();
				
				fecha = rs.getDate("fecha");
				numero = rs.getInt("numero");
				idIncidencia = rs.getInt("id_incidencia");
				idProyecto = rs.getInt("id_proyecto");
				idOperario = rs.getInt("id_operario");

			}			

		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return numero+1;
	}



	public boolean noExiste(int idOperario2, int idIncidencia2, int idProyecto2) {
		
		consulta = "SELECT * FROM proyecto.genera where id_incidencia = ? and id_proyecto = ? and id_operario = ?";
		
		boolean encontrado = false;
	
		try {
	
			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ps.setInt(1, idIncidencia2);
			ps.setInt(2, idProyecto2);
			ps.setInt(3, idOperario2);
			
			rs = ps.executeQuery();		
		
			
			if (rs.next()) {
				
				fecha = rs.getDate("fecha");
				numero = rs.getInt("numero");
				idIncidencia = rs.getInt("id_incidencia");
				idProyecto = rs.getInt("id_proyecto");
				idOperario = rs.getInt("id_operario");
				
				encontrado = true;
			}			
	
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return encontrado;
		
	}

	
	


	public boolean actualizarIncidenciaOperario(int idOperario2, int idIncidencia2, int idProyecto2) {
		// la incidencia se asignara al administrador que la esta creando.
	
		boolean registro;
		
		Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
		int siguienteId = obtenerSiguienteid();

		consulta = "UPDATE proyecto.genera SET id_operario=? where id_incidencia = ? and id_proyecto = ?;";

		try {
			
			ps = conec.prepareStatement(consulta);

			ps.setInt(1, idOperario2);
			ps.setInt(2, idIncidencia2);
			ps.setInt(3, idProyecto2);
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;
	}



	public boolean todaLaTabla() {
		
		boolean respuesta = false;
		
		consulta = "SELECT * FROM proyecto.genera natural join proyecto.incidencia;";
	
		try {
	
			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();		
		
			
			if (rs.next()) {
				
				fecha = rs.getDate("fecha");
				numero = rs.getInt("numero");
				idIncidencia = rs.getInt("id_incidencia");
				idProyecto = rs.getInt("id_proyecto");
				idOperario = rs.getInt("id_operario");				
				
				respuesta = true;
			}			
	
		} catch (SQLException e) {
			e.printStackTrace();			
		}			
		
		return respuesta;
	}
	
	public boolean borrarPorId(int idIncidencia) {

		 boolean respuesta = false;
			
			consulta = "DELETE FROM proyecto.genera WHERE id_incidencia = ? ;";
		
			try {
		
				ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ps.setInt(1, idIncidencia);
				
				ps.executeUpdate();	
				
				respuesta = true;
		
			} catch (SQLException e) {
				e.printStackTrace();			
			}			
			
			return respuesta;
	}	
	
	
	public boolean borrarGeneraPorIdProyecto(int idProyecto2) {
		
		boolean registro;

		consulta = "DELETE FROM proyecto.genera WHERE id_proyecto = ?;";

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



	public ResultSet getRs() {
		return rs;
	}



	public Date getFecha() {
		return fecha;
	}



	public int getNumero() {
		return numero;
	}



	public int getIdIncidencia() {
		return idIncidencia;
	}



	public int getIdProyecto() {
		return idProyecto;
	}



	public int getIdOperario() {
		return idOperario;
	}	
}
