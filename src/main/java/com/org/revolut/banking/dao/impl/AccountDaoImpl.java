/**
 * DAO class to hold implementations of various database operations. 
 */
package com.org.revolut.banking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.dbutils.DbUtils;

import com.org.revolut.banking.dao.AccountDao;
import com.org.revolut.banking.dao.H2DaoFactory;
import com.org.revolut.banking.exception.BankingException;
import com.org.revolut.banking.models.Account;
import com.org.revolut.banking.models.Response;
import com.org.revolut.banking.models.Transfer;
import com.org.revolut.banking.validation.ValidationRules;

import lombok.extern.log4j.Log4j;

@Log4j
public class AccountDaoImpl implements AccountDao {

	ValidationRules validate = null;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	static Boolean doesTableExist = false;

	final static String INSERT_INTO_ACT_TBL_SQL = "INSERT INTO ACCOUNT (ACT_HOLDR_NM, ACT_HOLDR_ADDR, ACT_NMBR, BALANCE) VALUES (?, ?, ?, ?)";
	static String CREATE_ACT_TBL_SQL = "CREATE TABLE ACCOUNT (ACT_HOLDR_NM VARCHAR(50) NOT NULL, ACT_HOLDR_ADDR VARCHAR(100) NOT NULL, ACT_NMBR VARCHAR(50) NOT NULL, BALANCE INT NOT NULL)";
	final static String SEARCH_ACT_TBL_SQL = "SELECT ACT_HOLDR_NM,  ACT_HOLDR_ADDR, ACT_NMBR, BALANCE FROM ACCOUNT WHERE ACT_NMBR = ?";
	final static String UPDATE_BALNC_IN_ACT_SQL = "UPDATE ACCOUNT SET BALANCE = (BALANCE + ?) WHERE ACT_NMBR = ?";

	/**
	 * Constructor for {@link AccountDaoImpl}.
	 * 
	 * @throws SQLException.
	 */
	public AccountDaoImpl() throws SQLException {
		if (doesTableExist.booleanValue() == false) {
			try {
				conn = H2DaoFactory.getConnection();
				stmt = conn.prepareStatement(CREATE_ACT_TBL_SQL);
				stmt.executeUpdate();
				doesTableExist = true;
				log.info("account table has been created...");
			} catch (SQLException sqlExcep) {
				log.error("something went wrong while creating the table...", sqlExcep);
			}
		}

		this.validate = new ValidationRules();
	}

	public Response createAccount(Account account) throws SQLException, BankingException {

		Response response = new Response();

		// check for non-null and non-empty.
		if (validate.isNullOrEmpty(account.getAccountHolderName())
				|| validate.isNullOrEmpty(account.getAccountHolderAddress())) {
			response.setStatus(String.valueOf(Status.BAD_REQUEST));
			response.setResponseMessage("invalid request parameters...");
			return response;
		}

		if (doesTableExist) {
			try {
				if (conn == null) {
					conn = H2DaoFactory.getConnection();
				}

				// create account.
				stmt = conn.prepareStatement(INSERT_INTO_ACT_TBL_SQL);
				String acnNum = String.valueOf(UUID.randomUUID());
				stmt.setString(1, account.getAccountHolderName());
				stmt.setString(2, account.getAccountHolderAddress());
				stmt.setString(3, acnNum);
				stmt.setInt(4, account.getAmount());
				stmt.executeUpdate();

				log.info("account created successfully...");
				response.setStatus(String.valueOf(Status.CREATED));
				response.setResponseMessage("Your IBAN is " + acnNum);
			} catch (SQLException sqlExcep) {
				log.error("technical error occurred while processing...");
				throw new BankingException(sqlExcep, "technical error occurred while processing...");
			} finally {
				DbUtils.closeQuietly(conn);
				DbUtils.closeQuietly(stmt);
			}
		}

		return response;
	}

