package PControlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import PModelo.ModeloCliente;
import PModelo.ModeloGenera;
import PModelo.ModeloIncidencia;
import PModelo.ModeloOperario;
import PModelo.ModeloProyectos;
import javazoom.upload.MultipartFormDataRequest;

/**
 * Servlet implementation class ServletProyectosAdmin
 */
@WebServlet("/ServletProyectosAdmin")
public class ServletProyectosAdmin extends HttpServlet {

	private ModeloIncidencia mIncidencia;
	private ModeloProyectos mProyectos;
	private ModeloOperario mOperario;
	private ModeloCliente mCliente;
	private ModeloGenera mGenera;
	private	HttpSession session;
	private Connection conex;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/proyectosAdmin.jsp").forward(request, response);		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		if (MultipartFormDataRequest.isMultipartFormData(request)) {

			   String idProyecto = "";
			   String idCliente = "";
			   String nombreProyec = "";
			   String urlImagen = "";
			   
				File file =null;
				int maxFileSize = 5000 * 1024;
				int maxMemSize = 5000 * 1024;
				ServletContext servletContext;
				String filePath;
				String contentType;
				FileItem fi;
				List <FileItem> fileItems;
				DiskFileItemFactory factory;
				String fileName=null;
				
				boolean adjunto = false;
			   
