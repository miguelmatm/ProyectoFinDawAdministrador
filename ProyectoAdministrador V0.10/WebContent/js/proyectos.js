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

	if (esVacioValor(nombreUser) || esVacioValor(tokenUser)) {
		mandarAlLogin();
	}
	
	$.ajax({
      url:"Proyectos",
      type: "POST",
      async:true,
      dataType: "json",
      data:{tipo: "obtenerTablaDeProyectos", nombreUsuario : nombreUser, tokenUsuario : tokenUser },
      success: function(respuesta){   
    	  
      	var cadena;
      	
      	if (respuesta['status'] == "ok") {
      		
      		cadena = respuesta['cadena'];
      		
		}else if(respuesta['status'] == "ko"){
			if (respuesta['motivo'] == "No es Administrador.") {
				
				alert("Tienes que ser Administrador");
				
				window.location="TrabajarEn";
				
			}else{
	      		cadena += "<tr>";
	      		cadena += 	"<td></td>";
	      		cadena += 	"<td></td>";
	      		cadena += 	"<td></td>";
	      		cadena += 	"<td></td>";
	      		cadena += 	"<td></td>";
	      		cadena += "</tr>";
			}
		}
      	
      	
      	$('#tbProyectos').html(cadena);
      	
      },
      error: function(jqXHR, status, error) {
          alert("Error detectado: " + status + "\nExcepcion: " + error);
      }
  });	
})



$(document).on('click', '.btnEliminarProyecto', function(event) {
	
	var answer = confirm("Deseas eliminar este Proyecto?")
    if (answer){
    	
    	var tdPadreDelbtn = $(this).parent();
    	var trFilaDelBtn = $(tdPadreDelbtn).parent();
    	
    	var trIdProyecto = $(trFilaDelBtn).find($('.idProyecto'));
    	var idProyecto = $(trIdProyecto).text();
    	
    	
    	$.ajax({
            url:"Proyectos",
            type: "POST",
            async:true,
            dataType: "json",
            data:{tipo: "adminEliminarProyectos", nombreUser: nombreUser, token : tokenUser, idProyecto : idProyecto},
            success: function(respuesta){      
            	
            	if(respuesta['status'] == "ok"){
            		alert("Operario Eliminado");
            		window.location="Proyectos";
            		
            	}else{
            		mostrarMensaje("Status : "+respuesta['status'], "Motivo : "+ respuesta['motivo']);
            	}
            	
            },
            error: function(jqXHR, status, error) {
                alert("Error detectado: " + status + "\nExcepcion: " + error);
            }
        });
    	
    }
	
})



$(document).on('click', '.btnEditarProyecto', function(event) {
    	
    	var tdPadreDelbtn = $(this).parent();
    	var trFilaDelBtn = $(tdPadreDelbtn).parent();
    	
    	var trIdProyecto = $(trFilaDelBtn).find($('.idProyecto'));
    	var trNombreProyecto = $(trFilaDelBtn).find($('.nombreProyecto'));
    	var trIdClienteProyecto = $(trFilaDelBtn).find($('.idClienteProyecto'));
    	
    	var idProyecto = $(trIdProyecto).text();
    	var nombreProyec = $(trNombreProyecto).text();
    	var idCliente = $(trIdClienteProyecto).text();
    	
		$('#windowIdProyecto').val(idProyecto);
		$('#windowNombreProyecto').val(nombreProyec);
		$('#windowIdCliente').val(idCliente);		
	
})



$(document).on('click', '#btnMostrarClientes', function(event) {
	
	//pedir al servidor todos los usuarios y cuando reciba la respuesta si es OK que abra el modal con todos los resultados   
	
	$.ajax({
	      url:"Proyectos",
	      type: "POST",
	      async:true,
	      dataType: "json",
	      data:{tipo: "mostrarClientes", nombreUsuario : nombreUser, tokenUsuario : tokenUser },
	      success: function(respuesta){   
	    	  
	      	var cadena;
	      	
	      	if (respuesta['status'] == "ok") {
	      		
	      		cadena = respuesta['cadena'];
	      		
	      		$('#login-modal-Mostrar-Tabla-Operarioss').modal('toggle');    	      		
	      		
	      		
			}else if(respuesta['status'] == "ko"){
				if (respuesta['motivo'] == "No es Administrador.") {
					
					alert("Tienes que ser Administrador");
					
					window.location="TrabajarEn";
					
				}else{
		      		cadena += "<tr>";
		      		cadena += 	"<td></td>";
		      		cadena += 	"<td></td>";
		      		cadena += 	"<td></td>";
		      		cadena += 	"<td></td>";
		      		cadena += 	"<td></td>";
		      		cadena += "</tr>";
				}
			}
	      	
	      	
	      	$('#tbOperarios').html(cadena);
	      	
	      },
	      error: function(jqXHR, status, error) {
	          alert("Error detectado: " + status + "\nExcepcion: " + error);
	      }
	  });	
	
})




$(document).on('click', '#btnActualizarProyecto', function(event) {
	   
	var idProyecto = $('#windowIdProyecto').val();
	var nombreProyecto = $('#windowNombreProyecto').val();
	var idCliente = $('#windowIdCliente').val();
	
	$.ajax({
	      url:"Proyectos",
	      type: "POST",
	      async:true,
	      dataType: "json",
	      data:{tipo: "actualizarProyecto", nombreUsuario : nombreUser, tokenUsuario : tokenUser, idProyecto : idProyecto,
	    	  nombreProyecto: nombreProyecto, idCliente : idCliente},
	      success: function(respuesta){   
	    	  
	      	var cadena;
	      	
	      	if (respuesta['status'] == "ok") {
	      		
//	      		mostrarMensaje("Actualizado", "Proyecto actualizado correctamente");  
	      		alert("Proyecto actualizado correctamente");
	      		window.location="Proyectos";
	      		
	      		
			}else if(respuesta['status'] == "ko"){
				if (respuesta['motivo'] == "No es Administrador.") {
					
					alert("Tienes que ser Administrador");
					
					window.location="TrabajarEn";
					
				}else{
		      		mostrarMensaje("Error", respuesta['motivo']);
				}
			}
	      	
	      	
	      	$('#tbOperarios').html(cadena);
	      	
	      },
	      error: function(jqXHR, status, error) {
	          alert("Error detectado: " + status + "\nExcepcion: " + error);
	      }
	  });	
	
})


$(document).on('click', '#btnInfoProyectoCerrar', function(event) {
    	
	$('#login-modal-Mostrar-Proyectos').modal('toggle');    
	
})


$(document).on('click', '#btnInfoOperariosCerrar', function(event) {
	
	$('#login-modal-Mostrar-Tabla-Operarioss').modal('toggle');    
	
})


function pasarAmd5(e){    
    return md5($(e).val());
}


