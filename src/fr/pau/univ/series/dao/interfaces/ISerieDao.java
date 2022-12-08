package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

//Une interface est une classe qui ne peut pas être instancié. Elle doit donc avoir des classes filles qui lui font références.
//Une interface n'a pas d'attributs, seulement des prototypes de méthodes (donc pas de code).
//Ca sert donc à vérifier et obliger les classes filles à suivre son implémentation. Donc si une classe veut hériter de l'interface,
//elle devra obligatoirement avoir les méthodes ci-dessous.

//Cette interface se concentre sur la classe SerieDAO
public interface ISerieDao {

	// Les méthodes en lecture seules
	public Serie readSerie(int id) throws DaoException;

	public List<Serie> readAllSeries() throws DaoException;

	public Serie readSerieBySaison(int idSaison) throws DaoException;

	// Les méthodes d'écritures (d'édition)
	public Serie createSerie(Serie serie) throws DaoException;

	public void updateSerie(Serie serie, final boolean useTransaction) throws DaoException;

	public void deleteSerie(Serie serie, final boolean useTransaction) throws DaoException;

}
