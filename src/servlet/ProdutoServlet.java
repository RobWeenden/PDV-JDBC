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
		
		RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
		
		try {
			if (acao != null && acao.equalsIgnoreCase("update")) {
				ProdutoBeans produtoBeans = produtoDao.consultarProd(produto);
				
				request.setAttribute("prod", produtoBeans);

			} else if (acao != null && acao.equalsIgnoreCase("delete")) {
				produtoDao.deleteProd(produto);
				
				request.setAttribute("msg", "Deletado com sucesso!!!");
				request.setAttribute("produtos", produtoDao.readProd());

			} else if (acao != null && acao.equalsIgnoreCase("listar")) {

				request.setAttribute("produtos", produtoDao.readProd());
				
			}else {
				request.setAttribute("produtos", produtoDao.readProd());
				
			}
			request.setAttribute("categorias", produtoDao.listCategoria());
			view.forward(request, response);
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
			String categoria = request.getParameter("categoria_id");

			ProdutoBeans produtoBeans = new ProdutoBeans();
			produtoBeans.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			produtoBeans.setNome(nome);
			produtoBeans.setCategoria_id(Long.parseLong(categoria));

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
					String valorParse = valor.replaceAll("\\.", "");
					valorParse = valorParse.replaceAll("\\,", ".");
					
					produtoBeans.setValor(Double.parseDouble(valorParse));
				}

				if (id == null || id.isEmpty() && produtoDao.validarNomeProduto(nome) && podeInserir) {
					request.setAttribute("msg", "Salvo com sucesso!!");
					produtoDao.createProd(produtoBeans);

				} else if (id != null && !id.isEmpty() && podeInserir) {
					
						request.setAttribute("msg", "Atualizado com sucesso!!!");
						produtoDao.updateProd(produtoBeans);
	
				}
				/*
				 * ATENÇÃO - CASO DESEJAR UTILIZAR O CODIGO ABAIXO -
				 * ELE É RESPONSAVEL EM NÃO PERMITIR QUE O PRODUTO SEJA ATUALIZADO,
				 * SENDO QUE JÁ EXISTA O MESMO NOME NO BANCO
				 * 
				 * SE DESEJAR COPIAR NA LINHA 138
				 * 
				 * if(!produtoDao.validarNomeProduto(nome)) { request.setAttribute("msg",
				 * "Produto já existi"); request.setAttribute("prod", produtoBeans); } else {
				 * request.setAttribute("msg", "Atualizado com sucesso!!!");
				 * produtoDao.updateProd(produtoBeans);
				 * }else {
				 */

				/*
				 * if(!podeInserir) { request.setAttribute("produtos", produtoBeans); }
				 */
				

				RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", produtoDao.readProd());
				request.setAttribute("categorias", produtoDao.listCategoria());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
