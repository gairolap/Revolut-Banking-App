package com.org.revolut.banking;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.org.revolut.banking.dao.H2DaoFactory;
import com.org.revolut.banking.dao.impl.AccountDaoImpl;
import com.org.revolut.banking.service.BankingService;
import com.org.revolut.banking.utils.AccountUtil;

import lombok.extern.log4j.Log4j;

@Log4j
public class App {

	public static void main(String[] args) throws Exception {
		log.info("starting jetty server...");

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		Server jettyServer = new Server(8080);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
				BankingService.class.getCanonicalName() + "," + H2DaoFactory.class.getCanonicalName() + ","
						+ AccountDaoImpl.class.getCanonicalName() + "," + AccountUtil.class.getCanonicalName());

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}

}