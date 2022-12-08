
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

public class SaisonDao implements ISaisonDao{

	private final DaoBddHelper bdd;

	public SaisonDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Saison createSaison(final Saison s) throws DaoException {
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
	public Saison readSaison(final int SaisonId) throws DaoException {
	final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findById", Saison.class);
	query.setParameter("id", SaisonId);
	final List<Saison> ret = query.getResultList();
	if (ret.size() > 0) {
	return ret.get(0);
	}
	return null;
	}

	@Override
	public List<Saison> readAllSaisons() throws DaoException {
	TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findAll", Saison.class);
	return query.getResultList();
	}

	@Override
	public void updateSaison(final Saison s, , final boolean useTransaction) throws DaoException {
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
	public void deleteSaison(final Saison s, final Boolean useTransaction) throws DaoException {
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
