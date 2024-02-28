package org.openjfx.Progetto.Boundary;
import java.io.IOException;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.openjfx.Progetto.*;

//FAI CHE LA FUNZIONE PRENDE COME PARAMETRO DI INPUT LA STRINGA DELLA FINESTRA SU CUI FARE LO SWITCH

public class SwitchFinestre {
	
	@FXML
	private void SwitchFinestraRicerca() throws IOException {
        App.setRoot("FinestraRicercaUtenti");
	}
	@FXML
	private void SwitchFinestraProfilo() throws IOException {
		ControllerProfilo controller = new ControllerProfilo();
		controller.assegnaValori();
    	App.setRoot("FinestraProfiloUtente");
    	
}
	@FXML
	public void SwitchFinestraHome() throws IOException {
		/*
		ControllerHomePage  controllerHomePage = new ControllerHomePage(); 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage2.fxml"));
		System.out.println("qua va abne prima");
    	Parent root = loader.load();
		System.out.println("qua va abne prima");
		System.out.println(""+loader.getController());
    	controllerHomePage.showUser(UtenteDao.getUtenteCorrente().getUserName());
    	*/
    	App.setRoot("HomePage2");
    	
}
	@FXML
	private void SwitchFinestraProvaModifica() throws IOException {
    	App.setRoot("ProvaModicaUtente");
	}
	@FXML
	private void SwitchFinestraRicercaGruppi() throws IOException {
        App.setRoot("FinestraRicercaGruppi");
	}
}
