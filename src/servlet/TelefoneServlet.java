package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.TelefoneBeans;
import beans.UsuarioBeans;
import dao.TelefoneDao;
import dao.UsuarioDao;

/**
 * Servlet implementation class TelefoneServlet
 */
@WebServlet("/salvarTelefones")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();
	private TelefoneDao telefoneDao = new TelefoneDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TelefoneServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		try {
			String acao = request.getParameter("acao");

			if (acao != null && acao.equalsIgnoreCase("addFone")) {

				String user = request.getParameter("users");

				UsuarioBeans usuarioBeans = usuarioDao.consultar(user);
//		request.getSession().setAttribute("users", usuarioBeans.getId());
//		request.getSession().setAttribute("nomeUser", usuarioBeans.getNome());
				request.getSession().setAttribute("userEscolhido", usuarioBeans);
				request.setAttribute("userEscolhido", usuarioBeans);

				RequestDispatcher view = request.getRequestDispatcher("cadastroTelefones.jsp");
				request.setAttribute("telefones", telefoneDao.readTel(usuarioBeans.getId()));
				view.forward(request, response);

			} else if (acao != null && acao.endsWith("deleteFone")) {

				UsuarioBeans usuarioBeans = (UsuarioBeans) request.getSession().getAttribute("userEscolhido");
				String foneId = request.getParameter("foneId");
				telefoneDao.deleteTel(foneId);

				RequestDispatcher view = request.getRequestDispatcher("cadastroTelefones.jsp");
				request.setAttribute("telefones", telefoneDao.readTel(usuarioBeans.getId()));
				request.setAttribute("msg", "Removido com Sucesso!!");
				view.forward(request, response);
				
			}else {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				view.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			UsuarioBeans usuarioBeans = (UsuarioBeans) request.getSession().getAttribute("userEscolhido");
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");

			TelefoneBeans telefoneBeans = new TelefoneBeans();
			telefoneBeans.setNumero(numero);
			telefoneBeans.setTipo(tipo);
			telefoneBeans.setUsuario(usuarioBeans.getId());

			if (numero == null || numero.isEmpty()) {
				request.setAttribute("msg", "INFORME O NUMERO DE TELEFONE!");
				RequestDispatcher view = request.getRequestDispatcher("cadastroTelefones.jsp");
				request.setAttribute("telefones", telefoneDao.readTel(usuarioBeans.getId()));
				view.forward(request, response);

			} else if (numero != null && !numero.isEmpty() && !telefoneDao.validarNumero(numero)) {
				request.setAttribute("msg", "TELEFONE JÁ EXISTI!");
				RequestDispatcher view = request.getRequestDispatcher("cadastroTelefones.jsp");
				request.setAttribute("telefones", telefoneDao.readTel(usuarioBeans.getId()));
				request.setAttribute("fone", telefoneBeans);
				view.forward(request, response);

			} else if (tipo == null || tipo.isEmpty()) {

				request.setAttribute("msg", "INFORME O TIPO DE TELEFONE!");
				RequestDispatcher view = request.getRequestDispatcher("cadastroTelefones.jsp");
				request.setAttribute("telefones", telefoneDao.readTel(usuarioBeans.getId()));
				request.setAttribute("fone", telefoneBeans);
				view.forward(request, response);

			} else {

				telefoneDao.createTel(telefoneBeans);

				request.getSession().setAttribute("userEscolhido", usuarioBeans);
				request.setAttribute("userEscolhido", usuarioBeans);

				RequestDispatcher view = request.getRequestDispatcher("cadastroTelefones.jsp");
				request.setAttribute("telefones", telefoneDao.readTel(usuarioBeans.getId()));
				request.setAttribute("msg", "Salvo com Sucesso!!");
				view.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
