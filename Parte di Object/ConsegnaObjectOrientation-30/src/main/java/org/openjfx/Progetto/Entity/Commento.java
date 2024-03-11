package org.openjfx.Progetto.Entity;

import java.sql.Timestamp;

public class Commento extends Interazione{
	private int idCommento;
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
	
	public int getIdCommento() {
		return idCommento;
	}

	public Commento(int idCommento,Timestamp dataInterazione, String commento, Utente utenteCommentatore, Post postCommentato) {
		super(dataInterazione);
		this.idCommento = idCommento;
		this.commento = commento;
		this.utenteCommentatore = utenteCommentatore;
		this.postCommentato = postCommentato;
	}

	@Override
	public String toString() {
		return "Commento [idCommento=" + idCommento + ", commento=" + commento + ", utenteCommentatore="
				+ utenteCommentatore + ", postCommentato=" + postCommentato + "]";
	}
}
