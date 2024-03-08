package org.openjfx.Progetto.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Commento;
import org.openjfx.Progetto.Entity.Post;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ControllerCommento implements Initializable{
	
	@FXML
	private Label username;
	@FXML
	private JFXTextArea didascalia;
	@FXML
	private ImageView imgProfilo;
	
	private Commento commentoCorrente;
	
	
	public void inizializzaCommento(Commento commentoCorrente) {
		username.setText(commentoCorrente.getUtenteCommentatore().getUserName());
		didascalia.setText(commentoCorrente.getCommento());
	}
	
	
	public ControllerCommento(Commento commentoCorrente) {
		this.commentoCorrente = commentoCorrente;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inizializzaCommento(commentoCorrente);
	}
}
