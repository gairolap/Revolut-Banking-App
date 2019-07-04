/**
 * Test class for BankingService implementations.
 */
package com.org.revolut.banking.service;

import java.sql.SQLException;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.org.revolut.banking.exception.BankingException;
import com.org.revolut.banking.models.Account;
import com.org.revolut.banking.models.Response;
import com.org.revolut.banking.models.Transfer;

import junit.framework.TestCase;

public class BankingServiceTest extends TestCase {

	static Logger log = Logger.getLogger(BankingServiceTest.class);
	private BankingService bankingService;
	private Response response;
	Account account = null;
	Transfer transfer = null;

	@Override
	protected void setUp() throws Exception {
		this.bankingService = new BankingService();
		account = new Account();
		transfer = new Transfer();
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#openAccount(com.org.revolut.banking.models.Account)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_openAccount_success() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.CREATED));
		Assert.assertNotNull(response.getResponseMessage());
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#openAccount(com.org.revolut.banking.models.Account)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_openAccount_NullAccHoldrNmFailure() throws SQLException, BankingException {

		account.setAccountHolderName(null);
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#openAccount(com.org.revolut.banking.models.Account)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_openAccount_EmptyAccHoldrNmFailure() throws SQLException, BankingException {

		account.setAccountHolderName("");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#openAccount(com.org.revolut.banking.models.Account)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_openAccount_NullAccHoldrAdrsFailure() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut");
		account.setAccountHolderAddress(null);
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#openAccount(com.org.revolut.banking.models.Account)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_openAccount_EmptyAccHoldrAdrsFailure() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut");
		account.setAccountHolderAddress("");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_success() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut Primary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		transfer.setTransferorAccount(response.getResponseMessage().substring(13));
		account.setAccountHolderName("Revolut Secondary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		this.bankingService = new BankingService();
		response = bankingService.openAccount(account);
		transfer.setBeneficiaryAccount(response.getResponseMessage().substring(13));
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.ACCEPTED));
		Assert.assertNotNull(response.getResponseMessage());
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_emptyTransferorAccFailure() throws SQLException, BankingException {

		transfer.setTransferorAccount("");
		account.setAccountHolderName("Revolut Secondary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		this.bankingService = new BankingService();
		response = bankingService.openAccount(account);
		transfer.setBeneficiaryAccount(response.getResponseMessage().substring(13));
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_nullTransferorAccFailure() throws SQLException, BankingException {

		transfer.setTransferorAccount(null);
		account.setAccountHolderName("Revolut Secondary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		this.bankingService = new BankingService();
		response = bankingService.openAccount(account);
		transfer.setBeneficiaryAccount(response.getResponseMessage().substring(13));
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_emptyBeneficiaryAccFailure() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut Primary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		transfer.setTransferorAccount(response.getResponseMessage().substring(13));
		transfer.setBeneficiaryAccount("");
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_nullBeneficiaryAccFailure() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut Primary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		transfer.setTransferorAccount(response.getResponseMessage().substring(13));
		transfer.setBeneficiaryAccount(null);
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.BAD_REQUEST));
		Assert.assertNotNull(response.getResponseMessage().equals("invalid request parameters..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_invalidTransferorAccFailure() throws SQLException, BankingException {

		transfer.setTransferorAccount("040d21bb-2093-49c0-a421-433606ba17d9");
		account.setAccountHolderName("Revolut Secondary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		this.bankingService = new BankingService();
		response = bankingService.openAccount(account);
		transfer.setBeneficiaryAccount(response.getResponseMessage().substring(13));
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.PRECONDITION_FAILED));
		Assert.assertNotNull(response.getResponseMessage().equals("incorrect transferor account..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_invalidBeneficiaryAccFailure() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut Primary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		transfer.setTransferorAccount(response.getResponseMessage().substring(13));
		transfer.setBeneficiaryAccount("040d21bb-2093-49c0-a421-433606ba17d9");
		transfer.setAmount(5000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.PRECONDITION_FAILED));
		Assert.assertNotNull(response.getResponseMessage().equals("incorrect beneficiary account..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#transfer(com.org.revolut.banking.models.Transfer)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	@Test
	public void test_transfer_insufficientBalanceFailure() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut Primary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		transfer.setTransferorAccount(response.getResponseMessage().substring(13));
		account.setAccountHolderName("Revolut Secondary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		this.bankingService = new BankingService();
		response = bankingService.openAccount(account);
		transfer.setBeneficiaryAccount(response.getResponseMessage().substring(13));
		transfer.setAmount(15000);
		this.bankingService = new BankingService();
		response = bankingService.transfer(transfer);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.PRECONDITION_FAILED));
		Assert.assertNotNull(response.getResponseMessage().equals("insufficient balance in account to proceed..."));
	}

	/**
	 * Test method for
	 * {@link com.org.revolut.banking.service.BankingService#getAccountBalance(java.lang.String)}.
	 * 
	 * @throws BankingException.
	 * @throws SQLException.
	 */
	public void testGetAccountBalance() throws SQLException, BankingException {

		account.setAccountHolderName("Revolut Primary");
		account.setAccountHolderAddress("London, United Kingdom");
		account.setAmount(10000);
		response = bankingService.openAccount(account);
		String acnNum = response.getResponseMessage().substring(13);
		this.bankingService = new BankingService();
		response = bankingService.getAccountBalance(acnNum);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatus(), String.valueOf(Status.ACCEPTED));
		Assert.assertEquals(response.getResponseMessage(), "Balance for IBAN " + acnNum + " is " + account.getAmount());
	}

}