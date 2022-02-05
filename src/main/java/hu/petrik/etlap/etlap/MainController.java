package hu.petrik.etlap.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MainController {

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
    }

    public void onTorlesButton(ActionEvent actionEvent) {
    }
}