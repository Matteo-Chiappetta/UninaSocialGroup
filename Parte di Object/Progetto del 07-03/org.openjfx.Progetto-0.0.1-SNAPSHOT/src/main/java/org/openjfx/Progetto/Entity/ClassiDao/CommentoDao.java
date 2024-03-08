package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openjfx.Progetto.Entity.Commento;

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
		String query = "insert into commento(commento,email_utente,id_post)values('"+commento+"','"+emailCreatore+"',"+idPost+")";
		try {
			//eseguo la insert sulla tabella
			Statement st1 = connessione.createStatement();
			st1.executeUpdate(query);
			connessione.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}