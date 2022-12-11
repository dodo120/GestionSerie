package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.DataProvider;
import fr.pau.univ.series.model.Episode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/etat")
public class ModifierEtat extends HttpServlet {

	/**
	 * Méthode qui gère les requêtes GET.
	 * 
	 * @param request Requête
	 * @param response Réponse
	 * @throws ServletException Si une erreur de servlet survient
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final DataProvider data = DataProvider.getInstance();
		final int serieId = Integer.parseInt(request.getParameter("serie"));
		final int saisonId = Integer.parseInt(request.getParameter("saison"));
		final int episodeId = Integer.parseInt(request.getParameter("episode"));
		try {
			final Episode e = data.getEpisodeByIds(serieId, saisonId, episodeId);
			e.setVu(!e.isVu());
		} catch (final DaoException e) {
			request.setAttribute("erreur", e.getMessage());
			e.printStackTrace();
		}
		final String redir = "http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/liste");
		response.sendRedirect(redir);
	}

}
