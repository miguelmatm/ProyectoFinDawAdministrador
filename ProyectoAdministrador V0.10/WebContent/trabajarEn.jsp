<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="less/incidencias.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	</head>
	<body>
		<div class="page-header">
		  <img class="img-responsive" src="imagenes/logo.png">
		  <p id="usuario">Usuario :</p>
		</div>
		<div class="row">
			<div class="container-fluid">
				<div class="col-xs-12">
					<div class="container-fluid incidenciasTituloTrabajarEn">
						<spam id='proyectosTituloTrabajarEn'>Trabajar en Proyecto</spam>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="container-fluid incidenciasTituloTrabajarEn">
						<spam id='incidenciasTituloTrabajarEn'>Ver Incidencias</spam>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="container-fluid incidenciasTituloTrabajarEn">
						<spam id='gestionarTrabajadores'>Gestionar Trabajadores</spam>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="container-fluid incidenciasTituloTrabajarEn">
						<spam id='gestionarProyectos'>Gestionar Proyectos</spam>
					</div>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<button id="CerrarSesion" type="button" class="btn btn-primary">Cerrar Sesion</button>
		</div>
		<script src="./js/jquery-1.11.3.min.js"></script>
		<script src="./js/bootstrap.min.js"></script>
		<script src="./js/trabajarEn.js"></script>
	</body>
</html>