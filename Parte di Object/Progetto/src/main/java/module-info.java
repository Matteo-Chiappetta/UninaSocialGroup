module org.openjfx.Progetto {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens org.openjfx.Progetto to javafx.fxml;
    exports org.openjfx.Progetto;
    exports org.openjfx.Progetto.Boundary;
    opens org.openjfx.Progetto.Boundary to javafx.fxml;
    exports org.openjfx.Progetto.Entity;
    opens org.openjfx.Progetto.Entity to javafx.fxml;
    //exports org.openjfx.Progetto.Controller;
    //opens org.openjfx.Progetto.Controller to javafx.fxml;
}
