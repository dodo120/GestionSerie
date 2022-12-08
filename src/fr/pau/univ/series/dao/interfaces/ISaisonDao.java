package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

public interface ISaisonDao {
	
	public Saison createSaison(Saison saison) throws DaoException;
	public Saison readSaison(int id) throws DaoException;
	public List<Saison> readAllSaison() throws DaoException;
	public List<Saison> readSaisonBySerie(int idEpisode) throws DaoException;
	public void updateSaison(Saison saison, final boolean useTransaction) throws DaoException;
	public void deleteSaison(Saison saison, final boolean useTransaction) throws DaoException;
	
	
}