<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!--=========================DECLARAÇÃO PARA INCORPORAR OS LOOPS===========================-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--=========================DECLARAÇÃO DO FORMART NUMBER===========================-->
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!--================================================================================-->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!--=========================DECLARAÇÃO DA BIBLIOTECA JQUERY===========================-->
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<!--=========================DECLARAÇÃO DO MASKMONEY===================================-->
<script src="resources/js/jquery.maskMoney.min.js" type="text/javascript"></script>
<!--================================================================================-->
<link rel="stylesheet" type="text/css" href="resources/css/mainProd.css" media="all">
<title>CADASTRAR PRODUTO</title>
</head>
<body>
	   <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 ">
        <a href="acessoliberado.jsp"><img src="resources/img/back.png" title="RETORNAR" width="50px"></a>
        <a href="index.jsp"><img src="resources/img/exit.png" title="HOME" width="50px"></a>
        <div class="input-group">
            <h2 class="title">CADASTRAR PRODUTO</h2>
            <div class="wrapper">

                <form action="salvarProduto" method="post" id="formUser" onsubmit="return validarCampos()?true:false;">
                    <table class="table">

                        <tr>
                            <td>ID:</td>
                            <td><input type="text" name="id" id="id" readonly="readonly" class="input--style-4" value="${prod.id}"></td>
                        </tr>
                        <tr>
                            <td>NOME:</td>
                            <td><input type="text" name="nome" id="nome" autocomplete="off"
                                    class="input--style-4" value="${prod.nome}" maxlength="100"/></td>
                        </tr>
                        <tr>
                            <td>QUANTIDADE:</td>
                            <td><input type="text" name="quantidade" id="quantidade"
                                    class="input--style-4"  value="${prod.quantidade}" maxlength="7"/></td>
                        </tr>
                        <tr>
                            <td>VALOR:</td>
                            <td><input type="text" name="valor" id="valor" data-thousands="." data-decimal="," autocomplete="off"
                                    class="input--style-4" value="${prod.valorEmTexto}" maxlength="8" /></td>
                        </tr>
                        <tr>

                            <td colspan="2"><input type="submit" value="SALVAR" class="btn btn--color" />
                                <input type="reset" value="CANCELAR"
                                    onclick="document.getElementById('formUser').action = 'salvarProduto?acao=reset'"
                                    class="btn btn--color btn2" />
                            </td>
                        </tr>
                    </table>
                </form>

            </div>
        </div>
    </div>
    <div class="page-wrapper page-wrapper-lb bg-gra-02 p-t-130-1 p-b-100 font-poppins-tb">
        <h2 class="title-tb-msg">${msg}</h2>
        <h2 class="title">PRODUTOS CADASTRADOS</h2>

        <table class="tb_Users table" border="2">
            <tr style="color:#fff; font-size:15pt;">
                <th>ID</th>
                <th>NOME</th>
                <th>QUANTIDADE</th>
                <th>VALOR</th>
                <th>EDITAR</th>
                <th>DELETAR</th>
            </tr>
            <c:forEach items="${produtos}" var="prod">
                <tr style="color:#FFED87; font-size:12pt;">
                    <td>
                        <c:out value="${prod.id}"></c:out>
                    </td>
                    <td>
                        <c:out value="${prod.nome}"></c:out>
                    </td>
                    <td>
                    	<fmt:formatNumber type="number"  maxFractionDigits="0" value="${prod.quantidade}" ></fmt:formatNumber>
                    </td>
                    <td>
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${prod.valor}"></fmt:formatNumber>
                    </td>
                    <td><a href="salvarProduto?acao=update&prod=${prod.id}"><img src="resources/img/editi.png"
                                width="60px"></a></td>
                    <td><a href="salvarProduto?acao=delete&prod=${prod.id}" onclick="return confirm('Deseja Excluir este Produto ?');"><img src="resources/img/del.png"
                                width="60px"></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <script type="text/javascript">
    
    //FUNÇÃO PARA VALIDAR CEP DO USUARIO
        function validarCampos() {
            if (document.getElementById("nome").value == '') {
                alert("INFORME O NOME DO PRODUTO!");
                return false;
            } else if (document.getElementById("quantidade").value == '') {
                alert("INFORME A QUANTIDADE!");
                return false;
            } else if (document.getElementById("valor").value == '') {
                alert("INFORME O VALOR!");
                return false;
            }
            return true;
        }

        //FUNÇÃO MASKMONEY - JQUERY
        $(function() {
			$('#valor').maskMoney();
		});
        
        //FUNÇÃO PARA LIMITAR VALOR NUMERICO NO INPUT
        $(document).ready(function() {
        	  $("#quantidade").keyup(function() {
        	      $("#quantidade").val(this.value.match(/[0-9]*/));
        	  });
        	});

    </script>
</body>
</html>