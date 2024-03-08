package org.openjfx.Progetto.Controller;
import java.io.IOException;
import org.openjfx.Progetto.App;
import javafx.fxml.FXML;


//FAI CHE LA FUNZIONE PRENDE COME PARAMETRO DI INPUT LA STRINGA DELLA FINESTRA SU CUI FARE LO SWITCH

public class GestoreFinestre {
	
	@FXML
	public void switchFinestraRicerca() throws IOException {
        App.setRoot("FinestraRicercaUtenti");
	}
	@FXML
	public void switchFinestraProfiloPersonale() throws IOException {
    	App.setRoot("FinestraProfiloPersonale");
	}
	
	@FXML
	public void switchFinestraProfilo() throws IOException {
    	App.setRoot("FinestraProfiloCercato");
	}
	
	@FXML
	public void switchFinestraHome() throws IOException {
    	App.setRoot("PaginaHome");
	}
	
	@FXML
	public void switchFinestraProvaModifica() throws IOException {
    	App.setRoot("ProvaModicaUtente");
	}
	
	@FXML
	public void switchFinestraRicercaGruppi() throws IOException {
        App.setRoot("FinestraRicercaGruppi");
	}
	@FXML
	public void switchFinestraModificaDescrizione() throws IOException {
        App.setRoot("FinestraModificaDescrizione");
	}
	@FXML
	public void switchFinestraGruppo() throws IOException {
        App.setRoot("FinestraGruppo");
	}
	
	@FXML
	public void switchFinestraCreaPost() throws IOException {
        App.setRoot("FinestraCreaPost");
	}
	
	@FXML
	public void switchFinestraCreaCommento() throws IOException {
        App.setRoot("CreaCommento");
	}
	
	@FXML
	public void logout() {
		javafx.application.Platform.exit();
	}
}
