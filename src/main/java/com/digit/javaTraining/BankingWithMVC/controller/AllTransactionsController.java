package com.digit.javaTraining.BankingWithMVC.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.javaTraining.BankingWithMVC.model.BankUser;
import com.digit.javaTraining.BankingWithMVC.model.Transaction;

@WebServlet("/viewAllTransactions")
public class AllTransactionsController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession curSession = req.getSession();
		BankUser curBankUser = (BankUser) curSession.getAttribute("curBankUser");
		if (curBankUser == null) {
			curSession.setAttribute("ERROR_NAME", "View Transactions Failed");
			curSession.setAttribute("ERROR_MSG", "Invalid Customer! Session Invalid!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");			
			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}

		Transaction curTransaction = new Transaction();
		ArrayList<Transaction> receiveTransactionsList = curTransaction
				.viewAllTransactions(curBankUser.getAccountNumber());

		curSession.setAttribute("ALL_TRANSACTIONS", receiveTransactionsList);
		curSession.setAttribute("isListGenerated", "true");

		resp.sendRedirect("/BankingWithMVC/ViewAllTransactions.jsp");
	}
}
