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
@Table(name="Saison")
@NamedQueries({
	@NamedQuery(name="Saison.findById",
			query="SELECT s FROM Saison s WHERE s.id = :id"),
	@NamedQuery(name="Saison.findAll",
			query="SELECT s FROM Saison s"),
	@NamedQuery(name="Saison.findByEpisode",
			query="SELECT s FROM Saison s WHERE :id = ANY (SELECT e.id FROM s.episodes e)"),
	@NamedQuery(name="Saison.findBySerie",
			query="SELECT sais FROM Serie ser, IN(ser.saisons) sais WHERE ser.id = :id")
})
@Path("/saison")
public class Saison {

	private int id;
	private int numero;
	private String nom;
	private boolean toutVu = false;
	private List<Episode> episodes = new ArrayList<>();

	/**
	 * @param nom Nom de la saison.
	 */
	public Saison(final String nom, final int numero) {
		this.nom = nom;
		this.numero = numero;
		setId(++DataProvider.lastSaisonId);
	}
	
	public Saison() {
		this.nom = "";
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
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "fk_saison",referencedColumnName = "Id")
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public final List<Episode> getEpisodes() {
		return this.episodes;
	}
	
	public final void setEpisodes(List<Episode> listeEpisode) {
		this.episodes = listeEpisode;
	}

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
	@GET
	@Path("/byId")
	@Produces(MediaType.APPLICATION_JSON)
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
