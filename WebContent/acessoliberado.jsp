<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CURSO JSP</title>
<link rel="stylesheet" type="text/css" href="resources/css/mainAcess.css">
</head>
<body>
	<div class="page-wrapper-lb bg-gra-02 p-t-130 p-b-100 font-poppins-lb">
	<a href="LoginServlet"><img src="resources/img/exit.png" title="HOME" style="width:50px; float:left;"></a>
		<div class="wrapper wrapper--w680">
			<h1>Seja Bem Vindo ao Sistema Didático JSP</h1>
		</div>
 			<table class="wrapper">
				<tr>
					<td><a href="salvarUsuario?acao=listartodos"><img src="resources/img/user.png" title="CADASTRAR USUARIOS" width="200px"></a></td>
					<td><a href="salvarProduto?acao=listartodos"><img src="resources/img/buy.png" title="CADASTRAR PRODUTOS" width="200px"></a></td>
				</tr>
				<tr>
					<td>Cadastro de Usuarios</td>
					<td>Cadastro de Produtos</td>
				</tr>

			</table>
			</div>

	
</body>
</html>