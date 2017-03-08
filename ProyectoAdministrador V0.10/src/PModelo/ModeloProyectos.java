package PModelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloProyectos {

	
	
	private Connection conec;
	private ResultSet rs;
	private PreparedStatement ps;
	private String consulta;
	
	private int idProyecto;
	private String nombre;
	private int idCliente;


	public ModeloProyectos(Connection c) {
		conec = c;
		rs = null;
		ps = null;
	}

	
	public int obtenerSiguienteid(){
		
		consulta = "SELECT * FROM proyecto.proyecto order by id_proyecto";
		
		idProyecto = -1;

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();		
		
			System.out.println("antes de la comprobacion");
			
			if (rs.next()) {
				rs.last();
				nombre = rs.getString("nombre");	
				idProyecto = rs.getInt("id_proyecto");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return idProyecto+1;
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


	public boolean nuevoProyecto(int idProyec, String nombreProyecto, int idCliente2, File file, String url) {
		
		byte buf[];
	    FileInputStream fis;
		boolean registro = false;
	    
	    try {
			fis = new FileInputStream(file);
			
			consulta = "INSERT INTO proyecto.proyecto(id_proyecto, nombre, id_cliente, foto, urlfoto) VALUES (?, ?, ?, ?, ?);";

			try {

				ps = conec.prepareStatement(consulta);

				ps.setInt(1, idProyec);
				ps.setString(2, nombreProyecto);
				ps.setInt(3, idCliente2);
				ps.setBinaryStream(4, fis, (int)file.length());
				ps.setString(5, url);

				
				ps.executeUpdate();
			
				registro = true;
			

			} catch (SQLException e) {
				e.printStackTrace();
				registro = false;
				
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		return registro;
		
	}


	public void buscarProyectoXId(int idProyecto2) {

		consulta = "SELECT * FROM proyecto.proyecto where id_proyecto = ?   ";

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setInt(1, idProyecto2);
			
			rs = ps.executeQuery();	
			
			
			if (rs.next()) {
				idProyecto = rs.getInt("id_proyecto");
				nombre = rs.getString("nombre");
				idCliente = rs.getInt("id_cliente");
			}


		} catch (SQLException e) {
			e.printStackTrace();			
		}

	}


	public boolean listarProyectos() {
		boolean siguiente = false;
		
		consulta = "SELECT * FROM proyecto.Proyecto";

		try {

			ps = conec.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				idCliente = rs.getInt("id_cliente");
				idProyecto = rs.getInt("id_proyecto");
				nombre = rs.getString("nombre");
			
				siguiente = true;
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		
		return siguiente;
	}
	
	
	public boolean siguienteProyecto(){
		
		boolean siguiente = false;
		
		try {
			
			if (rs.next()) {
				
				idCliente = rs.getInt("id_cliente");
				idProyecto = rs.getInt("id_proyecto");
				nombre = rs.getString("nombre");

				siguiente = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return siguiente;
	}
	
	
	
	public boolean borrarPorId(int idProyecto) {
		
		boolean registro;

		consulta = "DELETE FROM proyecto.proyecto WHERE id_proyecto = ?;";

		try {

			ps = conec.prepareStatement(consulta);

			ps.setInt(1, idProyecto);				
			
			ps.executeUpdate();
		
			registro = true;
		

		} catch (SQLException e) {
			e.printStackTrace();
			registro = false;
			
		}
		
		return registro;
	}
	
	
	public boolean actualizarProyecto(int idProyecto2, String nombreProyecto, int idCliente2) {
		
		consulta = "UPDATE proyecto.proyecto SET nombre= ?, id_cliente= ? WHERE id_proyecto =?;";	
		
		boolean actualizado = false;

		try {
			
			ps = conec.prepareStatement(consulta);
	

			ps.setString(1, nombreProyecto);
			ps.setInt(2, idCliente2);
			ps.setInt(3, idProyecto2);

			ps.executeUpdate();			

			actualizado = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	


	public int getIdProyecto() {
		return idProyecto;
	}


	public String getNombre() {
		return nombre;
	}


	public int getIdCliente() {
		return idCliente;
	}	
}
