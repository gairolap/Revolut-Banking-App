/**
 * Model class for Transfer. 
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
public class Transfer extends Account {

	private String transferorAccount;
	private String beneficiaryAccount;

}