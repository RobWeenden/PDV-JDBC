package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	private static String url = "jdbc:postgresql://localhost:5432/cursojsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "w33nd3n@!";

	private static Connection connection = null;

	/*
	 * Chamada Estática do Método Conectar
	 */
	static {
		conectar();
	}

	/*
	 * Construtor da Classe SingleConnection() Chama o Método conectar()
	 */
	public SingleConnection() {
		conectar();
	}

	/*
	 * Método conectar() Provê os Meios de Conexão ao BD
	 */
	public static void conectar() {

		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			throw new RuntimeException("ERRO AO CONECTAR COM O BANCO DE DADOS");
		}

	}

	/*
	 * Método getConnection() Responsavel por fazer Uso da Conexao na Aplicação
	 */
	public static Connection getConnection() {
		return connection;
	}
}
