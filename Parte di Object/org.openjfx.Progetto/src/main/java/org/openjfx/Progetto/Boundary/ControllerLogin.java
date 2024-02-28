package org.openjfx.Progetto.Boundary;

import java.io.IOException;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import org.openjfx.Progetto.*;

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
	SwitchFinestre switchFinestra = new SwitchFinestre();
	@FXML
	private void ProvaLogin() throws IOException {
        if(utente.Login(emailLogin.getText(),passwordLogin.getText())) {
        	
        	switchFinestra.SwitchFinestraHome();
        	
        	//username.setText(utente.getUtenteCorrente().getUserName());
        }
        else
        {
        	loginError.setVisible(true);
        }
    }
}
