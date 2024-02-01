package org.openjfx.Progetto.Entity;

import java.util.Date;

public class Post {
	private Date dataPubblicazione;
	private String didascalia;
	private int numMiPiace;
	private int numCommenti;

	public int getNumMiPiace() {
		return numMiPiace;
	}

	public void setNumMiPiace(int numMiPiace) {
		this.numMiPiace = numMiPiace;
	}

	public int getNumCommenti() {
		return numCommenti;
	}

	public void setNumCommenti(int numCommenti) {
		this.numCommenti = numCommenti;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public String getDidascalia() {
		return didascalia;
	}

	public void setDidascalia(String didascalia) {
		this.didascalia = didascalia;
	}
}