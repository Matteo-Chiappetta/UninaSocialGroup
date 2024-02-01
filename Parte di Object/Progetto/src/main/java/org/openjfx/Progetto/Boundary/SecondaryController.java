package org.openjfx.Progetto.Boundary;

import java.io.IOException;
import javafx.fxml.FXML;
import org.openjfx.Progetto.*;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
    	App.setRoot("FinestraPrincipale");
    }
    
}