package ci.weget.web.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_Document")
public class Document extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String titre;
	private String description;
    @ElementCollection
	private List<String> pathDocument;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_SousBlock")
	private SousBlock sousBlock;
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getPathDocument() {
		return pathDocument;
	}

	public void setPathDocument(List<String> pathDocument) {
		this.pathDocument = pathDocument;
	}

	public SousBlock getSousBlock() {
		return sousBlock;
	}

	public void setSousBlock(SousBlock sousBlock) {
		this.sousBlock = sousBlock;
	}

}
