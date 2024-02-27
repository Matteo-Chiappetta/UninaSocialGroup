package org.openjfx.Progetto.Boundary;
import java.io.IOException;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import org.openjfx.Progetto.*;


public class RicercaUtentiController {
	@FXML
	private Label utente;
	@FXML
	JFXListView<Utente> listaUtenti;
	@FXML
	TextField ricercaUtenti;
	@FXML
	public void RicercaUtenti() {
		
		UtenteDao utente = new UtenteDao();
		
		listaUtenti.setItems(FXCollections.observableArrayList(utente.ListaUtenti(ricercaUtenti.getText())));
		/*for(String username : utente.listaSeguiti(ricercaUtenti.getText())) {
			
		}*/
	}
	public void vaiInProfiloUtente() {
		Utente utenteSelezionato = listaUtenti.getSelectionModel().getSelectedItem();
		System.out.println(utenteSelezionato);
	}
}
