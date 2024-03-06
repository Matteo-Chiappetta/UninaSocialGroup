 package org.openjfx.Progetto.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;



public class ControllerProfilo implements Initializable{
	private UtenteDao utente = new UtenteDao();
	private GestoreFinestre gestoreFinestre = new GestoreFinestre();
	private Utente utenteCorrente = UtenteDao.getUtenteCorrente();

	
	@FXML
	private JFXTextArea descrizioneUtente;
	@FXML
	private Label username; 
	@FXML
	private ImageView imgProfilo;
	@FXML
	private Text email;
	@FXML
	private JFXListView<Utente> listaSeguiti;
	@FXML
	private JFXListView<Utente> listaFollower;
	@FXML
	private JFXListView<String> listaGruppi;
	
	@FXML
	public void tornaHome() throws IOException{
		gestoreFinestre.switchFinestraHome();
	}
	
	@FXML
	public void cercaGruppo() throws IOException{
		gestoreFinestre.switchFinestraRicercaGruppi();
	}
	@FXML
	public void modificaDescrizione() throws IOException {
		gestoreFinestre.switchFinestraModificaDescrizione();
	}
	
	
	
	public void assegnaValori(Utente utenteSelezionato) {
		
		//Nel caso in cui nessun utente è stato selezionato assegno i valori dell'utente corrente
	
		//caso in cui abbiamo selezionato un utente ,dalla ricerca, da una lista dei follower o dei seguiti mostrera la pagina profilo di quell'utente
		try {
			//controllo se l utente ha o meno un immagine di profilo
			if(utenteSelezionato.getImgProfilo() == null || utenteSelezionato.getImgProfilo().length() < 10) {
				File file = new File("src\\main\\resources\\org\\openjfx\\Immagine\\utente.jpg");
				Image image = new Image(file.toURI().toString());
				imgProfilo.setImage(image);
			}else
			{
				File file = new File(utenteSelezionato.getImgProfilo());
		        Image image = new Image(file.toURI().toString());
				imgProfilo.setImage(image);
			}
			//assegno i valori dell'utente alle componenti dellla pagina
			
			username.setText(utenteSelezionato.getUserName());
			descrizioneUtente.setText(utenteSelezionato.getDescrizione());
			email.setText(utenteSelezionato.getEmail());
			listaSeguiti.setItems(FXCollections.observableArrayList(utente.listaSeguiti(utenteSelezionato.getEmail())));
			listaFollower.setItems(FXCollections.observableArrayList(utente.listaFollower(utenteSelezionato.getEmail())));
			listaGruppi.setItems(FXCollections.observableArrayList(utente.ricercaPartecipazioni(utenteSelezionato.getEmail())));
			
		}catch(Exception e){
			System.out.println("non segue nessun");
		}
	}
	
	public void vaiInProfiloUtenteFollower() {
		Utente utenteSelezionato = listaFollower.getSelectionModel().getSelectedItem();
		assegnaValori(utenteSelezionato);
	}
	
	public void vaiInProfiloUtenteSeguiti() {
		Utente utenteSelezionato = listaSeguiti.getSelectionModel().getSelectedItem();
		assegnaValori(utenteSelezionato);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RicercaUtentiController con = new RicercaUtentiController();
		Utente utenteSelezionato = con.getUtenteSelezionato();
		try {
			if(utenteSelezionato == null || utenteSelezionato.getEmail().equals(utenteCorrente.getEmail())) {
				assegnaValori(utenteCorrente);
			}
			else
			{
				assegnaValori(con.getUtenteSelezionato());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assegnaValori(utenteDaVisualizzare);
	}
}
