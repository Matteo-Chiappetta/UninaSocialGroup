package org.openjfx.Progetto.Entity;

public class Utente {
	private String userName;
	private String email;
	private String password;
	private String imgProfilo;
	private String descrizione;


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
