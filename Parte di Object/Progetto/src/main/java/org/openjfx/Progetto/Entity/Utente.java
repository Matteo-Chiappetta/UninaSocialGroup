package org.openjfx.Progetto.Entity;

public class Utente {
	private String nome;
	private String cognome;
	private String password;
	private String imgProfilo;
	private String descrizione;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

}
