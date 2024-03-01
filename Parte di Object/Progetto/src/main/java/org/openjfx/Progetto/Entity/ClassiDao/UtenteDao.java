package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	public boolean Login(String inEmail,String inPassword) {
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
	public ArrayList<Utente> ListaUtenti(String inUserName){
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
			//stampa della lista
			for(Utente utente : listaUtenti) {
				System.out.println(utente);
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
	public ArrayList <String> ricercaPartecipazioni(String inEmail){
		ArrayList <String> listaPartecipazioni = new ArrayList <String>();
		Connection connessione = DbConnect.getConnection();
		String query = "select tag_gruppo from partecipanti_al_gruppo where email_membro = '"+inEmail+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaPartecipazioni.add(res1.getString("tag_gruppo"));
			}
			
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaPartecipazioni;
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
			// String imgProfilo, String descrizione,String password
			res1.next();
			Utente utenteRicercato = new Utente(res1.getString("username"),res1.getString("email"),res1.getString("password"),res1.getString("img_profilo"),res1.getString("descrizione"));
			res1.close();
			connessione.close();
			return utenteRicercato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	//Metodo che modifica la descrizione dell'utente corrente
	public void ModificaDescrizioneUtente(Utente utenteDaModificare,String nuovaDescrizione) {
		Connection connessione = DbConnect.getConnection();
		String query = "UPDATE Utente set descrizione = '"+nuovaDescrizione+"'where email ='"+utenteDaModificare.getEmail()+"'";
		try {
			Statement st1 = connessione.createStatement();
			//utilizziamo il metodo execute update per la modifica da fare al DataBase
			st1.executeUpdate(query);
			connessione.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Metodo che modifica l'immagine di profilo dell'utente corrente
	public void ModificaImgProfiloUtente(Utente utenteDaModificare,String nuovaImgProfilo) {
		Connection connessione = DbConnect.getConnection();
		String query = "UPDATE Utente set img_profilo = '"+nuovaImgProfilo+"'where email ='"+utenteDaModificare.getEmail()+"'";
		try {
			Statement st1 = connessione.createStatement();
			st1.executeUpdate(query);
			connessione.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}