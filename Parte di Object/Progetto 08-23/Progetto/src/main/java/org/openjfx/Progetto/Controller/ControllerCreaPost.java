package org.openjfx.Progetto.Controller;

import java.io.IOException;

import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;

public class ControllerCreaPost {
	@FXML
	private JFXTextArea didascalia;
	@FXML
	private JFXButton annullaButton;
	@FXML
	private JFXButton pubblicaButton;
	private GestoreFinestre gestore = new GestoreFinestre();
	
	public void pubblicaPost() throws IOException {
		PostDao postDaPubblicare = new PostDao();
		String didascaliaDaInserire = didascalia.getText();
		postDaPubblicare.creaPost(UtenteDao.getUtenteCorrente(), GruppoDao.getGruppoCorrente(), didascaliaDaInserire);
		gestore.switchFinestraGruppo();
	}
	
	public void annullaCreaPost() throws IOException {
		gestore.switchFinestraGruppo();
	}
}
