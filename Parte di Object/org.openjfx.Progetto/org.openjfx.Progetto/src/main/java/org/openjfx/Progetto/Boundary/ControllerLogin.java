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

public class ControllerLogin {
	UtenteDao utente = new UtenteDao();
	@FXML
	Text loginError;
	@FXML
	TextField emailLogin;
	@FXML
	PasswordField passwordLogin;
	@FXML
	private void ProvaLogin() throws IOException {
        if(utente.Login(emailLogin.getText(),passwordLogin.getText())) {
        	App.setRoot("HomePage2");
        }
        else
        {
        	loginError.setVisible(true);
        }
    }
}
