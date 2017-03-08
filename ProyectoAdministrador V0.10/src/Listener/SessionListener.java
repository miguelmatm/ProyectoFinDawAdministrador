package Listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import PModelo.ClConeccion;

public class SessionListener implements HttpSessionListener {

	@Override
	public synchronized void sessionCreated(HttpSessionEvent hse) {
		
		HttpSession sesion = hse.getSession();
		sesion.setMaxInactiveInterval(7200);
		
		ServletContext context = sesion.getServletContext();
		Integer numConectados = (Integer) context.getAttribute("usuarios.conectados");
		
		// Crea conexión y almacena el objeto en la sesión del usuario
		ClConeccion conexion = new ClConeccion( context.getInitParameter("dbHostname"),
		context.getInitParameter("dbDatabase"),
		context.getInitParameter("dbUsername"),
		context.getInitParameter("dbPassword"));
		
		
		sesion.setAttribute("conex", conexion.getConnection());
		
		
		numConectados = (numConectados != null) ? numConectados : 0;
		context.setAttribute("usuarios.conectados", ++numConectados);
	}

	@Override
	public synchronized void sessionDestroyed(HttpSessionEvent hse)	{
		
		HttpSession sesion = hse.getSession();
		ServletContext context = sesion.getServletContext();
		
		// Comprobar si se realizó conexión a la base de datos y cerrar la conexión limpiamente para liberar recursos
		Connection conexion = (Connection) sesion.getAttribute("conex");
		
		if (conexion != null)
		{
		try
		{
		conexion.close();
		
		} catch (SQLException x) {
		
		System.err.println("Error SQL - SesionListener:sessionDestroyed:conexion.close()");
		}
		}
		
		int numConectados = (int) context.getAttribute("usuarios.conectados");
		context.setAttribute("usuarios.conectados", --numConectados);
	} 
}
