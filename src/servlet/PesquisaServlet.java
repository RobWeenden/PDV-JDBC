package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UsuarioBeans;
import dao.UsuarioDao;

/**
 * Servlet implementation class PesquisaServlet
 */
@WebServlet("/servletPesquisa")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDao usuarioDao = new UsuarioDao();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String descricaoPesquisa = request.getParameter("descricaoconsulta");
		if(descricaoPesquisa == null || descricaoPesquisa.isEmpty()) {

			try {
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		String descricaoPesquisa = request.getParameter("descricaoconsulta");
		if(descricaoPesquisa != null && !descricaoPesquisa.trim().isEmpty()) {
			try {
				List<UsuarioBeans> listarDePesquisa = usuarioDao.searchUser(descricaoPesquisa);
				
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", listarDePesquisa);
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
