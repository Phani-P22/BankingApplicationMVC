<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="com.digit.javaTraining.BankingWithMVC.model.BankUser"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Balance</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background-position: center;
	background-image:url("images/image.jpg");
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

h3, h1, h4 {
	color: #3366cc;
	margin-bottom: 20px;
}

h1 {
	font-size: 50px;
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

	<%
	session = request.getSession();
	if (session == null) {
		response.sendRedirect("/BankingApplication/index.html");
		return;
	}

	BankUser curBankUser = (BankUser) session.getAttribute("curBankUser");
	if (curBankUser == null) {
		response.sendRedirect("/BankingWithMVC/index.html");
		return;
	}
	%>

	<div class="container">
		<h3>
			<%
			out.println("Hey! " + curBankUser.getCustomerName());
			%>
		</h3>
		<h4>
			<%
			out.println("\nYour Balance is");
			%>
		</h4>
		<h1>
			<%
			out.println("Rs." + curBankUser.getBalance() + "/-");
			%>
		</h1>
		<br> <br> <a href="home.jsp"> Click here to Redirect</a>
	</div>


</body>
</html>