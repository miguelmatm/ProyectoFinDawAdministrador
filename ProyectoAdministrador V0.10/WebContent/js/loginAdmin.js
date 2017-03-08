$(document).ready(function(){

    $.ajax({
        url: 'includes/modal.html',
        type: 'GET',
        async: false,
        success: function(respuesta){
            $("#modall").html(respuesta);
        }
    });
	
})

 $(document).on('click', '#btnLoginOperario', function(event) {
 	var nombreUsuario = $('#userName').val();
 	var password = $('#userPassword').val();
 	
 	if (!esVacioValor(nombreUsuario)) {
		if (!esVacioValor(password)) {
						
			password = pasarAmd5($('#userPassword'));
			
			$.ajax({
	            url:"LoginAdmin",
	            type: "POST",
	            async:true,
	            dataType: "json",
	            data:{tipo: "logearOperario", pass: password, userName: nombreUsuario},
	            success: function(respuesta){  
	            	if (respuesta['resp']) {
	            		var nomUser = respuesta['nombre'];
	            		var tokenUser = respuesta['token'];
	            		
	            		if (!esVacioValor(nomUser)) {
	            			
							if (!esVacioValor(tokenUser)) {
								
								setCookie("nombreUser", nomUser);
								setCookie("token", tokenUser);
								
								window.location="TrabajarEn";
								
							}else{								
								mostrarMensaje("Error en token", "Error : el servidor manda el token vacio");
							}
						}else{
							mostrarMensaje("Error en nombre", "Error : el servidor manda el nombre vacio");
						}	            		
					}else{
						var motivo = respuesta['motivo'];
						
						mostrarMensaje("Fallo en servidor", motivo);
					}
	            },
	            error: function(jqXHR, status, error) {
	                alert("Error detectado: " + status + "\nExcepcion: " + error);
	            }
	        });  
		}else{
			mostrarMensaje("PassWord Vacia", "El campo de la contraseña debe estar rellena");
		}
	}else{
		mostrarMensaje("Error nombre Operador", "El nombre del operador Debe estar relleno");
	}	 	
 });

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
 
 function mostrarMensaje(titulo, cuerpo){
	    var cadena = "<i class='glyphicon glyphicon-thumbs-up'></i>"+titulo;
	    $('#modalTitulo').html(cadena);
	    var cadenad = "<p>"+cuerpo+"</p>";
	    $('#modalTexto').html(cadenad);
	    $('#success').modal();
	}