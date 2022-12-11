package fr.pau.univ.series.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//Ces annotations permettent d'indiquer que c'est une entité de notre BDD. Grâce à JPA, nous pouvons utiliser ces annotations pour
//que Java, Jakarta, etc. comprennent la connexion classe Java <> Table SQL
//C'est pour cela que nous ajoutons @Entity et que nous expliquons le nom de la table avec @Table(name = "non_de_la_table")
//Puis, nous ajoutons deux query SQL que nous nommons findById et findAll. La requête byId contient un argument identifiable par :id
//Si une requête contient ':' puis le nom d'une variable, cela veut dire que ce sera notre argument et qu'il prendra la valeur de la 
//variable ID.
@Entity
@Table(name = "Episode")
@NamedQueries({
		@NamedQuery(name = "Episode.findById", query = "SELECT e FROM Episode e WHERE e.id = :id"),
		@NamedQuery(name = "Episode.findAll", query = "SELECT e FROM Episode e"),
		@NamedQuery(name = "Episode.findBySaison", query = "SELECT e FROM Saison sais, IN(sais.episodes) e WHERE sais.id = :id"),
		@NamedQuery(name = "Episode.findBySerie", query = "SELECT e FROM Saison sais, IN(sais.episodes) e WHERE sais.id = ANY (SELECT sais FROM Serie ser WHERE :id = ser.id) ")
})

// Notre simple classe Java.
public class Episode {

	// Nos attributs
	private int id; // Notre id de l'épisode (pour l'administration de la BDD)
	private String titre; // Le titre de l'épisode
	private int numero;// Le numéro de l'épisode
	private boolean vu = false; // Avons-nous regardé l'épisode?

	/**
	 * Constructor de la classe Episode.
	 * 
	 * @param titre
	 * @param numero
	 */
	public Episode(final String titre, final int numero) {
		this.titre = titre;
		this.numero = numero;
		// setId(++DataProvider.lastEpisodeId);
	}

	/**
	 * Constructor de la classe vide (si aucin argument passé en paramètre.
	 */
	public Episode() {
		this.titre = "";
		this.numero = -1;

	}

	/**
	 * Nous retrouvons des annotations ici pour signifier qu'il s'agit d'une colonne
	 * de notre requête. Ici,
	 * nous faisons la liaison entre
	 * la méthode getID et la variable de Id dans la BDD
	 * La méthode est légèrement complexe dans le sens ou nous expliquons à Java,
	 * Jakarta, etc. que c'est un identifiant et que la génération
	 * est alors "automatique"
	 * C'est pour cela que nous devons expliquer la liaison à la colonne de la BDD
	 * avec @Id.
	 *
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
	 * Ici le même cas de figure que pour Id, sauf que nous devons juste faire la
	 * liaison avec la colonne. Et nullable permet de
	 * signaler que la colonne peut recevoir des null ou non
	 * 
	 * @return the titre
	 */
	@Column(name = "titre", nullable = false)
	public final String getTitre() {
		return this.titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public final void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the numero
	 */
	@Column(name = "numero", nullable = false)
	public final int getNumero() {
		return this.numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public final void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @return the vu
	 */
	@Column(name = "vu", nullable = false)
	public final boolean isVu() {
		return this.vu;
	}

	/**
	 * @param vu the vu to set
	 */
	public final void setVu(final boolean vu) {
		this.vu = vu;
	}

	// Auto generated methods
	@Override
	public String toString() {
		return getTitre();
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Episode)) {
			return false;
		}
		return ((Episode) obj).getId() == getId();
	}

}
