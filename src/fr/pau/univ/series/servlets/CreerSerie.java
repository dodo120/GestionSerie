package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListeSeries
 */
@WebServlet(urlPatterns = "/creerSerie/")
public class CreerSerie extends HttpServlet {


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
		final String nom = request.getParameter("nomNouvelleSerie");
		if (nom != null && !nom.isBlank()) {
			try {
				DaoFactory.getInstance().getSerieDao().createSerie(new Serie(nom),true);
				
			} catch (final DaoException e) {
				request.setAttribute("erreur", e.getMessage());
			}
		}
		final String redir = "http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/listerSeries");
		response.sendRedirect(redir);
	}

}
