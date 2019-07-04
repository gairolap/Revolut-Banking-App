/**
 * DAO factory to load H2 data source configuration.
 */
package com.org.revolut.banking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.org.revolut.banking.utils.AccountUtil;

import lombok.extern.log4j.Log4j;

@Log4j
public class H2DaoFactory {

	static final String H2_DRIVER = AccountUtil.getStringProperty("h2_driver");
	static final String h2_connection_url = AccountUtil.getStringProperty("h2_connection_url");
	static final String h2_user = AccountUtil.getStringProperty("h2_user");
	static final String h2_password = AccountUtil.getStringProperty("h2_password");

	/**
	 * Constructor for {@link H2DaoFactory}.
	 */
	public H2DaoFactory() {

		log.info("loading H2 driver...");
		DbUtils.loadDriver(H2_DRIVER);
	}

	/**
	 * Gets connections to H2 data source.
	 * 
	 * @return Connection.
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		log.info("establishing connection to H2...");
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);
	}

}