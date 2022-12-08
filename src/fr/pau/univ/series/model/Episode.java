package fr.pau.univ.series.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Episode")
@NamedQueries({
	@NamedQuery(name="Episode.findById",
			query="SELECT e FROM Episode e WHERE e.id = :id"),
	@NamedQuery(name="Episode.findAll",
			query="SELECT e FROM Episode e")
})

public class Episode {

	private int id;
	private String titre;
	private int numero;
	private boolean vu = false;

	/**
	 * @param titre
	 * @param numero
	 * @param vu
	 */
	public Episode(final String titre, final int numero) {
		this.titre = titre;
		this.numero = numero;
		setId(++DataProvider.lastEpisodeId);
	}
	
	public Episode() {
		this.titre = "";
		this.numero = -1;
		
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy =GenerationType.IDENTITY)
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
	public final String getTitre() {
		return this.titre;
	}
	
	public final void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the numero
	 */
	public final int getNumero() {
		return this.numero;
	}
	
	public final void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @return the vu
	 */
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
