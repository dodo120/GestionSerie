package fr.pau.univ.series.services;

import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;
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

@Path("/saisons")
public class SaisonService {
	// Méthode de lecture des données
	/**
	* Lecture de la saison correspondant à l'id passé en paramètre.
	* 
	* @param saisonId l'id de l'épisode à lire
	* @return Une réponse HTTP au format json
	* @throws DaoException
	*/
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSaison(@jakarta.ws.rs.PathParam("id") final int saisonId){
		Saison s = null;
		try {
			s = DaoFactory.getInstance().getSaisonDao().readSaison(saisonId);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Saison not found").build();
			} else {
				final GenericEntity<Saison> json = new GenericEntity<>(s) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}

	/**
	 * Lecture de toutes les saisons de notre BDD
	 *
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAllSaisons() throws DaoException {
		try {
			final List<Saison> ret = DaoFactory.getInstance().getSaisonDao().readAllSaison();
			final GenericEntity<List<Saison>> json = new GenericEntity<>(ret) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
		/**
	 * D'après l'id de la série en paramètre, on récupère les saisons correspondantes.
	 *
	 * @param idSerie l'id de la série
	 * @return Une réponse HTTP au format json
	 * @throws DaoException
	 */
	@GET
	@Path("/bySerie{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSaisonBySerie(@jakarta.ws.rs.PathParam("id") int idSerie) {
		List<Saison> s = null;
		try {
			s = DaoFactory.getInstance().getSaisonDao().readSaisonBySerie(idSerie);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Saisons not found").build();
			} else {
				final GenericEntity<List<Saison>> json = new GenericEntity<>(s) {};
				return Response.ok().entity(json).build();
			}
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
	}
	
	/**
	* D'après l'id de l'épisode en paramètre, on récupère la saison correspondante.
	*
	* @param idEpisode l'id de la série
	* @return Une réponse HTTP au format json
	* @throws DaoException
	*/
	@GET
	@Path("/byEpisode{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readSaisonByEpisode(@jakarta.ws.rs.PathParam("id") int idEpisode){
		Saison s = null;
		try {
			s = DaoFactory.getInstance().getSaisonDao().readSaisonByEpisode(idEpisode);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Saison not found").build();
			} else {
				final GenericEntity<Saison> json = new GenericEntity<>(s) {};
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
	public Response addSaison(final MultivaluedMap<String, String> formParams) {
		if (formParams.get("titre") == null || formParams.get("numero") == null ||
			formParams.get("serieId") == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		} else {
			Serie s = null;
			try {
				s = DaoFactory.getInstance().getSerieDao()
				.readSerie(Integer.parseInt(formParams.getFirst("saisonId")));
			} catch (final DaoException e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(e.getMessage()).build();
			} catch (final NumberFormatException e) {
				return Response.status(Response.Status.BAD_REQUEST)
				.entity("saisonId must be an intger").build();
			}
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND)
				.entity("Parent season not found").build();
			}
			// Suite de la méthode avec statut CREATED si OK, INTERNAL_SERVER_ERROR sinon
			return Response.status(Response.Status.CREATED)
					.entity("Season created").build(); 
		}
	}
	
}
