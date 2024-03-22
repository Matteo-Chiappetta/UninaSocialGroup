package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;


import javafx.fxml.FXML;

public class ControllerProfiloPersonale extends ControllerProfilo{
	
	@FXML
	public void modificaDescrizione() throws IOException {
		gestoreFinestre.switchFinestraModificaDescrizione();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assegnaValori(UtenteDao.getUtenteCorrente());
		//assegnaValori(utenteDaVisualizzare);
	}
}
