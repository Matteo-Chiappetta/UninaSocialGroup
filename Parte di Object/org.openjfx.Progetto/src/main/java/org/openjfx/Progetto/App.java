package org.openjfx.Progetto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import org.openjfx.Progetto.Entity.ClassiDao.DbConnect;
import org.openjfx.Progetto.Entity.ClassiDao.PostDao;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    

    @Override
    public void start(Stage stage) throws IOException {
    	//E UNA PROVAAAAAAAAA
    	
    	/*if(stato.getConnection()!= null) {
    		System.out.println("Ciao dovrebbe anda");
    	}
    	else {
    		System.out.println("Uffa non va");
    	}*/
    	
        scene = new Scene(loadFXML("ProvaFinestraLogin"), 873, 609);
        stage.setScene(scene);
        stage.setTitle("Unina Social Group");
        //stage.getIcons().add(new Image("file:icon.png"));
        Image logo = new Image("USG.jpg");
        stage.getIcons().add(logo);
        stage.setTitle("UninaSocialGroup - Sing In");
        stage.show();
        PostDao post = new PostDao();
        post.listaPostGruppo(STYLESHEET_CASPIAN);
        post.listaPostGruppo(STYLESHEET_CASPIAN);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}