				servletContext = getServletContext();
				System.out.println(servletContext);
				filePath = servletContext.getInitParameter("file-upload");
			   	   
			   
			   //  Mis más sinceros agradecimientos al grupo de desarrollo de HTML5 por solucionar con simpleza un problema recurrente :C :C
			 //**************************************************** Recibe el archivo	   
			 	   // Verify the content type
			 	   contentType = request.getContentType();
			 	   if ((contentType.indexOf("multipart/form-data") >= 0)) {

			 	      factory = new DiskFileItemFactory();
			 	      // maximum size that will be stored in memory
			 	      factory.setSizeThreshold(maxMemSize);
			 	      // Location to save data that is larger than maxMemSize.
			 	      factory.setRepository(new File("."));

			 	      // Create a new file upload handler
			 	      ServletFileUpload upload = new ServletFileUpload(factory);
			 	      // maximum file size to be uploaded.
			 	      upload.setSizeMax( maxFileSize );
			 	      
			 	      try{ 
			 	         // Parse the request to get file items.
			 	         fileItems = upload.parseRequest(request);

			 	         // Process the uploaded file items
			 	         Iterator<FileItem> i = fileItems.iterator();
			 	         
			 	         while ( i.hasNext () ) 
			 	         {
			 	        	 System.out.println("!!!!ITERADOR!!!!");
			 	            fi = (FileItem)i.next();


			 	            if ( !fi.isFormField () ) {
			 	            	if (!fi.getName().isEmpty()) {
			 	            		
			 	            		adjunto = true;
			 	            		
			 	            	// Get the uploaded file parameters
				 		            String fieldName = fi.getFieldName();

				 		            fileName = fi.getName();

				 		            boolean isInMemory = fi.isInMemory();
				 		            long sizeInBytes = fi.getSize();

				 		            // Write the file
				 		            if( fileName.lastIndexOf("/") >= 0 ){

				 		            	urlImagen = "http://ns3034756.ip-91-121-81.eu:8080/imagenestlv/" + fileName.substring( fileName.lastIndexOf("/"));
				 			            file = new File( "/paginaspersonales/mtalavera/imagenestlv/" +
				 			            fileName.substring( fileName.lastIndexOf("/"))) ;
				 			          
				 		            }
				 		            else{
				 		            	
				 		            	urlImagen = "http://ns3034756.ip-91-121-81.eu:8080/imagenestlv/" + fileName.substring( fileName.lastIndexOf("/")+1);
				 			            file = new File( "/paginaspersonales/mtalavera/imagenestlv/" +
				 			            fileName.substring(fileName.lastIndexOf("/")+1)) ;
				 			          
				 		            }           
				 		           
				 		           
				 		            //TODO Cuando solo hay un unico proyecto no lo manda al cliente ?????????????????????????????????
				 		            fi.write( file ) ;
								} 
			 	            }else{
			 	            	
			 	                String fieldname = fi.getFieldName();
			 	                String fieldvalue = fi.getString();

			 	            	switch (fieldname) {
								case "idProyecto":
								    idProyecto = fi.getString();
									   
									break;
									
								case "nombre":
									nombreProyec = fi.getString();
									
									break;
									
								case "idCliente":
									idCliente = fi.getString();
									
									break;
																

								default:
									break;
								}
			 	            	
			 	            }
			 	         }
			 	      }catch(Exception ex) {
			 	         System.out.println(ex);
			 	      }
			 	   } 
			 	   
			 	   
					if (adjunto) {
						registrarProyecto(idProyecto, nombreProyec, idCliente, request, response, file, urlImagen);
					}else{
						registrarProyecto(idProyecto, nombreProyec, idCliente, request, response, null, urlImagen);
					}
				
			 	  
			 	 
			 	   
			}else{
	
			String accion = (String) request.getParameter("tipo");
	
			if (accion != null) {
				switch (accion) {
					case "obtenerTablaDeIncidencias":
						
						tablaDeIncidencias(request, response);
						
						break;
						
					case "buscarIdIncidencia":
						
						buscarIdIncidencia(request, response);
						
						break;
						
					case "registrarIncid":
						
						registrarIncidencia(request, response);
						
						break;
						
					case "buscarIdProyecto":
						
						buscarIdProyecto(request, response);									
						
						break;	
						
					case "listarClientes":
						
						listarClientes(request, response);
						
						break;
						
					case "listarProyectos":
						
						listarProyectos(request, response);
						
						break;
						
					case "AgregarOperarioAIncidencia":
						
						agregarOpeAInci(request, response);
						
						break;
						
					case "buscarOperario":
						
						buscarOperario(request, response);
						
						break;		
						
					case "adminActualiEstadoInci":

						adminActualiEstadoInci(request, response);
						
						break;
						
					case "adminEliminarInci":
						
						adminEliminarInci(request, response);
						
						break;
						
					default:
						break;
				}
			}				 
		}
	}
	
	
	
	private void adminEliminarInci(HttpServletRequest request, HttpServletResponse response) {
		
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        String idIncidencia = request.getParameter("idIncidencia");
 
        String status = "ko";
        String motivo = "Error motivo no encontrado";
        
        JsonObject json = new JsonObject();
        
        conectarModeloOperario(request);
        
        if (esAdmin(nombreUser, token)) {
        	
        	conectarModeloIncidencia(request);
        	
        	if (isNumeric(idIncidencia)) {
        		int idInciden = Integer.parseInt(idIncidencia);
        		
        		conectarModeloGenera(request);
        		
        		if (mGenera.borrarPorId(idInciden)) {
        			
        			if(mIncidencia.borrarIncidencia(idInciden)){
	        			status = "ok";
	        			motivo = "Se Actualizo correctamente";
	        			
	        		}else{
	        			motivo = "No se actualizo correctamente";
	        		}
				}
		        		
			}else{
				motivo = "el id de la incidencia no es numeric";
			}
        	
        	
        }else{
        	motivo = "No es administrador el que intenta actualizar el estado";
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

	private void adminActualiEstadoInci(HttpServletRequest request, HttpServletResponse response) {
		
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        String idIncidencia = request.getParameter("idIncidencia");
        String estado = request.getParameter("estado");
        System.out.println("OJO");
        String status = "ko";
        String motivo = "Error motivo no encontrado";
        
        JsonObject json = new JsonObject();
        
        conectarModeloOperario(request);
        
        if (esAdmin(nombreUser, token)) {
        	
        	conectarModeloIncidencia(request);
        	
        	if (isNumeric(idIncidencia)) {
        		int idInciden = Integer.parseInt(idIncidencia);
        		
        		if(mIncidencia.actualizarEstadoInci(idInciden, estado)){
        			status = "ok";
        			motivo = "Se Actualizo correctamente";
        			
        		}else{
        			motivo = "No se actualizo correctamente";
        		}
			}else{
				motivo = "el id de la incidencia no es numeric";
			}
        	
        	
        }else{
        	motivo = "No es administrador el que intenta actualizar el estado";
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
	
	
	private void agregarOpeAInci(HttpServletRequest request, HttpServletResponse response) {
		
        
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        String cadenaIdOperario = request.getParameter("idOperario");
        String cadenaIdIncidencia = request.getParameter("idIncidencia");

        
        String status = "ko";
        String motivo = "Error motivo no asignado";
		
        JsonObject json = new JsonObject();
        
				
		if (cadenaIdIncidencia != null && cadenaIdOperario != null) {
			if (!cadenaIdIncidencia.isEmpty() && !cadenaIdOperario.isEmpty()) {
				
		        if (isNumeric(cadenaIdIncidencia)) {
					if(isNumeric(cadenaIdOperario)){
						
						conectarModeloOperario(request);
						
						int idIncidencia = Integer.parseInt(cadenaIdIncidencia);
						int idOperario = Integer.parseInt(cadenaIdOperario);
						
						if (esAdmin(nombreUser, token)) {
							
							conectarModeloIncidencia(request);
							
							if(mIncidencia.buscarIncidencia(idIncidencia)){
								if (mIncidencia.cantidad() == 1) {						
																		
									conectarModeloGenera(request);

									if (!mGenera.noExiste(idOperario, idIncidencia, mIncidencia.getIdProyecto())) {
										if(mGenera.actualizarIncidenciaOperario(idOperario, idIncidencia, mIncidencia.getIdProyecto())){
											status = "ok";
											String respuesta = "Se guardo correctamente";
											
											json.addProperty("respuesta", respuesta);
										}else{
											motivo = "No se guardo correctamente";
										}
									}else{
										motivo = "Ya existe una Incidencia con ese proyecto y con ese operario y esta misma incidencia";
									}											
								}else{
									motivo = "Error en la busqueda ";
								}
							}else{
								motivo = "No encontro la incidencia";
							}							
																		
						} else {
							motivo = "Esta operación esta intentando ser llevada acabo por alguien que no es administrador";
						}					
					}else{
						motivo = "El id de los Operarios debe ser numerico.";
					}
				}else{
					motivo = "El id de incidencia debe de ser numerico.";
				}
			} else {
				motivo = "algun campo esta llegando al servidor con el valor vacio";
			}
		} else {
			motivo = "algun valor esta llegando null al servidor.";
		}
        
        if (status.equals("ko")) {
			json.addProperty("motivo", motivo);
		}
        
        json.addProperty("status", status);
        
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}		
	
	
	private void buscarOperario(HttpServletRequest request, HttpServletResponse response) {
		
        String nombreDeOperario = request.getParameter("nombreDeOperario");
        String status = "ko";
        String motivo = "Error motivo no asignado";
		
        JsonObject json = new JsonObject();

		if (nombreDeOperario != null) {
			if (!nombreDeOperario.isEmpty()) {
				
				conectarModeloOperario(request);
				
				if (mOperario.buscarOperarioXNombre(nombreDeOperario)) {
					if (mOperario.cantidad() == 1) {
						status = "ok";
					     json.addProperty("IdOperarioEncontrado", mOperario.getIdOperario());
					     json.addProperty("nombreOperario", mOperario.getNombre());
					     json.addProperty("primerApellido", mOperario.getPrimerApellido());
					     json.addProperty("segundoApellido", mOperario.getSegundoApellido());
					}else{
						motivo = "Error en la consulta";
					}
				}else{
					motivo = "No se encontro el operario";
				}
				
			}else{
				motivo = "El nombre del operario no puede estar vacio";
			}
		}else{
			motivo = "El nombre del operario no puede ser null";
		}
		
        if (status.equals("ko")) {
			json.addProperty("motivo", motivo);
		}
		
		json.addProperty("status", status);
        
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
	}
	
	
	private void registrarProyecto(String idProyec, String nombreProyecto, String idCliente, HttpServletRequest request, HttpServletResponse response, File file, String url) {		
		
		String status = "ko";
		String motivo = "error desconocido";
		
		PrintWriter out;
		
		try {
			
			out = response.getWriter();
			
			if (file != null) {
				if (isNumeric(idCliente) && isNumeric(idProyec)) {
					int idClient = Integer.parseInt(idCliente);
					int idProy = Integer.parseInt(idProyec);		
					
					if (idProyec != null && idCliente != null && nombreProyecto != null) {
						if (!idProyec.isEmpty() && !idCliente.isEmpty() && !nombreProyecto.isEmpty()) {				
			
								conectarModeloCliente(request);
								
								mCliente.existeCliente(idClient);
								
								//TODO hay que mirar si el cliente que estamos intentando registrarle un proyecto, es un cliente que esta validado
								
								
								boolean existeClient = (mCliente.cantidad() == 1);
								
								conectarModeloProyecto(request);
								
								
								if (existeClient) {
									if (mProyectos.nuevoProyecto(idProy, nombreProyecto, idClient, file, url)) {
										status = "ok";
										motivo = "Proyecto guardado correctamente";											
									}else{
										motivo = "No se pudo guardar el proyecto";
									}
								}else{
									motivo = "El cliente que estamos buscando no existe";
									System.out.println("// STARTS LOGGING //");
									System.out.println("hasta aqui Punto dos");
									System.out.println("FILE > " + file);
//									System.out.println(idProy+ " idproyec , "+ nombreProyecto+ " nombreP "+ idClient+ file, url);
//									
									System.out.println("// ENDS LOGGING //");
								}	
								
						}else{
							motivo = "Uno de los valores que recibe el servidor esta llegando vacio";
						}
					}else{
						motivo = "Uno de los valores que recibe el servidor llega como null";
					}
				}else{
					motivo = "El id del cliente no esta llegando como un valor numerico";
				}	
			}else{
				motivo = "Añadele un archivo adjunto";
			}

			 out.println("<html>");
	         out.println("<head>");
	         out.println("<title>JSP File upload</title>");  
	         out.println("</head>");
	         out.println("<body>");
	         out.println("<h1>Status : " + status + "</h1>");
	         out.println("<p>Motivo : " + motivo + "</p>");
	         out.println("<script src='./js/jquery-1.11.3.min.js'></script>");
	         out.println("<script src='./js/bootstrap.min.js'></script>");         
	         out.println("<button id='btnVolverProyectosAdmin' class='btn btn-primary' value='Volver'>Volver</button>");
	         out.println("<script src='./js/trabajarEn.js'></script>");
	         out.println("</body>");
	         out.println("</html>");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	private void listarClientes(HttpServletRequest request, HttpServletResponse response) {
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        
        String status = "ko";
        String motivo = "Error motivo no encontrado";
        
        String cadena = "";
        
        JsonObject json = new JsonObject();
        
        conectarModeloOperario(request);
        
        if (esAdmin(nombreUser, token)) {
        	
        	conectarModeloCliente(request);
		
        	if (mCliente.listarClientes()) {
        		
        		cadena += "<table class='table'>";
        		cadena += crearCabeceraClientes();
				cadena += crearFilalistadoClientes();
				int cantidad = mCliente.cantidad()-1;
				
				for (int i = 0; i < mCliente.cantidad()-1; i++) {
					if(mCliente.siguienteCliente()){
						cadena += crearFilalistadoClientes();
					}
				}
				
				cadena += "</table>";
				
				status = "ok";
				
				json.addProperty("tabla", cadena);
				
			}else{
				motivo = "No hay ningun cliente";
			}
        	
        }else{
        	motivo = "No es Admin";
        }
        
        json.addProperty("status", status);
        
        if (status.equals("ko")) {
			json.addProperty("motivo", motivo);
		}
        
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	
	private void listarProyectos(HttpServletRequest request, HttpServletResponse response) {
		
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        
        String status = "ko";
        String motivo = "Error motivo no encontrado";
        
        String cadena = "";
        
        JsonObject json = new JsonObject();
        
        conectarModeloOperario(request);

        if (esAdmin(nombreUser, token)) {
        	
        	conectarModeloProyecto(request);
		
        	if (mProyectos.listarProyectos()) {
        		
        		cadena += "<table class='table'>";
        		cadena += crearCabeceraProyectos();
				cadena += crearFilalistadoProyectos();
				int cantidad = mProyectos.cantidad()-1;
				
				for (int i = 0; i < cantidad; i++) {
					if(mProyectos.siguienteProyecto()){
						cadena += crearFilalistadoProyectos();
					}
				}
				
				cadena += "</table>";
				
				status = "ok";
				
				json.addProperty("tabla", cadena);
				
			}else{
				motivo = "No hay ningun cliente";
			}
        	
        }else{
        	motivo = "No es Admin";
        }
        
        json.addProperty("status", status);
        
        if (status.equals("ko")) {
			json.addProperty("motivo", motivo);
		}
        
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	private String crearCabeceraClientes(){
		
		String cadena = "<thead><tr>";
		
		cadena += "<th> Id Cliente </th>";
		cadena += "<th> Nick </th>";
		cadena += "<th> Correo </th>";
		cadena += "</tr></thead>";
		
		return cadena;
	}	
	
	
	private String crearCabeceraProyectos(){
		
		String cadena = "<thead><tr>";
		
		cadena += "<th> Id Proyecto </th>";
		cadena += "<th> nombre </th>";
		cadena += "<th> Id Cliente </th>";
		cadena += "</tr></thead>";
		
		return cadena;
	}	

	private String crearFilalistadoClientes() {
		
		String cadena = "<tbody id='tbListClientes'><tr>";
		
		cadena += "<td>" + mCliente.getIdCliente() + "</td>";
		cadena += "<td>" + mCliente.getNick() + "</td>";
		cadena += "<td>" + mCliente.getCorreo() + "</td>";
		cadena += "</tr></tbody>";	
			
		return cadena;
	}
	
	private String crearFilalistadoProyectos() {
		
		String cadena = "<tbody id='tbListClientes'><tr>";
		
		cadena += "<td>" + mProyectos.getIdProyecto() + "</td>";
		cadena += "<td>" + mProyectos.getNombre() + "</td>";
		cadena += "<td>" + mProyectos.getIdCliente() + "</td>";
		cadena += "</tr></tbody>";	
			
		return cadena;
	}
	
	
	private void buscarIdProyecto(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();
		
		String userName = request.getParameter("nombreUser");
		String userToken = request.getParameter("token");	
		
		conectarModeloOperario(request);
		
		if (userName != null && userToken != null && !userName.isEmpty() && !userToken.isEmpty()) {
			boolean esAdministrado = esAdmin(userName, userToken);
			
			System.out.println("userName : "+ userName + " userToken : " + userToken + " esAdmin : "+esAdministrado);

			json.addProperty("admin", esAdministrado);
			
			if (esAdministrado) {
				conectarModeloProyecto(request);
				
				int idProyectoLibre = mProyectos.obtenerSiguienteid();
				
				json.addProperty("idDisponible", idProyectoLibre);
			}
		}
		
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	
	private void registrarIncidencia(HttpServletRequest request, HttpServletResponse response) {
		
		String nombreUser = request.getParameter("nombreUser");
        String token = request.getParameter("token");
        String cadenaIdIncidencia = request.getParameter("idIncidencia");
        String estadoIncidencia = request.getParameter("estadoIncidencia");
        String cadenaIdProyecto = request.getParameter("idProyecto");
        String descripcionIncidencia = request.getParameter("descripcionIncidencia");
        
        String status = "ko";
        String motivo = "Error motivo no asignado";
		
        JsonObject json = new JsonObject();
        
        conectarModeloOperario(request);
        
        if (isNumeric(cadenaIdIncidencia)) {
			if(isNumeric(cadenaIdProyecto)){
				
				int idIncidencia = Integer.parseInt(cadenaIdIncidencia);
				int idProyecto = Integer.parseInt(cadenaIdProyecto);
				
				if (cadenaIdIncidencia != null && estadoIncidencia != null && cadenaIdProyecto != null && descripcionIncidencia != null) {
					if (!cadenaIdIncidencia.isEmpty() && !estadoIncidencia.isEmpty() && !cadenaIdProyecto.isEmpty() && !descripcionIncidencia.isEmpty()) {
						if (esAdmin(nombreUser, token)) {
							conectarModeloProyecto(request);
							
							mProyectos.buscarProyectoXId(idProyecto);

							if (mProyectos.cantidad() == 1) {
								
								if (estadoCorrecto(estadoIncidencia)) {
									conectarModeloIncidencia(request);
									boolean guardardo = mIncidencia.guardarIncidencia(idIncidencia, estadoIncidencia, idProyecto, descripcionIncidencia);
									
									if (guardardo) {
										
										conectarModeloGenera(request);
										
										int idDelCreadorDeLaIncidencia = mOperario.getIdOperario();
										
										if(mGenera.guardarIncidencia(idDelCreadorDeLaIncidencia, idIncidencia, idProyecto)){
											status = "ok";
											json.addProperty("respuesta", "Se guardo la incidencia correctamente");
										}else{
											if(mIncidencia.borrarIncidencia(idIncidencia)){
												motivo = " Error al crear en la tabla genera";
											}else{
												motivo = " Error consulte con el administrador de la web";
											}
											status = "ko";
											
										}

									}else{
										motivo = "Error no se consiguio guardar correctamente";
									}
								}			

							}else{
								motivo = "No se encontro a ningun proyecto con ese id";
							}											
						} else {
							motivo = "Esta operación esta intentando ser llevada acabo por alguien que no es administrador";
						}
					} else {
						motivo = "algun campo esta llegando al servidor con el valor vacio";
					}
				} else {
					motivo = "algun valor esta llegando null al servidor.";
				}
			}else{
				motivo = "El id de los Operarios debe ser numerico.";
			}
		}else{
			motivo = "El id de incidencia debe de ser numerico.";
		}
        
        if (status.equals("ko")) {
			json.addProperty("motivo", motivo);
		}
        
        json.addProperty("status", status);
        
        
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	

	private void tablaDeIncidencias(HttpServletRequest request, HttpServletResponse response) {

		String status = "ko";
		JsonObject jsonObject = new JsonObject();
		
		conectarModeloIncidencia(request);
		
		JsonArray jsonArray = new JsonArray();
		
		mIncidencia.mostrarTodasLasIncidencias();
		
		if (mIncidencia.cantidad() > 0) {
			jsonArray.add(montarJsonIncidencia());

			while (mIncidencia.haySiguiente()) {
				
				jsonArray.add(montarJsonIncidencia());
				
			}
						
			jsonObject.add("proyectos", jsonArray);
			status = "ok";
			
		}
		
		jsonObject.addProperty("status", status);
		
		try {	
			response.setCharacterEncoding("UTF-8");		
			response.getWriter().write(new com.google.gson.Gson().toJson(jsonObject));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	

	
	
	private void buscarIdIncidencia(HttpServletRequest request, HttpServletResponse response) {
		
		JsonObject json = new JsonObject();
		
		String userName = request.getParameter("nombreUser");
		String userToken = request.getParameter("token");	
		
		String status = "ko";
		String motivo = "Error desconocido";
		
		conectarModeloOperario(request);
		
		if (userName != null && userToken != null && !userName.isEmpty() && !userToken.isEmpty()) {
			if(esAdmin(userName, userToken)){				
				conectarModeloIncidencia(request);
					
				status = "ok";
				int idIncidenciaLibre = mIncidencia.obtenerSiguienteid();
					
				json.addProperty("idDisponible", idIncidenciaLibre);

			}else{
				motivo = "No es Administrador";
			}						
		}else{
			motivo = "Uno de los valores que recibe el servidor llega como null o vacio";
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
	


	private JsonObject montarJsonIncidencia(){
		
		JsonObject inci = new JsonObject();
		
		inci.addProperty("idIncidencia", mIncidencia.getIdIncidencia());
		inci.addProperty("descripcion", mIncidencia.getDescripcion());
		inci.addProperty("estado", mIncidencia.getEstado());
		inci.addProperty("idProyecto", mIncidencia.getIdProyecto());
		
		return inci;
		
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
	
	
	
	private boolean estadoCorrecto(String estado){
		
		boolean esValido = false;
		
		if (estado.equals("Asignada") || estado.equals("Resuelta") || estado.equals("Trabajandose") || estado.equals("No Asignada") || estado.equals("Esperando") || estado.equals("Cerrada")) {
			esValido = true;
		}
		 
		return esValido;
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
	
	
	public boolean isNumeric(String s) {
	    int len = s.length();
	    for (int i = 0; i < len; ++i) {
	        if (!Character.isDigit(s.charAt(i))) {
	            return false;
	        }
	    }
	  
	    return true;
	}
}
