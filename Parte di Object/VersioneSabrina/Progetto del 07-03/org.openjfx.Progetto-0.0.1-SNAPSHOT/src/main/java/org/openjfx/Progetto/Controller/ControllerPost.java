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
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXButton;
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
	private ComboBox commenti;
	
	private Post postCorrente;
	
	
	public void inizializzaPost(Post postCorrente) {
		//PostDao post = new PostDao();
		username.setText(PostDao.getPostCorrente().getUtenteCreatore().getUserName());
		didascalia.setText(postCorrente.getDidascalia());
	}
	
	
	public ControllerPost(Post postCorrente) {
		this.postCorrente = postCorrente;
	}
	
	public void mostraCommenti() {
		PostDao.setPostCorrente(listaPost.getSelectionModel().getSelectedItem());
		caricaListaCommenti();
	}
	
	public List<Node> prendiListaCommenti(Post post) throws IOException{
		CommentoDao commento = new CommentoDao();
		List<Node> listaCommenti = new ArrayList<>();
		for(Commento commentoCorrente : commento.listaCommentiPost(post.getIdPost()) )
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/FinestraCommento.fxml"));
			loader.setController(new ControllerPost(postCorrente));
			listaCommenti.add(loader.load());
		}
		
		return listaCommenti;
	}
	
	
	public void caricaListaCommenti() {
		try {
			commenti.setItems(FXCollections.observableArrayList(prendiListaCommenti(PostDao.getPostCorrente())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inizializzaPost(PostDao.getPostCorrente());
	}
	
	
}
