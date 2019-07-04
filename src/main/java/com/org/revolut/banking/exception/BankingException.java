/**
 * 
 */
package com.org.revolut.banking.exception;

import java.sql.SQLException;

/**
 * @author Priyank_Gairola
 *
 */
public class BankingException extends Exception {

	private static final long serialVersionUID = -6016326461717630091L;

	public BankingException(SQLException sqlException, String errMsg) {

		super(errMsg, sqlException);
	}

}