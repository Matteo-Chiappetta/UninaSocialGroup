package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openjfx.Progetto.App;
import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ControllerGruppo implements Initializable{
	@FXML
	private JFXButton creaPost;
	@FXML
	private JFXButton cercaGruppo;
	@FXML
	private JFXButton tornaHome;
	@FXML
	private Label tagGruppo;
	@FXML
	private JFXTextArea tema;
	@FXML
	private GridPane panePost;
	
	private GestoreFinestre gestore = new GestoreFinestre();
	

	
	//private ControllerPost postController = new ControllerPost();
	
	public void creaPostClicked() throws IOException {
		//gestore.switch
	}
	public void cercaGruppoClicked() throws IOException {
		gestore.switchFinestraRicercaGruppi();
	}
	public void tornaHomeClicked() throws IOException {
		gestore.switchFinestraHome();
	}
	
	
	public void popolaFinestraGruppo(Gruppo gruppoDaVisualizzare) {
		
		tagGruppo.setText(gruppoDaVisualizzare.getTag());
		tema.setText(gruppoDaVisualizzare.getTema());
		caricaListaPost();
		
	}
	
	public List<Node> prendiListaPost(Gruppo gruppo) throws IOException{
		PostDao post = new PostDao();
		List<Node> listaPost = new ArrayList<>();
		for(Post postCorrente : post.listaPostGruppo(gruppo.getTag()) )
		{
			System.out.println("Ciaonsono nel for");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			loader.setController(new ControllerPost(postCorrente));
			System.out.println(loader.getLocation());
			System.out.println("Ciaonsono dopo il set controller");
			System.out.println(postCorrente);
			listaPost.add(loader.load());
			System.out.println("ciao siamo sotto add");
		}
		return listaPost;
		
	}
	
	public void caricaListaPost() {
		
		int colonne = 0;
		int righe = 0;
		try {
			for(Node post : prendiListaPost(RicercaGruppiController.getGruppoSelezionato())) {
				panePost.add(post, colonne, righe++);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popolaFinestraGruppo(RicercaGruppiController.getGruppoSelezionato());
	}
	
	
	
}
