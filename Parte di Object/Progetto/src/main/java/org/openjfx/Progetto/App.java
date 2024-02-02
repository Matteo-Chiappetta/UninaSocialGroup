package org.openjfx.Progetto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ProvaFinestraLogin"), 640, 480);
        //String css = this.getClass().getResource("Tema.css").toExternalForm();
        //scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("Unina Social Group");
        //stage.getIcons().add(new Image("file:icon.png"));
        Image logo = new Image("USG.jpg");
        stage.getIcons().add(logo);
        stage.setTitle("UninaSocialGroup - Sing In");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    	//System.out.println(App.class.getResource(fxml + ".fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}