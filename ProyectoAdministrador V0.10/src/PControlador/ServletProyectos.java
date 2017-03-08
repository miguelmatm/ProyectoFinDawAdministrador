package PControlador;

import java.io.IOException;
import java.sql.Connection;

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
import PModelo.ModeloProyectosBorrados;

/**
 * Servlet implementation class ServletProyectos
 */
@WebServlet("/ServletProyectos")
public class ServletProyectos extends HttpServlet {

	private	HttpSession session;
	private Connection conex;
	private ModeloGenera mGenera;
	private ModeloCliente mCliente;
	private ModeloOperario mOperario;
	private ModeloProyectos mProyectos;
	private ModeloIncidencia mIncidencia;
	private ModeloProyectosBorrados mProyectosBorrados;

	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		
		getServletContext().getRequestDispatcher("/gestionProyectos.jsp").forward(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = (String) request.getParameter("tipo");		
		
		System.out.println(accion);
		
		if (accion != null) {
			switch (accion) {
				case "obtenerTablaDeProyectos":
					
					obtenerTablaDeProyectos(request, response);					
					
					break;	
					
				case "adminEliminarProyectos":
					
					adminEliminarProyectos(request, response);		
					
					break;
					
					
				case "mostrarClientes":
					
					mostrarClientes(request, response);
					
					break;
					
				case "actualizarProyecto":
					
					actualizarProyecto(request, response);
					
					break;
					
				default:
					break;
			}			
		}
	}

	
	
