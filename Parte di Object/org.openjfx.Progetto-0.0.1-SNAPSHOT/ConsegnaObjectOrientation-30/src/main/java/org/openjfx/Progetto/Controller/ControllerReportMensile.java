package org.openjfx.Progetto.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.ClassiDao.CommentoDao;
import org.openjfx.Progetto.Entity.ClassiDao.GruppoDao;
import org.openjfx.Progetto.Entity.ClassiDao.MiPiaceDao;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;
import org.openjfx.Progetto.Entity.ClassiDao.UtenteDao;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class ControllerReportMensile implements Initializable{
	
	@FXML
	private ComboBox listaMesi;
	@FXML
	private ComboBox<Gruppo> gruppi;
	@FXML
	private JFXButton report;
	@FXML
	private Tab piuLike;
	@FXML
	private Tab menoLike;
	@FXML
	private Tab piuCommenti;
	@FXML
	private Tab menoCommenti;
	@FXML
	private Text messErrore;
	@FXML
	private JFXButton homeBtn;
	@FXML
	private Text mediaPost;
	
	private GestoreFinestre gestoreFinestre = new GestoreFinestre();
	private GruppoDao gruppo = new GruppoDao();
	private PostDao post = new PostDao();
	private MiPiaceDao miPiace = new MiPiaceDao();
	private CommentoDao commento = new CommentoDao();
	
	
	//metodo che mostra a schermo il report richiesto
	@FXML
	public void ottieniReport() throws IOException {
		int mese = listaMesi.getSelectionModel().getSelectedIndex() + 1;
		Gruppo gruppoSelezionato = gruppi.getSelectionModel().getSelectedItem();
		try {
			
			messErrore.setVisible(false);
			//prendiamo le componenti di un post in cui verranno assegnati i rispettivi post generati dal report
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));	
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			//inizializzato il post con piu like
			loader1.setController(new ControllerPost(getPostConPiuMiPiace(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			piuLike.setContent(loader1.load());
			//inizializzato il post con meno like
			loader2.setController(new ControllerPost(getPostConMenoMiPiace(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			menoLike.setContent(loader2.load());
			//inizializzato il post con piu commenti
			loader3.setController(new ControllerPost(getPostConPiuCommenti(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			piuCommenti.setContent(loader3.load());
			//inizializzato il post con meno commenti
			loader4.setController(new ControllerPost(getPostConMenoCommenti(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			menoCommenti.setContent(loader4.load());
			
			mediaPost.setText("Il numero medio di post pubblicati nei gruppi è di : "+mediaPostGruppi(gruppo.getListaGruppiCreati(UtenteDao.getUtenteCorrente().getEmail()),mese));
			
			//in caso di questi errori verranno mostrati a schermo
		}catch(NullPointerException e) {
			messErrore.setText("Assicurati di aver selezionato gruppo e mese.");
			messErrore.setVisible(true);
		}catch(IndexOutOfBoundsException e) {
			messErrore.setText("Ops! Sembra che non ci siano post per il mese selezionato.");
			messErrore.setVisible(true);
		}
		
	}
	
	//metodo che ci restituisce il post con piu like data una lista di post per un certo mese
	public Post getPostConPiuMiPiace(ArrayList<Post> listaPost) {
		int idPost = 0;
		int numMiPiace = 0;
		for(Post postDaControllare : listaPost) {
			if(numMiPiace <= miPiace.getNumMiPiace(postDaControllare.getIdPost())) {
				idPost = postDaControllare.getIdPost();
				numMiPiace = miPiace.getNumMiPiace(postDaControllare.getIdPost());
			}
		}
		return post.recuperaPost(idPost);
	}
	
	//metodo che ci restituisce il post con meno like data una lista di post per un certo mese
	public Post getPostConMenoMiPiace(ArrayList<Post> listaPost) {
		int idPost = 0;
		int numMiPiace = miPiace.getNumMiPiace(listaPost.get(0).getIdPost());
		for(Post postDaControllare : listaPost) {
			if(numMiPiace >= miPiace.getNumMiPiace(postDaControllare.getIdPost())) {
				idPost = postDaControllare.getIdPost();
				numMiPiace = miPiace.getNumMiPiace(idPost);
			}
		}
		return post.recuperaPost(idPost);
	}
	
	//metodo che ci restituisce il post con piu commenti data una lista di post per un certo mese
	public Post getPostConPiuCommenti(ArrayList<Post> listaPost) {
		int idPost = 0;
		int numCommenti = 0;
		for(Post postDaControllare : listaPost) {
			if(numCommenti <= commento.getNumCommenti(postDaControllare.getIdPost())) {
				idPost = postDaControllare.getIdPost();
				numCommenti = commento.getNumCommenti(idPost);
			}
		}
		return post.recuperaPost(idPost);
	}
	
	//metodo che ci restituisce il post con meno commenti data una lista di post per un certo mese
	public Post getPostConMenoCommenti(ArrayList<Post> listaPost) {
		int idPost = 0;
		int numCommenti = commento.getNumCommenti(listaPost.get(0).getIdPost());
		for(Post postDaControllare : listaPost) {
			if(numCommenti >= commento.getNumCommenti(postDaControllare.getIdPost())) {
				idPost = postDaControllare.getIdPost();
				numCommenti = commento.getNumCommenti(idPost);
			}
		}
		return post.recuperaPost(idPost);
	}
	
	public int mediaPostGruppi(ArrayList<Gruppo> gruppiCreati,int mese) {
		int mediaPost = 0;
		//prendimao il numero di gruppi creati dall'utente
		int numGruppi = gruppiCreati.size();
		for(Gruppo gruppoCreato : gruppiCreati)
		{
			mediaPost += post.getNumPostInGruppo(gruppoCreato.getTag(), mese);
		}
		
		return mediaPost/numGruppi;
	}
	
	
	//lista di mesi per poter ricavare il mese ,tramite l'indice, da cui ottenere il report
	public void creaListaMesi() {
		String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
		listaMesi.setItems(FXCollections.observableArrayList(mesi));
	}
	
	//inizializzo la lista dei mesi e dei gruppi creati dall'utente
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gruppi.setItems(FXCollections.observableArrayList(gruppo.getListaGruppiCreati(UtenteDao.getUtenteCorrente().getEmail())));
		creaListaMesi();
	}
	
	public void tornaAllaHome() throws IOException {
		gestoreFinestre.switchFinestraHome();
	}

}
