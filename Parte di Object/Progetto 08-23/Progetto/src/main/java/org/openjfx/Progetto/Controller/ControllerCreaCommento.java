package org.openjfx.Progetto.Controller;

import java.io.IOException;

import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.ClassiDao.CommentoDao;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;

public class ControllerCreaCommento {
	@FXML
	private JFXTextArea commento;
	@FXML
	private JFXButton annullaButton;
	@FXML
	private JFXButton pubblicaButton;
	
	private GestoreFinestre gestore = new GestoreFinestre();
	
	private Post postDaCommentare;
	
	private ControllerPost postController;
	
	public void aggiungiCommento() throws IOException {
		CommentoDao commentoDaPubblicare = new CommentoDao();
		String commentoDaInserire = commento.getText();
		commentoDaPubblicare.aggiungiCommento(commentoDaInserire, UtenteDao.getUtenteCorrente().getEmail(), ControllerPost.getPostCorrente().getIdPost());
		gestore.switchFinestraGruppo();
	}
	
	public void annullaCreaCommento() throws IOException {
		gestore.switchFinestraGruppo();
	}



	
	
	
}
