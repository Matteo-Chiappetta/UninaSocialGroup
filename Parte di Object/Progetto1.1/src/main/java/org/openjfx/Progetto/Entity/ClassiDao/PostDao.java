package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Post;
import org.openjfx.Progetto.Entity.Utente;
import java.sql.PreparedStatement;

public class PostDao {
	UtenteDao utente = new UtenteDao();
	GruppoDao gruppo = new GruppoDao();
	
	private static Post postCorrente;
	
	public static Post getPostCorrente() {
		return postCorrente;
	}

	public static void setPostCorrente(Post postCorrente) {
		PostDao.postCorrente = postCorrente;
	}

	public Post recuperaPost(int inIdPost) {
		Connection connessione = DbConnect.getConnection();
		String query = "select * from post where id_post = "+inIdPost+"";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			// String imgProfilo, String descrizione,String password
			res1.next();
			Post postRicercato = new Post(res1.getInt("id_post"),res1.getTimestamp("data_ora_post"),res1.getString("didascalia"),res1.getString("tipo_file"),gruppo.recuperaGruppo(res1.getString("tag_gruppo")),utente.recuperaUtente(res1.getString("email_creatore_post")));
			res1.close();
			connessione.close();
			return postRicercato;
		} catch (SQLException e) {
			return null;
		}catch(NullPointerException e) {
			return null;
		}
	}
	
	//Metodo che prende in input il creatore del post, il grupppo in cui si vuole pubblicare e la didascalia del post
	public void creaPost(Utente utenteCreatore,Gruppo gruppoCorrente,String didascalia){
		Connection connessione = DbConnect.getConnection();
		String sql = "insert into Post (didascalia,email_creatore_post,tag_gruppo) values (?,?,?)";
		
		try {
			//eseguo la insert sulla tabella
			PreparedStatement st1 = connessione.prepareStatement(sql);
			st1.setString(1, didascalia);
			st1.setString(2,utenteCreatore.getEmail());
			st1.setString(3, gruppoCorrente.getTag());
			st1.executeUpdate();
			connessione.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Post> listaPostGruppo(String inTag){
		ArrayList<Post> listaPost = new ArrayList<Post>();
		Connection connessione = DbConnect.getConnection();
		String query = "select * from  post where tag_gruppo = '"+inTag+"' ORDER BY data_ora_post DESC";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaPost.add(recuperaPost(res1.getInt("id_post")));
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listaPost;
	} 
	
	
	
	public ArrayList<Post> listaPostGruppoPerMese(String inTag,int mese){
		ArrayList<Post> listaPost = new ArrayList<Post>();
		Connection connessione = DbConnect.getConnection();
		String query = "select * from  post where tag_gruppo = '"+inTag+"' AND  EXTRACT(Month from data_ora_post) = "+mese+" AND EXTRACT(year from data_ora_post) = "+Year.now().getValue()+"";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaPost.add(recuperaPost(res1.getInt("id_post")));
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listaPost;
	}
	
	public int getNumPostInGruppo(String inTag, int mese) {
		int numPost = 0;
		Connection connessione = DbConnect.getConnection();
		String query = "select Count(*) as numPost from  post where tag_gruppo = '"+inTag+"' AND  EXTRACT(Month from data_ora_post) = "+mese+" AND EXTRACT(year from data_ora_post) = "+Year.now().getValue()+"";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			//Prendo il numero dei post per un gruppo
			numPost = res1.getInt("numPost");
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return numPost;
	}
}
