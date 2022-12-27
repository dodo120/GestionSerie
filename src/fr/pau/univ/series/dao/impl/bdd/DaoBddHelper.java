package fr.pau.univ.series.dao.impl.bdd;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import fr.pau.univ.series.dao.SeriesContextListener;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;

//Cette classe simplifie la liaison entre notre projet, nos entités, notre base de données et l'entity manager. 
public class DaoBddHelper {

	private static DaoBddHelper instance;
	private final EntityManager em;

	public enum PersistencyAction {
		PERSIST,
		MERGE,
		REMOVE;
	}

	/**
	 * Retourne l'instance du singleton.
	 *
	 * @return l'insance du singleton.
	 * @throws DaoException
	 *
	 */
	public static final DaoBddHelper getInstance() throws DaoException {
		if (instance == null) {
			instance = new DaoBddHelper();
		}
		return instance;
	}

	/**
	 * Le constructeur de notre classe.
	 * Essaye de récupérer une instance de l'entity manager.
	 *
	 * @throws DaoException
	 *
	 */
	private DaoBddHelper() throws DaoException {
		try {
			this.em = SeriesContextListener.getEntityManager();
			System.out.println("EntityManager créé.");
		} catch (final Exception e) {
			throw new DaoException("Impossible de créer l'EntityManager.", e);
		}
	}

	/**
	 * Le getter pour récupérer l'entity manager attaché à cette classe.
	 *
	 * @return l'entity manager.
	 *
	 */
	public EntityManager getEm() {
		return this.em;
	}

	/**
	 * Lance une transaction SQL
	 */
	public void beginTransaction() {
		this.em.getTransaction().begin();
	}

	/**
	 * Commit la transaction SQL actuelle (si elle existe)
	 */
	public void commitTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (trans.isActive()) {
			trans.commit();
		}
	}

	/**
	 * Abandonne les changements de la transaction SQL actuelle (si elle existe)
	 */
	public void rollbackTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (trans.isActive()) {
			trans.rollback();
		}
	}

	/**
	 * Permet de démarrer, envoyer ou abandonner une transaction selon les
	 * paramètres.
	 * 
	 * @param obj      l'objet à persister
	 * @param useTrans true si on veut utiliser une transaction, false sinon
	 * @param action   l'action à effectuer (persist, merge ou remove)
	 */
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
	
	
	public void addSerie(Serie s) {
		
	}
	public void addSaison(Saison s) {
		
	}	
	public void addEpisode(Episode e) {
		
	}

}