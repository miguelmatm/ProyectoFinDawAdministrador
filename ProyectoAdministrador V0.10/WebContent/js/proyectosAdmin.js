

$(document).ready(function(){
	
	
	$.ajax({
        url:"ProyectosAdmin",
        type: "POST",
        async:true,
        dataType: "json",
        data:{tipo: "obtenerTablaDeIncidencias"},
        success: function(respuesta){                 
        	var cadena;
        	
        	if (respuesta['status'] == "ok") {
        		for (var int = 0; int < respuesta['proyectos'].length; int++) {
            		cadena += "<tr>";
            		cadena += 	"<td>"+int+"</td>";
            		cadena += 	"<td>" +respuesta['proyectos'][int]["idProyecto"]+"</td>";
            		cadena += 	"<td class='idIncidencias'>" +respuesta['proyectos'][int]["idIncidencia"]+"</td>";
            		cadena += 	"<td>" +respuesta['proyectos'][int]["descripcion"]+"</td>";
            		cadena +=   "<td class='classSelectEstado'>" +crearSelect(respuesta['proyectos'][int]["estado"])+"</td>";
            		cadena += 	"<td><button class='btn btn-primary btnActualizarEstadoIncid' value='Actualizar'>Actualizar</button><button class='btn btn-primary btnEliminarIncid' value='Eliminar'>Eliminar</button></td>";
            		cadena += "</tr>";
            	}
			}else if(respuesta['status'] == "ko"){
        		cadena += "<tr>";
        		cadena += 	"<td></td>";
        		cadena += 	"<td></td>";
        		cadena += 	"<td></td>";
        		cadena += 	"<td></td>";
        		cadena += 	"<td></td>";
        		cadena += "</tr>";
			}
        	
        	
        	$('#tbIncidencia').html(cadena);
        	
        },
        error: function(jqXHR, status, error) {
            alert("Error detectado: " + status + "\nExcepcion: " + error);
        }
    });
})

