<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="resources/css/mainUsers.css" media="all">
<title>CADASTRAR TELEFONE</title>
</head>
<body>
	<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 ">
		<a href="acessoliberado.jsp"><img src="resources/img/back.png" title="RETORNAR" width="50px"></a>
		<a href="index.jsp"><img src="resources/img/exit.png" title="HOME" width="50px"></a>
		<div class="input-group">
			<h2 class="title">CADASTRAR TELEFONES</h2>
			<div class="wrapper">
				<form action="salvarTelefones" method="post" id="formUser" onsubmit="return validarCampos()? true:false;">
					<table>
						<tr>
							<td>USER:</td>
							<td><input type="text" name="id" id="id" readonly="readonly" class="input--style-4" value="${users}"></td>
							<td>NOME:</td>
							<td><input type="text" name="nome" id="nome" class="input--style-4"  value="${nomeUser}"></td>
						</tr>
					
						<tr>
							<td colspan="2"><input type="submit" value="SALVAR" class="btn btn--color" /></td>

							<td colspan="2"><input type="submit" value="CANCELAR" class="btn btn--color btn2" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'" />
						</tr>
					</table>
				</form>

			</div>
		</div>
	</div>
	<div class="page-wrapper page-wrapper-lb bg-gra-02 p-t-130-1 p-b-100 font-poppins-tb">
		<h2 class="title-tb-msg">${msg}</h2>
		<h2 class="title">USUARIOS CADASTRADOS</h2>

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
					<td><a href="salvarUsuario?acao=delete&users=${fone.id}"><img src="resources/img/del.png" width="60px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("login").value == '') {
				alert("Informe o Login!");
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert("Informe a Senha!");
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert("Informe o Nome!");
				return false;
			} else if (document.getElementById("telefone").value == '') {
				alert("Informe o Telefone!");
				return false;
			}
			return true;
		}
		
		function consultaCep(){
			var cep = $("#cep").val();
		
			 //Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
	
                if (!("erro" in dados)) {
                    //Atualiza os campos com os valores da consulta.
					$("#rua").val(dados.logradouro);
                    $("#bairro").val(dados.bairro);
                    $("#cidade").val(dados.localidade);
                    $("#estado").val(dados.uf);
                    $("#ibge").val(dados.ibge);
                } //end if.
                else {
                    //CEP pesquisado não foi encontrado.
                    $("#cep").val('');
                    $("#rua").val('');
                    $("#bairro").val('');
                    $("#cidade").val();
                    $("#estado").val('');
                    $("#ibge").val('');
                    alert("CEP não encontrado.");
                }
            });
		}
        		
	</script>
</body>
</html>