package com.digit.javaTraining.BankingWithMVC.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.javaTraining.BankingWithMVC.model.Loan;

@WebServlet("/loan")
public class ApplyLoanController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String typeOfLoan = req.getParameter("typeOfLoan");

		HttpSession curSession = req.getSession();

		Loan curLoan = new Loan();
		boolean applyLoanStatus = curLoan.applyLoan(typeOfLoan);
		if (applyLoanStatus) {
			curSession.setAttribute("curLoan", curLoan);
			resp.sendRedirect("/BankingWithMVC/LoanDetails.jsp");
		} else {
			curSession.setAttribute("ERROR_NAME", "Loan Application Failed");
			curSession.setAttribute("ERROR_MSG", "Loan Application Failed! Something Went Wrong.");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");

			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}
	}
}
