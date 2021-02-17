package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.TelefoneBeans;
import connection.SingleConnection;

/**
 * Classe TelefoneDao
 * Classe que provê os metodos e validações
 * Para manipular dados, acesso no BD
 * @author ritim
 *
 */
public class TelefoneDao {

	/**
	 * Recebe o Objeto connection da Classe Connection como Atributo de Classe
	 */
	private Connection connection;

	/**
	 * Construtor TelefoneDao() Recebe um Objeto connection da Classe
	 * SingleConnection
	 */
	public TelefoneDao() {
		connection = SingleConnection.getConnection();
	}

	/**
	 * Metodo createTel() Responsável por fazer a inserção de dados(INSERT) no DB
	 * @param TelefoneBeans telefone = Objeto telefone da Classe TelefoneBeans
	 */
	public void createTel(TelefoneBeans telefone) {
		try {
			String sql = "INSERT INTO telefone(numero, tipo, usuario) VALUES (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();

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
	 * **
	 * Metodo readTel()
	 * Responsável em Listar(SELECT * FROM) todos Telefones do Usuario no sistema
	 * @param Long user = Atribuido ao usuario do Objeto TelefoneBeans
	 * @return list = Array da Lista do Objeto TelefoneBeans
	 * @throws Exception
	 */
	public List<TelefoneBeans> readTel(Long user) throws Exception {
		List<TelefoneBeans> list = new ArrayList<TelefoneBeans>();
		String sql = "SELECT * FROM telefone WHERE  usuario = "+user;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			TelefoneBeans telefone = new TelefoneBeans();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));

			list.add(telefone);
		}

		return list;
	}

	/**
	 * Metodo deleteTel() Responável por excluir(DELETE) produto do DB
	 * @param String id = Atribuido ao ID do telefone
	 */
	public void deleteTel(String id) {
		try {
			String sql = "DELETE FROM telefone WHERE id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();

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
}
