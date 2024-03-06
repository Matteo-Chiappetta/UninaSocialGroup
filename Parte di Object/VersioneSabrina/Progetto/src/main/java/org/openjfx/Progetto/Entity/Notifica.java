package org.openjfx.Progetto.Entity;

import java.security.Timestamp;

public class Notifica {
	private int idNotifica;
	private Timestamp dataNotifica;
	private Utente utenteDaNotificare;
	private Post postDiRiferimento;

	public Timestamp getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Timestamp dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public int getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(int idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Utente getUtenteDaNotificare() {
		return utenteDaNotificare;
	}

	public void setUtenteDaNotificare(Utente utenteDaNotificare) {
		this.utenteDaNotificare = utenteDaNotificare;
	}

	public Post getPostDiRiferimento() {
		return postDiRiferimento;
	}

	public void setPostDiRiferimento(Post postDiRiferimento) {
		this.postDiRiferimento = postDiRiferimento;
	}
	
}
