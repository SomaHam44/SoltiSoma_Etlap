package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.EtlapDb;
import hu.petrik.etlap.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

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
    private List<Kategoria> kategoriaList;

    private EtlapDb db;

    public void initialize() {
        try {
            db = new EtlapDb();
            kategoriaList = db.getKategoria();
            for (Kategoria kategoria : kategoriaList) {
                bevitelKategoria.getItems().add(kategoria.getNev());
            }
        } catch (SQLException e) {
            hibaKiiro(e);
        }
    }


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
            int i = 0;
            int kategoria_id = 0;
            while (i < kategoriaList.size() && !kategoriaList.get(i).getNev().equals(kategoria)) {
                i++;
            }
            if (i < kategoriaList.size()) {
                kategoria_id = kategoriaList.get(i).getId();
            }
            int siker = etlapDb.etlapHozzaadasa(nev,leiras, kategoria_id, ar);
            if (siker == 1) {
                alert("Az étlap felvétele sikeres!");
                bevitelNev.setText("");
                bevitelLeiras.setText("");
                bevitelKategoria.setValue("");
            }
            else {
                alert("Az étlap felvétele sikertelen!");
            }
        } catch (Exception e) {
            hibaKiiro(e);
        }




    }
}
