module org.openjfx.Progetto {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens org.openjfx.Progetto to javafx.fxml;
    exports org.openjfx.Progetto;
    opens org.openjfx.Progetto.Boundary to javafx.fxml;
    exports org.openjfx.Progetto.Boundary; 
    
}
