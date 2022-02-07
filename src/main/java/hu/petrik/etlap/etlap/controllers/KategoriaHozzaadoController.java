package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.EtlapDb;
import hu.petrik.etlap.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class KategoriaHozzaadoController extends Controller {

    public TextField bevitelNev;
    private List<Kategoria> kategoriaList;
    private EtlapDb db;

    public void initialize() {
        try {
            db = new EtlapDb();
            kategoriaList = db.getKategoria();

        } catch (SQLException e) {
            hibaKiiro(e);
        }
    }


    public void onHozzaadasButtonClick(ActionEvent actionEvent) {

        String nev = bevitelNev.getText().trim();
        if (nev.isEmpty()) {
            alert("A név megadása kötelező!");
            return;
        }

        try {
            int i = 0;
            while (i < kategoriaList.size() && !kategoriaList.get(i).getNev().equals(nev.toLowerCase())) {
                i++;
            }
            if (i < kategoriaList.size()) {
                alert("Az adott névvel már létezik kategória!");
                bevitelNev.setText("");
            } else {
                EtlapDb etlapDb = new EtlapDb();
                int siker = etlapDb.kategoriaHozzaadasa(nev);
                if (siker == 1) {
                    alert("A kategória felvétele sikeres!");
                    bevitelNev.setText("");
                } else {
                    alert("A kategória felvétele sikertelen!");
                }
            }
        } catch (Exception e) {
                hibaKiiro(e);
            }



    }
}
