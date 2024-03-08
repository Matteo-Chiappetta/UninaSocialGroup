module org.openjfx.Progetto {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires com.jfoenix;

    opens org.openjfx.Progetto to javafx.fxml;
    exports org.openjfx.Progetto;
    opens org.openjfx.Progetto.Controller to javafx.fxml;
    exports org.openjfx.Progetto.Controller; 
    
}
