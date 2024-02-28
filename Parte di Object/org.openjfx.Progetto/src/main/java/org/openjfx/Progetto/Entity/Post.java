package org.openjfx.Progetto.Entity;

import java.sql.Timestamp;
import java.util.Date;

public class Post {
	@Override
	public String toString() {
		return "Post [idPost=" + idPost + ", dataPubblicazione=" + dataPubblicazione + ", didascalia=" + didascalia
				+ ", tipo_file=" + tipo_file + ", gruppo=" + gruppo + ", utenteCreatore=" + utenteCreatore + "]";
	}

	private int idPost;
	private Timestamp dataPubblicazione;
	private String didascalia;
	private String tipo_file;
	private Gruppo gruppo;
	private Utente utenteCreatore;

	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Timestamp dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public String getDidascalia() {
		return didascalia;
	}

	public void setDidascalia(String didascalia) {
		this.didascalia = didascalia;
	}

	public Post(int idPost, Timestamp dataPubblicazione, String didascalia, String tipo_file, Gruppo gruppo,
			Utente utenteCreatore) {
		this.idPost = idPost;
		this.dataPubblicazione = dataPubblicazione;
		this.didascalia = didascalia;
		this.tipo_file = tipo_file;
		this.gruppo = gruppo;
		this.utenteCreatore = utenteCreatore;
	}
	
	

}