package org.openjfx.Progetto.Controller;

import java.io.IOException;
import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerRicercaGruppi {
	private GestoreFinestre gestoreFinestre = new GestoreFinestre();
	@FXML
	private JFXListView<Gruppo> listaGruppi;
	@FXML
	private TextField ricercaGruppi;
	
	ControllerGruppo controller = new ControllerGruppo();
	//static Gruppo gruppoSelezionato;
	
	public void cercaPerTema() {
		GruppoDao gruppo = new GruppoDao();
		listaGruppi.setItems(FXCollections.observableArrayList(gruppo.listagruppiPerTema(ricercaGruppi.getText())));
	}
	
	
	public void cercaPerTag() {
		GruppoDao gruppo = new GruppoDao();
		listaGruppi.setItems(FXCollections.observableArrayList(gruppo.listagruppiPerTag(ricercaGruppi.getText())));
	}
	
	
	public void vaiInGruppo() throws IOException {
		GruppoDao.setGruppoCorrente(listaGruppi.getSelectionModel().getSelectedItem());
		gestoreFinestre.switchFinestraGruppo();
	}
	
	
	public void tornaAllaHome() throws IOException {
		gestoreFinestre.switchFinestraHome();
	}

	public void vaiInRicercaUtenti() throws IOException {
		gestoreFinestre.switchFinestraRicerca();
	}

}
