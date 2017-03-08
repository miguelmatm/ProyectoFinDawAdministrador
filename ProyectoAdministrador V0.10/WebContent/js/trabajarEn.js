var nombreUser;
var tokenUser;

$(document).ready(function(){
	
	
    $.ajax({
        url: 'includes/modal.html',
        type: 'GET',
        async: false,
        success: function(respuesta){
            $("#modall").html(respuesta);
        }
    });
	
	nombreUser = getCookie("nombreUser");
	tokenUser = getCookie("token");

	$('#usuario').text($('#usuario').text()+nombreUser);

	if (esVacioValor(nombreUser) || esVacioValor(tokenUser)) {
		mandarAlLogin();
	}
	
//	Esto deberia estar solo en un js que lo use unicamente la pagina que lo necesita,
//	$.ajax({
//        url:"ProyectosAdmin",
//        type: "POST",
//        async:true,
//        dataType: "json",
//        data:{tipo: "obtenerTablaDeIncidencias"},
//        success: function(respuesta){                 
//        	var cadena;
//        	
//        	if (respuesta['status'] == "ok") {
//        		for (var int = 0; int < respuesta['proyectos'].length; int++) {
//            		cadena += "<tr>";
//            		cadena += 	"<td>"+int+"</td>";
//            		cadena += 	"<td>" +respuesta['proyectos'][int]["idProyecto"]+"</td>";
//            		cadena += 	"<td class='idIncidencias'>" +respuesta['proyectos'][int]["idIncidencia"]+"</td>";
//            		cadena += 	"<td>" +respuesta['proyectos'][int]["descripcion"]+"</td>";
//            		cadena +=   "<td class='classSelectEstado'>" +crearSelect(respuesta['proyectos'][int]["estado"])+"</td>";
//            		cadena += 	"<td><button class='btn btn-primary btnActualizarEstadoIncid' value='Actualizar'>Actualizar</button><button class='btn btn-primary btnEliminarIncid' value='Eliminar'>Eliminar</button></td>";
//            		cadena += "</tr>";
//            	}
//			}else if(respuesta['status'] == "ko"){
//        		cadena += "<tr>";
//        		cadena += 	"<td></td>";
//        		cadena += 	"<td></td>";
//        		cadena += 	"<td></td>";
//        		cadena += 	"<td></td>";
//        		cadena += 	"<td></td>";
//        		cadena += "</tr>";
//			}//TODO AQUI
//        	
//        	
//        	$('#tbIncidencia').html(cadena);
//        	
//        },
//        error: function(jqXHR, status, error) {
//            alert("Error detectado: " + status + "\nExcepcion: " + error);
//        }
//    });
})



$(document).on('click', '#listarClientes', function(event) {
	$.ajax({
        url:"ProyectosAdmin",
        type: "POST",
        async:true,
        dataType: "json",
        data:{tipo: "listarClientes", nombreUser: nombreUser, token : tokenUser},
        success: function(respuesta){                 
        	if (respuesta["status"] == "ok") {
				
        		$('#tablaListado').html(respuesta["tabla"]);
        		
			}else if(respuesta["status"] == "ko"){
				mostrarMensaje("Error", respuesta["motivo"]);
			}
        	
        },
        error: function(jqXHR, status, error) {
            alert("Error detectado: " + status + "\nExcepcion: " + error);
        }
    });
})




$(document).on('click', '.btnEliminarIncid', function(event) {
	
	var answer = confirm("Deseas eliminar esta incidencia?")
    if (answer){
    	
    	var tdPadreDelbtn = $(this).parent();
    	var trFilaDelBtn = $(tdPadreDelbtn).parent();
    	
    	var trIdIncidencias = $(trFilaDelBtn).find($('.idIncidencias'));
    	
    	
    	var tdSelect = $(trFilaDelBtn).find($('.classSelectEstado'));
    	var select = $(tdSelect).find('select');
    	
    	var idIncidencia = $(trIdIncidencias).text();
    	
    	
    	$.ajax({
            url:"ProyectosAdmin",
            type: "POST",
            async:true,
            dataType: "json",
            data:{tipo: "adminEliminarInci", nombreUser: nombreUser, token : tokenUser, idIncidencia : idIncidencia},
            success: function(respuesta){                 
//            	mostrarMensaje("Status : "+respuesta['status'], "Motivo : "+ respuesta['motivo']);
            	alert(respuesta['motivo']);
            	if (respuesta['status'] == "ok") {
            		window.location="ProyectosAdmin";
				}
            },
            error: function(jqXHR, status, error) {
                alert("Error detectado: " + status + "\nExcepcion: " + error);
            }
        });
    	
    }
	
})





