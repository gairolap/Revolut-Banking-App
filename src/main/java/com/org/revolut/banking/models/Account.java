/**
 * Model class for Account.
 */
package com.org.revolut.banking.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

	private String accountHolderName;
	private String accountHolderAddress;
	private int amount;

}