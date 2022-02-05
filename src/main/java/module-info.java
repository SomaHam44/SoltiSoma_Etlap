module hu.petrik.etlap.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens hu.petrik.etlap.etlap to javafx.fxml;
    exports hu.petrik.etlap.etlap;
    exports hu.petrik.etlap.etlap.controllers;
    opens hu.petrik.etlap.etlap.controllers to javafx.fxml;
}