package ci.weget.web.entites.annonce;

import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name="T_Annonce")
public class Annonce extends AbstractEntity {

	private static final long serialVersionUID = 1L;

}
