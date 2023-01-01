package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

//Cette classe respecte le pattern DAO et implémente son interface IESaisonDAO.
//Elle permet de faire le lien entre notre entité (classe) Saison et notre table des saisons dans la BDD.
@Path("/saison")
public class SaisonDao implements ISaisonDao {

	private final DaoBddHelper bdd;

	/**
	 * Le constructeur de notre classe.
	 * Essaye de récupérer une instance de la classe DaoBddHelper.
	 * 
	 * @throws DaoException
	 */
	public SaisonDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	// Méthodes de lecture des données
	/**
	 * Lecture de la saison correspondant à l'id passé en paramètre.
	 * 
	 * @param SaisonId
	 * @return la saison correspondant à l'id passé en paramètre.
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Saison readSaison(@jakarta.ws.rs.PathParam("id") final int SaisonId) throws DaoException {
		final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findById", Saison.class);
		query.setParameter("id", SaisonId);
		final List<Saison> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	/**
	 * Lecture des saisons de la série dont l'id est passé en paramètre.
	 * 
	 * @param idSerie
	 * @return les saisons appartenant à la série dont l'id est passé en paramètre.
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/bySerie{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Saison> readSaisonBySerie(@jakarta.ws.rs.PathParam("id") int idSerie) throws DaoException {
		TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findBySerie", Saison.class);
		query.setParameter("id", idSerie);
		return query.getResultList();
	}

	/**
	 * Nous cherchons la saison correspondante à l'épisode dont l'id est passé en
	 * paramètre.
	 * 
	 * @param idEpisode
	 * @return la saison correspondante à l'épisode dont l'id est passé en
	 *         paramètre.
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/byEpisode{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Saison readSaisonByEpisode(@jakarta.ws.rs.PathParam("id") int idEpisode) throws DaoException {
		final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findByEpisode", Saison.class);
		query.setParameter("id", idEpisode);
		final List<Saison> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	/**
	 * Lecture de toutes les saisons de la BDD.
	 * 
	 * @return les saisons de la BDD.
	 * @throws DaoException
	 */
	@Override
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Saison> readAllSaison() throws DaoException {
		TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findAll", Saison.class);
		return query.getResultList();
	}

	// Méthodes d'édition des données
	/**
	 * Création d'une saison dans la BDD.
	 * 
	 * @param s              la saison à créer.
	 * @param useTransaction si vrai, on utilise une transaction.
	 * @return la saison créée.
	 * @throws DaoException
	 */
	@Override
	public Saison createSaison(final Saison s, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().persist(s);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
			return s;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de créer la série", e);
		}
	}

	/**
	 * Mise à jour d'une saison dans la BDD.
	 * 
	 * @param s              la saison à mettre à jour.
	 * @param useTransaction si vrai, on utilise une transaction.
	 * @throws DaoException
	 */
	@Override
	public void updateSaison(final Saison s, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().merge(s);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de modifier la série", e);
		}
	}

	/**
	 * Suppression d'une saison dans la BDD.
	 * 
	 * @param s              la saison à supprimer.
	 * @param useTransaction si vrai, on utilise une transaction.
	 * @throws DaoException
	 */
	@Override
	public void deleteSaison(Saison s, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().remove(s);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
		} catch (final PersistenceException e) {
			if (useTransaction) {
				this.bdd.rollbackTransaction();
			}
			throw new DaoException("Impossible de supprimer la série", e);
		}
	}

}
