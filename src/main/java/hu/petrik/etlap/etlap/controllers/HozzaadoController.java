package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class HozzaadoController extends Controller {
    @FXML
    public TextField bevitelNev;
    @FXML
    public TextArea bevitelLeiras;
    @FXML
    public Spinner<Integer> bevitelAr;
    @FXML
    public ChoiceBox<String> bevitelKategoria;
    private int ar;

    public void onHozzaadasButtonClick(ActionEvent actionEvent) {
        String nev = bevitelNev.getText().trim();
        String leiras = bevitelLeiras.getText().trim();
        int kategoriaIndex = bevitelKategoria.getSelectionModel().getSelectedIndex();
        if (nev.isEmpty()) {
            alert("A név megadása kötelező!");
            return;
        }

        if (leiras.isEmpty()) {
            alert("A leírás megadása kötelező!");
        }

        try {
             ar = bevitelAr.getValue();
        } catch (NullPointerException ex) {
            alert("Az ár megadása kötelező!");
        }  catch (Exception ex) {
            System.out.println(ex);
            alert("Az ár 1 és 100000 között lehet!");
            return;
        }

        if (ar < 1 || ar > 100000) {
            alert("Az ár 1 és 10000 között lehet!");
        }

        if (kategoriaIndex == -1) {
            alert("A kategória megadása kötelező!");
            return;
        }

        System.out.println(ar);
        String kategoria = bevitelKategoria.getValue();

        try {
            EtlapDb etlapDb = new EtlapDb();
            int siker = etlapDb.etlapHozzaadasa(nev,leiras, kategoria, ar);
            if (siker == 1) {
                alert("Az étlap felvétele sikeres!");
            }
            else {
                alert("Az étlap felvétele sikertelen!");
            }
        } catch (Exception e) {
            hibaKiiro(e);
        }




    }
}
