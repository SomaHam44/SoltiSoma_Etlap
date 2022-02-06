package hu.petrik.etlap.etlap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public  abstract class Controller {
    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    public static Controller ujAblak(String fxml, String cim, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(cim);
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController() ;
        controller.stage = stage;
        return controller;

    }

    protected boolean megerositoAblak(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztosan ?");
        alert.setHeaderText(uzenet);
        Optional<ButtonType> eredmeny = alert.showAndWait();
        return eredmeny.get() == ButtonType.OK;
    }

}
