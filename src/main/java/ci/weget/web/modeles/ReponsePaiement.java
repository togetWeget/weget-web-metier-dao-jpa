package ci.weget.web.modeles;

import java.util.List;

public class ReponsePaiement<T,U> {
	private int status;
	
	private List<String> messages;
	private T body;
	private U body1;
	public ReponsePaiement() {
		super();
		
	}
	public ReponsePaiement(int status, List<String> messages, T body) {
		super();
		this.status = status;
		this.messages = messages;
		this.body = body;
	}
	
	public ReponsePaiement(int status, List<String> messages, T body, U body1) {
		super();
		this.status = status;
		this.messages = messages;
		this.body = body;
		this.body1 = body1;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	public U getBody1() {
		return body1;
	}
	public void setBody1(U body1) {
		this.body1 = body1;
	}

	
}
