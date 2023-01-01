package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.User;

public interface IUserDao {
	// Les méthodes en lecture seules
	public User readUser(int id) throws DaoException;

	public List<User> readAllUsers() throws DaoException;
	
	public User readUserByLogin(String login) throws DaoException;

	// Les méthodes d'écritures (d'édition)
	public User createUser(User user, final boolean useTransaction) throws DaoException;

	public void updateUser(User user, final boolean useTransaction) throws DaoException;

	public void deleteUser(User user, final boolean useTransaction) throws DaoException;
}
