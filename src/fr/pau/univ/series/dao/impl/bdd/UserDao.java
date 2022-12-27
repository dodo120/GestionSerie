package fr.pau.univ.series.dao.impl.bdd;

import fr.pau.univ.series.exception.DaoException;

public class UserDao {
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
	
	public User readUserByLogin(String login) {
		
	}
}