	public Response transfer(Transfer transfer) throws SQLException, BankingException {

		Response response = new Response();
		Map<String, Integer> acnsMap = new HashMap<String, Integer>();

		// check for non-null and non-empty.
		if (validate.isNullOrEmpty(transfer.getTransferorAccount())
				|| validate.isNullOrEmpty(transfer.getBeneficiaryAccount())) {
			response.setStatus(String.valueOf(Status.BAD_REQUEST));
			response.setResponseMessage("invalid request parameters...");
			return response;
		}

		try {
			if (conn == null) {
				conn = H2DaoFactory.getConnection();
			}

			stmt = conn.prepareStatement(SEARCH_ACT_TBL_SQL);

			// does transferor account exist or not.
			stmt.setString(1, transfer.getTransferorAccount());
			rs = stmt.executeQuery();
			if (!rs.first()) {
				response.setStatus(String.valueOf(Status.PRECONDITION_FAILED));
				response.setResponseMessage("incorrect transferor account...");
				return response;
			} else {
				acnsMap.put(rs.getString("ACT_NMBR"), rs.getInt("BALANCE"));
			}

			// does beneficiary account exist or not.
			stmt.setString(1, transfer.getBeneficiaryAccount());
			rs = stmt.executeQuery();
			if (!rs.first()) {
				response.setStatus(String.valueOf(Status.PRECONDITION_FAILED));
				response.setResponseMessage("incorrect beneficiary account...");
				return response;
			} else {
				acnsMap.put(rs.getString("ACT_NMBR"), rs.getInt("BALANCE"));
			}

			// does transferor account have sufficient balance or not.
			if (!validate.isEnoughBalanceAvailable(acnsMap, transfer.getTransferorAccount(), transfer.getAmount())) {
				response.setStatus(String.valueOf(Status.PRECONDITION_FAILED));
				response.setResponseMessage("insufficient balance in account to proceed...");
				return response;
			}

			stmt = conn.prepareStatement(UPDATE_BALNC_IN_ACT_SQL);

			// update transferor balance.
			stmt.setInt(1, Math.multiplyExact(transfer.getAmount(), -1));
			stmt.setString(2, transfer.getTransferorAccount());
			stmt.addBatch();

			// update beneficiary balance.
			stmt.setInt(1, transfer.getAmount());
			stmt.setString(2, transfer.getBeneficiaryAccount());
			stmt.addBatch();

			conn.setAutoCommit(false);
			stmt.executeBatch();
			conn.commit();
			response.setResponseMessage("amount of " + transfer.getAmount() + " has been transferred from "
					+ transfer.getTransferorAccount() + " account to " + transfer.getBeneficiaryAccount()
					+ " account.");
			response.setStatus(String.valueOf(Status.ACCEPTED));
		} catch (SQLException sqlExcep) {
			log.error("technical error occurred while processing...");
			conn.rollback();
			throw new BankingException(sqlExcep, "technical error occurred while processing...");
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(stmt);
		}

		return response;
	}

	public Response getAccountBalance(String acnNum) throws BankingException {

		Response response = new Response();

		try {
			if (conn == null) {
				conn = H2DaoFactory.getConnection();
			}

			// search for account.
			stmt = conn.prepareStatement(SEARCH_ACT_TBL_SQL);
			stmt.setString(1, acnNum);
			rs = stmt.executeQuery();
			if (rs.first()) {
				response.setStatus(String.valueOf(Status.ACCEPTED));
				response.setResponseMessage(
						"Balance for IBAN " + rs.getString("ACT_NMBR") + " is " + rs.getInt("BALANCE"));
			} else {
				response.setStatus(String.valueOf(Status.NOT_FOUND));
				response.setResponseMessage("invalid account...");
			}
		} catch (SQLException sqlExcep) {
			log.error("technical error occurred while processing...");
			throw new BankingException(sqlExcep, "technical error occurred while processing...");
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(stmt);
		}

		return response;
	}

}