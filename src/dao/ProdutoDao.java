package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.CategoriaBeans;
import beans.ProdutoBeans;
import connection.SingleConnection;

/**
 * Classe ProdutoDao
 * Classe que provê os Métodos e Validações,
 * Para manipular dados, acesso e manipulação no DB
 * @author ritim
 *
 */
public class ProdutoDao {

	private Connection connection;

	/**
	 *Construtor ProdutoDao()
	 *Recebe um Objeto connection da Classe SingleConnection 
	 */
	public ProdutoDao() {
		connection = SingleConnection.getConnection();
	}

	/**
	 * Metodo createProd()
	 * Responsável por fazer a inserção de dados(INSERT) no DB
	 * @param ProdutoBeans produto = Objeto Produto da Classe ProdutoBeans
	 */
	public void createProd(ProdutoBeans produto) {
		try {
			String sql = "INSERT INTO produto(nome, quantidade, valor, categoria_id) VALUES (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setDouble(3, produto.getValor());
			statement.setLong(4, produto.getCategoria_id());
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
	 * Metodo readProd()
	 * Responsável em Listar todos Produtos no sistema
	 * @return list = Array da Lista do Objeto ProdutosBeans
	 * @throws Exception
	 */
	public List<ProdutoBeans> readProd() throws Exception {
		List<ProdutoBeans> list = new ArrayList<ProdutoBeans>();
		String sql = "SELECT * FROM produto";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			ProdutoBeans produtos = new ProdutoBeans();
			produtos.setId(resultSet.getLong("id"));
			produtos.setNome(resultSet.getString("nome"));
			produtos.setQuantidade(resultSet.getDouble("quantidade"));
			produtos.setValor(resultSet.getDouble("valor"));
			produtos.setCategoria_id(resultSet.getLong("categoria_id"));

			list.add(produtos);
		}

		return list;
	}
	
	public List<CategoriaBeans> listCategoria() throws Exception{
		List<CategoriaBeans> list = new ArrayList<CategoriaBeans>();
		String sql = "SELECT * FROM categoria";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			CategoriaBeans categoria = new CategoriaBeans();
			categoria.setId(resultSet.getLong("id"));
			categoria.setNome(resultSet.getString("nome"));
			
			list.add(categoria);
		}
		
		
		return list;
	}
	
	/**
	 * Metodo deleteProd() Responável por excluir(DELETE) produto do DB
	 * @param String id = Atribuido ao ID do produto
	 */
	public void deleteProd(String id) {
		try {
			String sql = "DELETE FROM produto WHERE id = " + id;
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

	/**
	 * Metodo updateProd()
	 * Responsável por atualizar os dados (UPDATE) no BD
	 * @param ProdutosBeans produtos = Objeto produto da Classe ProdutoBeans
	 */
	public void updateProd(ProdutoBeans produtos) {
		try {
			String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ?, categoria_id = ? WHERE id = " + produtos.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produtos.getNome());
			statement.setDouble(2, produtos.getQuantidade());
			statement.setDouble(3, produtos.getValor());
			statement.setLong(4, produtos.getCategoria_id());
			statement.executeUpdate();

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
	 * Metodo consultarProd() Responsável por fazer consulta(SELECT) no DB
	 * @param String id = Atribuido ID do Produto
	 * @return Objeto produtos da Classe ProdutoBeans
	 * @throws Exception
	 */
	public ProdutoBeans consultarProd(String id) throws Exception {
		String sql = "SELECT * FROM produto WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			ProdutoBeans produtos = new ProdutoBeans();
			produtos.setId(resultSet.getLong("id"));
			produtos.setNome(resultSet.getString("nome"));
			produtos.setQuantidade(resultSet.getDouble("quantidade"));
			produtos.setValor(resultSet.getDouble("valor"));
			produtos.setCategoria_id(resultSet.getLong("categoria_id"));

			return produtos;
		}

		return null;

	}
	
	public boolean validarNomeProduto(String nome) throws Exception {
		String sql = "SELECT COUNT(1) as qtd FROM  produto WHERE nome = '" + nome + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		} else {
			return false;
		}
		
	}

}
