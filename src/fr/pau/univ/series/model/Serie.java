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
//Cette classe est semblable à la classe saison, sauf que nous avons un tableau de saison pour la série et nous pourrons donc 
//retrouver les épisodes de la série.
//Je n'ajoute pas tous les commentaires, seulement les commentaires nécessaires pour la compréhension sur les points qui changent 
//et les nouveautés.
@Entity
@Table(name = "Serie")
@NamedQueries({
		@NamedQuery(name = "Serie.findById", query = "SELECT ser FROM Serie ser WHERE ser.id = :id"),
		@NamedQuery(name = "Serie.findAll", query = "SELECT ser FROM Serie ser"),
		@NamedQuery(name = "Serie.findByEpisode", query = "SELECT ser FROM Serie ser, IN(ser.saisons) sais WHERE ser.id = ANY ( SELECT ser.id FROM s.episodes e)"),
		@NamedQuery(name = "Serie.findBySaison", query = "SELECT ser FROM Serie ser, IN(ser.saisons) sais WHERE sais.id = :id")
})
public class Serie {

	private int id;
	private String nom;
	private boolean toutVu = false;
	private List<Saison> saisons = new ArrayList<>();

	/**
	 * @param nom Nom de la série.
	 */
	public Serie(final String nom) {
		this.nom = nom;
		// setId(++DataProvider.lastSerieId);
	}

	/**
	 * Constructeur vide.
	 */
	public Serie() {
		this.nom = "";
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
	 * @return the nom
	 */
	@Column(name = "Nom", nullable = false)
	public final String getNom() {
		return this.nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public final void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the saisons
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_serie", referencedColumnName = "Id")
	public final List<Saison> getSaisons() {
		return this.saisons;
	}

	/**
	 * @param listeSaison the saisons to set
	 */
	public void setSaisons(List<Saison> listeSaison) {
		this.saisons = listeSaison;
	}

	/**
	 * Ajoute une saison à la liste des saisons.
	 *
	 * @param s la saison à ajouter.
	 * @return la saison ajoutée.
	 * @throws DaoException si la saison existe déjà.
	 */
	public Saison addSaison(final Saison s) throws DaoException {
		this.saisons.add(s);
		return s;
	}

	/**
	 * Supprime la saison de la liste des saisons.
	 *
	 * @param s la saison à supprimer.
	 * @throws DaoException si la saison n'existe pas.
	 */
	public void removeSaison(final Saison s) throws DaoException {
		if (this.saisons.get(s.getId()) == null) {
			throw new DaoException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(s.getId())));
		}
		this.saisons.remove(s.getId());
	}

	/**
	 * @param toutVu the toutVu to set
	 */
	public final void setToutVu(final boolean toutVu) {
		this.toutVu = toutVu;
	}

	/**
	 * @return <code>true</code> si toutes les Saisons de la Serie ont été
	 */
	public boolean isToutVu() {
		this.toutVu = true;
		if (this.saisons.size() > 0) {
			for (final Saison s : this.saisons) {
				if (!s.isToutVu()) {
					this.toutVu = false;
					return this.toutVu;
				}
			}
		}
		return this.toutVu;
	}

	/**
	 * Retourne la saison avec l'identifiant donné.
	 *
	 * @param id l'id de la saison à retourner.
	 * @return la saison avec l'identifiant donné.
	 * @throws DaoException si aucune saison n'a été trouvée avec l'identifiant
	 */
	public Saison getSaisonById(final int id) throws DaoException {
		for (final Saison saison : this.saisons) {
			if (saison.getId() == id) {
				return saison;
			}
		}
		throw new DaoException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(id)));
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
		if (!(obj instanceof Serie)) {
			return false;
		}
		return ((Serie) obj).getId() == getId();
	}

}
