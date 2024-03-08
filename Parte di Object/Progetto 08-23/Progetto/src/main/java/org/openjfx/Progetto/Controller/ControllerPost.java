package org.openjfx.Progetto.Controller;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

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
	
	private static Post postCorrente;
	
	private List<Commento> listaCommentiCorrente;
	
	private MiPiaceDao miPiaceCorrente = new MiPiaceDao();
	
	

	public static Post getPostCorrente() {
		return postCorrente;
	}

	public void creaCommento() throws IOException {
		GestoreFinestre gestore = new GestoreFinestre();
		gestore.switchFinestraCreaCommento();
	}
	
	
	public void inizializzaPost() {
		//PostDao post = new PostDao();
		username.setText(postCorrente.getUtenteCreatore().getUserName());
		didascalia.setText(postCorrente.getDidascalia());
		caricaListaCommenti();
		setPulsanteMiPiace();
	}
	
	
	public ControllerPost(Post postListaCorrente) {
		this.postCorrente = postListaCorrente;
	}
	
	
	/*
	 * public void mostraCommenti() {
	 * PostDao.setPostCorrente(listaPost.getSelectionModel().getSelectedItem());
	 * caricaListaCommenti(); }
	 */
	
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
