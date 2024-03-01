package org.openjfx.Progetto.Controller;

import java.io.IOException;
import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RicercaGruppiController {
	private SwitchFinestre tornaHome = new SwitchFinestre();
	@FXML
	private JFXListView<Gruppo> listaGruppi;
	@FXML
	private TextField ricercaGruppi;
	
	public void cercaPerTema() {
		GruppoDao gruppo = new GruppoDao();
		listaGruppi.setItems(FXCollections.observableArrayList(gruppo.listagruppiPerTema(ricercaGruppi.getText())));
	}
	
	public void cercaPerTag() {
		GruppoDao gruppo = new GruppoDao();
		listaGruppi.setItems(FXCollections.observableArrayList(gruppo.listagruppiPerTag(ricercaGruppi.getText())));
	}
	
	public void vaiInGruppo() {
		Gruppo gruppoSelezionato = listaGruppi.getSelectionModel().getSelectedItem();
		System.out.println(gruppoSelezionato);
	}
	public void tornaHome() throws IOException {
		tornaHome.switchFinestraHome();
	}
	
	
}
