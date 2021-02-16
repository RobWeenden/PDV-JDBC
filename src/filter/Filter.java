package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingleConnection;

/**
 * Classe Filter Responsavel por fazer a Interceptação das Transações
 * 
 * @author robson weenden
 *
 */
@WebFilter(urlPatterns = { "/*" })
public class Filter implements javax.servlet.Filter {

	/*
	 * Recebe o Objeto connection da Classe Connection Como Atributo de Classe
	 */
	private Connection connection;

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(ServletRequest request, ServletResponse
	 *      response, FilterChain chain) throws IOException, ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			chain.doFilter(request, response);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(FilterConfig filterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		connection = SingleConnection.getConnection();
	}

}
