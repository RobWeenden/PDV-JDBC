package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UsuarioBeans;
import dao.UsuarioDao;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/salvarUsuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsuarioServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {

			String acao = request.getParameter("acao");
			String users = request.getParameter("users");

			if (acao.equalsIgnoreCase("update")) {
				UsuarioBeans userUpdate = usuarioDao.consultar(users);
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readListar());
				request.setAttribute("users", userUpdate);
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("delete")) {
				usuarioDao.delete(users);

				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("msg", "Deletado com Sucesso!!");
				request.setAttribute("usuarios", usuarioDao.readListar());
				view.forward(request, response);

			} else if (acao.equals("listar")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readListar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo doPost() Responsavel em receber os dados Post E inserir os input nas
	 * String para o Objeto UsuarioBeans
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readListar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			try {
				String id = request.getParameter("id");
				String login = request.getParameter("login");
				String senha = request.getParameter("senha");
				String nome = request.getParameter("nome");
				String telefone = request.getParameter("telefone");
				String cep = request.getParameter("cep");
				String rua = request.getParameter("rua");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				String ibge = request.getParameter("ibge");

				UsuarioBeans usuarioBeans = new UsuarioBeans();
				usuarioBeans.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
				usuarioBeans.setLogin(login);
				usuarioBeans.setSenha(senha);
				usuarioBeans.setNome(nome);
				usuarioBeans.setTelefone(telefone);
				usuarioBeans.setCep(cep);
				usuarioBeans.setRua(rua);
				usuarioBeans.setBairro(bairro);
				usuarioBeans.setCidade(cidade);
				usuarioBeans.setEstado(estado);
				usuarioBeans.setIbge(ibge);
				
				if (login == null || login.isEmpty()) {
					request.setAttribute("msg", "Login deve ser Informado!!");
					request.setAttribute("users", usuarioBeans);

				} else if (senha == null || senha.isEmpty()) {
					request.setAttribute("msg", "Senha deve ser Informada!!");
					request.setAttribute("users", usuarioBeans);

				} else if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Nome deve ser Informado!!");
					request.setAttribute("users", usuarioBeans);

				} else if (telefone == null || telefone.isEmpty()) {
					request.setAttribute("msg", "Telefone deve ser Informado!!");
					request.setAttribute("users", usuarioBeans);

				} else if (id == null || id.isEmpty() && !usuarioDao.validarLogin(login)) {// LOGIN EXISTENTE
					request.setAttribute("msg", "Este Login ja existi!!");
					request.setAttribute("users", usuarioBeans);

				} else if (id == null || id.isEmpty() && !usuarioDao.validarSenha(senha)) {// SENHA EXISTENTE
					request.setAttribute("msg", "Esta Senha ja existi!!");
					request.setAttribute("users", usuarioBeans);
					
				} else if (id == null
						|| id.isEmpty() && usuarioDao.validarLogin(login) && usuarioDao.validarSenha(senha)) {//REALIZAR O CADASTRO DO USUARIO

					usuarioDao.create(usuarioBeans);
					request.setAttribute("msg", "Salvo com Sucesso!!");

				} else if (id != null && !id.isEmpty()) {//INSERIR E VALIDAR UPDATE
					usuarioDao.update(usuarioBeans);
					request.setAttribute("msg", "Atualizado com Sucesso!!");
					
					/*****REALIZAR A LIBERAÇÃO DA LOGICA ABAIXO CASO DESEJAR QUE NA ATUALIZAÇÃO 
					 * DOS DADOS DO USUARIOS SÓ POSSA SER ALTERADO CASO O LOGIN OU SENHA NÃO EXITA NO SISTEMA
					 * CASO DESEJAR REMOVER AS LINHA DE CODIGO 150 E 151
					 */
					
/*					if (!usuarioDao.validarLogin(login)) {//VALIDAR LOGIN EXISTENTE
						request.setAttribute("msg", "Este Login ja existi!!");
						request.setAttribute("users", usuarioBeans);

					} else if (!usuarioDao.validarSenha(senha)) {//VALIDAR SENHA EXISTENTE
						request.setAttribute("msg", "Esta Senha ja existi!!");
						request.setAttribute("users", usuarioBeans);
					}*
					else {//REALIZAR UPDATE DO USUARIO
						usuarioDao.update(usuarioBeans);
						request.setAttribute("msg", "Atualizado com Sucesso!!");
					}*/
				}

				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readListar());
				dispatcher.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
