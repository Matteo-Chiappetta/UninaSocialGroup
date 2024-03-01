package org.openjfx.Progetto.Entity;

public class Commento extends Interazione{
	private String commento;
	private Utente utenteCommentatore;
	private Post postCommentato; 
	

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Utente getUtenteCommentatore() {
		return utenteCommentatore;
	}

	public void setUtenteCommentatore(Utente utenteCommentatore) {
		this.utenteCommentatore = utenteCommentatore;
	}

	public Post getPostCommentato() {
		return postCommentato;
	}

	public void setPostCommentato(Post postCommentato) {
		this.postCommentato = postCommentato;
	}
	
	
}
