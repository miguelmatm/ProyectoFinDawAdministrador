<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ES">
    <head>
        <meta charset="utf-8">
        <title>Trabajadores</title>
        <link rel="stylesheet" type="text/css" href="less/incidencias.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
    	<div id="modall"></div>
        <div class="page-header">
          <img class="img-responsive" src="imagenes/logo.png">
          <p id="usuario">Usuario :</p>
        </div>
                
        <div class="btn-toolbar container-fluid">

<!--  AGREGAR OPERARIO -->
            <button class="btnNuevoOperario btn btn-primary" data-toggle="modal" data-target="#login-modal-Nuevo-Operario">Nuevo Operario</button>

            <div class="modal fade" id="login-modal-Nuevo-Operario" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        <h1>New Operario</h1><br>
                    
	                    <span class="label label-primary">Nombre</span>
	                    <input id="windowNewNombreOperario" type="text" name="windowIdIncidencia">
	                    
	                    <span class="label label-primary">Primer Apellido</span>  
	                    <input id="windowNewPrimerApellidoOperario" type="text" name="windowIdProyecto">
	                    
	                    <span class="label label-primary">Segundo Apellido</span>  
	                    <input id="windowNewSegundoApellido" type="text" name="windowNumero">
	                      
	                     <span class="label label-primary">Rango Operario</span>
	                    <input id="windowNewRangoOperario" type="text" name="windowFecha"> 
	                    
	                    <span class="label label-primary">Pass</span>
	                    <input id="windowNewPass" type="password" name="windowFecha"> 
	                    
	                    <span class="label label-primary">Re Pass</span>
	                    <input id="windowNewRePass" type="password" name="windowFecha"> 
	                           
                        <button id="btnAgregarNewOperario" class="btn btn-primary" value="Registrar">Registrar</button>
                    </div>
                </div>
            </div>
<!-- FIN AGREGAR  -->   
     
        </div>
        
        
    	<div class="modal fade" id="login-modal-Mostrar-Operario" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="loginmodal-container">
                    
                    <h1>Operario</h1><br>         
                                   
                    <span class="label label-primary">Id Operario</span>
                    <input id="windowIdOperario" readonly="readonly" type="text" name="windowIdOperario">
                    
                    <span class="label label-primary">Nombre</span>
                    <input id="windowNombreOperario" readonly="readonly" type="text" name="windowIdIncidencia">
                    
                    <span class="label label-primary">Primer Apellido</span>  
                    <input id="windowPrimerApellidoOperario" type="text" name="windowIdProyecto">
                    
                    <span class="label label-primary">Segundo Apellido</span>  
                    <input id="windowSegundoApellido" type="text" name="windowNumero">
                      
                      <span class="label label-primary">Rango Operario</span>
                    <input id="windowRangoOperario" type="text" name="windowFecha">  
              
                                     
                    <div class="form-actions">
                    	<button id="btnActualizarOperario" class="btn btn-primary" value="actualizar">Actualizar</button>
                		<button id="btnInfoOperarioCerrar" class="btn btn-primary" value="Cerrar">Cerrar</button>
                	</div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="well container-fluid">
            <table class="table">
              <thead>
                <tr>
                    <th>#</th>
                    <th>Id Operario</th>
                    <th>Nombre</th>
                    <th>Primer Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Rango Operario</th>
                    <th>Accion</th>
                </tr>
              </thead>
              <tbody id="tbOperarios">            
              </tbody>
            </table>
        </div>
        </div>
        
        <div class="form-actions">
            <button id="botonVolver" type="button" class="btn btn-primary">Volver</button>
        </div>
        <script src="./js/jquery-1.11.3.min.js"></script>
        <script src="./js/bootstrap.min.js"></script>
        <script src="./js/md5.js"></script>
        <script src="./js/trabajarEn.js"></script>
        <script src="./js/trabajadores.js"></script>
    </body>
</html>