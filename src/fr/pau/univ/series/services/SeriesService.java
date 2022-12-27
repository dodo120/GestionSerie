package fr.pau.univ.series.services;

import java.util.Date;

import org.apache.catalina.User;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;



public class SeriesService {
	
	public Response autenticateUser(final MultivaluedMap<String, String> formParams) {
		if (formParams.get("user") == null || formParams.get("pwd") == null) {
			return Response.status(Response.Status.BAD_REQUEST)
			.entity("Invalid parameters").build();
		}
		
		final String login = formParams.get("user").get(0);
		final String pwd = formParams.get("pwd").get(0);
		
		if (login == null || login.isBlank() || pwd == null || pwd.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST)
			.entity("Invalid parameters").build();
		}
		
		try {
			final User usr = DaoFactory.getInstance().getUserDao().readUserByLogin(login);
			if (usr == null || !PasswordHelper.verifyUserPassword(pwd, usr.getPassword())) {
			return Response.status(Response.Status.BAD_REQUEST)
			.entity("Unknown user or bad password").build();
		}
		final String token = createJWT(usr, TOKEN_LIFETIME_MS);
			return Response.ok(token).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSerieById(@PathParam("id") final int idSerie) {
		Serie s = null;
		try {
			s = DaoFactory.getInstance().getSerieDao().readSerie(idSerie);
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

	/*public static User checkToken(final MultivaluedMap<String, String> formParams) throws ServiceException {
	*	if (formParams.get("token") == null) {
	*		throw new ServiceException("Vous devez fournir un token d'identification valide");
	*	}
	*	final String token = formParams.getFirst("token");
	*  if (token == null || token.isBlank()) {
	*		throw new ServiceException("Vous devez fournir un token d'identification valide");
	*	}
	*		Claims claims = null;
	*	try {
	*		claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_API_KEY))
	*		.parseClaimsJws(token).getBody();
	*	} catch (final Exception e) {
	*		throw new ServiceException("Token invalide");
	*	}
	*	final Date now = new Date(System.currentTimeMillis());
	*  if (now.after(claims.getExpiration())) {
	*		throw new ServiceException("La durée de validité du Token est expirée.");
	*	}
	*	final User ret = new User();
	*	ret.setLogin(claims.getIssuer());
	*	ret.setRole(UserRole.valueOf(claims.getSubject()));
	*	return ret;
	*	
	*}*/
}	