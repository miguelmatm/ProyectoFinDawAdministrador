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
      url:"Trabajadores",
      type: "POST",
      async:true,
      dataType: "json",
      data:{tipo: "obtenerTablaDeTrabajadores", nombreUsuario : nombreUser, tokenUsuario : tokenUser },
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
      	
      	
      	$('#tbOperarios').html(cadena);
      	
      },
      error: function(jqXHR, status, error) {
          alert("Error detectado: " + status + "\nExcepcion: " + error);
      }
  });	
})



$(document).on('click', '.btnEliminar', function(event) {
	
	var answer = confirm("Deseas eliminar este Operario?")
    if (answer){
    	
    	var tdPadreDelbtn = $(this).parent();
    	var trFilaDelBtn = $(tdPadreDelbtn).parent();
    	
    	var trIdOperario = $(trFilaDelBtn).find($('.idOperario'));
    	var idOperario = $(trIdOperario).text();
    	
    	
    	$.ajax({
            url:"Trabajadores",
            type: "POST",
            async:true,
            dataType: "json",
            data:{tipo: "adminEliminarOperario", nombreUser: nombreUser, token : tokenUser, idOperario : idOperario},
            success: function(respuesta){      
            	
            	if(respuesta['status'] == "ok"){
            		alert("Operario Eliminado");
            		window.location="Trabajadores";
            		
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



$(document).on('click', '.btnEditarOperario', function(event) {
    	
    	var tdPadreDelbtn = $(this).parent();
    	var trFilaDelBtn = $(tdPadreDelbtn).parent();
    	
    	var trIdOperario = $(trFilaDelBtn).find($('.idOperario'));
    	var idOperario = $(trIdOperario).text();
    	
    	
    	$.ajax({
            url:"Trabajadores",
            type: "POST",
            async:true,
            dataType: "json",
            data:{tipo: "adminEditarOperario", nombreUser: nombreUser, token : tokenUser, idOperario : idOperario},
            success: function(respuesta){      
            	
            	if(respuesta['status'] == "ok"){					
					
	        		 $('#windowIdOperario').val(respuesta["idOperario"]);
	        		 $('#windowNombreOperario').val(respuesta["nombre"]);
	        		 $('#windowPrimerApellidoOperario').val(respuesta["primerApellido"]);
	        		 $('#windowSegundoApellido').val(respuesta["segundoApellido"]);
	        		 $('#windowRangoOperario').val(respuesta["rangoOperario"]);
	                
					$('#login-modal-Mostrar-Operario').modal('toggle');
            		
            	}else{
            		mostrarMensaje("Status : "+respuesta['status'], "Motivo : "+ respuesta['motivo']);
            	}
            	
            },
            error: function(jqXHR, status, error) {
                alert("Error detectado: " + status + "\nExcepcion: " + error);
            }
        });    
	
})


$(document).on('click', '#btnInfoOperarioCerrar', function(event) {
    	
	$('#login-modal-Mostrar-Operario').modal('toggle');    
	
})




$(document).on('click', '#btnActualizarOperario', function(event) {
	
	var idOperario = $('#windowIdOperario').val();
	var primerApellido = $('#windowPrimerApellidoOperario').val();
	var segundoApellido = $('#windowSegundoApellido').val();
	var rangoOperario = $('#windowRangoOperario').val();
		
	
	$.ajax({
        url:"Trabajadores",
        type: "POST",
        async:true,
        dataType: "json",
        data:{tipo: "adminActualizarOperario", nombreUser: nombreUser, token : tokenUser, idOperario : idOperario, primerApellido : primerApellido, segundoApellido: segundoApellido, rangoOperario : rangoOperario},
        success: function(respuesta){      
        	
        	if(respuesta['status'] == "ok"){					
        		
        		$('#login-modal-Mostrar-Operario').modal('toggle');
        		
        		alert("Actualizacion completada");
        		
        		window.location="Trabajadores";
        		
        	}else{
        		$('#login-modal-Mostrar-Operario').modal('toggle');
        		mostrarMensaje("Status : "+respuesta['status'], "Motivo : "+ respuesta['motivo']);
        	}
        	
        },
        error: function(jqXHR, status, error) {
            alert("Error detectado: " + status + "\nExcepcion: " + error);
        }
    });       
	
})



$(document).on('click', '#btnAgregarNewOperario', function(event) {
	
	var nombre = $('#windowNewNombreOperario').val();
	var primerApellido = $('#windowNewPrimerApellidoOperario').val();
	var segundoApellido = $('#windowNewSegundoApellido').val();
	var rangoOperario = $('#windowNewRangoOperario').val();
	var passWord = pasarAmd5($('#windowNewPass'));

		
	if ($('#windowNewPass').val() == $('#windowNewRePass').val()) {
		
		$.ajax({
	        url:"Trabajadores",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{tipo: "adminNewOperario", nombreUser: nombreUser, token : tokenUser, nombre : nombre, primerApellido : primerApellido, segundoApellido: segundoApellido, rangoOperario : rangoOperario, passWord : passWord},
	        success: function(respuesta){      
	        	
	        	if(respuesta['status'] == "ok"){					
	        		
	        		$('#login-modal-Nuevo-Operario').modal('toggle');
//	        		mostrarMensaje("Registrado", "Exito al guardar el operario");
	        		alert( "Exito al guardar el operario");
	        		window.location="Trabajadores";
	        		
	        	}else{
	        		$('#login-modal-Nuevo-Operario').modal('toggle');
	        		mostrarMensaje("Status : "+respuesta['status'], "Motivo : "+ respuesta['motivo']);
	        	}
	        	
	        },
	        error: function(jqXHR, status, error) {
	            alert("Error detectado: " + status + "\nExcepcion: " + error);
	        }
	    });       
		
	}else{
		mostrarMensaje("Error", "Las contraseñas no coinciden");
	}	
	
})

function pasarAmd5(e){    
    return md5($(e).val());
}


