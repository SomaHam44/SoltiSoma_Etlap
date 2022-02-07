package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.Etlap;
import hu.petrik.etlap.etlap.EtlapDb;
import hu.petrik.etlap.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class KategoriaMainController extends Controller {
    public TableView<Kategoria> kategoriaTable;
    public TableColumn<Kategoria, String> kategoriaNev;
    private EtlapDb db;

    public void initialize() {
        kategoriaNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        try {
            db = new EtlapDb();
            kategoriaListaFeltoltes();

        } catch (SQLException e) {
            hibaKiiro(e);
        }
    }



    private void kategoriaListaFeltoltes() {
        try {
            List<Kategoria> kategoriaList = db.getKategoria();
            kategoriaTable.getItems().clear();
            for (Kategoria kategoria: kategoriaList) {
                kategoriaTable.getItems().add(kategoria);
            }

        } catch (SQLException e) {
            hibaKiiro(e);
        }
    }


    public void onHozzaadasButton(ActionEvent actionEvent) {
        try {
            Controller hozzaado = ujAblak("kategoria_hozzaad-view.fxml", "Kategória hozzáadása", 300, 200);
            hozzaado.getStage().setOnCloseRequest(event -> kategoriaListaFeltoltes());
            hozzaado.getStage().show();

        }
        catch (Exception e ) {
            hibaKiiro(e);

        }

    }

    public void onTorlesButton(ActionEvent actionEvent) {
        int kivalasztottIndex = kategoriaTable.getSelectionModel().getSelectedIndex();
        if (kivalasztottIndex == -1) {
            alert("Nincsen kiválasztva elem a törlés előtt!");
            return;
        }

        Kategoria torolhetoKategoria = kategoriaTable.getSelectionModel().getSelectedItem();
        if (!megerositoAblak("Biztos, hogy törölni szeretné ezt a kategóriát: " + torolhetoKategoria.getNev())) {
            return;

        }

        try {
            db.kategoriaTorles(torolhetoKategoria.getId());
            kategoriaListaFeltoltes();
        } catch (SQLException e) {
            hibaKiiro(e);

        }
    }
}
