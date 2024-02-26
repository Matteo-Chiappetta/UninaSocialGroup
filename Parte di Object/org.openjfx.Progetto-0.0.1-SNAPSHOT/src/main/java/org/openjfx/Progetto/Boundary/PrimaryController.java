package org.openjfx.Progetto.Boundary;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.Progetto.*;


public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("HomePage2");
    }
}
