package fr.pau.univ.series.services;

import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

@Path("/episodes")
public class EpisodesService {
	// Méthode de lecture des données
	/**
	* Lecture de l'épisode correspondant à l'id passé en paramètre.
	* 
	* @param episodeId l'id de l'épisode à lire
	* @return Une réponse HTTP au format json
	* @throws DaoException
	*/
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readEpisode(@jakarta.ws.rs.PathParam("id") final int episodeId){
		Episode ep = null;
		try {
			ep = DaoFactory.getInstance().getEpisodeDao().readEpisode(episodeId);
			if (ep == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Episode not found").build();
			} else {
				final GenericEntity<Episode> json = new GenericEntity<>(ep) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}

	/**
	 * Lecture de tous les épisodes de notre BDD
	 *
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAllEpisodes() throws DaoException {
		try {
			final List<Episode> ret = DaoFactory.getInstance().getEpisodeDao().readAllEpisode();
			final GenericEntity<List<Episode>> json = new GenericEntity<>(ret) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
		/**
	 * D'après l'id de la saison en paramètre, on récupère les épisodes correspondants.
	 *
	 * @param idSaison l'id de la saison
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/bySaison{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSerieBySaison(@jakarta.ws.rs.PathParam("id") int idSaison) {
		List<Episode> ep = null;
		try {
			ep = DaoFactory.getInstance().getEpisodeDao().readEpisodeBySaison(idSaison);
			if (ep == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Episodes not found").build();
			} else {
				final GenericEntity<List<Episode>> json = new GenericEntity<>(ep) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
	
	/**
	* D'après l'id de la série en paramètre, on récupère les épisodes correspondants.
	*
	* @param idSerie l'id de la série
	* @return Une réponse HTTP au format json
	* @throws DaoException
	*/
	@GET
	@Path("/bySerie{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readEpisodeBySaison(@jakarta.ws.rs.PathParam("id") int idSerie){
		List<Episode> ep = null;
		try {
			ep = DaoFactory.getInstance().getEpisodeDao().readEpisodeBySerie(idSerie);
			if (ep == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Episodes not found").build();
			} else {
				final GenericEntity<List<Episode>> json = new GenericEntity<>(ep) {};
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
	public Response addEpisode(final MultivaluedMap<String, String> formParams) {
		return null;
	}
	
}
