package fr.pau.univ.series.dao;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.dao.impl.bdd.EpisodeDao;
import fr.pau.univ.series.dao.impl.bdd.SaisonDao;
import fr.pau.univ.series.dao.impl.bdd.SerieDao;

//Un pattern DAO permet de facilement créer un lien entre "nos classes Java et notre BDD PostegreSQL"
//Cette classe est de type Factory. Le pattern Factory permet d'avoir plusieurs classes abstraites qui auront chacune leurs 
//Spécificités. Pour faciliter la création selon le cas, nous créons un Factory (une usine) qui s'occupe de l'instanciation des classes
//selon notre besoin, tout ça depuis une seule et même classe (donc pas besoin d'avoir une référence entre toutes les classes abstraites).
//
//Vous pouvez en apprendre plus sur le pattern DAO ici : https://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/
//Et sur le pattern Factory ici : https://refactoring.guru/fr/design-patterns/factory-method
//https://refactoring.guru/fr/design-patterns/abstract-factory
public class DaoFactory {

	private static DaoFactory instance = null;

	private ISerieDao serieDao = null;
	private IEpisodeDao episodeDao = null;
	private ISaisonDao saisonDao = null;


	/**
	 * Ici, nous implémentons le côté Singleton de cette classe.
	 * S'il n'y a pas d'instance déjà créée, nous ne créons une, sinon nous
	 * retournons l'instance déjà créée.
	 * Vous pouvez en apprendre plus sur le pattern Singleton ici :
	 * https://refactoring.guru/fr/design-patterns/singleton
	 *
	 * @return instance de la classe DaoFactory
	 */
	public static DaoFactory getInstance() {
		if (instance == null) {
			instance = new DaoFactory();
		}
		return instance;
	}

	/**
	 * Ici, le cas est le même que précédemment en incluant une clause try-catch
	 * pour
	 * attraper de potentielles erreurs pendant l'instanciation.
	 * De plus, cette méthode s'occupe seulement de la classe SerieDAO
	 *
	 * @return instance de la classe SerieDAO
	 * @throws DaoException
	 */
	public ISerieDao getSerieDao() {
		if (this.serieDao == null) {
			try {
				this.serieDao = new SerieDao();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return this.serieDao;
	}

	/**
	 * Ici, le cas est le même que précédemment en incluant une clause try-catch
	 * pour
	 * attraper de potentielles erreurs pendant l'instanciation.
	 * De plus, cette méthode s'occupe seulement de la classe SaisonDAO
	 *
	 * @return instance de la classe SaisonDAO
	 * @throws DaoException
	 */
	public ISaisonDao getSaisonDao() throws DaoException {
		if (this.saisonDao == null) {
			try {
				this.saisonDao = new SaisonDao();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return this.saisonDao;
	}

	/**
	 * Ici, le cas est le même que précédemment en incluant une clause try-catch
	 * pour
	 * attraper de potentielles erreurs pendant l'instanciation.
	 * De plus, cette méthode s'occupe seulement de la classe EpisodeDAO
	 *
	 * @return instance de la classe EpisodeDAO
	 * @throws DaoException
	 */
	public IEpisodeDao getEpisodeDao() throws DaoException {
		if (this.episodeDao == null) {
			try {
				this.episodeDao = new EpisodeDao();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return this.episodeDao;
	}
	

}
