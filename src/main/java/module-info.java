module es.iesfranciscodelosrios.cronometro {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires javafx.base;

    opens es.iesfranciscodelosrios.cronometro to javafx.fxml;
    exports es.iesfranciscodelosrios.cronometro;
    
}
