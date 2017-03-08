<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ES">
	<head>
		<meta charset="utf-8">
		<title>Title Page</title>
		<link rel="stylesheet" type="text/css" href="less/incidencias.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	</head>
	<body>
		<div class="page-header">
		  <img class="img-responsive" src="imagenes/logo.png">
		  <p id="usuario">Usuario : <span id="usuarioLogeado"></span></p>
		</div>
		<div class=" container-fluid">
			<div class="row">
				<div class="col-xs-6">
					<div class="container-fluid incidenciasTitulo">
						<spam>Asignadas a mi (pendientes)</spam>
					</div>
					<div id="incidenciasTitulo" class="container-fluid nuevasIncidencias incidenciasPendientes">
						
					</div>
				</div>
				<div class="col-xs-6">
					<div class="container-fluid incidenciasTitulo">
						<spam>No Asignadas</spam>
					</div>
					<div id="incidenciasNoAsignadas" class="container-fluid nuevasIncidencias incidenciasNoAsignadas">
						
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="container-fluid incidenciasTitulo">
						<spam>Resueltas</spam>
					</div>
					<div id="incidenciasResuelta" class="container-fluid nuevasIncidencias incidenciasResuelta">
						
					</div>
				</div>
				<div class="col-xs-6">
					<div class="container-fluid incidenciasTitulo">
						<spam>Esperando mas Informacion</spam>
					</div>
					<div id="incidenciasNeedInfo" class="container-fluid nuevasIncidencias incidenciasNeedInfo">
						
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="container-fluid incidenciasTitulo">
						<spam>Trabajandose</spam>
					</div>
					<div id="incidenciasTrabajandose" class="container-fluid nuevasIncidencias incidenciasTrabajandose">
						
					</div>
				</div>
				<div class="col-xs-6">
					<div class="container-fluid incidenciasTitulo">
						<spam>Cerradas</spam>
					</div>
					<div id="incidenciasCerradas" class="container-fluid nuevasIncidencias incidenciasCerradas">
						
					</div>
				</div>
			</div>
		</div>
		<div class="form-actions">
       		<button id="btnCerrarPagInci" class="btn btn-primary" value="Cerrar">Cerrar</button>
       	</div>
		<div class="panel panel-default container-fluid">
<!-- 			<div id="cabeceraDelPie" class="panel-body"> -->
<!-- 				<div class="row container-fluid"> -->
<!-- 					<div class="col-xs-2 incidenciasPendientes">Asignadas a mi</div> -->
<!-- 					<div class="col-xs-2 incidenciasNoAsignadas">No Asignadas</div> -->
<!-- 					<div class="col-xs-2 incidenciasResuelta">Resueltas</div> -->
<!-- 					<div class="col-xs-2 incidenciasNeedInfo">Necesita Mas Información</div> -->
<!-- 					<div class="col-xs-2 incidenciasTrabajandose">Trabajandose</div> -->
<!-- 					<div class="col-xs-2 incidenciasCerradas">Cerradas</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="panel-footer">
				<span>© Proyecto, S.L. - Cruz roja española, 11 - 11009 Cadiz [España] - Tel. 856075269</span>
			</div>
		</div>
		
         	<div class="modal fade" id="login-modal-Buscar-Incidencias" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        
                        <h1>Incidencia</h1><br>         
                                       
                        <span class="label label-primary">Id Operario</span>
                        <input id="windowIdOperario" readonly="readonly" type="text" name="windowIdOperario">
                        
                        <span class="label label-primary">Id Incidencia</span>
                        <input id="windowIdIncidencia" readonly="readonly" type="text" name="windowIdIncidencia">
                        
                        <span class="label label-primary">Id Proyecto</span>  
                        <input id="windowIdProyecto" readonly="readonly" type="text" name="windowIdProyecto">
                        
                        <span class="label label-primary">Numero</span>  
                        <input id="windowNumero" readonly="readonly" type="text" name="windowNumero">
                          
                          <span class="label label-primary">Fecha</span>
                        <input id="windowFecha" readonly="readonly" type="text" name="windowFecha">  
                        
                        <span class="label label-primary">Descripcion</span>
                        <input id="windowDescripcion" type="text" name="windowDescripcion">  
                        
                        <span class="label label-primary">Estado</span>
                        <div id="windowEstado" > 
                        </div>     
                                         
                        <div class="form-actions">
                        	<button id="btnActualizarInformacion" class="btn btn-primary" value="actualizar">Actualizar</button>
                    		<button id="btnCerrar" class="btn btn-primary" value="Cerrar">Cerrar</button>
                    	</div>
                    </div>
                </div>
            </div>
		
		<script src="./js/jquery-1.11.3.min.js"></script>
		<script src="./js/bootstrap.min.js"></script>
		<script src="./js/md5.js"></script>
<!-- 		<script src="./js/trabajarEn.js"></script> -->
		<script src="./js/incidencia.js"></script>
	</body>
</html>