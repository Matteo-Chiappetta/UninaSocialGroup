package org.openjfx.Progetto.Controller;

import java.io.IOException;

import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;

public class ControllerModificaDescrizione {
	@FXML
	private JFXButton annullaButton;
	@FXML
	private JFXButton salvaButton;
	@FXML
	private JFXTextArea descrizione;
	
	private GestoreFinestre gestore = new GestoreFinestre();
	private UtenteDao utenteDaModificare = new UtenteDao();
	
	//private UtenteDao utente = new UtenteDao();
	
	
	
	public void annullaClicked() throws IOException {
		gestore.switchFinestraProfilo();
	}
	
	public void salvaclicked() throws IOException{
		utenteDaModificare.modificaDescrizioneUtente(UtenteDao.getUtenteCorrente(), descrizione.getText());
		UtenteDao.getUtenteCorrente().setDescrizione(descrizione.getText());
		gestore.switchFinestraProfilo();
	}
}
