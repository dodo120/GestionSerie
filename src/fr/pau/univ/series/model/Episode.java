package fr.pau.univ.series.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//Ces annotations permettent d'indiquer que c'est une entité de notre BDD. Grâcee à JPA, nous pouvons utiliser ces annotations pour
//que Java, Jakarta, etc. comprennet la connection classe Java <> Table SQL
//C'est pour cela que nous ajoutons @Entity et que nous expliquons le nom de la table avec @Table(name = "non_de_la_table")
//Puis, nous ajoutons deux query SQL que nous nommons findById et findAll. La requête byId contient un argument identifiable par :id
//Si une requête contient ':' puis le nom d'une varible, cela veut dire que se sera notre argument et qu'il prendra la valeur de la 
//variable ID.
@Entity
@Table(name = "Episode")
@NamedQueries({
		@NamedQuery(name = "Episode.findById", query = "SELECT e FROM Episode e WHERE e.id = :id"),
		@NamedQuery(name = "Episode.findAll", query = "SELECT e FROM Episode e")
})

//Notre simple classe Java.
public class Episode {

	//Nos attributs
	private int id; //Notre id de l'épisode (pour l'administration de la BDD)
	private String titre; //Le titre de l'épisode
	private int numero;// Le numéro de l'épisode
	private boolean vu = false; //Avons-nous regardé l'épisode?

	//Constructor de la classe
	/* 
	 * @param titre
	 * @param numero
	 * @param vu
	 */
	public Episode(final String titre, final int numero) {
		this.titre = titre;
		this.numero = numero;
		setId(++DataProvider.lastEpisodeId);
	}

	//Constructor de la classe vide (si aucin argument passé en paramètre.
	public Episode() {
		this.titre = "";
		this.numero = -1;

	}

	/**
	 * @return the id
	 */
	//Nous retrouvons des annotations ici pour signifier qu'il s'agit d'une tuple de notre requête. Iic nous faison la liaison entre
	//la méthode getID et la variable de Id dans la BDD
	//La méthode est légèrement complexe dans le sens ou nous explicons à Java, Jakarta, etc. que c'est un identifiant et que la génération
	//est alors "automatique"
	//C'est pour cela que nous devons expliquer la liaison à la colonne de la BDD avec @Id.
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
	 * @return the titre
	 */
	//Ici le même cas de figure que pour Id, sauf que nous devons juste faire la liaison avec la colonne. Et nullable permet de 
	//signaler que la colonne peut recevoir des null ou non
	@Column(name = "titre", nullable = false)
	public final String getTitre() {
		return this.titre;
	}

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
