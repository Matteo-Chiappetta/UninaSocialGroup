package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Utente;

public class RichiestaDao {
	
	public Boolean statoRichiesta(Utente utenteCorrente,Gruppo gruppoCorrente) {
		Boolean statoRichiesta = null;
		String result;
		Connection connessione = DbConnect.getConnection();
		String query = "select accettato from richiesta where email_utente = '"+utenteCorrente.getEmail()+"' AND tag_gruppo = '"+gruppoCorrente.getTag()+"' AND data_ora_richiesta = (select MAX(data_ora_richiesta)from richiesta where email_utente =  '"+utenteCorrente.getEmail()+"' AND tag_gruppo = '"+gruppoCorrente.getTag()+"')";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			if(! res1.next())
				return false;
			//se uso res1.getBoolean non mi permette di avere null, solo true e false

			result = res1.toString();
			if(result.equals("null")) {
				statoRichiesta = null;
			}
			if(result.equals("false")) {
				statoRichiesta = false;
			}
			if(result.equals("true")) {
				statoRichiesta = true;
			}
		
			//se non c'è nessuna richiesta: FALSE
			//se l'ultima richiesta non è stata accettata: FALSE
			//se l'ultima richiesta è stata accettata: TRUE
			//se l'ultima richiesta non è stata ancora valutata: NULL
			
			//quindi nel ControllerGruppo devo preoccuparmi di distinguere i due casi in cui è false
			
			res1.close();
			connessione.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
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
