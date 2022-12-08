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

@Entity
@Table(name="Serie")
@NamedQueries({
	@NamedQuery(name="Serie.findById",
			query="SELECT ser FROM Serie ser WHERE ser.id = :id"),
	@NamedQuery(name="Serie.findAll",
			query="SELECT ser FROM Serie ser"),
	@NamedQuery(name="Serie.findByEpisode",
			query="SELECT ser FROM Serie ser, IN(ser.saisons) sais WHERE ser.id = ANY ( SELECT ser.id FROM s.episodes e)"),
	@NamedQuery(name="Serie.findBySaison",
			query="SELECT ser FROM Serie ser, IN(ser.saisons) sais WHERE ser.id = :id")
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
		setId(++DataProvider.lastSerieId);
	}
	
	public Serie() {
		this.nom = "";
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
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "fk_serie",referencedColumnName = "Id")
	public final List<Saison> getSaisons() {
		return this.saisons;
	}
	
	public void setSaisons(List<Saison> listeSaison) {
		this.saisons = listeSaison;
	}

	/**
	 * This method adds given Saison to the list of Saisons.
	 *
	 * @param s The Saison to add. <b>note :</b>given Saison id is calculated before
	 *          storing. No need to set it before calling this method.
	 * @return the added Saison
	 * @throws SeriesException if a Saison already exist with same id.
	 */
	public Saison addSaison(final Saison s) throws DaoException {
		this.saisons.add(s);
		return s;
	}

	/**
	 * This method removes given Saison from the list of Saisons.
	 *
	 * @param s the Saison to remove.
	 * @throws SeriesException if given Saison doesn't exist in the list of Saisons.
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
	 *
	 * This method returns the visualization state of the Serie.
	 *
	 * @return <code>true</code> if all Saisons of the Serie have been fully
	 *         watched, <code>false</code> otherwise.
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
	 * This method return the Saison with given id.
	 *
	 * @param id Identifier of Saison to return
	 * @return the Saison with given id or <code>null</code> if no Saison exists
	 *         with given Id.
	 * @throws SeriesException in case season was not found.
	 */
	public Saison getSaisonById(final int id) throws DaoException {
		for (final Saison saison : this.saisons) {
			if (saison.getId() == id) {
				return saison;
			}
		}
		throw new DaoException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(id)));
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
		if (!(obj instanceof Serie)) {
			return false;
		}
		return ((Serie) obj).getId() == getId();
	}

}