$(document).on('click', '#listarProyectos', function(event) {
	$.ajax({
        url:"ProyectosAdmin",
        type: "POST",
        async:true,
        dataType: "json",
        data:{tipo: "listarProyectos", nombreUser: nombreUser, token : tokenUser},
        success: function(respuesta){                 
        	if (respuesta["status"] == "ok") {
				
        		$('#tablaListadoProyectos').html(respuesta["tabla"]);
        		
			}else if(respuesta["status"] == "ko"){
				mostrarMensaje("Error", respuesta["motivo"]);
			}
        	
        },
        error: function(jqXHR, status, error) {
            alert("Error detectado: " + status + "\nExcepcion: " + error);
        }
    });
})

$(document).on('click', '#proyectosTituloTrabajarEn', function(event) {
	window.location="ProyectosAdmin";
})


$(document).on('click', '#incidenciasTituloTrabajarEn', function(event) {
	window.location="Incidencias";
})

$(document).on('click', '#gestionarTrabajadores', function(event) {
	window.location="Trabajadores";
})

$(document).on('click', '#gestionarProyectos', function(event) {
	window.location="Proyectos";
})

$(document).on('click', '#CerrarSesion', function(event) {
	mandarAlLogin();
})

$(document).on('click', '#btnVolverProyectosAdmin', function(event) {
	window.location="ProyectosAdmin";
})




$(document).on('click', '.btnActualizarEstadoIncid', function(event) {

	var tdPadreDelbtn = $(this).parent();
	var trFilaDelBtn = $(tdPadreDelbtn).parent();
	
	var trIdIncidencias = $(trFilaDelBtn).find($('.idIncidencias'));
	
	
	var tdSelect = $(trFilaDelBtn).find($('.classSelectEstado'));
	var select = $(tdSelect).find('select');
	
	var selectVal = $(select).val();
	var idIncidencia = $(trIdIncidencias).text();
	
	
	$.ajax({
        url:"ProyectosAdmin",
        type: "POST",
        async:true,
        dataType: "json",
        data:{tipo: "adminActualiEstadoInci", nombreUser: nombreUser, token : tokenUser, idIncidencia : idIncidencia, estado : selectVal},
        success: function(respuesta){                 
        	mostrarMensaje("Status : "+respuesta['status'], "Motivo : "+ respuesta['motivo']);
        },
        error: function(jqXHR, status, error) {
            alert("Error detectado: " + status + "\nExcepcion: " + error);
        }
    });
	
})


$(document).on('click', '#botonBuscarIdProyecto', function(event) {
	
	if (!esVacioValor(nombreUser) || !esVacioValor(tokenUser)) {
		$.ajax({
	        url:"ProyectosAdmin",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{tipo: "buscarIdProyecto", nombreUser: nombreUser, token : tokenUser},
	        success: function(respuesta){                 
	            if (respuesta["admin"]) {
					$('#idProyecto').val(respuesta["idDisponible"]);
				}else{

					mostrarMensaje("No es Administrador", "No es una cuenta administradora uo Error");
				}
	        },
	        error: function(jqXHR, status, error) {
	            alert("Error detectado: " + status + "\nExcepcion: " + error);
	        }
	    });
	}
})


$(document).on('click', '#btnBuscarOperario', function(event) {
	
	var nombreDeOperario = $('#buscarOperarioo').val();
	
	$.ajax({
        url:"ProyectosAdmin",
        type: "POST",
        async:true,
        dataType: "json",
        data:{tipo: "buscarOperario", nombreDeOperario: nombreDeOperario},
        success: function(respuesta){                 
        	 if (respuesta["status"] == "ok") {			
        		 $('#idOperarioEncont').val(respuesta["IdOperarioEncontrado"]);
        		 $('#nombreOpera').val(respuesta["nombreOperario"]);
        		 $('#primerApell').val(respuesta["primerApellido"]);
        		 $('#segunApell').val(respuesta["segundoApellido"]);
                
					$('#login-modal-Operario-encontrado').modal('toggle');
				}else if(respuesta["status"] == "ko"){
					$('#login-modal-Buscar-Incidencias').modal('toggle');
					mostrarMensaje("ERROR ", respuesta["motivo"]);
				
				}
        },
        error: function(jqXHR, status, error) {
            alert("Error detectado: " + status + "\nExcepcion: " + error);
        }
    });
})



$(document).on('click', '#btnCerrarOperarioEncontrado', function(event) {
	
	$('#login-modal-Operario-encontrado').modal('toggle');
})


$(document).on('click', '#botonVolver', function(event) {
	
	window.location="TrabajarEn";
})

