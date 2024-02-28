package org.openjfx.Progetto.Entity;

public class Gruppo {
	
	
	
	private String tag;
	private String tema;
	private Utente utenteCreatore;
	
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