package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerHomePage implements Initializable {
	SwitchFinestre gestoreFinestre = new SwitchFinestre();
	@FXML
	private Label username;
	
	@FXML
	private void profiloPage() throws IOException {
		gestoreFinestre.switchFinestraProfilo();
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
	public void logout() {
		gestoreFinestre.logout();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username.setText(UtenteDao.getUtenteCorrente().getUserName());
	}
	
}