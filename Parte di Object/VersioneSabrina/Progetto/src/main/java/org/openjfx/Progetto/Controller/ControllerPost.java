package org.openjfx.Progetto.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	
	private Post postCorrente;
	
	
	public void inizializzaPost(Post postCorrente) {
		//PostDao post = new PostDao();
		username.setText(postCorrente.getUtenteCreatore().getUserName());
		didascalia.setText(postCorrente.getDidascalia());
	}
	
	
	
	public ControllerPost(Post postCorrente) {
		this.postCorrente = postCorrente;
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inizializzaPost(postCorrente);
	}
	
	
}
