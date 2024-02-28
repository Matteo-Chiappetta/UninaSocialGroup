package org.openjfx.Progetto.Boundary;

import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerHomePage implements Initializable {
	@FXML
	public Label username;
	
	//Utente utenteCorrente = UtenteDao.getUtenteCorrente();
	
	public void showUser(String name) {
		System.out.println(name);
		username.setText(name);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
