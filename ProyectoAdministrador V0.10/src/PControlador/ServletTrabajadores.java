package PControlador;

import java.io.IOException;
import java.sql.Connection;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import PModelo.ClConeccion;
import PModelo.ModeloOperario;

/**
 * Servlet implementation class ServletTrabajadores
 */
@WebServlet("/ServletTrabajadores")
public class ServletTrabajadores extends HttpServlet {
	
	private	HttpSession session;
	private Connection conex;
	private ModeloOperario mOperario;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		
		getServletContext().getRequestDispatcher("/gestionTrabajadores.jsp").forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = (String) request.getParameter("tipo");		
		
		System.out.println(accion);
		
		if (accion != null) {
			switch (accion) {
				case "obtenerTablaDeTrabajadores":
					
					obtenerTablaDeTrabajadores(request, response);					
					
					break;	
					
				case "adminEliminarOperario":
					
					adminEliminarOperario(request, response);
					
					break;
				
				case "adminEditarOperario":
					
					adminEditarOperario(request, response);
					
					break;
					
				case "adminActualizarOperario":
					
					adminActualizarOperario(request, response);
					
					break;
					
				case "adminNewOperario":
					
					adminNewOperario(request, response);
					
					break;
					
				default:
					break;
			}
			
		}
	}
	
	
	private void adminNewOperario(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUser");
		String token = (String) request.getParameter("token");
		
		String status = "ko";
		String motivo = "error desconocido";
				
			
			conectarModeloOperario(request);
			
			if (esAdmin(usuario, token)) {	
				
				String nombre = (String) request.getParameter("nombre");
				String primerApellido = (String) request.getParameter("primerApellido");
				String segundoApellido = (String) request.getParameter("segundoApellido");
				String rangoOperario = (String) request.getParameter("rangoOperario");
				String passWord = (String) request.getParameter("passWord");
				
				String tokenn = getCadenaAlfanumAleatoria(15);
				 
				mOperario.obtenerUltimoId();
				
				int idNuevo = mOperario.getIdOperario()+1;
				
				if(mOperario.guardarOperario(idNuevo, nombre, primerApellido, segundoApellido, rangoOperario, passWord, tokenn)){
					status = "ok";
				}else{
					motivo = "no se pudo registrar el operario";
				}
		      
				
			}else{
				motivo = "No es Administrador.";
			}
			
	
		
		json.addProperty("status", status);
		
		if (status == "ko") {
			json.addProperty("motivo", motivo);
		}
		
		
		try {
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void adminActualizarOperario(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUser");
		String token = (String) request.getParameter("token");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		String cadenaIdOperario = (String) request.getParameter("idOperario");
		
		if (isNumeric(cadenaIdOperario)) {
			
			int idOperario = Integer.parseInt(cadenaIdOperario);
			
			conectarModeloOperario(request);
			
			if (esAdmin(usuario, token)) {			
					
				String primerApellido = (String) request.getParameter("primerApellido");
				String segundoApellido = (String) request.getParameter("segundoApellido");
				String rangoOperario = (String) request.getParameter("rangoOperario");
				
				if(mOperario.actualizarOperario(idOperario, primerApellido, segundoApellido, rangoOperario)){
					status = "ok";
				}else{
					motivo = "el usuario no se actualizo correctamente";
				}
				
			}else{
				motivo = "No es Administrador.";
			}
			
		}else{			
			motivo = "El id Operario no es un numero";
		}
		
		json.addProperty("status", status);
		
		if (status == "ko") {
			json.addProperty("motivo", motivo);
		}
		
		
		try {
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void adminEditarOperario(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUser");
		String token = (String) request.getParameter("token");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		String cadenaIdOperario = (String) request.getParameter("idOperario");
		
		if (isNumeric(cadenaIdOperario)) {
			
			int idOperario = Integer.parseInt(cadenaIdOperario);
			
			conectarModeloOperario(request);
			
			if (esAdmin(usuario, token)) {
				
				
				mOperario.buscarOperarioXId(idOperario);
				
				if (mOperario.cantidad() == 1) {
					
					json.addProperty("idOperario", mOperario.getIdOperario());
					json.addProperty("nombre", mOperario.getNombre());
					json.addProperty("primerApellido", mOperario.getPrimerApellido());
					json.addProperty("segundoApellido", mOperario.getSegundoApellido());
					json.addProperty("rangoOperario", mOperario.getRango());
					
					status = "ok";
					motivo = "todo Correcto";
					
				}else{
					motivo = "consulta devuelve mas de 1 resultado";
				}
				
			}else{
				motivo = "No es Administrador.";
			}
			
		}else{			
			motivo = "El id Operario no es un numero";
		}
		
		json.addProperty("status", status);
		json.addProperty("motivo", motivo);
		
		try {
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void adminEliminarOperario(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUser");
		String token = (String) request.getParameter("token");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		String cadenaIdOperario = (String) request.getParameter("idOperario");
		
		if (isNumeric(cadenaIdOperario)) {
			
			int idOperario = Integer.parseInt(cadenaIdOperario);
			
			conectarModeloOperario(request);
			
			if (esAdmin(usuario, token)) {

				if(mOperario.borrarOperario(idOperario)){

					status = "ok";
					motivo = "Se borro correctamente";
					
				}else{
					motivo = "no se pudo borrar";
				}
				
				
				
			}else{
				motivo = "No es Administrador.";
			}
		}else{			
			motivo = "El id Operario no es un numero";
		}
		
		json.addProperty("status", status);
		json.addProperty("motivo", motivo);
		
		try {
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	

	private void obtenerTablaDeTrabajadores(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUsuario");
		String token = (String) request.getParameter("tokenUsuario");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		conectarModeloOperario(request);
		
		if (esAdmin(usuario, token)) {
			
			String cadena;
			
			if(mOperario.todosLosOperarios()){
				
				int linea = 1;
											
				cadena = "<tr>";
				
				cadena += "<td>" + linea + "</td>";
				cadena += "<td class='idOperario'>" + mOperario.getIdOperario() + "</td>";
				cadena += "<td>" + mOperario.getNombre() + "</td>";
				cadena += "<td>" + mOperario.getPrimerApellido() + "</td>";
				cadena += "<td>" + mOperario.getSegundoApellido() + "</td>";
				cadena += "<td>" + mOperario.getRango() + "</td>";
				cadena += "<td><button class='btnEditarOperario btn btn-primary' value='Editar'>Editar</button><button class='btnEliminar btn btn-primary' value='Eliminar'>Eliminar</button></td>";
				
				cadena += "</tr>";					
				
				while (mOperario.siguienteOperario()) {
					
					cadena += "<tr>";
					
					linea = linea +1 ;
					
					cadena += "<td>" + linea + "</td>";
					cadena += "<td class='idOperario'>" + mOperario.getIdOperario() + "</td>";
					cadena += "<td>" + mOperario.getNombre() + "</td>";
					cadena += "<td>" + mOperario.getPrimerApellido() + "</td>";
					cadena += "<td>" + mOperario.getSegundoApellido() + "</td>";
					cadena += "<td>" + mOperario.getRango() + "</td>";
					cadena += "<td><button class='btnEditarOperario btn btn-primary' value='Editar'>Editar</button><button class='btnEliminar btn btn-primary' value='Eliminar'>Eliminar</button></td>";
					
					cadena += "</tr>";
					
				}
				
				
				status = "ok";
				motivo = "todo OK";
				
				json.addProperty("cadena", cadena);
				
			}else{
				
				motivo = "No existen operarios";
				
			}
			
			
			
		}else{
			motivo = "No es Administrador.";
		}
	
		 
		json.addProperty("status", status);
		json.addProperty("motivo", motivo);
		
		try {
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	private boolean esAdmin(String userName, String userToken) {

		System.out.println("Es ADMIN");
		System.out.println("usuario "+userName);
		System.out.println("token "+userToken);
		
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
	
	
	private boolean isNumeric(String s) {
	    int len = s.length();
	    for (int i = 0; i < len; ++i) {
	        if (!Character.isDigit(s.charAt(i))) {
	            return false;
	        }
	    }
	  
	    return true;
	}
	
	private String getCadenaAlfanumAleatoria(int longitud) {
		
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

}
