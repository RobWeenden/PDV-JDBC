<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CURSO JSP</title>

	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/estilo.css">
<!--===============================================================================================-->
</head>
<body>
	 <div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100">
                <form action="LoginServlet" method="post" class="login100-form">
                    <div class="login100-form-title">
                        <h2>ACESSO DO USUARIO</h2>
                    </div>

                    <div class="wrap-input100-input">
                        <input class="input100" type="text" name="login" placeholder="Username">

                        <input class="input100" type="password" name="senha" placeholder="Password">

                    </div>

                    <div class="container-login100-form-btn">
                        <button class="login100-form-btn">
                            Sign in
                        </button>
                    </div>
                    <div>
                    	
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>