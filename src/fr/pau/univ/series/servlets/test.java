package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/test")
public class test extends HttpServlet{
	public test() {
	}

	/**
	 * Méthode qui gère les requêtes GET.
	 * 
	 * @param request La requête HTTP
	 * @param response La réponse HTTP
	 * @throws ServletException Si une erreur de servlet survient
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		
		String redir = "http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/test");
		response.sendRedirect(redir);
	}
}
