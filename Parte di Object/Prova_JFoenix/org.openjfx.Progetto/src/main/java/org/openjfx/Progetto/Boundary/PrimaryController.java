package org.openjfx.Progetto.Boundary;

import java.io.IOException;
import javafx.fxml.FXML;
import org.openjfx.Progetto.*;


public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
