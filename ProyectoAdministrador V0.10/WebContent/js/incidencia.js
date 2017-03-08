var nombreUser;
var tokenUser;

$(document).ready(function(){
	nombreUser = getCookie("nombreUser");
	tokenUser = getCookie("token");

	$('#usuario').text($('#usuario').text()+nombreUser);
	
	var idOperario = $('#idOperarioNewOperario').val();
	var idIncidencia = $('#idIncidenciaNewOperario').val();
	
	if (!esVacioValor(nombreUser) || !esVacioValor(tokenUser)) {
		$.ajax({
	        url:"Incidencias",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{tipo: "obtenerTodasLasIncidencias", nombreUser: nombreUser, token : tokenUser},
	        success: function(respuesta){             
	        	
	        	$('#incidenciasTitulo').html(respuesta["asignada"]);
	        	$('#incidenciasNoAsignadas').html(respuesta["noAsignada"]);
	        	$('#incidenciasResuelta').html(respuesta["resueltas"]);
	        	$('#incidenciasNeedInfo').html(respuesta["esperando"]);
	        	$('#incidenciasTrabajandose').html(respuesta["trabajandose"]);
	        	$('#incidenciasCerradas').html(respuesta["cerrada"]);
	        	
	     
	        },
	        error: function(jqXHR, status, error) {
	            alert("Error detectado: " + status + "\nExcepcion: " + error);
	        }
	    });
	}
})




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
	

	$(document).on('click', '.fila', function(event) {
		$('div.emphasis').children('p');
		
		var idOperario = $(this).children('.filaIdOperario');	
		$('#windowIdOperario').val(idOperario.text());
		
		var idIncidencia = $(this).children('.filaIdIncidencia');	
		$('#windowIdIncidencia').val(idIncidencia.text());
		
		var idProyecto = $(this).children('.filaIdProyecto');	
		$('#windowIdProyecto').val(idProyecto.text());
		
		var numero = $(this).children('.filaNumero');	
		$('#windowNumero').val(numero.text());
		
		var fecha = $(this).children('.filaFecha');	
		$('#windowFecha').val(fecha.text());
		
		var descripcion = $(this).children('.filaDescripcion');	
		$('#windowDescripcion').val(descripcion.text());
		
		var estado = $(this).children('.filaEstado');		
		
		var cadena = "<select>";
		
		if (estado.text() == "Asignada") {
			cadena += 	"<option value='Asignada' selected>Asignada</option>";
		} else {
			cadena += 	"<option value='Asignada'>Asignada</option>";
		}
		
		if (estado.text() == "Resuelta") {
			cadena += 	"<option value='Resuelta' selected>Resuelta</option>";
		} else {
			cadena += 	"<option value='Resuelta'>Resuelta</option>";
		}
		
		if (estado.text() == "Trabajandose") {
			cadena += 	"<option value='Trabajandose' selected>Trabajandose</option>";
		} else {
			cadena += 	"<option value='Trabajandose'>Trabajandose</option>";
		}
		
		if (estado.text() == "No Asignada") {
			cadena += 	"<option value='No Asignada' selected>No Asignada</option>";
		} else {
			cadena += 	"<option value='No Asignada'>No Asignada</option>";
		}
		
		if (estado.text() == "Esperando") {
			cadena += 	"<option value='Esperando' selected>Esperando</option>";
		} else {
			cadena += 	"<option value='Esperando'>Esperando</option>";
		}
		
		if (estado.text() == "Cerrada") {
			cadena += 	"<option value='Cerrada' selected>Cerrada</option>";
		} else {
			cadena += 	"<option value='Cerrada'>Cerrada</option>";
		}
		cadena += "</select>";
		
		$('#windowEstado').html(cadena);
		
		$('#login-modal-Buscar-Incidencias').modal('toggle');
		                
	})

	$(document).on('click', '#btnCerrar', function(event) {	
		$('#login-modal-Buscar-Incidencias').modal('toggle');
	})
	
	
	
	$(document).on('click', '#btnCerrarPagInci', function(event) {			
		window.location="TrabajarEn";
	})
	
	
	
	$(document).on('click', '#btnActualizarInformacion', function(event) {	
			
		var idIncidencia = $('#windowIdIncidencia').val();		
		var idProyecto = $('#windowIdProyecto').val();			
		var descripcion = $('#windowDescripcion').val();
		
		var estado = $('#windowEstado').children('select');	
		var estadoSeleccionado = $(estado).val();
		
		$.ajax({
	        url:"Incidencias",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{tipo: "actualizarIncidencia", nombreUser: nombreUser, token : tokenUser, idIncidencia : idIncidencia, idProyecto : idProyecto, descripcion : descripcion, estado : estadoSeleccionado},
	        success: function(respuesta){   
	        	alert(respuesta["motivo"]);
//	        	mostrarMensaje("ok", respuesta["motivo"]);    
	        	window.location="Incidencias";
	        },
	        error: function(jqXHR, status, error) {
	            alert("Error detectado: " + status + "\nExcepcion: " + error);
	        }
	    });

		// coger todos los campos y actualizar la base de datos
		// lo ideal es que solo puedan modificar sus incidencias asignadas.
		// en el servidor hay que comprobar que son sus incidencias las que estan modoficando
		// cualquier persona puede modificar cualquier incidencia menos las resueltas , por lo q hay que comprobar que la tabla de la incidencia que se esta pulsando no
		// no sea la de resueltas
	})