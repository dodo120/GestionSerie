package fr.pau.univ.series.exception;

//Cette classe permet de gérer les exceptions de type DAO. 
//Java inclus de base des exceptions déjà implémenté, mais vous pouvez ajouter vos propres Exception à appeler quand vous le souhaitez.
//Pour ça, il faut créer une classe et "l'étendre" de la classe Exception.
//Je ne pense pas que vous devez comprendre ou apprendre cette classe, mais si vous êtes curieux, je vous ai ajouté quelques commentaires
//pour expliquer certaines choses intéressantes.
public class DaoException extends Exception {

	/**
	 * 
	 */

	// Une exception a besoin d'un UID, un UID est un identifiant comme les
	// programmes et utilisateurs sous UNIX.
	private static final long serialVersionUID = 1L;

	// Nous appelons cette méthode quand nous voulons juste envoyer un message.
	public DaoException(String message) {
		super(message);
	}

	// Nous appelons cette méthode quand nous voulons juste renvoyer la cause de
	// l'erreur.
	public DaoException(Throwable cause) {
		super(cause);
	}

	// Cette méthode est un mélange des deux précédents où nous pouvons renvoyer un
	// message et la cause de l'erreur.
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	// Cette trois classes sont exactement les mêmes (mise à part le nombre
	// d'arguments). Plusieurs méthodes peuvent avoir le même nom si
	// elles effectuent la même fonctionnalité, mais que les arguments changent.
}
