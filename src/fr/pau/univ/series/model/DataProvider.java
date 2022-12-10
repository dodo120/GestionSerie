package fr.pau.univ.series.model;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import fr.pau.univ.series.exception.DaoException;

//Grosse classe
//Elle permet d'ajouter des données dans notre programme en dure sans s'occuper des données du serveur SQL.
//Elle devient obsolète dès lors que nous utilisons les données de notre serveur SQL (donc que nous connectons notre serveur SQL)
public class DataProvider {

	private static DataProvider instance;
	static int lastSerieId = 0;
	static int lastSaisonId = 0;
	static int lastEpisodeId = 0;

	private final List<Serie> listeSeries = new ArrayList<>();

	/**
	 * Returns the instance of this singleton.
	 *
	 * @return the instance of this singleton.
	 *
	 */
	public static final DataProvider getInstance() {
		if (instance == null) {
			instance = new DataProvider();
		}
		return instance;
	}

	/**
	 * @return the listeSeries
	 */
	public Collection<Serie> getAllSeries() {
		return this.listeSeries;
	}

	/**
	 * This method adds given Serie to the list of series.
	 *
	 * @param s The serie to add. <b>note :</b>given serie id is calculated before
	 *          storing. No need to set it befor calling this method.
	 * @return the added Serie
	 * @throws SeriesException if a Serie already exist with same id.
	 */
	public Serie addSerie(final Serie s) throws DaoException {
		this.listeSeries.add(s);
		return s;
	}

	/**
	 * This method removes given Serie from the list of series.
	 *
	 * @param s the Serie to remove.
	 * @throws SeriesException if given Serie doesn't exist in the list of series.
	 */
	public void removeSerie(final Serie s) throws DaoException {
		if (this.listeSeries.get(s.getId()) == null) {
			throw new DaoException("Aucune série trouvée avec l'identifiant ".concat(Integer.toString(s.getId())));
		}
		this.listeSeries.remove(s.getId());
	}

	/**
	 * This method return the Serie with given id.
	 *
	 * @param serieId Identifier of Serie to return
	 * @return the Serie with given id.
	 * @throws SeriesException if no Serie exists with given id in the list of
	 *                         series.
	 */
	public Serie getSerieById(final int serieId) throws DaoException {
		for (final Serie serie : this.listeSeries) {
			if (serie.getId() == serieId) {
				return serie;
			}
		}
		throw new DaoException("Aucune série trouvée avec l'identifiant ".concat(Integer.toString(serieId)));
	}

