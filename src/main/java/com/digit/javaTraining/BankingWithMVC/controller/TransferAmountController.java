package com.digit.javaTraining.BankingWithMVC.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.javaTraining.BankingWithMVC.model.BankUser;

@WebServlet("/amountTransfer")
public class TransferAmountController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession curSession = req.getSession();
		BankUser curBankUser = (BankUser) curSession.getAttribute("curBankUser");
		if (curBankUser == null) {
			curSession.setAttribute("ERROR_NAME", "Transfer Amount Failed! Something Went Wrong.");
			curSession.setAttribute("ERROR_MSG", "Invalid Customer! Session Invalid!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");

			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}

		int senderCustomerID = Integer.parseInt(req.getParameter("senderCustomerID"));
		int senderAccountNumber = Integer.parseInt(req.getParameter("senderAccountNumber"));
		String senderIFSCCode = req.getParameter("senderIFSCCode");
		int amountToTransfer = Integer.parseInt(req.getParameter("amountToTransfer"));
		int receiverAccountNumber = Integer.parseInt(req.getParameter("receiverAccountNumber"));
		String receiverIFSCCode = req.getParameter("receiverIFSCCode");
		int senderPINForAuth = Integer.parseInt(req.getParameter("senderPINValidation"));

		if (curBankUser.getPin() != senderPINForAuth) {
			curSession.setAttribute("ERROR_NAME", "Transfer Amount Failed!");
			curSession.setAttribute("ERROR_MSG", "Invalid PIN! You are not Authenticated!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");

			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}
		
		if (curBankUser.getCustomerID() != senderCustomerID || curBankUser.getAccountNumber() != senderAccountNumber
				|| !(curBankUser.getIfscCode().equals(senderIFSCCode))) {
			curSession.setAttribute("ERROR_NAME", "Transfer Amount Failed!");
			curSession.setAttribute("ERROR_MSG", "Invalid Customer Details! You are not Authenticated!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");
			
			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}
		if (curBankUser.getBalance() < amountToTransfer) {
			curSession.setAttribute("ERROR_NAME", "Transfer Amount Failed!");
			curSession.setAttribute("ERROR_MSG", "Insufficient Funds! You do not have enough amount to Transfer");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");
			
			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}
		if (amountToTransfer <= 0) {
			curSession.setAttribute("ERROR_NAME", "Transfer Amount Failed!");
			curSession.setAttribute("ERROR_MSG", "Invalid Amount! Cannot transfer " + amountToTransfer + "!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");
			
			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}

		boolean transferStatus = curBankUser.transferAmount(senderCustomerID, senderAccountNumber, senderIFSCCode,
				amountToTransfer, receiverAccountNumber, receiverIFSCCode, senderPINForAuth);
		if (transferStatus) {
			curSession.setAttribute("SUCCESS_NAME", "Transaction Successful!");
			curSession.setAttribute("SUCCESS_MSG", "Amount Transfered Successfully!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");
			
			resp.sendRedirect("/BankingWithMVC/Success.jsp");
			return;
		} else {
			curSession.setAttribute("ERROR_NAME", "Transfer Amount Failed!");
			curSession.setAttribute("ERROR_MSG", "Transfer Fail!");
			curSession.setAttribute("FILE_TO_REDIRECT", "home.jsp");
			
			resp.sendRedirect("/BankingWithMVC/Failure.jsp");
			return;
		}
	}
}
