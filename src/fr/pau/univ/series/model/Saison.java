package fr.pau.univ.series.model;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fr.pau.univ.series.exception.DaoException;

//Ces annotations permettent d'indiquer que c'est une entité de notre BDD. Grâce à JPA, nous pouvons utiliser ces annotations pour
//que Java, Jakarta, etc. comprennent la connexion classe Java <> Table SQL
//C'est pour cela que nous ajoutons @Entity et que nous expliquons le nom de la table avec @Table(name = "non_de_la_table")
//Puis, nous ajoutons deux query SQL que nous nommons findById et findAll. La requête byId contient un argument identifiable par :id
//Si une requête contient ':' puis le nom d'une variable, cela veut dire que ce sera notre argument et qu'il prendra la valeur de la 
//variable ID.
@Entity
@Table(name = "Saison")
@NamedQueries({
		@NamedQuery(name = "Saison.findById", query = "SELECT s FROM Saison s WHERE s.id = :id"),
		@NamedQuery(name = "Saison.findAll", query = "SELECT s FROM Saison s"),
		@NamedQuery(name = "Saison.findByEpisode", query = "SELECT s FROM Saison s WHERE :id = ANY (SELECT e.id FROM s.episodes e)"),
		@NamedQuery(name = "Saison.findBySerie", query = "SELECT sais FROM Serie ser, IN(ser.saisons) sais WHERE ser.id = :id")
})
// Noter simple classe Java
public class Saison {

	// Nos attributs
	private int id; // Notre id
	private int numero; // Le numéro de la saison
	private String nom; // Le nom de la saison
	private boolean toutVu = false; // Tous les épisodes visionnés
	private List<Episode> episodes = new ArrayList<>(); // La liste des épisodes de la saison

	// Constructor
	/**
	 * @param nom Nom de la saison.
	 */
	public Saison(final String nom, final int numero) {
		this.nom = nom;
		this.numero = numero;
		// setId(++DataProvider.lastSaisonId);
	}

	// Constructor vide
	public Saison() {
		this.nom = "";
		this.numero = -1;
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the numero
	 */
	@Column(name = "Numero", nullable = false)
	public int getNumero() {
		return this.numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(final int numero) {
		this.numero = numero;
	}

	/**
	 * @return the nom
	 */
	@Column(name = "Nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public final void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Petite différence sur les annotations ici. Nous avons notre liste d'épisode
	 * de la série. Nous devons donc faire des références
	 * à des épisodes dans notre BDD. Nous devons donc spécifier la relation
	 * (OneToMany ici, car une saison contient plusieurs épisodes)
	 * Et nous devons joindre les colonnes avec la ForeignKey fk_saison qui
	 * référence la colonne ID dans la table des épisodes.
	 *
	 * @return retourne la liste des épisodes de la saison
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_saison", referencedColumnName = "Id")
	public final List<Episode> getEpisodes() {
		return this.episodes;
	}

	/**
	 * @param listeEpisode la liste dse épisodes à ajouter pour la saison
	 */
	public final void setEpisodes(List<Episode> listeEpisode) {
		this.episodes = listeEpisode;
	}

	/**
	 * Nous devons être capable d'ajouter, modifier et supprimer des épisodes de
	 * notre liste d'épisode pour la saison depuis notre
	 * projet (notre application). Les méthodes ci-dessous s'occupent de faire ces
	 * ajouts / suppression de notre liste "episodes"
	 * Cette méthode permet d'ajouter un épisode à la liste d'épisode de la saison.
	 *
	 * @param e l'épisode à ajouter.
	 * @return l'épisode ajouté.
	 * @throws DaoException si l'épisode existe déjà.
	 */
	public Episode addEpisode(final Episode e) throws DaoException {
		this.episodes.add(e);
		return e;
	}

	/**
	 * Cette méthode permet de supprimer un épisode de la liste d'épisode de la
	 * saison.
	 *
	 * @param e l'épisode à supprimer.
	 * @throws DaoException si l'épisode n'existe pas.
	 */
	public void removeEpisode(final Episode e) throws DaoException {
		if (this.episodes.get(e.getId()) == null) {
			throw new DaoException("Aucun épisode trouvée avec l'identifiant ".concat(Integer.toString(e.getId())));
		}
		this.episodes.remove(e.getId());
	}

	/**
	 * Cet attribut ne fait pas référence à une colonne de notre table. Il s'agit
	 * d'une valeur définie par la liste des épisodes
	 * par rapport à leur état vu.
	 * 
	 * @param toutVu the toutVu to set
	 */
	public final void setToutVu(final boolean toutVu) {
		this.toutVu = toutVu;
	}

	/**
	 * @return the toutVu
	 */
	public final boolean isToutVu() {
		this.toutVu = true;
		if (this.episodes.size() > 0) {
			for (final Episode e : this.episodes) {
				if (!e.isVu()) {
					this.toutVu = false;
					return this.toutVu;
				}
			}
		}
		return this.toutVu;
	}

	/**
	 * Cette méthode retourne un épisode de la saison en fonction de son
	 * identifiant.
	 *
	 * @param id L'identifiant de l'épisode à retourner.
	 * @return L'épisode correspondant à l'identifiant.
	 * @throws DaoException Si aucun épisode n'est trouvé avec l'identifiant
	 */
	public Episode getEpisodeById(final int id) throws DaoException {
		for (final Episode ep : this.episodes) {
			if (ep.getId() == id) {
				return ep;
			}
		}
		throw new DaoException("Aucun épisode trouvé avec l'identifiant ".concat(Integer.toString(id)));
	}

	// Auto generated methods
	@Override
	public String toString() {
		return getNom();
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Saison)) {
			return false;
		}
		return ((Saison) obj).getId() == getId();
	}

}
