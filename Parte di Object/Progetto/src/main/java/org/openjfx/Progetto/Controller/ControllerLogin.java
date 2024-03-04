package org.openjfx.Progetto.Controller;

import java.io.IOException;

import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class ControllerLogin {
	UtenteDao utente = new UtenteDao();
	@FXML
	Text loginError;
	@FXML
	TextField emailLogin;
	@FXML
	PasswordField passwordLogin;
	@FXML
	Label username;
	GestoreFinestre switchFinestra = new GestoreFinestre();
	@FXML
	private void ProvaLogin() throws IOException {
        if(utente.login(emailLogin.getText(),passwordLogin.getText())) {
        	
        	switchFinestra.switchFinestraHome();
        }
        else
        {
        	loginError.setVisible(true);
        }
    }
}
