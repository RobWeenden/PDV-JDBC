<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Adicionando JQuery -->
 <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>

<link rel="stylesheet" type="text/css" href="resources/css/mainUsers.css" media="all">
<title>CADASTRAR TELEFONE</title>
</head>
<body>
	<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 ">
		<a href="salvarUsuario?acao=listar"><img src="resources/img/back.png" title="RETORNAR" width="50px"></a>
		<a href="index.jsp"><img src="resources/img/exit.png" title="HOME" width="50px"></a>
		<div class="input-group">
			<h2 class="title">CADASTRAR TELEFONES</h2>
			<div class="wrapper">
				<form action="salvarTelefones" method="post" id="formUser" onsubmit="return validarCampos()? true:false;">
					<table>
						<tr>
							<td>USER:</td>
							<td><input type="text" name="id" id="id" readonly="readonly" class="input--style-4" value="${userEscolhido.id}"></td>
							<td>NOME:</td>
							<td><input type="text" name="nome" id="nome" readonly="readonly" class="input--style-4"  value="${userEscolhido.nome}"></td>
						</tr>
						<tr>
							<td>NÚMERO:</td>
							<td><input type="text" name="numero" id="numero" class="input--style-4" value="${fone.numero}" maxlength="11"></td>
							<td>TIPO:</td>
							
							<td>
								<select name="tipo" id="tipo" class="input--style-4" style="width:200px;">
									<option/>
									<option>Residencial</option>
									<option>Recado</option>
									<option>Trabalho</option>
									<option>Celular</option>
								</select>
							
							</td>
						</tr>
						
						<tr>
							<td colspan="4" rowspan="2"><input type="submit" value="SALVAR" class="btn btn--color" /></td>
						</tr>
					</table>
				</form>

			</div>
		</div>
	</div>
	<div class="page-wrapper page-wrapper-lb bg-gra-02 p-t-130-1 p-b-100 font-poppins-tb">
		<h2 class="title-tb-msg">${msg}</h2>
		<h2 class="title">TELEFONES CADASTRADOS</h2>

		<table class="tb_Users table" border="2">
			<tr style="color: #fff; font-size: 15pt;">
				<th>ID</th>
				<th>NÚMERO</th>
				<th>TIPO</th>
				<th>DELETAR</th>
			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr style="color: #FFED87; font-size: 12pt;">
					<td><c:out value="${fone.id}"></c:out></td>
					<td><c:out value="${fone.numero}"></c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>
					<td><a href="salvarTelefones?acao=deleteFone&foneId=${fone.id}" onclick="return confirm('Deseja Excluir este Telefone ?');"><img src="resources/img/del.png" width="60px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("numero").value == '') {
				alert("INFORME O NÚMERO DE TELEFONE!");
				return false;
			} else if (document.getElementById("tipo").value == '') {
				alert("INFORME O TIPO!");
				return false;
			} 
			return true;
		}
		
		 //FUNÇÃO PARA LIMITAR VALOR NUMERICO NO INPUT
        $(document).ready(function() {
        	  $("#numero").keyup(function() {
        	      $("#numero").val(this.value.match(/[0-9]*/));
        	  });
        	});
	</script>
</body>
</html>