package org.openjfx.Progetto.Boundary;

import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ControllerProfilo {
	@FXML
	private JFXTextArea descrizioneUtente;
	@FXML
	private Text username; 
	@FXML
	private Text imgProfilo;
	@FXML
	private Text email;
	
	public void assegnaValori(@SuppressWarnings("exports") Utente utenteCorrente){
		username.setText(utenteCorrente.getUserName());
		descrizioneUtente.setText(utenteCorrente.getDescrizione());
		imgProfilo.setText(utenteCorrente.getImgProfilo());
		email.setText(utenteCorrente.getEmail());
	}
}
