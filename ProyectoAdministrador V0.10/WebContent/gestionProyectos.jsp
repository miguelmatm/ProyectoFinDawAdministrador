<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ES">
    <head>
        <meta charset="utf-8">
        <title>Proyectos</title>
        <link rel="stylesheet" type="text/css" href="less/incidencias.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
    	
        <div class="page-header">
          <img class="img-responsive" src="imagenes/logo.png">
          <p id="usuario">Usuario :</p>
        </div>        
        
    	<div class="modal fade" id="login-modal-Mostrar-Proyectos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="loginmodal-container">
                    
                    <h1>Proyectos</h1><br>         
                                   
                    <span class="label label-primary" readonly="readonly">Id Proyectos</span>
                    <input id="windowIdProyecto" readonly="readonly" type="text" name="windowIdProyecto">
                    
                    <span class="label label-primary">Nombre</span>
                    <input id="windowNombreProyecto" type="text" name="windowNombreProyecto">
                    
                    <span class="label label-primary">Id Cliente</span>  
                    <input id="windowIdCliente" type="text" name="windowIdCliente">              
                                     
                    <div class="form-actions">
                    	<button id="btnActualizarProyecto" class="btn btn-primary" value="Actualizar">Actualizar</button>
                    	        
        				<button id="btnMostrarClientes" class="btn btn-primary" value="Mostrar Clientes">Mostrar Clientes</button>
       
                		<button id="btnInfoProyectoCerrar" class="btn btn-primary" value="Cerrar">Cerrar</button>
                	</div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="login-modal-Mostrar-Tabla-Operarioss" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="loginmodal-container">
		            <table class="table">
		              <thead>
		                <tr>
		                    <th>#</th>
		                    <th>Nombre</th>
		                    <th>Correo</th>		                    
		                    <th>Id Cliente</th>                  
		                </tr>
		              </thead>
		              <tbody id="tbOperarios">            
		              </tbody>
		            </table>
                    <div class="form-actions">                    	
                		<button id="btnInfoOperariosCerrar" class="btn btn-primary" value="Cerrar">Cerrar</button>
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
	                    <th>Id Proyectos</th>
	                    <th>Nombre</th>
	                    <th>Id Cliente</th>
	                    <th>Accion</th>
	                </tr>
	              </thead>
	              <tbody id="tbProyectos">            
	              </tbody>
	            </table>
        	</div>
        </div>
        <div id="modall"></div>
        <div class="form-actions">
            <button id="botonVolver" type="button" class="btn btn-primary">Volver</button>
        </div>
        <script src="./js/jquery-1.11.3.min.js"></script>
        <script src="./js/bootstrap.min.js"></script>
        <script src="./js/md5.js"></script>
        <script src="./js/trabajarEn.js"></script>
        <script src="./js/proyectos.js"></script>
    </body>
</html>