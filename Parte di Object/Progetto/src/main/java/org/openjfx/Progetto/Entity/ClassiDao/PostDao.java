package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.openjfx.Progetto.Entity.Post;

public class PostDao {
	UtenteDao utente = new UtenteDao();
	GruppoDao gruppo = new GruppoDao();
	
	public Post recuperaPost(int inIdPost) {
		Connection connessione = DbConnect.getConnection();
		String query = "select * from post where id_post = '"+inIdPost+"'";
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Post> listaPostGruppo(String inTag){
		ArrayList<Post> listaPost = new ArrayList<Post>();
		Connection connessione = DbConnect.getConnection();
		//la ricerca viene fatta con ILIKE che ricerca la stringa o carattere in UPPER e lower case 
		//String query = "select * from  post where tag_gruppo = '"+inTag+"'";
		String query = "select * from  post where tag_gruppo = 'Anime'";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaPost.add(recuperaPost(res1.getInt("id_post")));
			}
			//stampa della lista
			for(Post post : listaPost) {
				System.out.println(post);
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaPost;
	} 
	
	public ArrayList<Post> listaPostGruppo(String inTag,String ricerca){
		ArrayList<Post> listaPost = new ArrayList<Post>();
		Connection connessione = DbConnect.getConnection();
		//la ricerca viene fatta con ILIKE che ricerca la stringa o carattere in UPPER e lower case 
		//String query = "select * from  post where tag_gruppo = '"+inTag+"' AND didascalia ILIKE '%"+ricerca+"%'";
		String query = "select * from  post where tag_gruppo = 'Anime' AND didascalia ILIKE 's'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaPost.add(recuperaPost(res1.getInt("id_post")));
			}
			//stampa della lista
			for(Post post : listaPost) {
				System.out.println(post);
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaPost;
	}
}
