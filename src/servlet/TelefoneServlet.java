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
 * Servlet implementation class TelefoneServlet
 */
@WebServlet("/salvarTelefones")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UsuarioDao usuarioDao = new UsuarioDao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TelefoneServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
		String user = request.getParameter("users");
		UsuarioBeans usuarioBeans = usuarioDao.consultar(user);
		request.getSession().setAttribute("users", usuarioBeans.getId());
		request.getSession().setAttribute("nomeUser", usuarioBeans.getNome());

		RequestDispatcher view = request.getRequestDispatcher("telefones.jsp");
		request.setAttribute("userEscolhido",usuarioBeans );
		
		view.forward(request, response);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
