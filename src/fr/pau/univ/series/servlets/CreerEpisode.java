package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.DataProvider;
import fr.pau.univ.series.model.Episode;
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
@WebServlet(urlPatterns = "/creerEpisode")
public class CreerEpisode extends HttpServlet {
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idSaison = Integer.parseInt(request.getParameter("saison"));
            String nomEpisode = request.getParameter("nomEpisode");
            if (nomEpisode != null && !nomEpisode.isBlank()) {
                Episode ep = new Episode(nomEpisode, idSaison);
                Saison sais = daoSais.readSaison(idSaison);
                DaoBddHelper.getInstance().beginTransaction();
                ep = DaoFactory.getInstance().getEpisodeDao().createEpisde(ep, false);
                sais.addEpisode(ep);
                DaoFactory.getInstance().getSaisonDao().updateSaison(sais, false);
                DaoBddHelper.getInstance().commitTransaction();
            }
        } catch (DaoException e) {
            DaoBddHelper.getInstance().rollbackTransaction();
            request.setAttribute("erreur", e.getMessage());
        }
    }
}
