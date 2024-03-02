package org.openjfx.Progetto.Entity;

import java.util.ArrayList;

public class Gruppo {
	
	
	
	private String tag;
	private String tema;
	private Utente utenteCreatore;
	private ArrayList<Utente> listaPartecipanti;
	private ArrayList<Richiesta> listaRichieste;
	private ArrayList<Post> listaPost;
	
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
	
	public Utente getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setEmailCreatore(Utente utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}
	
	
	public ArrayList<Utente> getListaPartecipanti() {
		return listaPartecipanti;
	}

	public void setListaPartecipanti(ArrayList<Utente> listaPartecipanti) {
		this.listaPartecipanti = listaPartecipanti;
	}

	public ArrayList<Richiesta> getListaRichieste() {
		return listaRichieste;
	}

	public void setListaRichieste(ArrayList<Richiesta> listaRichieste) {
		this.listaRichieste = listaRichieste;
	}

	public ArrayList<Post> getListaPost() {
		return listaPost;
	}

	public void setListaPost(ArrayList<Post> listaPost) {
		this.listaPost = listaPost;
	}

	
	public Gruppo(String tag, String tema,Utente utenteCreatore) {
		this.tag = tag;
		this.tema = tema;
		this.utenteCreatore = utenteCreatore;
	}

	
	@Override
	public String toString() {
		return "Gruppo [tag=" + tag + ", tema=" + tema + ", emailCreatore=" + utenteCreatore + "]";
	}
}