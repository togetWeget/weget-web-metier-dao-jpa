package ci.weget.web.entites;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="T_Messages")
public class Message extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
    private String sujet;
    private String contenu;
    private LocalDateTime date;
    
    
	public Message() {
		super();
		
	}
	
	public Message(String sujet, String contenu) {
		super();
		this.sujet = sujet;
		this.contenu = contenu;
	}

	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public LocalDateTime getDate() {
		return date;
	}
	/* @PrePersist
	 @PreUpdate
	public void setDate(LocalDateTime date) {
		this.date = LocalDateTime.now();
	}
	*/
   
    
    
}