	/**
	 * This method returns the Saison corresponding with given Ids
	 *
	 * @param serieId  Identifier of the Serie to search for the wanted Saison
	 * @param saisonId Identifier of the Saison to find
	 * @return Saison corresponding to given ids.
	 * @throws SeriesException if
	 *                         <ul>
	 *                         <li>either no Serie has been found with given
	 *                         ID.</li>
	 *                         <li>or the Serie has been found but doesn't contain a
	 *                         Saison with given ID.</li>
	 *                         </ul>
	 */
	public Saison getSaisonByIds(final int serieId, final int saisonId) throws DaoException {
		final Serie s = getSerieById(serieId);
		final Saison ret = s.getSaisonById(saisonId);
		if (ret == null) {
			throw new DaoException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(saisonId))
					.concat(" dans la série ayant l'identifiant ").concat(Integer.toString(serieId)));
		}
		return ret;
	}

	/**
	 * This method returns the Episode corresponding with given Ids
	 *
	 * @param serieId   Identifier of the Serie to search for the wanted Episode
	 * @param saisonId  Identifier of the Saison to search for the wanted Episode
	 * @param episodeId Identifier of the Episode to find
	 * @return Episode corresponding to given ids.
	 * @throws SeriesException if
	 *                         <ul>
	 *                         <li>either no Serie has been found with given
	 *                         ID.</li>
	 *                         <li>or the Serie has been found but doesn't contain a
	 *                         Saison with given ID.</li>
	 *                         <li>or the Serie and Saison have been found but
	 *                         Saison doesn't contain a Episode with given ID.</li>
	 *                         </ul>
	 */
	public Episode getEpisodeByIds(final int serieId, final int saisonId, final int episodeId) throws DaoException {
		final Saison s = getSaisonByIds(serieId, saisonId);
		final Episode e = s.getEpisodeById(episodeId);
		if (e == null) {
			throw new DaoException("Aucun épisode trouvé avec l'identifiant ".concat(Integer.toString(episodeId))
					.concat(" dans la saison ayant l'identifiant ").concat(Integer.toString(saisonId))
					.concat(" de la série ayant l'identifiant ").concat(Integer.toString(serieId)));
		}
		return e;
	}

	/**
	 * private constructor
	 */
	private DataProvider() {
		initData();
	}

	private void initData() {
		final Serie got = new Serie("Game of Thrones");
		final Saison got1 = new Saison("Season one", 1);
		final Episode got11 = new Episode("Winter is coming", 1);
		final Episode got12 = new Episode("The Kingsroad", 2);
		final Episode got13 = new Episode("Lord Snow", 3);
		final Episode got14 = new Episode("Cripples, Bastards, and Broken Things", 4);
		final Episode got15 = new Episode("The Wolf and the Lion", 5);
		final Episode got16 = new Episode("A Golden Crown", 6);
		final Episode got17 = new Episode("You Win or You Die", 7);
		final Episode got18 = new Episode("The Pointy End", 8);
		final Saison got2 = new Saison("Season two", 2);
		final Episode got21 = new Episode("The North Remembers", 1);
		final Episode got22 = new Episode("The Night Lands", 2);
		final Episode got23 = new Episode("What Is Dead May Never Die", 3);
		final Episode got24 = new Episode("Garden of Bones", 4);
		final Episode got25 = new Episode("The Ghost of Harrenhal", 5);
		final Episode got26 = new Episode("The Old Gods and the New", 6);
		final Episode got27 = new Episode("A Man Without Honor", 7);
		final Episode got28 = new Episode("The Prince of Winterfell", 8);
		try {
			got1.addEpisode(got11);
			got1.addEpisode(got12);
			got1.addEpisode(got13);
			got1.addEpisode(got14);
			got1.addEpisode(got15);
			got1.addEpisode(got16);
			got1.addEpisode(got17);
			got1.addEpisode(got18);
			got.addSaison(got1);
			got2.addEpisode(got21);
			got2.addEpisode(got22);
			got2.addEpisode(got23);
			got2.addEpisode(got24);
			got2.addEpisode(got25);
			got2.addEpisode(got26);
			got2.addEpisode(got27);
			got2.addEpisode(got28);
			got.addSaison(got2);
			addSerie(got);

		} catch (final DaoException e) {
			e.printStackTrace();
		}
		final Serie tgp = new Serie("The Good Place");
		final Saison tgp1 = new Saison("Season one", 1);
		final Episode tgp11 = new Episode("Pilot", 1);
		final Episode tgp12 = new Episode("Flying", 2);
		final Episode tgp13 = new Episode("Tahani Al-Jamil", 3);
		final Episode tgp14 = new Episode("Jason Mendoza", 4);
		final Episode tgp15 = new Episode("Category 55 Emergency Doomsday Crisis", 5);
		final Episode tgp16 = new Episode("What We Owe to Each Other", 6);
		final Episode tgp17 = new Episode("The Eternal Shriek", 7);
		final Episode tgp18 = new Episode("Most Improved Player", 8);
		final Saison tgp2 = new Saison("Season two", 2);
		final Episode tgp21 = new Episode("Everything Is Great!", 1);
		final Episode tgp22 = new Episode("Dance Dance Resolution", 2);
		final Episode tgp23 = new Episode("Team Cockroach", 3);
		final Episode tgp24 = new Episode("Existential Crisis", 4);
		final Episode tgp25 = new Episode("The Trolley Problem", 5);
		final Episode tgp26 = new Episode("Janet and Michael", 6);
		final Episode tgp27 = new Episode("Derek", 7);
		final Episode tgp28 = new Episode("Leap to Faith", 8);
		try {
			tgp1.addEpisode(tgp11);
			tgp1.addEpisode(tgp12);
			tgp1.addEpisode(tgp13);
			tgp1.addEpisode(tgp14);
			tgp1.addEpisode(tgp15);
			tgp1.addEpisode(tgp16);
			tgp1.addEpisode(tgp17);
			tgp1.addEpisode(tgp18);
			tgp.addSaison(tgp1);
			tgp2.addEpisode(tgp21);
			tgp2.addEpisode(tgp22);
			tgp2.addEpisode(tgp23);
			tgp2.addEpisode(tgp24);
			tgp2.addEpisode(tgp25);
			tgp2.addEpisode(tgp26);
			tgp2.addEpisode(tgp27);
			tgp2.addEpisode(tgp28);
			tgp.addSaison(tgp2);
			addSerie(tgp);

		} catch (final DaoException e) {
			e.printStackTrace();
		}

	}
}
