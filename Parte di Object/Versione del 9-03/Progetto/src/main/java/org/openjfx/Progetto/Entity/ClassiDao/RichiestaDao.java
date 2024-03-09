package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Utente;

public class RichiestaDao {
	
	public int statoRichiesta(Utente utenteCorrente,Gruppo gruppoCorrente) {
		int statoRichiesta = 0;
		Connection connessione = DbConnect.getConnection();
		String query = "select accettato from richiesta where email_utente = '"+utenteCorrente.getEmail()+"' AND tag_gruppo = '"+gruppoCorrente.getTag()+"' AND data_ora_richiesta = (select MAX(data_ora_richiesta)from richiesta where email_utente =  '"+utenteCorrente.getEmail()+"' AND tag_gruppo = '"+gruppoCorrente.getTag()+"')";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			statoRichiesta = res1.getInt("accettato");
			res1.close();
			connessione.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			return 2;
		}
		return statoRichiesta;
	}
	
	//Metodo che prende in input l'utente che vuole partecipare al gruppo ed il grupppo in cui si vuole entrare
	public void inserisciRichiesta(Utente utenteCorrente,Gruppo gruppoCorrente) {
		Connection connessione = DbConnect.getConnection();
		String query = "insert into richiesta (email_utente,tag_gruppo) values ('"+utenteCorrente.getEmail()+"','"+gruppoCorrente.getTag()+"')";
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
	
	
	/*SOLO PER I GRUPPI DA 3 MEMBRI SE ABBIAMO TEMéPO LA FACCIAMO
	 * public void accettaRichiesta(){
	 * 
	 * }
	 */
}
