package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.ClassiDao.CommentoDao;
import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;
import org.openjfx.Progetto.Entity.ClassiDao.RichiestaDao;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ControllerGruppo implements Initializable{
	@FXML
	private JFXButton creaPost;
	@FXML
	private JFXButton cercaGruppo;
	@FXML
	private JFXButton tornaHome;
	@FXML
	private JFXButton richiedi;
	@FXML
	private Label tagGruppo;
	@FXML
	private JFXTextArea tema;
	@FXML
	private JFXListView<Node> listaPost;
	
	private GestoreFinestre gestore = new GestoreFinestre();
	private RichiestaDao richiesta = new RichiestaDao();
	

	
	//private ControllerPost postController = new ControllerPost();
	
	public void creaPost() throws IOException {
		gestore.switchFinestraCreaPost();
	}
	public void cercaGruppoClicked() throws IOException {
		gestore.switchFinestraRicercaGruppi();
	}
	public void tornaHomeClicked() throws IOException {
		gestore.switchFinestraHome();
	}
	
	
	public void setPulsanteRichiesta() {
		UtenteDao utente = new UtenteDao();
		int statoRichiesta = richiesta.statoRichiesta(UtenteDao.getUtenteCorrente(), GruppoDao.getGruppoCorrente());
		
		if(utente.isPartecipanteAlgruppo(UtenteDao.getUtenteCorrente().getEmail(), GruppoDao.getGruppoCorrente().getTag()))
		{
			statoRichiesta = 1;
		}
		
		creaPost.setDisable(true);
		creaPost.setVisible(false);
		switch(statoRichiesta) {
		case 0 :
			{
				richiedi.setText("Richiesta effettuata");
				richiedi.setDisable(true);
				break;
			}
		case 1 :
			{
				richiedi.setText("Partecipi");
				richiedi.setDisable(true);
				creaPost.setDisable(false);
				creaPost.setVisible(true);
				break;
			}
		case 2:
			{
				richiedi.setText("Effettua Richiesta");
				richiedi.setDisable(false);
				break;
			}
		}
		
		
		
		/*
		 * richiedi.setText("Partecipi"); richiedi.setDisable(true);
		 * creaPost.setVisible(true); creaPost.setDisable(false);
		 * listaPost.setDisable(false); listaPost.setVisible(true);
		 */
	}
	
	public void inviaRichiesta() {
		richiesta.inserisciRichiesta(UtenteDao.getUtenteCorrente(), GruppoDao.getGruppoCorrente());
		setPulsanteRichiesta();
	}
	
	public void popolaFinestraGruppo(Gruppo gruppoDaVisualizzare) {
		
		tagGruppo.setText(gruppoDaVisualizzare.getTag());
		tema.setText(gruppoDaVisualizzare.getTema());
		caricaListaPost();
		setPulsanteRichiesta();
		
	}
	
	public List<Node> prendiListaPost(Gruppo gruppo) throws IOException{
		PostDao post = new PostDao();
		CommentoDao commento = new CommentoDao();
		List<Node> listaPost = new ArrayList<>();
		for(Post postCorrente : post.listaPostGruppo(gruppo.getTag()) )
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));		
			loader.setController(new ControllerPost(postCorrente));
			listaPost.add(loader.load());
		}
		return listaPost;
		
	}
	
	public void caricaListaPost() {
		try {
			listaPost.setItems(FXCollections.observableArrayList(prendiListaPost(GruppoDao.getGruppoCorrente())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popolaFinestraGruppo(GruppoDao.getGruppoCorrente());
		
	}
	
	
	
}
