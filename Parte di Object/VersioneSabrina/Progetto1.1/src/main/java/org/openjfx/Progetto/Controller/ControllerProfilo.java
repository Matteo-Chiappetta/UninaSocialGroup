 package org.openjfx.Progetto.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Utente;
import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
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
	protected UtenteDao utente = new UtenteDao();
	
	protected GestoreFinestre gestoreFinestre = new GestoreFinestre();
	
	private static Utente utenteSelezionato;
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
	protected JFXListView<Utente> listaFollower;
	@FXML
	private JFXListView<Gruppo> listaGruppi;
	
	@FXML
	public void tornaHome() throws IOException{
		gestoreFinestre.switchFinestraHome();
	}
	
	
	public Utente getUtenteSelezionato() {
		return utenteSelezionato;
	}
	
	public static void setUtenteSelezionato(Utente utenteRicercato) {
		//l'utente ricercato verra salvato per poi essere caricato nella pagina del profilo
		utenteSelezionato = utenteRicercato ;
	}
	
	public void vaiInGruppo() throws IOException {
		try {
			GruppoDao.setGruppoCorrente(listaGruppi.getSelectionModel().getSelectedItem());
			//System.out.println(GruppoDao.getGruppoCorrente());
			gestoreFinestre.switchFinestraGruppo();
		}catch(Exception e) {
			System.out.print("");
		}
		
	}
	
	public void assegnaValori(Utente utenteSelezionato) {
		
		//Nel caso in cui nessun utente è stato selezionato assegno i valori dell'utente corrente
	
		//caso in cui abbiamo selezionato un utente ,dalla ricerca, da una lista dei follower o dei seguiti mostrera la pagina profilo di quell'utente
		try {
			//controllo se l utente ha o meno un immagine di profilo
			if(utenteSelezionato.getImgProfilo() == null) {
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
			System.out.print("");
		}
	}
	
	public void vaiInProfiloUtenteFollower() throws IOException {
		try {
			utenteSelezionato = listaFollower.getSelectionModel().getSelectedItem();
			//Controllo che l'utente selezionato sia l'utente personale in caso di true vado alla pagina personale false vado nella pagina dell'utente cercato
			if(utenteSelezionato.equals(UtenteDao.getUtenteCorrente()))
			{
				gestoreFinestre.switchFinestraProfiloPersonale();
			}
			else {
				gestoreFinestre.switchFinestraProfiloRicercato();
			}
		}catch(Exception e) {
			System.out.print("");
		}
		
		
	}
	
	public void vaiInProfiloUtenteSeguiti() throws IOException {
		try {
			utenteSelezionato = listaSeguiti.getSelectionModel().getSelectedItem();
			//Controllo che l'utente selezionato sia l'utente personale in caso di true vado alla pagina personale false vado nella pagina dell'utente cercato
				if(utenteSelezionato.equals(UtenteDao.getUtenteCorrente()))
				{
					gestoreFinestre.switchFinestraProfiloPersonale();
				}
				else {
					gestoreFinestre.switchFinestraProfiloRicercato();
				}
		}catch(Exception e) {
			System.out.print("");
		}
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//assegnaValori(utenteDaVisualizzare);
	}
}
