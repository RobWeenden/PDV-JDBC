package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.UsuarioBeans;
import dao.UsuarioDao;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/salvarUsuario")
@MultipartConfig
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());// !IMPORTANTE REMOVER ESTA LINHA PARA UTILIZAR response.getOutputStream();
		try {

			String acao = request.getParameter("acao");
			String users = request.getParameter("users");

			if (acao != null && acao.equalsIgnoreCase("update")) {
				UsuarioBeans userUpdate = usuarioDao.consultar(users);
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				request.setAttribute("users", userUpdate);
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("delete")) {
				usuarioDao.delete(users);

				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("msg", "Deletado com Sucesso!!");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				view.forward(request, response);

			} else if (acao != null && acao.equals("listartodos")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				UsuarioBeans usuarioBeans = usuarioDao.consultar(users);
				if (usuarioBeans != null) {

					String contentType = "";
					byte[] fileBytes = null;
					String tipo = request.getParameter("tipo");

					if (tipo.equalsIgnoreCase("imagem")) {
						/* Converte a base64 da imagem do banco para byte[] */
						contentType = usuarioBeans.getContentType();
						fileBytes = new Base64().decodeBase64(usuarioBeans.getFotoBase64());

					} else if (tipo.equalsIgnoreCase("curriculo")) {
						/* Converte a base64 da imagem do banco para byte[] */
						contentType = usuarioBeans.getContentTypeCurriculo();
						fileBytes = new Base64().decodeBase64(usuarioBeans.getCurriculoBase64());
					}

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					/* Inserido os bytes codificados em um objeto de entrada para processamento */
					InputStream is = new ByteArrayInputStream(fileBytes);

					/* Inicio da resposta para o Browser */
					int read = 0;// inicializar o controle do fluxo
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					os.flush();// finalizar
					os.close();// fechar o processo/fluxo

				}
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				dispatcher.forward(request, response);

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
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
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
				String sexo = request.getParameter("sexo");
				String perfil = request.getParameter("perfil");

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
				usuarioBeans.setSexo(sexo);
				usuarioBeans.setPerfil(perfil);
				
				if(request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
					usuarioBeans.setAtivo(true);
					
				}else{
					usuarioBeans.setAtivo(false);
				}

				/*
				 * Inicio File upload de imagens e pdf
				 */
				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
					
 						String fotoBase64 = new Base64().encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));
						usuarioBeans.setFotoBase64(fotoBase64);
						usuarioBeans.setContentType(imagemFoto.getContentType());
						
						/*INICIO MINIATURA IMAGEM*/
						
						//Transformar em um BufferedImage
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						
						//Setar o Tipo da Imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB: bufferedImage.getType();
						
						//Criar imagem em miniatura - Trabalhar a parte grafica
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g2D = resizedImage.createGraphics();
						g2D.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g2D.dispose();
						
						//ESCREVER IMAGEM NOVAMENTE
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);
						
						//IMAGEM EM MINIATURA - gravar no DB pronto para ser retornado na Tela passando o cabeçalho
						String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
						
						usuarioBeans.setFotoBase64Miniatura(miniaturaBase64);
						
						/*FIM MINIATURA IMAGEM*/

					} else {
						usuarioBeans.setAtualizarImage(false);
//						usuarioBeans.setFotoBase64Miniatura(request.getParameter("fotoTemp"));
//						usuarioBeans.setFotoBase64(request.getParameter("fotoTemp"));
//						usuarioBeans.setContentType(request.getParameter("contentTypeTemp"));
					}

					/* Processar o PDF */
					Part curriculoPdf = request.getPart("curriculo");
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {

						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));
						usuarioBeans.setCurriculoBase64(curriculoBase64);
						usuarioBeans.setContentTypeCurriculo(curriculoPdf.getContentType());
						
					} else {
						usuarioBeans.setAtualizarPdf(false);

//						usuarioBeans.setCurriculoBase64(request.getParameter("curriculoTemp"));
//						usuarioBeans.setContentTypeCurriculo(request.getParameter("contentTypeTempCurriculo"));
					}
				}

				/*
				 * Fim File upload de imagens e pdf
				 */

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
						|| id.isEmpty() && usuarioDao.validarLogin(login) && usuarioDao.validarSenha(senha)) {// REALIZAR
																												// O
																												// CADASTRO
																												// DO
																												// USUARIO

					usuarioDao.create(usuarioBeans);
					request.setAttribute("msg", "Salvo com Sucesso!!");

				} else if (id != null && !id.isEmpty()) {// INSERIR E VALIDAR UPDATE
					usuarioDao.update(usuarioBeans);
					request.setAttribute("msg", "Atualizado com Sucesso!!");

					/*****
					 * REALIZAR A LIBERAÇÃO DA LOGICA ABAIXO CASO DESEJAR QUE NA ATUALIZAÇÃO DOS
					 * DADOS DO USUARIOS SÓ POSSA SER ALTERADO CASO O LOGIN OU SENHA NÃO EXITA NO
					 * SISTEMA CASO DESEJAR REMOVER AS LINHA DE CODIGO 150 E 151
					 */

					/*
					 * if (!usuarioDao.validarLogin(login)) {//VALIDAR LOGIN EXISTENTE
					 * request.setAttribute("msg", "Este Login ja existi!!");
					 * request.setAttribute("users", usuarioBeans);
					 * 
					 * } else if (!usuarioDao.validarSenha(senha)) {//VALIDAR SENHA EXISTENTE
					 * request.setAttribute("msg", "Esta Senha ja existi!!");
					 * request.setAttribute("users", usuarioBeans); }* else {//REALIZAR UPDATE DO
					 * USUARIO usuarioDao.update(usuarioBeans); request.setAttribute("msg",
					 * "Atualizado com Sucesso!!"); }
					 */
				}

				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.readAllUsers());
				dispatcher.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo convertStreamParaByte() Responsavel em converter a entrada do fluxo de
	 * dados da IMAGEM para byte[]
	 * 
	 * @param imagem
	 * @return
	 * @throws Exception
	 */
	private byte[] converteStreamParaByte(InputStream imagem) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}

}
