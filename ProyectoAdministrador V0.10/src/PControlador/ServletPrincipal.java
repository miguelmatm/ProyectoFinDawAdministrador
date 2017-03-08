package PControlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonObject;

import PModelo.ClConeccion;
import PModelo.ModeloCliente;
import PModelo.ModeloGenera;
import PModelo.ModeloIncidencia;
import PModelo.ModeloOperario;
import PModelo.ModeloProyectos;



/**
 * Servlet implementation class ServletPrincipal
 */

//http://localhost:8080/LoginAdmin

@WebServlet("/ServletPrincipal")
public class ServletPrincipal extends HttpServlet {

	private	HttpSession session;
	private Connection conex;
	private ModeloOperario mOperario;


	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session = request.getSession();
		
		try {
			if(session.isNew()){
				
				crearConeccion(session);
							
			} 	
			
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private void crearConeccion(HttpSession session2) {
		
		if (conex == null) {

			conex = (Connection) session2.getAttribute("conex");
				
		}	
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = (String) request.getParameter("tipo");

		if (accion != null) {
			switch (accion) {
				case "logearOperario":
					
					logearOperario(request, response);
					
					break;					
					
				default:
					break;
			}
		}				  	  
   }		
	
	
	

	
	
	private void logearOperario(HttpServletRequest request, HttpServletResponse response) {

		String nombreOperario = request.getParameter("userName");
		String passOperario = request.getParameter("pass");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		boolean resultado = false;
		JsonObject json = new JsonObject();	
		
		conectarModeloOperario(request);
							
		if (!nombreOperario.isEmpty()) {
			if (!passOperario.isEmpty()) {
				mOperario.buscarOperarioXNombre(nombreOperario);
				
				if (mOperario.cantidad() == 1) {
					if (mOperario.getPassword().equals(passOperario)) {
						String tokenGenerado = generarToken(15);
						resultado = mOperario.actualizarTokenPorLogeo(nombreOperario, tokenGenerado);
						
						if (resultado) {
							json.addProperty("token", tokenGenerado);
							json.addProperty("nombre", nombreOperario);									
						}else{
							motivo = "Error al asignar el token";
							json.addProperty("motivo", motivo);
						}									
					}else{
						motivo = "Error password no coinciden";	
						json.addProperty("motivo", motivo);
					}
				}else{
					motivo = "Error en la consulta al buscar al usuario";
					json.addProperty("motivo", motivo);
				}							
			}else{
				motivo = "Password llega al servidor vacia";
				json.addProperty("motivo", motivo);
			}
		}else{
			motivo = "El nombre del operario llega vacio al servidor";
			json.addProperty("motivo", motivo);
		}
		
		json.addProperty("resp", resultado);
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private boolean esAdmin(String userName, String userToken) {

		
		mOperario.buscarOperarioXNombre(userName);
		boolean esAdmin = false;
		
		
		if (mOperario.cantidad() == 1) {
			if (userToken.equals(mOperario.getToken())) {
				if (mOperario.getRango().equals("1")) {
					esAdmin = true;
				}
			}
		}
		
		return esAdmin;
		
	}

	
	private String generarToken(int longitud) {

		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < longitud) {
			char c = (char) r.nextInt(255);
			if ((c >= '0' && c <= 9) || (c >= 'A' && c <= 'Z')) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
	}

	
	
	
	private void conectarModeloOperario(HttpServletRequest request){

		if (mOperario == null) {

			if (session == null) {

				session = request.getSession();
				
			}
			
			if (session.getAttribute("mOperario") == null) {

				if (conex == null) {
					
					conex = (Connection) session.getAttribute("conex");										
					
				}
				
				mOperario = new ModeloOperario(conex);
				session.setAttribute("mOperario", mOperario);	
				
			}else{
				
				mOperario = (ModeloOperario) session.getAttribute("mOperario");
				
			}									
		}		
	}
}
