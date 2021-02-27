<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/mainUsers.css" media="all">
<title>CADASTRAR USUARIO</title>

<!-- Adicionando JQuery -->
 <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>

</head>
<body>
	<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 ">
		<a href="acessoliberado.jsp"><img src="resources/img/back.png" title="RETORNAR" width="50px"></a>
		<a href="index.jsp"><img src="resources/img/exit.png" title="HOME" width="50px"></a>
		<div class="input-group">
			<h2 class="title">CADASTRAR USUARIO</h2>
			<div class="wrapper">
				<form action="salvarUsuario" method="post" id="formUser" onsubmit="return validarCampos()? true:false;" enctype="multipart/form-data">
					<table>
						<tr>
							<td>ID:</td>
							<td><input type="text" name="id" id="id" readonly="readonly" value="${users.id}" class="input--style-4" placeholder="CAMPO DESATIVADO"></td>
							<td>CEP:</td>
							<td><input type="text" name="cep" id="cep" class="input--style-4" onblur="consultaCep();" value="${users.cep}" maxlength="9"></td>
						</tr>
						<tr>
							<td>Login:</td>
							<td><input type="text" name="login" id="login" placeholder="" autocomplete="off" value="${users.login}" class="input--style-4"  maxlength="10"/></td>
							<td>Rua:</td>
							<td><input type="text" name="rua" id="rua" class="input--style-4" value="${users.rua}" maxlength="50"></td>
						</tr>
						<tr>
							<td>Senha:</td>
							<td><input type="password" name="senha" id="senha" placeholder="" value="${users.senha}" class="input--style-4" maxlength="10"/></td>
							<td>Bairro:</td>
							<td><input type="text" name="bairro" id="bairro" class="input--style-4" value="${users.bairro}" maxlength="50"></td>
						</tr>
						<tr>
							<td>Nome:</td>
							<td><input type="text" name="nome" id="nome" placeholder="" autocomplete="off" value="${users.nome}" class="input--style-4" maxlength="50"/></td>
							<td>Cidade:</td>
							<td><input type="text" name="cidade" id="cidade" class="input--style-4" value="${users.cidade}" maxlength="50"></td>

						</tr>
						<tr>
							<td>Telefone:</td>
							<td><input type="text" name="telefone" id="telefone" placeholder="" autocomplete="off" value="${users.telefone}" class="input--style-4" /></td>
							<td>Estado:</td>
							<td><input type="text" name="estado" id="estado" class="input--style-4" value="${users.estado}" maxlength="2"></td>

						</tr>
						<tr>
	
							<td>IBGE:</td>
							<td><input type="text" name="ibge" id="ibge" class="input--style-4" value="${users.ibge}"></td>
							
							<td>Foto:</td>
							<td>
								<input type="file" name="foto" id="foto"  class="input--style-4 ">
								<input type="hidden" name="fotoTemp"  value="${users.fotoBase64}" readonly="readonly" />
								<input type="hidden" name="contentTypeTemp"  value="${users.contentType}" readonly="readonly"/>
								
							</td>

						</tr>
						<tr>
							<td colspan="2"></td>
							<td>Curriculo:</td>
							<td>
								<input type="file" name="curriculo" id="curriculo"  class="input--style-4 ">
								<input type="hidden" name="curriculoTemp"  value="${users.curriculoBase64}" readonly="readonly"/>
								<input type="hidden" name="contentTypeTempCurriculo" value="${users.contentTypeCurriculo}" readonly="readonly"/>
							</td>
						
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
				<th>LOGIN</th>
				<th>IMAGEM</th>
				<th>CURRICULO</th>
				<th>NOME</th>
				<th>TELEFONE</th>
				<th>CEP</th>
				<th>RUA</th>
				<th>BAIRRO</th>
				<th>CIDADE</th>
				<th>ESTADO</th>
				<th>IBGE</th>
				<th>EDITAR</th>
				<th>DELETAR</th>
				<th>FONES</th>
			</tr>
			<c:forEach items="${usuarios}" var="users">
				<tr style="color: #FFED87; font-size: 12pt;">
					<td><c:out value="${users.id}"></c:out></td>
					<td><c:out value="${users.login}"></c:out></td>
					
					<c:if test="${!users.fotoBase64.isEmpty()}">
					<td><a href="salvarUsuario?acao=download&tipo=imagem&users=${users.id}"><img src='<c:out value="${users.tempFotoUser}"></c:out>' width="60px"></a></td>
					</c:if>
					<c:if test="${users.fotoBase64.isEmpty()}">
					<td><img src="resources/img/user_pattern.png" width="60px" alt="Imagem do Usuario vazia" onclick="alert('USUARIO N�O POSSUI IMAGEM')"></td>
					</c:if>
					
					<c:if test="${!users.curriculoBase64.isEmpty()}">
					<td><a href="salvarUsuario?acao=download&tipo=curriculo&users=${users.id}"><img src="resources/img/icon_pdf.png" alt="PDF Curriculo" width="50px"></a></td>
					</c:if>
					<c:if test="${users.curriculoBase64.isEmpty()}">
					<td><img src="resources/img/icon_no_pdf.png" width="50px" alt="Curriculo do Usuario Vazia" onclick="alert('USUARIO N�O POSSUI CURRICULO')"></td>
					</c:if>
					
					<td><c:out value="${users.nome}"></c:out></td>
					<td><c:out value="${users.telefone}"></c:out></td>
					<td><c:out value="${users.cep}"></c:out></td>
					<td><c:out value="${users.rua}"></c:out></td>
					<td><c:out value="${users.bairro}"></c:out></td>
					<td><c:out value="${users.cidade}"></c:out></td>
					<td><c:out value="${users.estado}"></c:out></td>
					<td><c:out value="${users.ibge}"></c:out></td>
					<td><a href="salvarUsuario?acao=update&users=${users.id}"><img src="resources/img/editi.png" width="60px" alt="Atualizar Usuario"></a></td>
					<td><a href="salvarUsuario?acao=delete&users=${users.id}"><img src="resources/img/del.png" width="60px" alt="Deletar usuario"></a></td>
					<td><a href="salvarTelefones?acao=addFone&users=${users.id}"><img src="resources/img/phone.png" width="60px" alt="Adicionar Telefones"></a></td>
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
                    //CEP pesquisado n�o foi encontrado.
                    $("#cep").val('');
                    $("#rua").val('');
                    $("#bairro").val('');
                    $("#cidade").val();
                    $("#estado").val('');
                    $("#ibge").val('');
                    alert("CEP n�o encontrado.");
                }
            });
		}
        		
	</script>
</body>
</html>