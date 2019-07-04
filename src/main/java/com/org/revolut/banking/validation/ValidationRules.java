/**
 * Class to hold all the custom logic for all various business validation. 
 */
package com.org.revolut.banking.validation;

import java.util.Map;

public class ValidationRules {

	/**
	 * Check for non-null and non-empty.
	 * 
	 * @param reqAttrib.
	 * @return boolean (true/false).
	 */
	public boolean isNullOrEmpty(String reqAttrib) {

		return reqAttrib == null || reqAttrib == "";
	}

	/**
	 * Check for validity of account.
	 * 
	 * @param acnsMap.
	 * @param acnNum.
	 * @return boolean (true/false).
	 */
	public boolean isValidAccount(Map<String, Integer> acnsMap, String acnNum) {

		return acnsMap.containsKey(acnNum);
	}

	/**
	 * Check for insufficient balance.
	 * 
	 * @param acnsMap.
	 * @param acnNum.
	 * @param amtToTransfer.
	 * @return boolean (true/false).
	 */
	public boolean isEnoughBalanceAvailable(Map<String, Integer> acnsMap, String acnNum, int amtToTransfer) {

		return acnsMap.get(acnNum) >= amtToTransfer;
	}

}