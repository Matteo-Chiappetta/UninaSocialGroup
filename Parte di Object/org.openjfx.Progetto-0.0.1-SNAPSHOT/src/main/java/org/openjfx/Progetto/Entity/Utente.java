package org.openjfx.Progetto.Entity;

public class Utente {
	private String userName;
	private String email;
	private String password;
	private String imgProfilo;
	private String descrizione;

	public Utente (Utente utente) {
		this.userName = utente.userName;
		this.email = utente.email;
		this.password = utente.password;
		this.imgProfilo = utente.imgProfilo;
		this.descrizione = utente.descrizione;
	}
	@Override
	public String toString() {
		return "Utente [userName=" + userName + ", email=" + email + ", password=" + password + ", imgProfilo="
				+ imgProfilo + ", descrizione=" + descrizione + "]";
	}
	public Utente(String userName, String email, String password, String imgProfilo, String descrizione) {
		super();
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
	/*public void setPassword(String password) {
		this.password = password;
	}*/
}
