package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HozzaadoController extends Controller {
    @FXML
    public TextField bevitelNev;
    @FXML
    public TextArea bevitelLeiras;
    @FXML
    public Spinner<Integer> bevitelAr;
    @FXML
    public ChoiceBox<String> bevitelKategoria;

    public void onHozzaadasButtonClick(ActionEvent actionEvent) {

    }
}
