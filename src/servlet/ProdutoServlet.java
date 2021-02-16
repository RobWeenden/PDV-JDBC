package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProdutoBeans;
import dao.ProdutoDao;

/**
 * Servlet implementation class ProdutoServlet
 */
@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProdutoDao produtoDao = new ProdutoDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProdutoServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String acao = request.getParameter("acao");
		String produto = request.getParameter("prod");

		try {
			if (acao.equalsIgnoreCase("update")) {
				ProdutoBeans produtoBeans = produtoDao.consultarProd(produto);
				RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("prod", produtoBeans);
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("delete")) {
				produtoDao.deleteProd(produto);
				RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("msg", "Deletado com sucesso!!!");
				request.setAttribute("produtos", produtoDao.readProd());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("listar")) {

				RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", produtoDao.readProd());
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
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", produtoDao.readProd());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");

			ProdutoBeans produtoBeans = new ProdutoBeans();
			produtoBeans.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			produtoBeans.setNome(nome);

			try {

				boolean podeInserir = true;
				if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Nome deve ser informado");
					podeInserir = false;
					request.setAttribute("prod", produtoBeans);

				} else if (quantidade == null || quantidade.isEmpty()) {
					request.setAttribute("msg", "Quantidade deve ser Informado");
					request.setAttribute("prod", produtoBeans);
					podeInserir = false;

				} else if (valor == null || valor.isEmpty()) {
					request.setAttribute("msg", "Valor deve ser informada");
					request.setAttribute("prod", produtoBeans);
					podeInserir = false;

				} else if (id == null || id.isEmpty() && !produtoDao.validarNomeProduto(nome)) {
					request.setAttribute("msg", "Produto já existi");
					request.setAttribute("prod", produtoBeans);
					podeInserir = false;
				}

				if (quantidade != null && !quantidade.isEmpty()) {
					produtoBeans.setQuantidade(Double.parseDouble(quantidade));
				}

				if (valor != null && !valor.isEmpty()) {
					produtoBeans.setValor(Double.parseDouble(valor));
				}

				if (id == null || id.isEmpty() && produtoDao.validarNomeProduto(nome) && podeInserir) {
					request.setAttribute("msg", "Salvo com sucesso!!");
					produtoDao.createProd(produtoBeans);

				} else if (id != null && !id.isEmpty() && podeInserir) {
					if (!produtoDao.validarNomeProduto(nome)) {
						request.setAttribute("msg", "Produto já existi");
						request.setAttribute("prod", produtoBeans);
					} else {
						request.setAttribute("msg", "Atualizado com sucesso!!!");
						produtoDao.updateProd(produtoBeans);
					}

				}

				/*
				 * if(!podeInserir) { request.setAttribute("produtos", produtoBeans); }
				 */

				RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", produtoDao.readProd());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
