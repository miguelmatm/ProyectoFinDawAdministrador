package PControlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import PModelo.ClConeccion;
import PModelo.ModeloGenera;
import PModelo.ModeloIncidencia;
import PModelo.ModeloOperario;

/**
 * Servlet implementation class ServletIncidencias
 */
@WebServlet("/ServletIncidencias")
public class ServletIncidencias extends HttpServlet {

	private	HttpSession session;
	private Connection conex;
	private ModeloIncidencia mIncidencia;
	private ModeloOperario mOperario;
	private ModeloGenera mGenera;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ServletIncidencias() {
//        super();
//        // TODO Auto-generated constructor stub
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/incidencias.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = (String) request.getParameter("tipo");

		if (accion != null) {
			switch (accion) {
			
				case "obtenerTodasLasIncidencias":
					
					obtenerTodasLasIncidencias(request, response);
					
					break;
					
				case "actualizarIncidencia":
					
					actualizarIncidencia(request, response);
					
					break;	
			}
		}	
	}
	
	
private void actualizarIncidencia(HttpServletRequest request, HttpServletResponse response) {
		
		String nombreUser = request.getParameter("nombreUser");
        
        String status = "ko";
        String motivo = "Error motivo no asignado";
        
        JsonObject json = new JsonObject();
		
        conectarModeloOperario(request);      
        
        
		if(mOperario.buscarOperarioXNombre(nombreUser)){
			if (mOperario.cantidad() == 1) {
				conectarModeloIncidencia(request);
				
				String cadenaIdIncidencia = request.getParameter("idIncidencia");
				String cadenaIdProyecto = request.getParameter("idProyecto");
				String descripcion = request.getParameter("descripcion");
				String estado = request.getParameter("estado");
		      
			    if (isNumeric(cadenaIdProyecto) && isNumeric(cadenaIdIncidencia)) {
			    	 
			    	int idIncidencia = Integer.parseInt(cadenaIdIncidencia);
			    	int idProyecto = Integer.parseInt(cadenaIdProyecto);
			    	
			    	if(mIncidencia.actualizarIncidencia(idIncidencia, idProyecto, descripcion, estado)){
			    		status = "ok";
			    		motivo = "Actualizado correctamente";
			    	}else{
			    		motivo = "no se pudo actualizar bien";
			    	}
				
			    }else{
					motivo = "id Proyecto o Id Incidencia no son numeros";
				}
				
				
			} else {
				motivo = "Error de logeo , usuario de mas";
			}
		}else{
			motivo = "Error de logeo";
		}
		
		json.addProperty("motivo", motivo);
		json.addProperty("status", status);
    
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
	private void obtenerTodasLasIncidencias(HttpServletRequest request, HttpServletResponse response) {
		
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        
        String status = "ko";
        String motivo = "Error motivo no asignado";
        
        JsonObject json = new JsonObject();
        
        conectarModeloOperario(request);
        
        if (mOperario.buscarOperarioXNombre(nombreUser)) {
			if (mOperario.getToken().equals(token)) {
				
				conectarModeloGenera(request);
				
				if(mGenera.todaLaTabla()){
					
					
					json.addProperty("asignada", crearJsonIncidencias("estado", "Asignada", mOperario.getIdOperario()));
					json.addProperty("resueltas", crearJsonIncidencias("estado", "Resuelta", -1));
					json.addProperty("trabajandose", crearJsonIncidencias("estado", "Trabajandose", -1));
					json.addProperty("noAsignada", crearJsonIncidencias("estado", "No Asignada", -1));
					json.addProperty("esperando", crearJsonIncidencias("estado", "Esperando", -1));
					json.addProperty("cerrada", crearJsonIncidencias("estado", "Cerrada", -1));
				}else{
					motivo = "error al buscar la tabla genera";
				}
				
				
			}else{
				motivo = "Error logeo";
			}
		}else{
			motivo = "Error logeo";
		}
		
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}


	private String crearJsonIncidencias(String marc , String param, int idOperario) {
		
		String cadena = "";
		
		try {
			ResultSet rs = mGenera.getRs();
			rs.first();
	 		
			String coloreadoDeFila = "impar";
					
			cadena += "<table class='table'>";
			cadena += 	"<thead>";
			cadena += 		"<tr>";								
			cadena += 			"<th>numero</th>";
			cadena += 			"<th>fecha</th>";
			cadena += 			"<th>Id Proyecto</th>";
			cadena +=			"<th>Id Incidencia</th>";
			cadena +=		"</tr>";
			cadena +=	"</thead>";
			cadena +=	"<tbody>";		
	
			
			if (idOperario != -1) {		
				if (rs.getInt("id_operario") == idOperario) {
					if (rs.getString(marc).equals(param)) {
						cadena += 		"<tr class='fila "+coloreadoDeFila+"'>";
						cadena += 			"<td class='filaNumero'>"+rs.getInt("numero")+"</td>";
						cadena += 			"<td class='filaFecha'>"+rs.getDate("fecha")+"</td>";
						cadena += 			"<td class='filaIdProyecto'>"+rs.getInt("id_proyecto")+"</td>";
						cadena += 			"<td class='filaIdIncidencia'>"+rs.getInt("id_incidencia")+"</td>";
						cadena += 			"<td class='oculta filaIdOperario'>"+rs.getString("id_operario")+"</td>";
						cadena += 			"<td class='oculta filaDescripcion'>"+rs.getString("descripcion")+"</td>";
						cadena += 			"<td class='oculta filaEstado'>"+rs.getString("estado")+"</td>";
						cadena += 		"</tr>";
						
						if (coloreadoDeFila.equals("impar")) {
							coloreadoDeFila = "par";
						} else {
							coloreadoDeFila = "impar";
						}
					}
				}	
				
				while (rs.next()) {
					if (rs.getInt("id_operario") == idOperario) {
						if (rs.getString(marc).equals(param)) {
							cadena += 		"<tr class='fila "+coloreadoDeFila+"'>";
							cadena += 			"<td class='filaNumero'>"+rs.getInt("numero")+"</td>";
							cadena += 			"<td class='filaFecha'>"+rs.getDate("fecha")+"</td>";
							cadena += 			"<td class='filaIdProyecto'>"+rs.getInt("id_proyecto")+"</td>";
							cadena += 			"<td class='filaIdIncidencia'>"+rs.getInt("id_incidencia")+"</td>";
							cadena += 			"<td class='oculta filaIdOperario'>"+rs.getString("id_operario")+"</td>";
							cadena += 			"<td class='oculta filaDescripcion'>"+rs.getString("descripcion")+"</td>";
							cadena += 			"<td class='oculta filaEstado'>"+rs.getString("estado")+"</td>";
							cadena += 		"</tr>";
							
							if (coloreadoDeFila.equals("impar")) {
								coloreadoDeFila = "par";
							} else {
								coloreadoDeFila = "impar";
							}
						}
					}					
				}		
			} else {
				if (rs.getString(marc).equals(param)) {
					cadena += 		"<tr class='fila "+coloreadoDeFila+"'>";
					cadena += 			"<td class='filaNumero'>"+rs.getInt("numero")+"</td>";
					cadena += 			"<td class='filaFecha'>"+rs.getDate("fecha")+"</td>";
					cadena += 			"<td class='filaIdProyecto'>"+rs.getInt("id_proyecto")+"</td>";
					cadena += 			"<td class='filaIdIncidencia'>"+rs.getInt("id_incidencia")+"</td>";
					cadena += 			"<td class='oculta filaIdOperario'>"+rs.getString("id_operario")+"</td>";
					cadena += 			"<td class='oculta filaDescripcion'>"+rs.getString("descripcion")+"</td>";
					cadena += 			"<td class='oculta filaEstado'>"+rs.getString("estado")+"</td>";
					cadena += 		"</tr>";
					
					if (coloreadoDeFila.equals("impar")) {
						coloreadoDeFila = "par";
					} else {
						coloreadoDeFila = "impar";
					}
				}

	
				while (rs.next()) {
					if (rs.getString(marc).equals(param)) {

						cadena += 		"<tr class='fila "+coloreadoDeFila+"'>";
						cadena += 			"<td class='filaNumero'>"+rs.getInt("numero")+"</td>";
						cadena += 			"<td class='filaFecha'>"+rs.getDate("fecha")+"</td>";
						cadena += 			"<td class='filaIdProyecto'>"+rs.getInt("id_proyecto")+"</td>";
						cadena += 			"<td class='filaIdIncidencia'>"+rs.getInt("id_incidencia")+"</td>";
						cadena += 			"<td class='oculta filaIdOperario'>"+rs.getString("id_operario")+"</td>";
						cadena += 			"<td class='oculta filaDescripcion'>"+rs.getString("descripcion")+"</td>";
						cadena += 			"<td class='oculta filaEstado'>"+rs.getString("estado")+"</td>";
						cadena += 		"</tr>";
						
						if (coloreadoDeFila.equals("impar")) {
							coloreadoDeFila = "par";
						} else {
							coloreadoDeFila = "impar";
						}
					}
					
				}		
			}
				
			
			cadena +=	"</tbody>";	
			cadena += "</table>";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cadena;
		
	}
	
	
	public boolean isNumeric(String s) {
	    int len = s.length();
	    for (int i = 0; i < len; ++i) {
	        if (!Character.isDigit(s.charAt(i))) {
	            return false;
	        }
	    }
	  
	    return true;
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
	
	
	private void conectarModeloGenera(HttpServletRequest request) {

		if (mGenera == null) {
			
			if (session == null) {
				
				session = request.getSession();
				
			}
			
			if (session.getAttribute("mGenera") == null) {
				
				if (conex == null) {
					
						conex = (Connection) session.getAttribute("conex");
					
				}
				
				mGenera = new ModeloGenera(conex);
				session.setAttribute("mGenera", mGenera);
				
			}else{
				mGenera = (ModeloGenera) session.getAttribute("mGenera");
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

}
