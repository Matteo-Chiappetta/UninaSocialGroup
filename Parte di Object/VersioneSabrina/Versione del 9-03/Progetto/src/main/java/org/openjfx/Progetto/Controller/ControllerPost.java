package org.openjfx.Progetto.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Commento;
import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.ClassiDao.CommentoDao;
import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
import org.openjfx.Progetto.Entity.ClassiDao.MiPiaceDao;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ControllerPost implements Initializable{
	
	@FXML
	private Label username;
	@FXML
	private JFXTextArea didascalia;
	@FXML
	private JFXButton miPiace;
	@FXML
	private JFXButton aggiungiCommento;
	@FXML
	private JFXListView<Node> commenti;
	@FXML
	private Text dataPost;
	@FXML
	private ImageView imgProfilo;
	
	private Post postCorrente;
	
	
	private MiPiaceDao miPiaceCorrente = new MiPiaceDao();
	

	public Post getPostCorrente() {
		return postCorrente;
	}

	public void creaCommento() throws IOException {
		PostDao.setPostCorrente((Post)aggiungiCommento.getUserData());
		GestoreFinestre gestore = new GestoreFinestre();
		gestore.switchFinestraCreaCommento();
	}
	
	
	public void inizializzaPost() {
		if(postCorrente != null) {
			username.setText(postCorrente.getUtenteCreatore().getUserName());
			didascalia.setText(postCorrente.getDidascalia());
			dataPost.setText(postCorrente.getDataPubblicazione().toString());
			caricaListaCommenti();
			setPulsanteMiPiace();
			aggiungiCommento.setUserData(postCorrente);
			if(postCorrente.getUtenteCreatore().getImgProfilo() == null || postCorrente.getUtenteCreatore().getImgProfilo().length() < 10) {
				File file = new File("src\\main\\resources\\org\\openjfx\\Immagine\\utente.jpg");
				Image image = new Image(file.toURI().toString());
				imgProfilo.setImage(image);
			}else
			{
				File file = new File(postCorrente.getUtenteCreatore().getImgProfilo());
		        Image image = new Image(file.toURI().toString());
				imgProfilo.setImage(image);
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("attenzione!");
		}
		
        
	}
	
	
	public ControllerPost(Post postCorrente) {
		this.postCorrente = postCorrente;		
	}
	

	
	public List<Node> prendiListaCommenti() throws IOException{
		CommentoDao commento = new CommentoDao();
		postCorrente.setListaCommenti(commento.listaCommentiPost(postCorrente.getIdPost()));
		List<Node> listaCommenti = new ArrayList<>();
		for(Commento commentoCorrente : postCorrente.getListaCommenti()){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/FinestraCommento.fxml")); 
			loader.setController(new ControllerCommento(commentoCorrente));
			listaCommenti.add(loader.load()); 
		}
		for(Node commentoCorrente : listaCommenti){
			 System.out.println(commentoCorrente);
		}
		return listaCommenti;
	}
	
	
	public void caricaListaCommenti() {
		try {
			commenti.setItems(FXCollections.observableArrayList(prendiListaCommenti()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setPulsanteMiPiace() {
		if(miPiaceCorrente.miPiaceGiaMesso(postCorrente.getIdPost(), UtenteDao.getUtenteCorrente().getEmail())) {
			miPiace.setText("- Mi Piace ("+miPiaceCorrente.getNumMiPiace(postCorrente.getIdPost())+")");
		}
		else
		{
			miPiace.setText("+ Mi Piace ("+miPiaceCorrente.getNumMiPiace(postCorrente.getIdPost())+")");
		}
	}
	
	public void miPiaceClicked() {
		if(miPiaceCorrente.miPiaceGiaMesso(postCorrente.getIdPost(), UtenteDao.getUtenteCorrente().getEmail())) {
			miPiaceCorrente.rimuoviMiPiace(UtenteDao.getUtenteCorrente().getEmail(),postCorrente.getIdPost());
		}
		else
		{
			miPiaceCorrente.aggiungiMiPiace(UtenteDao.getUtenteCorrente().getEmail(),postCorrente.getIdPost());
		}
		setPulsanteMiPiace();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inizializzaPost();
	}
	
	
}
