package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/episode")
public class EpisodeDao implements IEpisodeDao {

	private final DaoBddHelper bdd;

	/**
	 * Le constructeur de notre classe.
	 * Essaye de récupérer une instance de la classe DaoBddHelper.
	 * 
	 * @throws DaoException
	 */
	public EpisodeDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	// Méthodes de lecture des données
	/**
	 * Lecture de l'épisode correspondant à l'id passé en paramètre.
	 * 
	 * @param EpisodeId
	 * @return l'épisode correspondant à l'id passé en paramètre.
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Episode readEpisode(@jakarta.ws.rs.PathParam("id") final int EpisodeId) throws DaoException {
		final TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findById", Episode.class);
		query.setParameter("id", EpisodeId);
		final List<Episode> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	/**
	 * Lecture de tous les épisodes possible dans notre BDD
	 * 
	 * @return une liste de tous les épisodes dans la BDD
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Episode> readAllEpisode() throws DaoException {
		TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findAll", Episode.class);
		return query.getResultList();
	}

	/**
	 * Lecture des épisodes de la saison dont l'id est passé en paramètre.
	 * 
	 * @param idSaison
	 * @return une liste de tous les épisodes de la saison dont l'id est passé en
	 *         paramètre.
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Episode> readEpisodeBySaison(@jakarta.ws.rs.PathParam("id") int idSaison) {
		TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findBySaison", Episode.class);
		query.setParameter("id", idSaison);
		return query.getResultList();
	}

	/**
	 * Lecture des épisodes de la série dont l'id est passé en paramètre.
	 * 
	 * @param idSerie
	 * @return une liste de tous les épisodes de la série dont l'id est passé en
	 *         paramètre.
	 * @throws DaoException
	 */
	@Override
	public List<Episode> readEpisodeBySerie(int idSerie) {
		TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findBySaison", Episode.class);
		query.setParameter("id", idSerie);
		return query.getResultList();
	}

	// Méthodes d'édition des données.
	/**
	 * Création d'un épisode dans la BDD. Nous utilisons une transaction pour éviter
	 * les problèmes de concurrence.
	 * 
	 * @param e              l'épisode à créer
	 * @param useTransaction true si on veut utiliser une transaction, false sinon
	 * @return l'épisode créé
	 * @throws DaoException
	 */
	@Override
	public Episode createEpisode(final Episode e, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().persist(e);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
			return e;
		} catch (final PersistenceException e1) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de créer la série", e1);
		}
	}

	/**
	 * Mise à jour de l'épisode passé en paramètre. Nous utilisons une transaction
	 * pour éviter les problèmes de concurrence.
	 * 
	 * @param e              l'épisode à mettre à jour
	 * @param useTransaction true si on veut utiliser une transaction, false sinon
	 * @throws DaoException
	 */
	@Override
	public void updateEpisode(final Episode e, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().merge(e);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
		} catch (final PersistenceException e1) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de modifier la série", e1);
		}
	}

	/**
	 * Suppression de l'épisode passé en paramètre. Nous utilisons une transaction
	 * pour éviter les problèmes de concurrence.
	 * 
	 * @param e              l'épisode à supprimer
	 * @param useTransaction true si on veut utiliser une transaction, false sinon
	 * @throws DaoException
	 */
	@Override
	public void deleteEpisode(final Episode e, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().remove(e);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
		} catch (final PersistenceException e1) {
			if (useTransaction) {
				this.bdd.rollbackTransaction();
			}
			throw new DaoException("Impossible de supprimer la série", e1);
		}
	}

}
