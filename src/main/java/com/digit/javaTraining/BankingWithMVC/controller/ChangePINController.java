package com.digit.javaTraining.BankingWithMVC.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.javaTraining.BankingWithMVC.model.BankUser;

@WebServlet("/changePIN")
public class ChangePINController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession curSession = req.getSession();
		BankUser curBankUser = (BankUser) curSession.getAttribute("curBankUser");

		int oldPIN = Integer.parseInt(req.getParameter("oldPIN"));
		int newPIN = Integer.parseInt(req.getParameter("newPIN"));
		int reEnteredNewPIN = Integer.parseInt(req.getParameter("reEnterNewPIN"));

		if (newPIN != reEnteredNewPIN || curBankUser.getPin() != oldPIN) {
			curSession.setAttribute("ERROR_NAME", "PIN Change Failed");
			curSession.setAttribute("ERROR_MSG", "PIN Change Failed! Something Went Wrong.");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");

			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}

		boolean changePINStatus = curBankUser.changePIN(newPIN);
		if (changePINStatus) {
			curBankUser.setPin(newPIN);
			curSession.setAttribute("curBankUser", curBankUser);

			curSession.setAttribute("SUCCESS_NAME", "Success");
			curSession.setAttribute("SUCCESS_MSG", "Pin Changed Successfully.");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");

			resp.sendRedirect("/BankingWithMVC/Success.jsp");
			return;
		}
		curSession.setAttribute("ERROR_NAME", "PIN Change Failed");
		curSession.setAttribute("ERROR_MSG", "PIN Change Failed! Something Went Wrong.");
		curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");

		resp.sendRedirect("/BankingWithMVC/Failure.jsp");
	}
}
