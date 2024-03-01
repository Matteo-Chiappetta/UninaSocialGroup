package org.openjfx.Progetto.Entity;

public class MiPiace extends Interazione {
	// Vedere come rappresentare in java la relazione post e mi piace
	private Utente utenteMiPiace;
	private Post postPiaciuto;
	
	
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
	
	
}
