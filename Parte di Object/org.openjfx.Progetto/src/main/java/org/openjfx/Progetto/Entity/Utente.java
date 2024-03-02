package org.openjfx.Progetto.Entity;

import java.util.ArrayList;

public class Utente {
	private String userName;
	private String email;
	private String password;
	private String imgProfilo;
	private String descrizione;
	private ArrayList<Post> postPubblicati;
	private ArrayList<Utente> listaSeguiti;
	private ArrayList<Utente> listaFollower;
	private ArrayList<Notifica> listaNotifiche;
	
	
	
	public Utente (Utente utente) {
		this.userName = utente.userName;
		this.email = utente.email;
		this.password = utente.password;
		this.imgProfilo = utente.imgProfilo;
		this.descrizione = utente.descrizione;
	}
	@Override
	public String toString() {
		return userName;
	}
	public Utente(String userName, String email, String password, String imgProfilo, String descrizione) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.imgProfilo = imgProfilo;
		this.descrizione = descrizione;
	}
	public String getImgProfilo() {
		return imgProfilo;
	}

	public void setImgProfilo(String imgProfilo) {
		this.imgProfilo = imgProfilo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getPassword() {
		return password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public ArrayList<Post> getPostPubblicati() {
		return postPubblicati;
	}
	public void setPostPubblicati(ArrayList<Post> postPubblicati) {
		this.postPubblicati = postPubblicati;
	}
	
	
	public ArrayList<Utente> getListaSeguiti() {
		return listaSeguiti;
	}
	public void setListaSeguiti(ArrayList<Utente> listaSeguiti) {
		this.listaSeguiti = listaSeguiti;
	}
	
	
	
	public ArrayList<Utente> getListaFollower() {
		return listaFollower;
	}
	public void setListaFollower(ArrayList<Utente> listaFollower) {
		this.listaFollower = listaFollower;
	}
	
	
	public ArrayList<Notifica> getListaNotifiche() {
		return listaNotifiche;
	}
	public void setListaNotifiche(ArrayList<Notifica> listaNotifiche) {
		this.listaNotifiche = listaNotifiche;
	}
}
