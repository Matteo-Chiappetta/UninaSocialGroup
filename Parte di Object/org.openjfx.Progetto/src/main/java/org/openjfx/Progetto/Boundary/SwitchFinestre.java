package org.openjfx.Progetto.Boundary;
import java.io.IOException;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import org.openjfx.Progetto.*;

//FAI CHE LA FUNZIONE PRENDE COME PARAMETRO DI INPUT LA STRINGA DELLA FINESTRA SU CUI FARE LO SWITCH

public class SwitchFinestre {
	@FXML
	private void SwitchFinestraRicerca() throws IOException {
        	App.setRoot("FinestraRicercaUtenti");
	}
}
