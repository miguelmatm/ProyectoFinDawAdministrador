package PModelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloProyectosBorrados {

	
	private Connection conec;
	private ResultSet rs;
	private PreparedStatement ps;
	private String consulta;
	

	public ModeloProyectosBorrados(Connection c) {
		
		conec = c;
		rs = null;
		ps = null;
		
	}
	
	public boolean borrarPorId(int idProyecto) {
		
		boolean registro;

		consulta = "DELETE FROM proyecto.proyectos_borrados WHERE id_proyecto = ?;";

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

}
