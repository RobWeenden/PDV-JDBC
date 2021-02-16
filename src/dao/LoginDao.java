package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

/**
 * Classe LoginDao Responsável pela Autenticação do Usuário
 * 
 * @author ritim
 *
 */
public class LoginDao {

	/**
	 * Objeto connection da Classe SingleConnection Como atributo de Classe
	 */
	private Connection connection;

	/**
	 * Construtor LoginDao() Recebe o Objeto connection da Classe SingleConnection
	 */
	public LoginDao() {
		connection = SingleConnection.getConnection();
	}

	/**
	 * Metodo validarLogin() Responsável por fazer a verificação de Autenticação no
	 * Sistema
	 * 
	 * @param login = login do usuario
	 * @param senha = senha do usuario
	 * @return
	 * @throws Exception
	 */
	public boolean validarLogin(String login, String senha) throws Exception {
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha+"'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return true;
		} else {
			return false;
		}

	}
}
