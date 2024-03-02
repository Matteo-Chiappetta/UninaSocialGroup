package org.openjfx.Progetto.Controller;

import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class RicercaUtentiController {
	@FXML
	private Label utente;
	@FXML
	private JFXListView<Utente> listaUtenti;
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
