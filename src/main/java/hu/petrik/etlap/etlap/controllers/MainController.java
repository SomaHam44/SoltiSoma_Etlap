package hu.petrik.etlap.etlap.controllers;

import hu.petrik.etlap.etlap.Controller;
import hu.petrik.etlap.etlap.Etlap;
import hu.petrik.etlap.etlap.EtlapDb;
import hu.petrik.etlap.etlap.Kategoria;
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
    public Spinner<Integer> forintSpinner;
    public Spinner<Integer> szazalekSpinner;
    public ChoiceBox<String> szuro;
    @FXML
    private TableColumn<Etlap, Integer> etlapAr;
    @FXML
    private TableColumn<Etlap, String> etlapKategoria;
    @FXML
    private TableColumn<Etlap, String> etlapNev;
    @FXML
    private TextArea etlapLeiras;
    private List<Kategoria> kategoriak;

    private EtlapDb db;


    public void initialize() {
        szuro.getItems().add("Összes");
        etlapNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        etlapAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        etlapKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));

        try {
            db = new EtlapDb();
            etlapListaFeltoltes();
            kategoriak = db.getKategoria();
            for (Kategoria kategoria: kategoriak) {
                szuro.getItems().add(kategoria.getNev());
            }
            

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
        int emelo = 0;
        try {
            emelo = szazalekSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez kötelező megadni az emelés értékét!");
            return;
        } catch (Exception e) {
            alert("Az ár százalékos emelése 5 és 50 között lehet");
            return;
        }

        if (emelo < 5 || emelo > 50) {
            alert("Az ár százalékos emelése 5 és 50 között lehet");
            return;
        }

        int kivalasztottIndex = etlapTable.getSelectionModel().getSelectedIndex();
        Etlap kivalasztott = etlapTable.getSelectionModel().getSelectedItem();
        String seged = "Összes étel";
        if (kivalasztottIndex == -1) {
            if (!megerositoAblak("Biztosan szeretné emelni : " + seged)) {
                return;
            }
            try {
                db.etlapArEmelesSzazalekkalOsszes((emelo));
                etlapListaFeltoltes();

            } catch (SQLException e) {
                hibaKiiro(e);
            }
        }
        else {
                if (!megerositoAblak("Biztosan szeretné emelni : " + kivalasztott.getNev())) {
                    return;
                }
                try {
                    db.etlapArEmelesSzazalekkal(kivalasztott.getId(), emelo);
                    etlapListaFeltoltes();
                } catch (SQLException e) {
                    hibaKiiro(e);
                }

            }


        }


    public void onForintEmelesButtonClick(ActionEvent actionEvent) {
        int emelo = 0;
        try {
            emelo = forintSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez kötelező megadni az emelés értékét!");
            return;
        } catch (Exception e) {
            alert("Az ár értéke 50 és 3000 között lehet");
            return;
        }

        if (emelo < 50 || emelo > 3000) {
            alert("Az ár értéke 50 és 3000 között lehet");
            return;
        }
        int kivalasztottIndex = etlapTable.getSelectionModel().getSelectedIndex();
        Etlap kivalasztott = etlapTable.getSelectionModel().getSelectedItem();
        String seged = "Összes étel";
        if (kivalasztottIndex == -1) {
            if (!megerositoAblak("Biztosan szeretné emelni : " + seged)) {
                return;
            }
            try {
                db.etlapArEmelesForinttalOsszes(emelo);

                etlapListaFeltoltes();

            } catch (SQLException e) {
                hibaKiiro(e);
            }

        }
        else {
            if (!megerositoAblak("Biztosan szeretné emelni : " + kivalasztott.getNev())) {
                return;
            }
                try {
                    db.etlapArEmelesForinttal(kivalasztott.getId(), emelo);
                    etlapListaFeltoltes();
                } catch (SQLException e) {
                    hibaKiiro(e);
                }


        }

    }

    public void onKategoriaMegjelenit(ActionEvent actionEvent) {
        try {
            Controller kategoria = ujAblak("kategoria_main-view.fxml", "Kategória", 540, 500);
            kategoria.getStage().show();

        }
        catch (Exception e ) {
            hibaKiiro(e);

        }

    }
}