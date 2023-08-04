package com.digit.javaTraining.BankingWithMVC.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankUser {

	public static Connection conn;

	int bankID;
	String bankName;
	String ifscCode;
	int accountNumber;
	int pin;
	int customerID;
	String customerName;
	int balance;
	String email;
	Long phoneNumber;

	public BankUser() {
		String url = "jdbc:mysql://localhost:3306/bankingapplication";
		String user = "root";
		String pwd = "Phani@123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getBankID() {
		return bankID;
	}

	public void setBankID(int bankID) {
		this.bankID = bankID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean register() {
		String query = "INSERT INTO BankUser VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, bankID);
			ps.setString(2, bankName);
			ps.setString(3, ifscCode);
			ps.setInt(4, accountNumber);
			ps.setInt(5, pin);
			ps.setInt(6, customerID);
			ps.setString(7, customerName);
			ps.setInt(8, balance);
			ps.setString(9, email);
			ps.setLong(10, phoneNumber);

			int statusCode = ps.executeUpdate();
			if (statusCode > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean login() {
		String query = "SELECT * FROM BankUser WHERE customerID = ? AND pin = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, customerID);
			ps.setInt(2, pin);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.setBankID(rs.getInt("bankID"));
				this.setBankName(rs.getString("bankName"));
				this.setIfscCode(rs.getString("ifscCode"));
				this.setAccountNumber(rs.getInt("accountNumber"));
				this.setPin(rs.getInt("pin"));
				this.setCustomerID(rs.getInt("customerName"));
				this.setCustomerName(rs.getString("customerName"));
				this.setBalance(rs.getInt("balance"));
				this.setEmail(rs.getString("email"));
				this.setPhoneNumber(rs.getLong("phoneNumber"));

				return true;

			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changePIN(int newPIN) {
		String query = "UPDATE BankUser SET pin = ? WHERE customerID = ? AND accountNumber = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newPIN);
			ps.setInt(2, customerID);
			ps.setInt(3, accountNumber);

			int statusCode = ps.executeUpdate();
			if (statusCode > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean transferAmount(int senderCustomerID, int senderAccountNumber, String senderIFSCCode,
			int amountToTransfer, int receiverAccountNumber, String receiverIFSCCode, int senderPINForAuth) {
		String query = "SELECT * FROM BankUser WHERE accountNumber = ? AND ifscCode = ?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, receiverAccountNumber);
			ps.setString(2, receiverIFSCCode);

			rs = ps.executeQuery();
			if (rs.next()) {
				query = "UPDATE BankUser SET balance = balance - ? where accountNumber = ? and ifscCode = ?";
				ps.clearBatch();
				ps = conn.prepareStatement(query);
				ps.setInt(1, amountToTransfer);
				ps.setInt(2, senderAccountNumber);
				ps.setString(3, senderIFSCCode);

				int statusCode = ps.executeUpdate();
				if (statusCode > 0) {
					query = "UPDATE BankUser SET balance = balance + ? WHERE accountNumber = ? and ifscCode = ?";
					ps.clearBatch();
					ps = conn.prepareStatement(query);
					ps.setInt(1, amountToTransfer);
					ps.setInt(2, receiverAccountNumber);
					ps.setString(3, receiverIFSCCode);

					statusCode = ps.executeUpdate();
					if (statusCode > 0) {
						ps.clearBatch();
						Long transactionID = 0L;
						while (true) {
							transactionID = (long) (Math.random() * (999999999999999L - 100000000000000L)
									+ 100000000000000L);
							query = "SELECT * FROM transactions WHERE transactionID = ?";
							ps = conn.prepareStatement(query);
							ps.setLong(1, transactionID);

							rs = ps.executeQuery();
							if (rs.next()) {
								continue;
							}
							break;
						}

						query = "INSERT INTO transactions VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
						ps.clearBatch();

						ps = conn.prepareStatement(query);
						ps.setInt(1, senderCustomerID);
						ps.setString(2, bankName);
						ps.setString(3, senderIFSCCode);
						ps.setInt(4, senderAccountNumber);
						ps.setString(5, receiverIFSCCode);
						ps.setInt(6, receiverAccountNumber);
						ps.setInt(7, amountToTransfer);
						ps.setLong(8, transactionID);

						statusCode = ps.executeUpdate();
						if (statusCode > 0) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
