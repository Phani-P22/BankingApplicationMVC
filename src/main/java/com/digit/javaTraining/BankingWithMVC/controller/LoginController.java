package com.digit.javaTraining.BankingWithMVC.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.javaTraining.BankingWithMVC.model.BankUser;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int customerID = Integer.parseInt(req.getParameter("customerID"));
		int pin = Integer.parseInt(req.getParameter("pin"));
		
		BankUser curBankUser = new BankUser();
		curBankUser.setCustomerID(customerID);
		curBankUser.setPin(pin);

		boolean loginStatus = curBankUser.login();
		if (loginStatus) {
			HttpSession session = req.getSession(true);
			session.setAttribute("curBankUser", curBankUser);

			resp.sendRedirect("/BankingWithMVC/home.jsp");

		} else {
			HttpSession curSession = req.getSession();
			curSession.setAttribute("ERROR_NAME", "Login Failed");
			curSession.setAttribute("ERROR_MSG", "Login Failed! Invalid Credentials.");
			curSession.setAttribute("FILE_TO_REDIRECT", "index.html");
			
			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
		}
	}
}
