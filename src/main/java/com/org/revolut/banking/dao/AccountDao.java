/**
 * DAO interface to hold declarations for various database operations.
 */
package com.org.revolut.banking.dao;

import java.sql.SQLException;

import com.org.revolut.banking.exception.BankingException;
import com.org.revolut.banking.models.Account;
import com.org.revolut.banking.models.Response;
import com.org.revolut.banking.models.Transfer;

public interface AccountDao {

	/**
	 * Creates an account.
	 * 
	 * @param account.
	 * @return {@link Response}.
	 * @throws SQLException.
	 * @throws BankingException.
	 */
	public Response createAccount(Account account) throws SQLException, BankingException;

	/**
	 * Transfers amount between accounts.
	 * 
	 * @param transfer.
	 * @return {@link Response}.
	 * @throws SQLException.
	 * @throws BankingException.
	 */
	public Response transfer(Transfer transfer) throws SQLException, BankingException;

	/**
	 * Gets account balance for given account.
	 * 
	 * @param acnNum.
	 * @return {@link Response}.
	 * @throws BankingException.
	 */
	public Response getAccountBalance(String acnNum) throws BankingException;

}