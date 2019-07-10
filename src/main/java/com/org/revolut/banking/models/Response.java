/**
 * Model class to hold banking response.
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
public class Response {

	private String responseMessage;
	private String status;

}