package org.openjfx.Progetto.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	private Notifica notificaAssociata;
	private ArrayList<MiPiace> listaMiPiace;
	private ArrayList<Commento> listaCommenti;

	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}

	public Timestamp getDataPubblicazione() {
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

	
	
	public String getTipo_file() {
		return tipo_file;
	}

	public void setTipo_file(String tipo_file) {
		this.tipo_file = tipo_file;
	}

	public Gruppo getGruppo() {
		return gruppo;
	}

	public void setGruppo(Gruppo gruppo) {
		this.gruppo = gruppo;
	}

	public Utente getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(Utente utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Notifica getNotificaAssociata() {
		return notificaAssociata;
	}

	public void setNotificaAssociata(Notifica notificaAssociata) {
		this.notificaAssociata = notificaAssociata;
	}

	public ArrayList<MiPiace> getListaMiPiace() {
		return listaMiPiace;
	}

	public void setListaMiPiace(ArrayList<MiPiace> listaMiPiace) {
		this.listaMiPiace = listaMiPiace;
	}

	public ArrayList<Commento> getListaCommenti() {
		return listaCommenti;
	}

	public void setListaCommenti(ArrayList<Commento> listaCommenti) {
		this.listaCommenti = listaCommenti;
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