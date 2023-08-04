package com.digit.javaTraining.BankingWithMVC.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Transaction {

	public static Connection conn;
	static {
		String url = "jdbc:mysql://localhost:3306/BankingApplication";
		String user = "root";
		String pwd = "Phani@123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int customerID;
	String senderBankName;
	String senderIFSC;
	int senderAccountNumber;
	String receiverIFSC;
	int receiverAccountNumber;
	int amountOfTransfer;
	Long transactionID;
	
	public Transaction() {
		
	}

	public Transaction(int customerID, String senderBankName, String senderIFSC, int senderAccountNumber,
			String receiverIFSC, int receiverAccountNumber, int amountOfTransfer, Long transactionID) {
		super();
		this.customerID = customerID;
		this.senderBankName = senderBankName;
		this.senderIFSC = senderIFSC;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverIFSC = receiverIFSC;
		this.receiverAccountNumber = receiverAccountNumber;
		this.amountOfTransfer = amountOfTransfer;
		this.transactionID = transactionID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getSenderBankName() {
		return senderBankName;
	}

	public void setSenderBankName(String senderBankName) {
		this.senderBankName = senderBankName;
	}

	public String getSenderIFSC() {
		return senderIFSC;
	}

	public void setSenderIFSC(String senderIFSC) {
		this.senderIFSC = senderIFSC;
	}

	public int getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(int senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getReceiverIFSC() {
		return receiverIFSC;
	}

	public void setReceiverIFSC(String receiverIFSC) {
		this.receiverIFSC = receiverIFSC;
	}

	public int getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(int receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public int getAmountOfTransfer() {
		return amountOfTransfer;
	}

	public void setAmountOfTransfer(int amountOfTransfer) {
		this.amountOfTransfer = amountOfTransfer;
	}

	public Long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}

	public ArrayList<Transaction> viewAllTransactions(int curAccountNumber) {
		ArrayList<Transaction> receiveTransactionsList = new ArrayList<Transaction>();

		try {
			String query = "SELECT * FROM transactions WHERE senderAccountNumber = ? OR receiverAccountNumber = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, curAccountNumber);
			ps.setInt(2, curAccountNumber);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int customerID = rs.getInt("customerID");
				String senderBankName = rs.getString("senderBankName");
				String senderIFSC = rs.getString("senderIFSC");
				int senderAccountNumber = rs.getInt("senderAccountNumber");
				String receiverIFSC = rs.getString("receiverIFSC");
				int receiverAccountNumber = rs.getInt("receiverAccountNumber");
				int amountOfTransfer = rs.getInt("amountOfTransfer");
				Long transactionID = rs.getLong("transactionID");

				Transaction curTransaction = new Transaction(customerID, senderBankName, senderIFSC,
						senderAccountNumber, receiverIFSC, receiverAccountNumber, amountOfTransfer, transactionID);

				receiveTransactionsList.add(curTransaction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receiveTransactionsList;
	}
}
