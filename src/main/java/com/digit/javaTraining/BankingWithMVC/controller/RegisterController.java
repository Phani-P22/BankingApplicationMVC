package com.digit.javaTraining.BankingWithMVC.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.javaTraining.BankingWithMVC.model.BankUser;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BankUser curBankUser = new BankUser();
		curBankUser.setBankID(Integer.parseInt(req.getParameter("bankID")));
		curBankUser.setBankName(req.getParameter("bankName"));
		curBankUser.setIfscCode(req.getParameter("ifscCode"));
		curBankUser.setAccountNumber(Integer.parseInt(req.getParameter("accountNumber")));
		curBankUser.setPin(Integer.parseInt(req.getParameter("pin")));
		curBankUser.setCustomerID(Integer.parseInt(req.getParameter("customerID")));
		curBankUser.setCustomerName(req.getParameter("customerName"));
		curBankUser.setBalance(Integer.parseInt(req.getParameter("balance")));
		curBankUser.setEmail(req.getParameter("email"));
		curBankUser.setPhoneNumber(Long.parseLong(req.getParameter("phoneNumber")));

		HttpSession curSession = req.getSession();
		boolean registerStatus = curBankUser.register();
		if (registerStatus) {
			curSession.setAttribute("SUCCESS_NAME", "Registration Successful");
			curSession.setAttribute("SUCCESS_MSG", "Registration Done Successfully!");
			curSession.setAttribute("FILE_TO_REDIRECT", "index.html");
			
			resp.sendRedirect("/BankingWithMVC/Success.jsp");
		} else {
			curSession.setAttribute("ERROR_NAME", "Registration Failed");
			curSession.setAttribute("ERROR_MSG", "Registration Failed! Something Went Wrong.");
			curSession.setAttribute("FILE_TO_REDIRECT", "index.html");

			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
		}
	}
}
