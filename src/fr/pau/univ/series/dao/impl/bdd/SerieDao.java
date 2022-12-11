package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

//Cette classe respecte le pattern DAO et implémente son interface ISerieDAO.
//Elle permet de faire le lien entre notre entité (classe) Serie et notre table des séries dans la BDD.
public class SerieDao implements ISerieDao {

	private final DaoBddHelper bdd;

	/**
	 * Le constructeur de notre classe.
	 * Essaye de récupérer une instance de la classe DaoBddHelper.
	 * 
	 * @throws DaoException
	 */
	public SerieDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	// Méthode de lecture des données
	/**
	 * Lecture de la série correspondant à l'id passé en paramètre.
	 * 
	 * @param serieId l'id de la série à lire
	 * @return la série correspondant à l'id passé en paramètre.
	 * @throws DaoException
	 */
	@Override
	public Serie readSerie(final int serieId) throws DaoException {
		final TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findById", Serie.class);
		query.setParameter("id", serieId);
		final List<Serie> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	/**
	 * Lecture de toutes les séries de notre BDD
	 *
	 * @return les séries se trouvant dans notre BDD.
	 * @throws DaoException
	 */
	@Override
	public List<Serie> readAllSeries() throws DaoException {
		TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findAll", Serie.class);
		return query.getResultList();
	}

	/**
	 * D'après l'id de la saison en paramètre, on récupère la série correspondante.
	 *
	 * @param idSaison l'id de la saison
	 * @return la série dont la saison appartient
	 * @throws DaoException
	 */
	@Override
	public Serie readSerieBySaison(int idSaison) {
		final TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findBySaison", Serie.class);
		query.setParameter("id", idSaison);
		final List<Serie> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	/**
	 * D'après l'id de la saison en paramètre, on récupère la série correspondante.
	 *
	 * @param idEpisode l'id de l'épisode
	 * @return la série dont la saison appartient
	 * @throws DaoException
	 */
	@Override
	public Serie readSerieByEpisode(int idEpisode) throws DaoException {
		TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findByEpisode", Serie.class);
		query.setParameter("id", idEpisode);
		final List<Serie> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	// Méthodes d'édition des données
	/**
	 * Création d'une nouvelle série dans notre BDD.
	 *
	 * @param s              la série à créer
	 * @param useTransaction si on utilise une transaction ou non
	 * @return la série créée
	 * @throws DaoException
	 */
	@Override
	public Serie createSerie(final Serie s, final boolean useTransaction) throws DaoException {
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
	 * Mise à jour de la série dans notre BDD.
	 *
	 * @param s              la série à créer
	 * @param useTransaction si on utilise une transaction ou non
	 * @throws DaoException
	 */
	@Override
	public void updateSerie(final Serie s, final boolean useTransaction) throws DaoException {
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
	 * Suppression de la série dans notre BDD.
	 *
	 * @param s              la série à créer
	 * @param useTransaction si on utilise une transaction ou non
	 * @throws DaoException
	 */
	@Override
	public void deleteSerie(final Serie s, final boolean useTransaction) throws DaoException {
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
