package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;

//Une interface est une classe qui ne peut pas être instancié. Elle doit donc avoir des classes filles qui lui font références.
//Une interface n'a pas d'attributs, seulement des prototypes de méthodes (donc pas de code).
//Ca sert donc à vérifier et obliger les classes filles à suivre son implémentation. Donc si une classe veut hériter de l'interface,
//elle devra obligatoirement avoir les méthodes ci-dessous.

//Cette interface se concentre sur la classe EpisodeDAO
public interface IEpisodeDao {

	// Les méthodes en lecture seules
	public Episode readEpisode(int id) throws DaoException;

	public List<Episode> readAllEpisode() throws DaoException;

	public List<Episode> readEpisodeBySerie(int idSerie) throws DaoException;

	public List<Episode> readEpisodeBySaison(int idSaison) throws DaoException;

	// Les méthodes d'écritures (d'édition)
	public Episode createEpisode(Episode episode) throws DaoException;

	public void updateEpisode(Episode episode, final boolean useTransaction) throws DaoException;

	public void deleteEpisode(Episode episode, final boolean useTransaction) throws DaoException;

}