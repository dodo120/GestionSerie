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
//Noter simple classe Java
public class Saison {

	//Nos attributs
	private int id; //Notre id
	private int numero; //Le numéro de la saison
	private String nom; //Le nom de la saison
	private boolean toutVu = false; //Tous les épisodes visionnés
	private List<Episode> episodes = new ArrayList<>(); //La liste des épisodes de la saison

	//Constructor
	/**
	 * @param nom Nom de la saison.
	 */
	public Saison(final String nom, final int numero) {
		this.nom = nom;
		this.numero = numero;
		setId(++DataProvider.lastSaisonId);
	}

	//Constructor vide
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
	public final String getNom() {
		return this.nom;
	}

	public final void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the saisons
	 */
	//Petite différence sur les annotations ici. Nous avons notre liste d'épisode de la série. Nous devons donc faire des références 
	//à des épisodes dans notre BDD. Nous devons donc spécifier la relation (OneToMany ici, car une saison contient plusieurs épisodes)
	//Et nous devons joindre les colonnes avec la ForeignKey fk_saison qui référence la colonne ID dans la table des épisodes.
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_saison", referencedColumnName = "Id")
	public final List<Episode> getEpisodes() {
		return this.episodes;
	}

	public final void setEpisodes(List<Episode> listeEpisode) {
		this.episodes = listeEpisode;
	}

	//Nous devons être capable d'ajouter, modifier et supprimer des épisodes de notre liste d'épisode pour la saison depuis notre 
	//projet (notre application). Les méthodes ci-dessous s'occupent de faire ces ajouts / suppression de notre liste "episodes"
	/**
	 * This method adds given Episode to the list of Episodes.
	 *
	 * @param e The Episode to add. <b>note :</b>given Episode id is calculated
	 *          before storing. No need to set it before calling this method.
	 * @return the added Episode
	 * @throws SeriesException if a Episode already exist with same id.
	 */
	public Episode addEpisode(final Episode e) throws DaoException {
		this.episodes.add(e);
		return e;
	}

	/**
	 * This method removes given Episode from the list of Episodes.
	 *
	 * @param e the Episode to remove.
	 * @throws SeriesException if given Episode doesn't exist in the list of
	 *                         Episodes.
	 */
	public void removeEpisode(final Episode e) throws DaoException {
		if (this.episodes.get(e.getId()) == null) {
			throw new DaoException("Aucun épisode trouvée avec l'identifiant ".concat(Integer.toString(e.getId())));
		}
		this.episodes.remove(e.getId());
	}

	/**
	 * @param toutVu the toutVu to set
	 */
	//Cet attribut ne fait pas référence à une colonne de notre table. Il s'agit d'une valeur définie par la liste des épisodes 
	//par rapport à leur état vu.
	public final void setToutVu(final boolean toutVu) {
		this.toutVu = toutVu;
	}

	public boolean isToutVu() {
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
	 * This method return the Episode with given id.
	 *
	 * @param id Identifier of Episode to return
	 * @return the Episode with given id or <code>null</code> if no Episode exists
	 *         with given Id.
	 * @throws SeriesException in case of error
	 */
	public Episode getEpisodeById(final int id) throws DaoException {
		for (final Episode ep : this.episodes) {
			if (ep.getId() == id) {
				return ep;
			}
		}
		throw new DaoException("Aucun épisode trouvé avec l'identifiant ".concat(Integer.toString(id)));
	}

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