$(document).on('click', '#btnAgregarOperarioAIncidencia', function(event) {
	
	var idOperario = $('#idOperarioNewOperario').val();
	var idIncidencia = $('#idIncidenciaNewOperario').val();
	
	if (!esVacioValor(nombreUser) || !esVacioValor(tokenUser)) {
		$.ajax({
	        url:"LoginAdmin",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{tipo: "AgregarOperarioAIncidencia", nombreUser: nombreUser, token : tokenUser, idOperario : idOperario, idIncidencia : idIncidencia},
	        success: function(respuesta){                 
	        	if (respuesta["status"] == "ok") {
	        		mostrarMensaje("Status : " + respuesta["status"], respuesta["respuesta"]);
					$('#login-modal-Asignar-Operario').modal('toggle');
				}else if(respuesta["status"] == "ko"){
					mostrarMensaje("Status : " + respuesta["status"], respuesta["motivo"]);
					$('#login-modal-Asignar-Operario').modal('toggle');
				}
	        },
	        error: function(jqXHR, status, error) {
	            alert("Error detectado: " + status + "\nExcepcion: " + error);
	        }
	    });
	}
})




$(document).on('click', '#botonBuscarIdIncidencia', function(event) {
	
	if (!esVacioValor(nombreUser) || !esVacioValor(tokenUser)) {
		$.ajax({
	        url:"ProyectosAdmin",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{tipo: "buscarIdIncidencia", nombreUser: nombreUser, token : tokenUser},
	        success: function(respuesta){                 
	            if (respuesta["status"] == "ok") {
					if(respuesta["idDisponible"] != null){
						$('#idIncidencia').val(respuesta["idDisponible"]);
					}
				}else if(respuesta["status"] == "ko"){
					// ESTO CAMBIANDO LSO ALERT POR EL MODAL DE AVISo;
					mostrarMensaje("Error", respuesta["motivo"]);
				}
	        },
	        error: function(jqXHR, status, error) {
	            alert("Error detectado: " + status + "\nExcepcion: " + error);
	        }
	    });
	}
})



$(document).on('click', '#btnRegistrarProy', function(event) {
	
	if (!esVacioValor(nombreUser) || !esVacioValor(tokenUser)) {

		var idProyecto = $('#idProyecto').val();
		var nombreProyecto = $('#nombreProy').val();
		var idCliente = $('#idClienteProy').val();
		
		
		if (!esVacioValor(idProyecto)) {
			if (!esVacioValor(nombreProyecto)) {
				if (!esVacioValor(idCliente)) {
					
					$.ajax({
				        url:"LoginAdmin",
				        type: "POST",
				        async:true,
				        dataType: "json",
				        data:{tipo: "registrarProy", nombreUser: nombreUser, token : tokenUser, idProy : idProyecto, nombreProyec : nombreProyecto, idCliente : idCliente},
				        success: function(respuesta){                 
				        	if (respuesta["status"] == "ok") {
				        		$('#idProyecto').val("");
				        		$('#nombreProy').val("");
				        		$('#idClienteProy').val("");
				        		
				        		mostrarMensaje("Ok", respuesta["respuesta"]);
							
							}else if (respuesta["status"] == "ko") {
								mostrarMensaje("ko", respuesta["motivo"]);
							}
				        },
				        error: function(jqXHR, status, error) {
				            alert("Error detectado: " + status + "\nExcepcion: " + error);
				        }
				    });
					
				} else {
					mostrarMensaje("Error", "idCliente vacio");
				}
			} else {
				mostrarMensaje("Error", "nombreProyecto Vacio");
			}
		}else{
			mostrarMensaje("Error", "idProyecto Vacio");
		}		
	}
})




$(document).on('click', '#btnRegistrarIncidencia', function(event) {
	
	var idIncidencia = $('#idIncidencia').val();
	var estadoIncidencia = $('#estadoInci').val();
	var idProyecto = $('#idProyectoInc').val();
	var descripcionIncidencia = $('#incidenciaDescripcion').val();
	
	if (!esVacioValor(idIncidencia)) {
		if (!esVacioValor(estadoIncidencia)) {
			if (!esVacioValor(idProyecto)) {
				if (!esVacioValor(descripcionIncidencia)) {
					if (!esVacioValor(nombreUser) || !esVacioValor(tokenUser)) {
						$.ajax({
					        url:"ProyectosAdmin",
					        type: "POST",
					        async:true,
					        dataType: "json",
					        data:{tipo: "registrarIncid", nombreUser: nombreUser, token : tokenUser, idIncidencia : idIncidencia, estadoIncidencia : estadoIncidencia, idProyecto : idProyecto, descripcionIncidencia : descripcionIncidencia},
					        success: function(respuesta){                 
					        	if (respuesta["status"]== "ok") {
					        		$('#idIncidencia').val("");
					        		$('#estadoInci').val("");
					        		$('#idProyectoInc').val("");
					        		$('#incidenciaDescripcion').val("");
					        		alert(respuesta["respuesta"]);
									window.location="ProyectosAdmin";
								}else if (respuesta["status"] == "ko") {
									mostrarMensaje("Ok", respuesta["motivo"]);
								}
					        },
					        error: function(jqXHR, status, error) {
					            alert("Error detectado: " + status + "\nExcepcion: " + error);
					        }
					    });
					}else{
						mostrarMensaje("Error", "error en el logeado");
					}
				} else {
					mostrarMensaje("Descripcion Error", "La descripción de la incidencia no puede estar vacia");
				}
			} else {
				mostrarMensaje("Id Operario Error", "El id operario no puede estar vacio")
			}
		} else {
			mostrarMensaje("Estado Error", "El estado no puede estar vacio");
		}
	} else {
		mostrarMensaje("Id Incidencia Error", "El idIncidencia no puede ser vacio asigne uno.")
	}
})




