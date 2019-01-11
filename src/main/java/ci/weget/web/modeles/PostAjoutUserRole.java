package ci.weget.web.modeles;

public class PostAjoutUserRole {
	private long idRole;
	private long idPersonne;
	
	
	// getter and setter
	
	public long getIdPersonne() {
		return idPersonne;
	}
	public long getIdRole() {
		return idRole;
	}
	public void setIdRole(long idRole) {
		this.idRole = idRole;
	}
	public void setIdPersonne(long idPersonne) {
		this.idPersonne = idPersonne;
	}
	
}
