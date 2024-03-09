package org.openjfx.Progetto.Entity.ClassiDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.openjfx.Progetto.Entity.Gruppo;

public class GruppoDao {
	UtenteDao utente = new UtenteDao();
	private static Gruppo gruppoCorrente;
	
	
	
	public static Gruppo getGruppoCorrente() {
		return gruppoCorrente;
	}
	public static void setGruppoCorrente(Gruppo gruppoCorrente) {
		GruppoDao.gruppoCorrente = gruppoCorrente;
	}
	
	
	public Gruppo recuperaGruppo(String inTag) {
		Connection connessione = DbConnect.getConnection();
		String query = "select * from Gruppo where tag = '"+inTag+"'";
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			// String imgProfilo, String descrizione,String password
			res1.next();
			Gruppo gruppoRicercato = new Gruppo(res1.getString("tag"),res1.getString("tema"),utente.recuperaUtente(res1.getString("email_creatore")));
			res1.close();
			connessione.close();
			return gruppoRicercato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<Gruppo> listagruppiPerTema(String inTema){
		ArrayList<Gruppo> listaGruppi = new ArrayList<Gruppo>();
		Connection connessione = DbConnect.getConnection();
		//la ricerca viene fatta con ILIKE che ricerca la stringa o carattere in UPPER e lower case 
		String query = "select tag from gruppo where tema ILIKE '%"+inTema+"%' order by tema";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaGruppi.add(recuperaGruppo(res1.getString("tag")));
			}
			//stampa della lista
			for(Gruppo gruppo : listaGruppi) {
				System.out.println(gruppo);
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaGruppi;
	}
	public ArrayList<Gruppo> listagruppiPerTag(String inTag){
		ArrayList<Gruppo> listaGruppi = new ArrayList<Gruppo>();
		Connection connessione = DbConnect.getConnection();
		//la ricerca viene fatta con ILIKE che ricerca la stringa o carattere in UPPER e lower case 
		String query = "select tag from gruppo where tag ILIKE '%"+inTag+"%' order by tag";
		
		try {
			Statement st1 = connessione.createStatement();
			ResultSet res1 = st1.executeQuery(query);
			//scorro la select risultante e l'assegno all'arraylist
			while(res1.next()) {
				listaGruppi.add(recuperaGruppo(res1.getString("tag")));
			}
			//stampa della lista
			for(Gruppo gruppo : listaGruppi) {
				System.out.println(gruppo);
			}
			//chiudo la connessione
			res1.close();
			connessione.close();
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return listaGruppi;
	}
	
	
}