	private void actualizarProyecto(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUsuario");
		String token = (String) request.getParameter("tokenUsuario");
		String cadenaIdProyecto = (String) request.getParameter("idProyecto");
		String nombreProyecto = (String) request.getParameter("nombreProyecto");
		String cadenaIdCliente = (String) request.getParameter("idCliente");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		conectarModeloOperario(request);
		
		if (esAdmin(usuario, token)) {
			
			String cadena;
			
			
			
			if (isNumeric(cadenaIdProyecto)) {
				if (isNumeric(cadenaIdCliente)) {
					
					int idProyecto = Integer.parseInt(cadenaIdProyecto);
					int idCliente = Integer.parseInt(cadenaIdCliente);
					
					conectarModeloCliente(request);
					
					if (mCliente.existeCliente(idCliente)) {
						
						conectarModeloProyecto(request);
						
						if(mProyectos.actualizarProyecto(idProyecto, nombreProyecto, idCliente)){
							
							status = "ok";
							motivo = "todo OK";
							
						}else{
							
							motivo = "No existen operarios";
							
						}	
					}else{
						motivo = "Ese idCliente no existe";
					}				
					
				}else{
					motivo = "el idCliente debe ser un numero, nada de cadenas";
				}
			}else{
				motivo = "idProyecto erroneo";
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


	private void mostrarClientes(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUsuario");
		String token = (String) request.getParameter("tokenUsuario");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		conectarModeloOperario(request);
		
		if (esAdmin(usuario, token)) {
			
			String cadena;
			
			conectarModeloCliente(request);
			
			if(mCliente.listarClientes()){
				
				int linea = 1;
											
				cadena = "<tr>";
				
				cadena += "<td>" + linea + "</td>";
				cadena += "<td>" + mCliente.getNick() + "</td>";
				cadena += "<td>" + mCliente.getCorreo() + "</td>";
				cadena += "<td>" + mCliente.getIdCliente() + "</td>";
				
				cadena += "</tr>";					
				
				while (mCliente.siguienteCliente()) {
					
					cadena += "<tr>";
					
					linea = linea +1 ;
					
					cadena += "<td>" + linea + "</td>";
					cadena += "<td>" + mCliente.getNick() + "</td>";
					cadena += "<td>" + mCliente.getCorreo() + "</td>";
					cadena += "<td>" + mCliente.getIdCliente() + "</td>";
					
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



	private void adminEliminarProyectos(HttpServletRequest request, HttpServletResponse response) {
		

		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUser");
		String token = (String) request.getParameter("token");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		String cadenaIdProyecto = (String) request.getParameter("idProyecto");
		
		if (isNumeric(cadenaIdProyecto)) {
			
			int idProyecto = Integer.parseInt(cadenaIdProyecto);
			
			conectarModeloOperario(request);
			
			if (esAdmin(usuario, token)) {
				
				conectarModeloProyectosBorrados(request);

				if (mProyectosBorrados.borrarPorId(idProyecto)) {
					
					conectarModeloGenera(request);
					
					if (mGenera.borrarGeneraPorIdProyecto(idProyecto)) {
						
						conectarModeloIncidencia(request);
						
						if (mIncidencia.borrarIncidenciaPorIdProyecto(idProyecto)) {
							
							conectarModeloProyecto(request);
							
							if(mProyectos.borrarPorId(idProyecto)){

								status = "ok";
								motivo = "Se borro correctamente";
								
							}else{
								motivo = "no se pudo borrar de la tabla proyectos";
							}
							
						} else {
							
							motivo = "No se consiguio borrar la incidencia";

						}	
						
					} else {

						motivo = "no se puede borrar de la tabla genera";
						
					}					
					
				}else{
					motivo = "no se pudo borrar el proyecto de la tabla proyectos basura";
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





	private void obtenerTablaDeProyectos(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();	
		
		String usuario = (String) request.getParameter("nombreUsuario");
		String token = (String) request.getParameter("tokenUsuario");
		
		String status = "ko";
		String motivo = "error desconocido";
		
		conectarModeloOperario(request);
		
		if (esAdmin(usuario, token)) {
			
			String cadena;
			
			conectarModeloProyecto(request);
			
			if(mProyectos.listarProyectos()){
				
				int linea = 1;
											
				cadena = "<tr>";
				
				cadena += "<td>" + linea + "</td>";
				cadena += "<td class='idProyecto'>" + mProyectos.getIdProyecto() + "</td>";
				cadena += "<td class='nombreProyecto'>" + mProyectos.getNombre() + "</td>";
				cadena += "<td class='idClienteProyecto'>" + mProyectos.getIdCliente() + "</td>";
				cadena += "<td><button class='btnEditarProyecto btn btn-primary' value='Editar' data-toggle='modal' data-target='#login-modal-Mostrar-Proyectos'>Editar</button><button class='btnEliminarProyecto btn btn-primary' value='Eliminar'>Eliminar</button></td>";
				
				cadena += "</tr>";					
				
				while (mProyectos.siguienteProyecto()) {
					
					cadena += "<tr>";
					
					linea = linea +1 ;
					
					cadena += "<td>" + linea + "</td>";
					cadena += "<td class='idProyecto'>" + mProyectos.getIdProyecto() + "</td>";
					cadena += "<td class='nombreProyecto'>" + mProyectos.getNombre() + "</td>";
					cadena += "<td class='idClienteProyecto'>" + mProyectos.getIdCliente() + "</td>";
					cadena += "<td><button class='btnEditarProyecto btn btn-primary' value='Editar' data-toggle='modal' data-target='#login-modal-Mostrar-Proyectos'>Editar</button><button class='btnEliminarProyecto btn btn-primary' value='Eliminar'>Eliminar</button></td>";
					
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
	
	
	private void conectarModeloProyecto(HttpServletRequest request){

		if (mProyectos == null) {
			
			if (session == null) {
				
				session = request.getSession();
				
			}
			
			if (session.getAttribute("mProyectos") == null) {
				
				if (conex == null) {
					
						conex = (Connection) session.getAttribute("conex");
						
				}
			
				mProyectos = new ModeloProyectos(conex);
				session.setAttribute("mProyectos", mProyectos);		
				
			}else{
				
				mProyectos = (ModeloProyectos) session.getAttribute("mProyectos");
				
			}					
				
		}		
	}
	
	private void conectarModeloProyectosBorrados(HttpServletRequest request) {
		
		if (mProyectosBorrados == null) {
			
			if (session == null) {
				
				session = request.getSession();
			
			}
			
			if (session.getAttribute("mProyectosBorrados") == null) {
				
				if (conex == null) {

						conex = (Connection) session.getAttribute("conex");
							
				}
			
				mProyectosBorrados = new ModeloProyectosBorrados(conex);
				session.setAttribute("mProyectosBorrados", mProyectosBorrados);	
				
			}else{
				
				mProyectosBorrados = (ModeloProyectosBorrados) session.getAttribute("mProyectosBorrados");
				
			}						
		}		
	}
	
	
	private void conectarModeloCliente(HttpServletRequest request) {
		
		if (mCliente == null) {
			
			if (session == null) {
				
				session = request.getSession();
				
			}
			
			if (session.getAttribute("mCliente") == null) {
				
				if (conex == null) {
										
					conex = (Connection) session.getAttribute("conex");
				}
				
				mCliente = new ModeloCliente(conex);
				session.setAttribute("mCliente", mCliente);	
				
			}else{
				
				mCliente = (ModeloCliente) session.getAttribute("mCliente");
				
			}							
		}	
	}
	
	
	private void conectarModeloIncidencia(HttpServletRequest request){

		if (mIncidencia == null) {
			
			if (session == null) {
				
				session = request.getSession();
				
			}
			
			if (session.getAttribute("mIncidencia") == null) {
				
				if (conex == null) {
					
						conex = (Connection) session.getAttribute("conex");
	
				}
				
				mIncidencia = new ModeloIncidencia(conex);
				session.setAttribute("mIncidencia", mIncidencia);	
				
			}else{
				
				mIncidencia = (ModeloIncidencia) session.getAttribute("mIncidencia");
				
			}					
		}		
	}
	
	private void conectarModeloGenera(HttpServletRequest request) {

		if (mGenera == null) {
			
			if (session == null) {
				
				session = request.getSession();

			}
			
			if (session.getAttribute("mGenera") == null) {
				
				if (conex == null) {
					
					if (session.getAttribute("conex") == null) {
						
						conex = (Connection) session.getAttribute("conex");
						
					}	
				}
				
				mGenera = new ModeloGenera(conex);
				session.setAttribute("mGenera", mGenera);	
				
			}else{
				
				mGenera = (ModeloGenera) session.getAttribute("mGenera");
				
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

}
