package fr.pau.univ.series.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;

public class EpisodeDao implements IEpisodeDao {

	private final DaoBddHelper bdd;

	public EpisodeDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	//Méthodes de lecture des données

	@Override
	public Episode readEpisode(final int EpisodeId) throws DaoException {
		final TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findById", Episode.class);
		query.setParameter("id", EpisodeId);
		final List<Episode> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<Episode> readAllEpisode() throws DaoException {
		TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findAll", Episode.class);
		return query.getResultList();
	}

	@Override
	public List<Episode> readEpisodeBySaison(int idSaison) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Episode> readEpisodeBySerie(int idSaison) {
		// TODO Auto-generated method stub
		return null;
	}

	//Méthodes d'édition des données.
	
	@Override
	public Episode createEpisode(final Episode e) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEm().persist(e);
			this.bdd.commitTransaction();
			return e;
		} catch (final PersistenceException e1) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de créer la série", e1);
		}
	}
	
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
