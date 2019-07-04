/**
 * 
 */
package com.org.revolut.banking.models;

/**
 * @author Priyank_Gairola
 *
 */
public class Transfer extends Account {

	private String transferorAccount;
	private String beneficiaryAccount;

	/**
	 * @return the transferorAccount
	 */
	public String getTransferorAccount() {
		return transferorAccount;
	}

	/**
	 * @param transferorAccount the transferorAccount to set
	 */
	public void setTransferorAccount(String transferorAccount) {
		this.transferorAccount = transferorAccount;
	}

	/**
	 * @return the beneficiaryAccount
	 */
	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	/**
	 * @param beneficiaryAccount the beneficiaryAccount to set
	 */
	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}

}