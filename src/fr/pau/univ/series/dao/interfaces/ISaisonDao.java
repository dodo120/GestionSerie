package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

//Une interface est une classe qui ne peut pas être instancié. Elle doit donc avoir des classes filles qui lui font références.
//Une interface n'a pas d'attributs, seulement des prototypes de méthodes (donc pas de code).
//Ca sert donc à vérifier et obliger les classes filles à suivre son implémentation. Donc si une classe veut hériter de l'interface,
//elle devra obligatoirement avoir les méthodes ci-dessous.

//Cette interface se concentre sur la classe SaisonDAO
public interface ISaisonDao {

	// Les méthodes en lecture seules
	public Saison readSaison(int id) throws DaoException;

	public List<Saison> readAllSaison() throws DaoException;

	public List<Saison> readSaisonBySerie(int idSaison) throws DaoException;
	
	public Saison readSaisonByEpisode(int idEpisode) throws DaoException;

	// Les méthodes d'écritures (d'édition)
	public Saison createSaison(Saison saison, final boolean useTransaction) throws DaoException;

	public void updateSaison(Saison saison, final boolean useTransaction) throws DaoException;

	public void deleteSaison(Saison saison, final boolean useTransaction) throws DaoException;

}