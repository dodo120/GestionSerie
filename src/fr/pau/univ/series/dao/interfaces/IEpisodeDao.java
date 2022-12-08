package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;

public interface IEpisodeDao {
	
	public Episode createEpisode(Episode episode) throws DaoException;
	public Episode readEpisode(int id) throws DaoException;
	public List<Episode> readAllEpisode() throws DaoException;
	public List<Episode> readEpisodeBySerie(int idSerie) throws DaoException;
	public List<Episode> readEpisodeBySaison(int idSaison) throws DaoException;
	public void updateEpisode(Episode episode,final boolean useTransaction) throws DaoException;
	public void deleteEpisode(Episode episode,final boolean useTransaction) throws DaoException;
	
}