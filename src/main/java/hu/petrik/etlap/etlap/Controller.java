package hu.petrik.etlap.etlap;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

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

    protected void hibaKiiro(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> alert.show());
            }
        }, 400);

    }

    protected void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Információ");
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }



    protected boolean megerositoAblak(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztosan ?");
        alert.setHeaderText(uzenet);
        Optional<ButtonType> eredmeny = alert.showAndWait();
        return eredmeny.get() == ButtonType.OK;
    }

}
