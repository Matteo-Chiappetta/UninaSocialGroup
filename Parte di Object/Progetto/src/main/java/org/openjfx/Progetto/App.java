package org.openjfx.Progetto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    

    @Override
    public void start(Stage stage) throws IOException {
    	
        scene = new Scene(loadFXML("ProvaFinestraLogin"), 873, 609);
        stage.setScene(scene);
        stage.setTitle("Unina Social Group");
        //stage.getIcons().add(new Image("file:icon.png"));
        Image logo = new Image("USG.jpg");
        stage.getIcons().add(logo);
        stage.setTitle("UninaSocialGroup - Sing In");
        stage.show();
        stage.setResizable(false);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static URL prendiFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }

}