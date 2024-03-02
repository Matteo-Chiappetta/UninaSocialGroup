package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Gruppo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerGruppo implements Initializable{
	@FXML
	private JFXButton creaPost;
	@FXML
	private JFXButton cercaGruppo;
	@FXML
	private JFXButton tornaHome;
	@FXML
	private Label tagGruppo;
	@FXML
	private JFXTextArea tema;
	
	private SwitchFinestre gestore = new SwitchFinestre();
	
	public void creaPostClicked() throws IOException {
		//gestore.switch
	}
	public void cercaGruppoClicked() throws IOException {
		gestore.switchFinestraRicercaGruppi();
	}
	public void tornaHomeClicked() throws IOException {
		gestore.switchFinestraHome();
	}
	
	
	public void popolaFinestraGruppo(Gruppo gruppoDaVisualizzare) {
		tagGruppo.setText(gruppoDaVisualizzare.getTag());
		tema.setText(gruppoDaVisualizzare.getTema());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popolaFinestraGruppo(RicercaGruppiController.getGruppoSelezionato());
	}
	
	
	
}
