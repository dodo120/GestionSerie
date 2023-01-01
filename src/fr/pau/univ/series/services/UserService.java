package fr.pau.univ.series.services;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.DatatypeConverter;

@Path("/users")
public class UserService {
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
	
	private String createJWT(final User user, final long ttlMillis) {
		final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_API_KEY);
		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		final Key signingKey = new SecretKeySpec(apiKeySecretBytes,
		signatureAlgorithm.getJcaName());
		final long nowMillis = System.currentTimeMillis();
		final Date now = new Date(nowMillis);
		final String id = UUID.randomUUID().toString();
		final JwtBuilder builder = Jwts.builder().setId(id)
		.setIssuedAt(now)
		.setSubject(user.getRole().name())
		.setIssuer(user.getLogin()).signWith(signatureAlgorithm, signingKey);
		final long expMillis = nowMillis + ttlMillis;
		final Date exp = new Date(expMillis);
		builder.setExpiration(exp);
		return builder.compact();
	}

}
