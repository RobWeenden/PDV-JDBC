package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UsuarioBeans;
import connection.SingleConnection;

/**
 * Classe UsuarioDao
 * Classe Responsavel que provê os Métodos e Validações 
 * Para Manipular Dados, e Acesso e Manipulação do BD e
 * Realizar o CRUD no BD
 * @author ritim
 *
 */
public class UsuarioDao {
	/*
	 * Recebe o Objeto connection da Classe Connection Como Atributo de Classe
	 */
	private Connection connection;
	
	/*
	 * Construtor UsuarioDao()
	 * Recebe um Objeto connection da Classe SingleConnection
	 */
	public UsuarioDao() {
		connection = SingleConnection.getConnection();
	}
	
	/**
	 * Metodo create()
	 * Provê a inserção dos dados(INSERT) do Usuario no DB
	 * @param UsuarioBeans usuario = Objeto Usuario da Classe UsuarioBeans
	 */
	public void create(UsuarioBeans usuario) {
		try {
			String sql = "INSERT INTO usuario (login, senha, nome, telefone, cep, rua, bairro, cidade, estado, ibge, "
					+ "fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getTelefone());
			statement.setString(5, usuario.getCep());
			statement.setString(6, usuario.getRua());
			statement.setString(7, usuario.getBairro());
			statement.setString(8, usuario.getCidade());
			statement.setString(9, usuario.getEstado());
			statement.setString(10, usuario.getIbge());
			statement.setString(11, usuario.getFotoBase64());
			statement.setString(12, usuario.getContentType());
			statement.setString(13, usuario.getCurriculoBase64());
			statement.setString(14, usuario.getContentTypeCurriculo());
			statement.setString(15, usuario.getFotoBase64Miniatura());
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
	 * Metodo readListar()
	 * Responsavel Por listar(SELECT * FROM) todos os usarios no sistema
	 * @return uma lista de usuarios da Classe UsuarioBeans
	 * @throws Exception
	 */
	public List<UsuarioBeans> readListar() throws Exception{
		
		List<UsuarioBeans> usuarioLista = new ArrayList<UsuarioBeans>();
		String sql = "SELECT * FROM usuario";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			UsuarioBeans usuarios = new UsuarioBeans();
			usuarios.setId(resultSet.getLong("id"));
			usuarios.setLogin(resultSet.getString("login"));
			usuarios.setNome(resultSet.getString("nome"));
			usuarios.setSenha(resultSet.getString("senha"));
			usuarios.setTelefone(resultSet.getString("telefone"));
			usuarios.setCep(resultSet.getString("cep"));
			usuarios.setRua(resultSet.getString("rua"));
			usuarios.setBairro(resultSet.getString("bairro"));
			usuarios.setCidade(resultSet.getString("cidade"));
			usuarios.setEstado(resultSet.getString("estado"));
			usuarios.setIbge(resultSet.getString("ibge"));
			usuarios.setFotoBase64(resultSet.getString("fotobase64"));
			usuarios.setContentType(resultSet.getString("contenttype"));
			usuarios.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuarios.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			usuarioLista.add(usuarios);
		}
		
		
		return usuarioLista;
		
	}
	
	/**
	 * Metodo delete()
	 * Responsavel por fazer a Exclusão(DELETE) no DB
	 * @param String id = Atributo ID do Usuario
	 */
	public void delete(String id) {
		try {
			String sqlFone = "DELETE FROM telefone WHERE usuario = "+id;//Excluir Telefone que está Relacionado com o Usuario	
			String sqlUser = "DELETE FROM usuario WHERE id =" + id; //Excluir Usuario Após Telefone ser Excluido
			
			PreparedStatement statement = connection.prepareStatement(sqlFone);//Telefone(TABELA FILHO)
			
			statement.executeUpdate();
			connection.commit();
			
			statement = connection.prepareStatement(sqlUser);//Usuario(TABELA PAI)
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
	
	/**Metodo update()
	 * Responsavel em atualizar dados (UPDATE) no DB
	 * @param UsuarioBeans usuarioUpdate = Objeto usuario da Classe UsuarioBeans
	 */
	public void update(UsuarioBeans usuarioUpdate) {
		try {
			String sql = "UPDATE usuario SET login = ?, senha = ?, nome = ?, "
					+ "telefone = ?, cep = ?, rua = ?, bairro = ?,cidade = ?, estado = ?, ibge = ?, "
					+ "fotobase64 = ?, contenttype = ?, curriculobase64 = ?, "
					+ "contenttypecurriculo = ?, fotobase64miniatura = ? WHERE id = "+ usuarioUpdate.getId();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuarioUpdate.getLogin());
			statement.setString(2, usuarioUpdate.getSenha());
			statement.setString(3, usuarioUpdate.getNome());
			statement.setString(4, usuarioUpdate.getTelefone());
			statement.setString(5, usuarioUpdate.getCep());
			statement.setString(6, usuarioUpdate.getRua());
			statement.setString(7, usuarioUpdate.getBairro());
			statement.setString(8, usuarioUpdate.getCidade());
			statement.setString(9, usuarioUpdate.getEstado());
			statement.setString(10, usuarioUpdate.getIbge());
			statement.setString(11, usuarioUpdate.getFotoBase64());
			statement.setString(12, usuarioUpdate.getContentType());
			statement.setString(13, usuarioUpdate.getCurriculoBase64());
			statement.setString(14, usuarioUpdate.getContentTypeCurriculo());
			statement.setString(15,usuarioUpdate.getFotoBase64Miniatura());
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
	 * Metodo consultar()
	 * Responsavel em por fazer consulta(SELECT) no BD
	 * @param Strig id = Atribuido o ID do usuario
	 * @return 
	 * @throws Exception
	 */
	public UsuarioBeans consultar(String id) throws Exception {
		String sql = "SELECT * FROM usuario WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			UsuarioBeans usuario = new UsuarioBeans();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setTelefone(resultSet.getString("telefone"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
			usuario.setFotoBase64(resultSet.getString("fotobase64"));
			usuario.setContentType(resultSet.getString("contenttype"));
			usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuario.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));

			return usuario;
		}

		return null;

	}
	
	/**
	 * Metodo validarLogin()
	 * Reponsavel por validar Login(Não pode existir um mesmo Login para dois Usuarios Diferentes)
	 * @param String login = Atribuido login do Usuario
	 * @return true/false
	 * @throws Exception
	 */
	public boolean validarLogin(String login) throws Exception {
		String sql = "SELECT COUNT(1) as qtd FROM usuario WHERE login ='"+login+"'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}else {
			return false;
		}
	}
	
	/**
	 * Metodo validarSenha()
	 * Reponsável por validar senha(Não pode existir uma mesma Senha para dois Usuarios Diferentes)
	 * @param String senha = Atribuido Senha do Usuario
	 * @return true/false
	 * @throws Exception
	 */
	public boolean validarSenha(String senha) throws Exception {
		String sql = "SELECT COUNT(1) as qtd FROM usuario WHERE senha ='" + senha + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		} else {
			return false;
		}
	}
	
}
