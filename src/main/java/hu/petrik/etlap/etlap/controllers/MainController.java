package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.Etlap;
import hu.petrik.etlap.etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

import static hu.petrik.etlap.etlap.Controller.ujAblak;

public class MainController extends Controller {

    public TableView<Etlap> etlapTable;
    @FXML
    private TableColumn<Etlap, Integer> etlapAr;
    @FXML
    private TableColumn<Etlap, String> etlapKategoria;
    @FXML
    private TableColumn<Etlap, String> etlapNev;
    @FXML
    private TextArea etlapLeiras;

    private EtlapDb db;


    public void initialize() {
        etlapNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        etlapAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        etlapKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));

        try {
            db = new EtlapDb();
            etlapListaFeltoltes();

        }
        catch (SQLException e) {
            hibaKiiro(e);
        }
    }

    private void etlapListaFeltoltes() {
        try {
            List<Etlap> etlapList = db.getEtlap();
            etlapTable.getItems().clear();
            for (Etlap etlap: etlapList) {
                etlapTable.getItems().add(etlap);
            }

        } catch (SQLException e) {
            hibaKiiro(e);
        }
    }

    public void onHozzaadButton(ActionEvent actionEvent) {
        try {
            Controller hozzaado = ujAblak("hozzaad-view.fxml", "Étlap felvétele", 540, 500);
            hozzaado.getStage().setOnCloseRequest(event -> etlapListaFeltoltes());
            hozzaado.getStage().show();

        }
        catch (Exception e ) {
            hibaKiiro(e);

        }
    }

    public void onTorlesButton(ActionEvent actionEvent) {
        int kivalasztottIndex = etlapTable.getSelectionModel().getSelectedIndex();
        if (kivalasztottIndex == -1) {
            alert("Nincsen kiválasztva elem a törlés előtt!");
            return;
        }

        Etlap torlendoEtlap = etlapTable.getSelectionModel().getSelectedItem();
        if (!megerositoAblak("Biztos, hogy törölni szeretné ezt az étlapot: " + torlendoEtlap.getNev())) {
            return;

        }

        try {
            db.etlapTorles(torlendoEtlap.getId());
            etlapListaFeltoltes();
        } catch (SQLException e) {
            hibaKiiro(e);

        }


    }

    public void leirasMegjelenit(MouseEvent mouseEvent) {
        Etlap kivalasztott = etlapTable.getSelectionModel().getSelectedItem();
        etlapLeiras.setText(kivalasztott.getLeiras());
    }

    public void onSzazalekosEmelesButtonClick(ActionEvent actionEvent) {

    }

    public void onForintEmelesButtonClick(ActionEvent actionEvent) {

    }
}