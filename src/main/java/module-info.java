module hu.petrik.etlap.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens hu.petrik.etlap.etlap to javafx.fxml;
    exports hu.petrik.etlap.etlap;
}