package org.openjfx.Progetto.Boundary;
import java.io.IOException;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import org.openjfx.Progetto.*;


public class RicercaUtentiController {
	@FXML
	JFXTextArea viewListaUtenti;
	@FXML
	TextField ricercaUtenti;
	@FXML
	public void RicercaUtenti() {
		UtenteDao utente = new UtenteDao();
		/*for(String username : utente.ListaUtenti(ricercaUtenti.getText())) {
			viewListaUtenti.setText(username);
		}*/
		for(String username : utente.listaSeguiti(ricercaUtenti.getText())) {
			viewListaUtenti.setText(username);
		}
	}
}
