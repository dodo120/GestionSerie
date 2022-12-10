package fr.pau.univ.series.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

//Cette classe respecte le pattern DAO et implémente son interface IESaisonDAO.
//Elle permet de faire le lien entre notre entité (classe) Saison et notre table des saisons dans la BDD.
public class SaisonDao implements ISaisonDao {

	private final DaoBddHelper bdd;

	public SaisonDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	//Méthodes de lecture des données

	@Override
	public Saison readSaison(final int SaisonId) throws DaoException {
		final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findById", Saison.class);
		query.setParameter("id", SaisonId);
		final List<Saison> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<Saison> readSaisonBySerie(int idSaison) throws DaoException {
		TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findBySerie", Saison.class);
		query.setParameter("id", idSaison);
		return query.getResultList();
	}
	
	@Override
	public Saison readSaisonByEpisode(int idEpisode) throws DaoException {
		final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findByEpisode", Saison.class);
		query.setParameter("id", idEpisode);
		final List<Saison> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}
	
	@Override
	public List<Saison> readAllSaison() throws DaoException {
		TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findAll", Saison.class);
		return query.getResultList();
	}
	
	//Méthodes d'édition des données
	
	@Override
	public Saison createSaison(final Saison s, final boolean useTransaction ) throws DaoException {
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
