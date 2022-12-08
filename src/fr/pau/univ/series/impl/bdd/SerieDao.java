package fr.pau.univ.series.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

public class SerieDao implements ISerieDao{

	private final DaoBddHelper bdd;

	public SerieDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Serie createSerie(final Serie s) throws DaoException {
	try {
	this.bdd.beginTransaction();
	this.bdd.getEm().persist(s);
	this.bdd.commitTransaction();
	return s;
	} catch (final PersistenceException e) {
	this.bdd.rollbackTransaction();
	throw new DaoException("Impossible de créer la série", e);
	}
	}


	@Override
	public Serie readSerie(final int serieId) throws DaoException {
	final TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findById", Serie.class);
	query.setParameter("id", serieId);
	final List<Serie> ret = query.getResultList();
	if (ret.size() > 0) {
	return ret.get(0);
	}
	return null;
	}

	@Override
	public List<Serie> readAllSeries() throws DaoException {
	TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findAll", Serie.class);
	return query.getResultList();
	}


	@Override
	public Serie readSerieBySaison(int idSaison) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSerie(final Serie s, final boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
		this.bdd.beginTransaction();
			}
		this.bdd.getEm().merge(s);
		if (useTransaction) {
		this.bdd.commitTransaction();
		}
		} catch (final PersistenceException e) {
		this.bdd.rollbackTransaction();
		throw new DaoException("Impossible de modifier la série", e);
		}
	}


	@Override
	public void deleteSerie(final Serie s, final boolean useTransaction) throws DaoException {
	try {
	if (useTransaction) {
	this.bdd.beginTransaction();
	}
	this.bdd.getEm().remove(s);
	if (useTransaction) {
	this.bdd.commitTransaction();
	}
	} catch (final PersistenceException e) {
	if (useTransaction) {
	this.bdd.rollbackTransaction();
	}
	throw new DaoException("Impossible de supprimer la série", e);
	}
	}

}
