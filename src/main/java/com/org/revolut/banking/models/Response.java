/**
 * Model class to hold banking response.
 */
package com.org.revolut.banking.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

	private String responseMessage;
	private String status;

}