function mandarAlLogin(){
	setCookie("token", null);
	setCookie("nombreUser", null);
	window.location="LoginAdmin";
}



function esVacio(e){
    if (e.val() == ""  || e.val() == " " || e.val() == null) {
        return true;
    }else{
        return false;
    }
}




function esVacioValor(e){
    if (e == ""  || e == " " || e == null) {
        return true;
    }else{
        return false;
    }
}



function pasarAmd5(e){    
    return md5($(e).val());
}



function getCookie(nombre) {
    var name = nombre + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}




 function setCookie(nombre, valor) {
    document.cookie = nombre +"="+ valor +";";
}
// Motivación Deportiva NO PAIN NO GAIN  BORRAR
 
 function crearSelect(select){
	 
	 var cadena = "";
	 	 cadena += "<select>";
	 switch (select) {
		case "Asignada":			
			 
			 cadena += "<option value='Asignada' selected>Asignada</option>";
			 cadena += "<option value='Resuelta'>Resuelta</option>";
			 cadena += "<option value='Trabajandose'>Trabajandose</option>";
			 cadena += "<option value='No Asignada'>No Asignada</option>";
			 cadena += "<option value='Esperando'>Esperando</option>";
			 cadena += "<option value='Cerrada'>Cerrada</option>";		 
			
			break;
			
		case "Resuelta":

			 cadena += "<option value='Asignada'>Asignada</option>";
			 cadena += "<option value='Resuelta' selected>Resuelta</option>";
			 cadena += "<option value='Trabajandose'>Trabajandose</option>";
			 cadena += "<option value='No Asignada'>No Asignada</option>";
			 cadena += "<option value='Esperando'>Esperando</option>";
			 cadena += "<option value='Cerrada'>Cerrada</option>";	
			
			break;
			
		case "Trabajandose":
			
			 cadena += "<option value='Asignada'>Asignada</option>";
			 cadena += "<option value='Resuelta'>Resuelta</option>";
			 cadena += "<option value='Trabajandose' selected>Trabajandose</option>";
			 cadena += "<option value='No Asignada'>No Asignada</option>";
			 cadena += "<option value='Esperando'>Esperando</option>";
			 cadena += "<option value='Cerrada'>Cerrada</option>";		
			
			break;
			
		case "No Asignada":
			
			 cadena += "<option value='Asignada'>Asignada</option>";
			 cadena += "<option value='Resuelta'>Resuelta</option>";
			 cadena += "<option value='Trabajandose'>Trabajandose</option>";
			 cadena += "<option value='No Asignada' selected>No Asignada</option>";
			 cadena += "<option value='Esperando'>Esperando</option>";
			 cadena += "<option value='Cerrada'>Cerrada</option>";		
			
			break;
			
		case "Esperando":
			
			 cadena += "<option value='Asignada'>Asignada</option>";
			 cadena += "<option value='Resuelta'>Resuelta</option>";
			 cadena += "<option value='Trabajandose'>Trabajandose</option>";
			 cadena += "<option value='No Asignada'>No Asignada</option>";
			 cadena += "<option value='Esperando' selected>Esperando</option>";
			 cadena += "<option value='Cerrada'>Cerrada</option>";			
			
			break;
			
		case "Cerrada":
			
			 cadena += "<option value='Asignada'>Asignada</option>";
			 cadena += "<option value='Resuelta'>Resuelta</option>";
			 cadena += "<option value='Trabajandose'>Trabajandose</option>";
			 cadena += "<option value='No Asignada'>No Asignada</option>";
			 cadena += "<option value='Esperando'>Esperando</option>";
			 cadena += "<option value='Cerrada' selected>Cerrada</option>";	
	
			break;
	
		default:
			break;
	}
	 
	 cadena += "</select>";	
	 
	 return cadena;
 }
 
 
 function mostrarMensaje(titulo, cuerpo){
	    var cadena = "<i class='glyphicon glyphicon-thumbs-up'></i>"+titulo;
	    $('#modalTitulo').html(cadena);
	    var cadenad = "<p>"+cuerpo+"</p>";
	    $('#modalTexto').html(cadenad);
	    $('#success').modal();
	}