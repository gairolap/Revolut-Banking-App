/**
 * Model class for Account.
 */
package com.org.revolut.banking.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Account {

	private String accountHolderName;
	private String accountHolderAddress;
	private int amount;

}