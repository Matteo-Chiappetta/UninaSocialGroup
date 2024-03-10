package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.openjfx.Progetto.Entity.Gruppo;
import org.openjfx.Progetto.Entity.Utente;


public class UtenteDao{
	private static Utente utenteCorrente;
	
		//metodo per recuperare tutte le informazioni dell'utente che ha effettuato il login
		public static Utente getUtenteCorrente() {
			return utenteCorrente;
		}

		public static void setUtenteCorrente(Utente utenteCorrente) {
			UtenteDao.utenteCorrente = utenteCorrente;
		}

	//Metodo per fare il login tramite le credenziali di un utente
	public boolean login(String inEmail,String inPassword) {
		//Assegno la connessione del database al metodo
		Connection connessione = DbConnect.getConnection();
		//Query che verifica email e password dell utente
		String query = "select count(*) as numero from utente where email = '"+inEmail+"' AND password = '"+inPassword+"' ";
		boolean isValido;
		
		try {
			//verifico che l'applicazione si connette al database
			Statement st1 = connessione.createStatement();
			//Oggetto che eseguirà la query in caso di errore verrà lanciata una SQLException che aprirà un messaggio di errore
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			//variabile per salvare il risultato della query
			int numero = res1.getInt("numero");
			if(numero == 1) {
				isValido = true;
				//se il login è verificato assegno gli attributi dell'utente che ha eseguito il login ad una variabile statica per mantenerne le informazioni
				utenteCorrente = new Utente(this.recuperaUtente(inEmail));
			}
			else {
				isValido = false;
			}
			//chiudo la connessione al database
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		//se è valido inoltre accediamo alla finestra della HomePage
		return isValido;
	}
	
	
	
	
	//metodo che prende una stringa in input e ne restituisce la lista degli utenti che hanno quella stringa compresa nell'username
	public ArrayList<Utente> listaUtenti(String inUserName){
		ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
		Connection connessione = DbConnect.getConnection();
		//la ricerca viene fatta con ILIKE che ricerca la stringa o carattere in UPPER e lower case 
		String query = "select username,email from utente where username ILIKE '%"+inUserName+"%' order by username";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaUtenti.add(recuperaUtente(res1.getString("Email")));
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listaUtenti;
	}

	
	
	//metodo che prende una stringa ,contenente un email ,in input e ne restituisce la lista dei gruppi di cui fa parte 
	public ArrayList <Gruppo> ricercaPartecipazioni(String inEmail){
		GruppoDao gruppo = new GruppoDao();
		ArrayList <Gruppo> listaPartecipazioni = new ArrayList <Gruppo>();
		Connection connessione = DbConnect.getConnection();
		String query = "select tag_gruppo from partecipanti_al_gruppo where email_membro = '"+inEmail+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaPartecipazioni.add(gruppo.recuperaGruppo(res1.getString("tag_gruppo")));
			}
			
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaPartecipazioni;
	}
	
	
	public Boolean isPartecipanteAlgruppo(String inEmail, String inTag) {
		Connection connessione = DbConnect.getConnection();
		String query = "select COUNT(*) as partecipante from partecipanti_al_gruppo where email_membro = '"+inEmail+"' AND tag_gruppo = '"+inTag+"'";
		Boolean isPartecipante = null;
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			if(res1.getInt("partecipante") == 1) {
				isPartecipante = true;
			}
			else {
				
				isPartecipante = false;
			}
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			return isPartecipante;
		}
		return isPartecipante;
	}
	
	//metodo che prende una stringa ,contenente un email ,in input e ne restituisce la lista dei seguiti di quell'utente
	public ArrayList <Utente> listaSeguiti(String inEmail){
		ArrayList <Utente> listaSeguiti = new ArrayList <Utente>();
		Connection connessione = DbConnect.getConnection();
		String query = "select email_utente_2 from seguiti where email_utente_1 = '"+inEmail+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaSeguiti.add(recuperaUtente(res1.getString("email_utente_2")));
			}
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaSeguiti;
	}
	
	public ArrayList <Utente> listaFollower(String inEmail){
		ArrayList <Utente> listaFollower = new ArrayList <Utente>();
		Connection connessione = DbConnect.getConnection();
		String query = "select email_utente_1 from seguiti where email_utente_2 = '"+inEmail+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaFollower.add(recuperaUtente(res1.getString("email_utente_1")));
			}
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaFollower;
	}
	
	
	//Metodo che data una stringa contenente un email restituisce quell utente
	public Utente recuperaUtente(String inEmail) {
		Connection connessione = DbConnect.getConnection();
		String query = "select * from Utente where email = '"+inEmail+"'";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			Utente utenteRicercato = new Utente(res1.getString("username"),res1.getString("email"),res1.getString("password"),res1.getString("img_profilo"),res1.getString("descrizione"));
			res1.close();
			connessione.close();
			return utenteRicercato;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void seguiUtente(String emailUtenteDaSeguire) {
		Connection connessione = DbConnect.getConnection();
		String query = "insert into seguiti(email_utente_1,email_utente_2) values ('"+getUtenteCorrente().getEmail()+"','"+emailUtenteDaSeguire+"')";
		try {
			Statement st1 = connessione.createStatement();
			//utilizziamo il metodo execute update per la modifica da fare al DataBase
			st1.executeUpdate(query);
			connessione.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void smettiDiSeguire(String emailUtenteDaNonSeguire) {
		Connection connessione = DbConnect.getConnection();
		String query = "delete from seguiti where email_utente_1 = '"+getUtenteCorrente().getEmail()+"' AND email_utente_2 = '"+emailUtenteDaNonSeguire+"'";
		try {
			Statement st1 = connessione.createStatement();
			//utilizziamo il metodo execute update per la modifica da fare al DataBase
			st1.executeUpdate(query);
			connessione.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isSeguito(String emailUtente) {
		Connection connessione = DbConnect.getConnection();
		String query = "select COUNT(*) as isSeguito from seguiti where email_utente_1 = '"+getUtenteCorrente().getEmail()+"' AND email_utente_2 = '"+emailUtente+"'";
		boolean isSeguito = false;
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			res1.next();
			if(res1.getInt("isSeguito") == 1) {
				isSeguito = true;
			}
			else{
				isSeguito = false;
			}
			res1.close();
			connessione.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSeguito;
	}
	
	
	//Metodo che modifica la descrizione dell'utente corrente
	public void modificaDescrizioneUtente(Utente utenteDaModificare,String nuovaDescrizione) {
		Connection connessione = DbConnect.getConnection();
		String query = "UPDATE Utente set descrizione = ? where email = ?";
		try {
			PreparedStatement st1 = connessione.prepareStatement(query);
			st1.setString(1, utenteDaModificare.getEmail());
			st1.setString(2,nuovaDescrizione);
			st1.executeUpdate();
			connessione.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}