/**
 * Service class to hold implementations of various banking operations.
 */
package com.org.revolut.banking.service;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.org.revolut.banking.dao.AccountDao;
import com.org.revolut.banking.dao.impl.AccountDaoImpl;
import com.org.revolut.banking.exception.BankingException;
import com.org.revolut.banking.models.Account;
import com.org.revolut.banking.models.Response;
import com.org.revolut.banking.models.Transfer;

import lombok.extern.log4j.Log4j;

@Log4j
@Path("/banking")
public class BankingService {

	private AccountDao accountDao;

	public BankingService() throws SQLException {

		this.accountDao = new AccountDaoImpl();
	}

	/**
	 * Open an account.
	 * 
	 * @param account.
	 * @return {@link Response}.
	 * @throws SQLException.
	 * @throws BankingException.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/account")
	public Response openAccount(Account account) throws SQLException, BankingException {

		log.info("openAccount invoked...");
		return accountDao.createAccount(account);
	}

	/**
	 * Transfer amount between accounts.
	 * 
	 * @param transfer.
	 * @return {@link Response}.
	 * @throws SQLException.
	 * @throws BankingException.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transfer(Transfer transfer) throws SQLException, BankingException {

		log.info("transfer invoked...");
		return accountDao.transfer(transfer);
	}

	/**
	 * Get account balance.
	 * 
	 * @param acnNum.
	 * @return {@link Response}.
	 * @throws BankingException.
	 */
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountBalance(@QueryParam("acnNum") String acnNum) throws BankingException {

		log.info("getAccountBalance invoked...");
		return accountDao.getAccountBalance(acnNum);
	}

}