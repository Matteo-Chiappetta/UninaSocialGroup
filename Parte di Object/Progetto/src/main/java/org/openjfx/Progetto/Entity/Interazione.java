package org.openjfx.Progetto.Entity;

import java.sql.Timestamp;

public class Interazione {
	private Timestamp dataInterazione;

	public Timestamp getDataInterazione() {
		return dataInterazione;
	}

	public void setDataInterazione(Timestamp dataInterazione) {
		this.dataInterazione = dataInterazione;
	}

	public Interazione(Timestamp dataInterazione2) {
		this.dataInterazione = dataInterazione2;
	}
	
}
