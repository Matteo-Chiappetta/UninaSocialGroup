package org.openjfx.Progetto.Entity.ClassiDao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openjfx.Progetto.Entity.MiPiace;

public class MiPiaceDao {
	UtenteDao utente = new UtenteDao();
	PostDao post = new PostDao();
	public MiPiace recuperaMiPiace(int idMiPiace) {
		Connection connessione = DbConnect.getConnection();
		String query = "select * from mi_piace where id_mi_piace = "+idMiPiace+"";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			MiPiace miPiaceRicercato = new MiPiace(res1.getInt("id_mi_piace"),utente.recuperaUtente(res1.getString("email_utente")),post.recuperaPost(res1.getInt("id_post")),res1.getTimestamp("data_ora_mi_piace"));
			res1.close();
			connessione.close();
			return miPiaceRicercato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void aggiungiMiPiace(String inEmail,int idPost) {
		Connection connessione = DbConnect.getConnection();
		String query = "insert into mi_piace(email_utente,id_post) values ('"+inEmail+"',"+idPost+")";
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
	
	public void rimuoviMiPiace(MiPiace miPiaceDaRimuovere) {
		Connection connessione = DbConnect.getConnection();
		String query = "delete from mi_piace where id_mi_piace = "+miPiaceDaRimuovere.getIdMiPiace()+"";
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

