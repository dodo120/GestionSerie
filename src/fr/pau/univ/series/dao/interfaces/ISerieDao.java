package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

public interface ISerieDao {
	
	/**
	 * Créer 
	 * @param serie
	 * @return
	 */
	public Serie createSerie(Serie serie) throws DaoException;
	public Serie readSerie(int id) throws DaoException;
	public List<Serie> readAllSeries() throws DaoException;
	public Serie readSerieBySaison(int idSaison) throws DaoException;
	public void updateSerie(Serie serie) throws DaoException;
	public void deleteSerie(Serie serie) throws DaoException;
	
	
}
