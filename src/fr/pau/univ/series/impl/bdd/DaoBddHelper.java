package fr.pau.univ.series.impl.bdd;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import fr.pau.univ.series.dao.SeriesContextListener;
import fr.pau.univ.series.exception.DaoException;

//Cette classe simplifie la liaison entre notre projet, nos entités, notre base de données et l'entity manager. 
class DaoBddHelper {

	private static DaoBddHelper instance;
	private final EntityManager em;

	/**
	 * Returns the instance of this singleton.
	 *
	 * @return the instance of this singleton.
	 * @throws DaoException
	 *
	 */
	
	public enum PersistencyAction{
		PERSIST,
		MERGE,
		REMOVE;
	}
	
	public static final DaoBddHelper getInstance() throws DaoException {
		if (instance == null) {
			instance = new DaoBddHelper();
		}
		return instance;
	}

	private DaoBddHelper() throws DaoException {
		try {
			this.em = SeriesContextListener.getEntityManager();
			System.out.println("EntityManager créé.");
		} catch (final Exception e) {
			throw new DaoException("Impossible de créer l'EntityManager.", e);
		}
	}

	public EntityManager getEm() {
		return this.em;
	}

	public void beginTransaction() {
		this.em.getTransaction().begin();
	}

	public void commitTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (trans.isActive()) {
			trans.commit();
		}
	}

	public void rollbackTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (trans.isActive()) {
			trans.rollback();
		}
	}

	public void makePersistencyAction(Object obj, boolean useTrans, PersistencyAction action)
			throws PersistenceException {
		try {
			if (useTrans) {
				beginTransaction();
			}
			switch (action) {
				case PERSIST:
					this.em.persist(obj);
					break;
				case MERGE:
					this.em.merge(obj);
					break;
				case REMOVE:
					this.em.remove(obj);
					break;
			}
			if (useTrans) {
				commitTransaction();
			}
		} catch (final PersistenceException e) {
			if (useTrans) {
				rollbackTransaction();
			}
			throw e;
		}
	}

}