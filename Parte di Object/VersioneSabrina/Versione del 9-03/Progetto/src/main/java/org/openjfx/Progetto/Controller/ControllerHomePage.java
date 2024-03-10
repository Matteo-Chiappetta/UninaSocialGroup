package org.openjfx.Progetto.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class ControllerHomePage implements Initializable {
	GestoreFinestre gestoreFinestre = new GestoreFinestre();
	@FXML
	private Label username;
	@FXML
	private ImageView imgProfilo;
	
	@FXML
	private void profiloPage() throws IOException {
		gestoreFinestre.switchFinestraProfiloPersonale();
	}
	@FXML
	private void cercaGruppo() throws IOException {
		gestoreFinestre.switchFinestraRicercaGruppi();
	}
	
	@FXML
	public void cercaUtente() throws IOException {
		gestoreFinestre.switchFinestraRicerca();
	}
	
	@FXML
	public void vaiInReport() throws IOException {
		gestoreFinestre.switchFinestraReport();
	}
	
	@FXML
	public void logout() {
		gestoreFinestre.logout();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		 if(UtenteDao.getUtenteCorrente().getImgProfilo() == null || UtenteDao.getUtenteCorrente().getImgProfilo().length() < 10) { 
			 File file = new File("src\\main\\resources\\org\\openjfx\\Immagine\\utente.jpg"); Image
			 image = new Image(file.toURI().toString()); 
			 imgProfilo.setImage(image); 
		 }else{ 
			 File file = new File(UtenteDao.getUtenteCorrente().getImgProfilo()); Image
			 image = new Image(file.toURI().toString()); 
			 imgProfilo.setImage(image); 
		}
		 
		username.setText(UtenteDao.getUtenteCorrente().getUserName());
		username.setTextAlignment(TextAlignment.LEFT);
	}
	
}