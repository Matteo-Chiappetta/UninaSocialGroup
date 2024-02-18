package org.openjfx.Progetto.Entity.ClassiDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.openjfx.Progetto.Entity.Utente;


public class UtenteDao{
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
		return isValido;
	}
	public ArrayList <String> ListaUtenti(String inUserName){
		ArrayList <String> listaUtenti = new ArrayList <String>();
		Connection connessione = DbConnect.getConnection();
		String query = "select username from utente where username LIKE '%"+inUserName+"%' order by username";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaUtenti.add(res1.getString("username"));
			}
			
			for(String username : listaUtenti) {
				System.out.println(username);
			}
			
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaUtenti;
	}
	/*
	 * SELECT *
FROM   Fatture
INNER/LEFT/RIGHT/FULL 
JOIN   Fornitori
  ON Fatture.IdFornitore = Fornitori.IdFornitore;*/
	
	public ArrayList <String> ricercaPartecipazioni(String inUserName){
		ArrayList <String> listaPartecipazioni = new ArrayList <String>();
		Connection connessione = DbConnect.getConnection();
		String query = "select P.tag_gruppo from partecipanti_al_gruppo as P JOIN utente as u on u.email = P.email_membro where u.username = '"+inUserName+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaPartecipazioni.add(res1.getString("tag_gruppo"));
			}
			
			for(String username : listaPartecipazioni) {
				System.out.println(username);
			}
			
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaPartecipazioni;
	}
	//ATTENTO LA RICERCA LA FA PER EMAIL E NON USERNAME
	public ArrayList <String> listaSeguiti(String inEmail){
		ArrayList <String> listaSeguiti = new ArrayList <String>();
		Connection connessione = DbConnect.getConnection();
		String query = "select email_utente_2 from seguiti where email_utente_1 = '"+inEmail+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			while(res1.next()) {
				listaSeguiti.add(res1.getString("email_utente_2"));
			}
			
			for(String username : listaSeguiti) {
				System.out.println(username);
			}
			
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaSeguiti;
	}
}