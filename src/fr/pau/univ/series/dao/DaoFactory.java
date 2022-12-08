package fr.pau.univ.series.dao;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.impl.bdd.EpisodeDao;
import fr.pau.univ.series.impl.bdd.SaisonDao;
import fr.pau.univ.series.impl.bdd.SerieDao;

public class DaoFactory {
	
	private static DaoFactory instance = null;
	
	private ISerieDao serieDao = null;
	private IEpisodeDao episodeDao = null;
	private ISaisonDao saisonDao = null;
	
	public static DaoFactory getInstance() {
		if(instance == null) {
			instance = new DaoFactory();
		} 
		return instance;
	}
	
	public ISerieDao getSerieDao() {
		if(this.serieDao == null) {
			try {
				this.serieDao = new SerieDao();
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.serieDao;
	}
	
	public ISaisonDao getSaisonDao() {
		if(this.saisonDao == null) {
			this.saisonDao = new SaisonDao();
		}
		return this.saisonDao;
	}
	
	public IEpisodeDao getEpisodeDao() {
		if(this.episodeDao == null) {
			this.episodeDao = new EpisodeDao();
		}
		return this.episodeDao;
	}

}
