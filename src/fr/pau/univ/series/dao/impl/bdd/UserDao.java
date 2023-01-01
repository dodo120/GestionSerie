package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.pau.univ.series.dao.interfaces.IUserDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.User;

public class UserDao implements IUserDao {
	private final DaoBddHelper bdd;

	/**
	 * Le constructeur de notre classe.
	 * Essaye de récupérer une instance de la classe DaoBddHelper.
	 * 
	 * @throws DaoException
	 */
	public UserDao() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}
	
	@Override
	public User readUser(int id) throws DaoException {
		final TypedQuery<User> query = this.bdd.getEm().createNamedQuery("User.findById", User.class);
		query.setParameter("id", id);
		final List<User> ret = query.getResultList();
		if (ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<User> readAllUsers() throws DaoException {
		TypedQuery<User> query = this.bdd.getEm().createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}
	

	@Override
	public User createUser(User user, boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().persist(user);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
			return user;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de créer l'utilisateur", e);
		}
	}

	@Override
	public void updateUser(User user, boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().merge(user);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Impossible de modifier l'utilisateur", e);
		}
		
	}

	@Override
	public void deleteUser(User user, boolean useTransaction) throws DaoException {
		try {
			if (useTransaction) {
				this.bdd.beginTransaction();
			}
			this.bdd.getEm().remove(user);
			if (useTransaction) {
				this.bdd.commitTransaction();
			}
		} catch (final PersistenceException e) {
			if (useTransaction) {
				this.bdd.rollbackTransaction();
			}
			throw new DaoException("Impossible de supprimer l'utilisateur", e);
		}
		
	}
	
	@Override
	public User readUserByLogin(String login) throws DaoException{
		return null;
	}
		
	
}
