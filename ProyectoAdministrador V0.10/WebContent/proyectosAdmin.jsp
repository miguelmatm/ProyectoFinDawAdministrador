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
    	<div id="modall"></div>
        <div class="page-header">
          <img class="img-responsive" src="imagenes/logo.png">
          <p id="usuario">Usuario :</p>
        </div>
        <div class="container-fluid">
            <div class="col-xs-6">
                <div>
                    <h2>Nuevo Proyecto</h2>
                    <form action="ProyectosAdmin"  method="post" enctype="multipart/form-data">
	                    <div id="formularioProyecto" class="formulario col-xs-11">
	                        <div class="row">
	                            <span class="label label-primary">Id Proyecto</span>
	                            <div>
	                                <input type="text" id="idProyecto" readonly="readonly" name="idProyecto">
	                                <button id="botonBuscarIdProyecto" type="button" class="btn btn-primary">Disponible</button>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <span class="label label-primary">nombre</span>
	                            <div>
	                                <input id="nombreProy" type="text" name="nombre">
	                            </div>
	                        </div>
	                        <div class="row">
	                            <span class="label label-primary">Id Cliente</span>
	                            <div>
	                                <input id="idClienteProy" type="text" name="idCliente">
	                                <button id="listarClientes" class="btn btn-primary" type="button" data-toggle="modal" data-target="#modalListarClientes">Buscar Clientes</button>
	                                
	                            </div>
	                        </div>
	<!--                         <button id='btnRegistrarProy' value="Registrar" class="btnProyectosAdmin btn btn-primary">Registrar</button> -->
	                       	<input type=file size=60 name="file" value="Examinar"><br><br>	                       
							<input type=submit value="Registrar" class="btn btn-primary"><br>	
	                    </div>
                    </form>
                </div>
            </div>
            <div class="col-xs-6">
                <div>
                    <h2>Nueva Incidencia</h2>
                        <div id="formularioProyecto" class="formulario col-xs-11">
	                        <div class="row">
	                            <span class="label label-primary">Id Incidencia</span>
	                            <div>
	                                <input type="text" id="idIncidencia" disabled="disabled" ="idIncidencia" readonly>
	                                <button id="botonBuscarIdIncidencia" type="button" class="btn btn-primary">Disponible</button>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <span class="label label-primary">Estado</span>
	                            <div>
	                            	<select id="estadoInci">
		                                <option value="Asignada">Asignada</option>
										<option value="Resuelta">Resuelta</option>
										<option value="Trabajandose">Trabajandose</option>
										<option value="No Asignada">No Asignada</option>
										<option value="Esperando">Esperando</option>
										<option value="Cerrada">Cerrada</option>
									</select>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <span class="label label-primary">Proyecto</span>
	                            <div>
	                                <input id="idProyectoInc" type="text" name="id proyecto">
	                                <button id="listarProyectos" class="btn btn-primary" data-target="#modalListarProyectos" data-toggle="modal" type="button">Buscar Proyectos</button>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <span class="label label-primary">Descripcion</span>
	                            <div>
	                                <input id='incidenciaDescripcion' type="text" name="descripcion">
	                            </div>
	                        </div>
	                        <button id='btnRegistrarIncidencia' value="Registrar" class="btnProyectosAdmin btn btn-primary">Registrar</button>
	                    </div>                    
                </div>
            </div>
        </div>
        <div class="btn-toolbar container-fluid">

            <button class="btn btn-primary" data-toggle="modal" data-target="#login-modal-Asignar-Operario">Asignar Operario</button>

            <div class="modal fade" id="login-modal-Asignar-Operario" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        <h1>Agregar Operario</h1><br>
                        <input id="idOperarioNewOperario" type="text" name="IdOperario" placeholder="Id_Operario">
                        <input id="idIncidenciaNewOperario" type="text" name="IdIncidencia" placeholder="Id_Incidencia">       
                        <button id="btnAgregarOperarioAIncidencia" class="btn btn-primary" value="Registrar">Registrar</button>
                    </div>
                </div>
            </div>
            
            
            
            <div class="modal fade" id="modalListarClientes" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        <h1>!CLIENTES!</h1><br>
                        <div id="tablaListado"></div>                               
                        <button id="btnCerrarListadoClientes" class="btn btn-primary" value="Cerrar" data-toggle="modal" data-target="#modalListarClientes">Cerrar</button>
                    </div>
                </div>
            </div>
            
             <div class="modal fade" id="modalListarProyectos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        <h1>!Proyectos!</h1><br>
                        <div id="tablaListadoProyectos"></div>                               
                        <button id="btnCerrarListadoProyectos" class="btn btn-primary" value="Cerrar" data-toggle="modal" data-target="#modalListarProyectos">Cerrar</button>
                    </div>
                </div>
            </div>

            <button class="btn btn-primary" data-toggle="modal" data-target="#login-modal-Buscar-Incidencias">Buscar Operario</button>

            <div class="modal fade" id="login-modal-Buscar-Incidencias" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        <h1>Buscar Operario</h1><br>                        
                        <input id="buscarOperarioo" type="text" name="nombreOperario" placeholder="Nombre Del Operario">                     
                    	<button id="btnBuscarOperario" class="btn btn-primary" value="Cerrar">Buscar</button>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="login-modal-Operario-encontrado" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="loginmodal-container">
                        <h1>Operario Encontrado</h1><br>
                        <input id="idOperarioEncont" type="text" name="IdOperarioEncontrado" placeholder="Id Operario">
                        <input id="nombreOpera" type="text" name="nombreOperario" placeholder="Nombre del Operario">
                        <input id="primerApell" type="text" name="primerApellido" placeholder="Primer Apellido Operario">
                        <input id="segunApell" type="text" name="segundoApellido" placeholder="Segundo Apellido Operario">
                        <button id="btnCerrarOperarioEncontrado" class="btn btn-primary" value="Cerrar">Cerrar</button>
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
                    <th>Id Proyecto</th>
                    <th>Id Incidencia</th>
                    <th>Descripcion</th>
                    <th>Estado</th>
                    <th>Accion</th>
                </tr>
              </thead>
              <tbody id="tbIncidencia">            
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
        <script src="./js/proyectosAdmin.js"></script>
    </body>
</html>