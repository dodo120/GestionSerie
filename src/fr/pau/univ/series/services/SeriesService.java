package fr.pau.univ.series.services;

import java.util.List;

import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

@Path("/series")
public class SeriesService {
	// Méthode de lecture des données
	/**
	* Lecture de la série correspondant à l'id passé en paramètre.
	* 
	* @param serieId l'id de la série à lire
	* @return Une réponse HTTP au format json
	* @throws DaoException
	*/
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSerie(@jakarta.ws.rs.PathParam("id") final int serieId){
		Serie s = null;
		try {
			s = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Serie not found").build();
			} else {
				final GenericEntity<Serie> json = new GenericEntity<>(s) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}

	/**
	 * Lecture de toutes les séries de notre BDD
	 *
	 * @return les séries se trouvant dans notre BDD.
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAllSeries() throws DaoException {
		try {
			final List<Serie> ret = DaoFactory.getInstance().getSerieDao().readAllSeries();
			final GenericEntity<List<Serie>> json = new GenericEntity<>(ret) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
		/**
	 * D'après l'id de la saison en paramètre, on récupère la série correspondante.
	 *
	 * @param idSaison l'id de la saison
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/bySaison{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSerieBySaison(@jakarta.ws.rs.PathParam("id") int idSaison) {
		Serie s = null;
		try {
			s = DaoFactory.getInstance().getSerieDao().readSerieBySaison(idSaison);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Serie not found").build();
			} else {
				final GenericEntity<Serie> json = new GenericEntity<>(s) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
		/**
	 * D'après l'id de la saison en paramètre, on récupère la série correspondante.
	 *
	 * @param idEpisode l'id de l'épisode
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/byEpisode{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSerieByEpisode(@jakarta.ws.rs.PathParam("id") int idEpisode){
		Serie s = null;
		try {
			s = DaoFactory.getInstance().getSerieDao().readSerieByEpisode(idEpisode);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Serie not found").build();
			} else {
				final GenericEntity<Serie> json = new GenericEntity<>(s) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
	
	
	// Méthode d'écriture des données
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSerie(final MultivaluedMap<String, String> formParams) {
		//TODO: à implémenter plus tard.
		return null;
	}
	
}
