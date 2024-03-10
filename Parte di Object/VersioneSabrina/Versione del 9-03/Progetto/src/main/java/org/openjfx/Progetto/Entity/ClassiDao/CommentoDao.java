package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.openjfx.Progetto.Entity.Commento;
import org.openjfx.Progetto.Entity.Post;

public class CommentoDao {
	UtenteDao utente = new UtenteDao();
	PostDao post = new PostDao();
	
	public Commento recuperaCommento(int idCommento) {
		Connection connessione = DbConnect.getConnection();
		String query = "select * from commento where id_commento = "+idCommento+"";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			Commento commentoRicercato = new Commento(res1.getInt("id_commento"),res1.getTimestamp("data_ora_commento"),res1.getString("commento"),utente.recuperaUtente(res1.getString("email_utente")),post.recuperaPost(res1.getInt("id_post")));
			res1.close();
			connessione.close();
			return commentoRicercato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public void aggiungiCommento(String commento,String emailCreatore,int idPost) {
		Connection connessione = DbConnect.getConnection();
		String sql = "insert into commento(commento,email_utente,id_post) values (?,?,?)";
		
		try {
			//eseguo la insert sulla tabella
			PreparedStatement st1 = connessione.prepareStatement(sql);
			st1.setString(1, commento);
			st1.setString(2,emailCreatore);
			st1.setInt(3, idPost);
			st1.executeUpdate();
			connessione.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Commento> listaCommentiPost(int inIdPost) {
		ArrayList<Commento> listaCommenti = new ArrayList<Commento>();
		Connection connessione = DbConnect.getConnection();
		String query = "select id_commento from commento where id_post = '"+inIdPost+"' ORDER BY data_ora_commento DESC";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaCommenti.add(recuperaCommento(res1.getInt("id_commento")));
				//System.out.println("siamo in lista commenti post dao : "+ recuperaCommento(res1.getInt("id_post")));
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaCommenti;
	}
	
	public int getNumCommenti(int idPost) { 
		  Connection connessione = DbConnect.getConnection(); 
		  String query = "select COUNT(*) as num from commento where id_post = "+idPost+"";
		  int numCommenti = 0;
		  try {
				Statement st1 = connessione.createStatement();
				ResultSet res1 = st1.executeQuery(query);
				res1.next();
				numCommenti = res1.getInt("num");
				res1.close();
				connessione.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		  return numCommenti;
	  }

}