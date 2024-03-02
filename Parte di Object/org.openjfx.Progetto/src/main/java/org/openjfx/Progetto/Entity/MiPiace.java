package org.openjfx.Progetto.Entity;

import java.sql.Timestamp;

public class MiPiace extends Interazione {
	
	// Vedere come rappresentare in java la relazione post e mi piace
	private Utente utenteMiPiace;
	private Post postPiaciuto;
	private int idMiPiace;
	
	public Utente getUtenteMiPiace() {
		return utenteMiPiace;
	}
	public void setUtenteMiPiace(Utente utenteMiPiace) {
		this.utenteMiPiace = utenteMiPiace;
	}
	public Post getPostPiaciuto() {
		return postPiaciuto;
	}
	public void setPostPiaciuto(Post postPiaciuto) {
		this.postPiaciuto = postPiaciuto;
	}
	public int getIdMiPiace() {
		return idMiPiace;
	}
	public MiPiace(int idMiPiace, Utente utenteMiPiace, Post postPiaciuto,Timestamp dataInterazione) {
		super(dataInterazione);
		this.utenteMiPiace = utenteMiPiace;
		this.postPiaciuto = postPiaciuto;
		this.idMiPiace = idMiPiace;
	}
	
	
}
