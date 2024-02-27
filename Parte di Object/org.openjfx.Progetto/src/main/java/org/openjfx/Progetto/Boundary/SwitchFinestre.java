package org.openjfx.Progetto.Boundary;
import java.io.IOException;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import org.openjfx.Progetto.*;

//FAI CHE LA FUNZIONE PRENDE COME PARAMETRO DI INPUT LA STRINGA DELLA FINESTRA SU CUI FARE LO SWITCH

public class SwitchFinestre {
	UtenteDao utente = new UtenteDao();
	Utente utenteCorrente = UtenteDao.getUtente();
	@FXML
	private void SwitchFinestraRicerca() throws IOException {
        App.setRoot("FinestraRicercaUtenti");
	}
	@FXML
	private void SwitchFinestraProfilo() throws IOException {
		ControllerProfilo controller = new ControllerProfilo();
    	App.setRoot("FinestraProfiloUtente");
    	controller.assegnaValori(utenteCorrente);
}
	@FXML
	private void SwitchFinestraHome() throws IOException {
    	App.setRoot("HomePage2");
}
}
