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
	
	private GruppoDao gruppo = new GruppoDao();
	private PostDao post = new PostDao();
	private MiPiaceDao miPiace = new MiPiaceDao();
	private CommentoDao commento = new CommentoDao();
	
	
	@FXML
	public void ottieniReport() throws IOException {
		//String mese = listaMesi.getS
		int mese = listaMesi.getSelectionModel().getSelectedIndex() + 1;
		Gruppo gruppoSelezionato = gruppi.getSelectionModel().getSelectedItem();
		try {
			
			//getPostConMenoMiPiace(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese));
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));	
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/org/openjfx/Progetto/Post.fxml"));
			loader1.setController(new ControllerPost(getPostConPiuMiPiace(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			piuLike.setContent(loader1.load());
			loader2.setController(new ControllerPost(getPostConMenoMiPiace(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			menoLike.setContent(loader2.load());
			loader3.setController(new ControllerPost(getPostConPiuCommenti(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			piuCommenti.setContent(loader3.load());
			loader4.setController(new ControllerPost(getPostConMenoCommenti(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese))));
			menoCommenti.setContent(loader4.load());
			//System.out.println(getPostConPiuMiPiace(post.listaPostGruppoPerMese(gruppoSelezionato.getTag(), mese)));
			
		}catch(NullPointerException e) {
			System.out.println("DEVI SELEZIONARE MESE E GRUPPO");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("NON CI SONO POST O NON HAI SELEZIONATO IL MESE");
		}
		
	}
	
	
	public Post getPostConPiuMiPiace(ArrayList<Post> listaPost) {
		int idPost = 0;
		int numMiPiace = 0;
		for(Post PostDa : listaPost) {
			System.out.println(PostDa);
		}
		for(Post postDaControllare : listaPost) {
			if(numMiPiace <= miPiace.getNumMiPiace(postDaControllare.getIdPost())) {
				idPost = postDaControllare.getIdPost();
				numMiPiace = miPiace.getNumMiPiace(idPost);
			}
		}
		return post.recuperaPost(idPost);
	}
	
	public Post getPostConMenoMiPiace(ArrayList<Post> listaPost) {
		int idPost = 0;
		int numMiPiace = miPiace.getNumMiPiace(listaPost.get(0).getIdPost());
		for(Post postDaControllare : listaPost) {
			if(numMiPiace >= miPiace.getNumMiPiace(postDaControllare.getIdPost())) {
				idPost = postDaControllare.getIdPost();
				numMiPiace = miPiace.getNumMiPiace(idPost);
			}
		}
		System.out.println(post.recuperaPost(idPost));
		return post.recuperaPost(idPost);
	}
	
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
	
	
	/*
	 * public int getMeseSelezionato(String mese) { switch(mese) { case "Gennaio":
	 * return 1; case "Febbraio": return 2; case "Marzo": return 3; case "Aprile":
	 * return 4; case "Maggio": return 5; case "Giugno": return 6; case "Luglio":
	 * return 7; case "Agosto": return 8; case "Settembre": return 9; case
	 * "Ottobre": return 10; case "Novembre": return 11; case "Dicembre": return 12;
	 * default : return 0; } }
	 */
	
	public void creaListaMesi() {
		String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
		listaMesi.setItems(FXCollections.observableArrayList(mesi));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gruppi.setItems(FXCollections.observableArrayList(gruppo.getListaGruppiCreati(UtenteDao.getUtenteCorrente().getEmail())));
		creaListaMesi();
	}

}
