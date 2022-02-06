package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.Etlap;
import hu.petrik.etlap.etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
            System.out.println(e);
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
            System.out.println(e);
        }
    }

    public void onHozzaadButton(ActionEvent actionEvent) {
        try {
            Controller hozzaado = ujAblak("hozzaad-view.fxml", "Étlap felvétele", 540, 500);
            hozzaado.getStage().setOnCloseRequest(event -> etlapListaFeltoltes());
            hozzaado.getStage().show();

        }
        catch (Exception e ) {
            System.out.println(e);

        }
    }

    public void onTorlesButton(ActionEvent actionEvent) {
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