<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
session = request.getSession();
String successName = (String) session.getAttribute("SUCCESS_NAME");
String successMessage = (String) session.getAttribute("SUCCESS_MSG");
String fileToRedirect = (String) session.getAttribute("FILE_TO_REDIRECT");
session.removeAttribute("SUCCESS_NAME");
session.removeAttribute("SUCCESS_MSG");
session.removeAttribute("FILE_TO_REDIRECT");
%>
<title><%=successName%></title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f0f0;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background-position: center;
	background-image: url("images/image.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	position: relative;
}

body::before {
	content: "";
	background-image: inherit;
	background-size: cover;
	background-repeat: no-repeat;
	filter: blur(2px);
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: -1;
}

.container {
	max-width: 400px;
	padding: 30px;
	background-color: rgba(255, 255, 255, 0.7);
	border-radius: 10px;
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
	text-align: center;
}

h4 {
	color: #3366cc;
	margin-bottom: 20px;
}

a {
	display: inline-block;
	padding: 10px 20px;
	background-color: #3366cc;
	color: #fff;
	text-decoration: none;
	border-radius: 5px;
	font-weight: bold;
	transition: background-color 0.3s ease;
}

a:hover {
	background-color: #005580;
}
</style>
</head>
<body>

	<div class="container">
		<h1>
			<%=successMessage%>
		</h1>
		<br> <a href="<%=fileToRedirect%>">Click here to Redirect</a>
	</div>

</body>
</html>