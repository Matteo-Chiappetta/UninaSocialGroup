package org.openjfx.Progetto.Controller;

import java.io.IOException;

import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ControllerRicercaUtenti {
	@FXML
	private Label utente;
	@FXML
	private JFXListView<Utente> listaUtenti;
	@FXML
	TextField ricercaUtenti;
	private GestoreFinestre gestore = new GestoreFinestre();
	private static Utente utenteRicercato;
	
	@FXML
	public void RicercaUtenti() {
		
		UtenteDao utente = new UtenteDao();	
		
			
		listaUtenti.setItems(FXCollections.observableArrayList(utente.listaUtenti(ricercaUtenti.getText())));
	}
	public void vaiInProfiloUtente() throws IOException {
		utenteRicercato = listaUtenti.getSelectionModel().getSelectedItem();
		ControllerProfilo.setUtenteSelezionato(utenteRicercato);
		gestore.switchFinestraProfiloRicercato();
		
	}
	
	public void vaiInRicercaGruppi() throws IOException {
		gestore.switchFinestraRicercaGruppi();
		
	}
	
	public void tornaAllaHome() throws IOException {
		gestore.switchFinestraHome();
		
	}
	
	public static Utente getUtenteSelezionato() {
		return utenteRicercato;
	}
	
}
