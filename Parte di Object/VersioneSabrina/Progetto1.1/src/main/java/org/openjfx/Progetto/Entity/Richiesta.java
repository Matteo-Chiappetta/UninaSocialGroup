package org.openjfx.Progetto.Entity;

import java.security.Timestamp;

public class Richiesta {
	private Timestamp dataRichiesta;
	private boolean accettato;
	private Utente utenteRichiedente;
	private Gruppo gruppoRichiesto;
	
	
	public Timestamp getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Timestamp dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public boolean getAccettato() {
		return accettato;
	}
	public void setAccettato(boolean accettato) {
		this.accettato = accettato;
	}
	public Utente getUtenteRichiedente() {
		return utenteRichiedente;
	}
	public void setUtenteRichiedente(Utente utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}
	public Gruppo getGruppoRichiesto() {
		return gruppoRichiesto;
	}
	public void setGruppoRichiesto(Gruppo gruppoRichiesto) {
		this.gruppoRichiesto = gruppoRichiesto;
	}
	
	
}
