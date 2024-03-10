package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.awt.Desktop;


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
	private void login() throws IOException {
        if(utente.login(emailLogin.getText(),passwordLogin.getText())) {
        	
        	switchFinestra.switchFinestraHome();
        }
        else
        {
        	loginError.setVisible(true);
        }
	}
	
	@FXML
	private void apriLink() throws IOException, URISyntaxException {
		 if (Desktop.isDesktopSupported()) {
		        Desktop desktop = Desktop.getDesktop();
		        URI uri = new URI("https://informatica.dieti.unina.it/index.php/it/");
		         desktop.browse(uri);
		 }
	}
	
	
}

