package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/etat/")
 class ModifierEtat extends HttpServlet{
	/**
	 * Méthode qui gère les requêtes GET.
	 * 
	 * @param request La requête HTTP
	 * @param response La réponse HTTP
	 * @throws ServletException Si une erreur de servlet survient
	 * @throws IOException Si une erreur d'entrée/sortie survient
	 */
	
}
