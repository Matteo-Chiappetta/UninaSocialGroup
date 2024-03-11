package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class ControllerProfiloRicercato extends ControllerProfilo{
	@FXML
	private JFXButton segui;
	
	public void segui() throws IOException {
		if(utente.isSeguito(getUtenteSelezionato().getEmail())) {
			//in caso di true e premiamo il pulsante smettiamo di seguiure quell'utente
			utente.smettiDiSeguire(getUtenteSelezionato().getEmail());
			segui.setText("+ Segui");
			//Aggiorniamo la lista dei follower
			listaFollower.setItems(FXCollections.observableArrayList(utente.listaFollower(getUtenteSelezionato().getEmail())));
		}
		else {
			//viceversa se premiamo il pulsante seguiamo quell'utente
			utente.seguiUtente(getUtenteSelezionato().getEmail());
			segui.setText("- Smetti di seguire");
			//Aggiorniamo la lista dei follower
			listaFollower.setItems(FXCollections.observableArrayList(utente.listaFollower(getUtenteSelezionato().getEmail())));
		}
	}
	
	public void setPulsanteSegui() {
		if(utente.isSeguito(getUtenteSelezionato().getEmail())) {
			segui.setText("- Smetti di seguire");
		}
		else {
			segui.setText("+ Segui");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assegnaValori(getUtenteSelezionato());
		setPulsanteSegui();
	}
}
