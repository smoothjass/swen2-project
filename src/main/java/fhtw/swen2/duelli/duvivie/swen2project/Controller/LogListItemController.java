package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.LogListItemModel;
import javafx.fxml.Initializable;

public class LogListItemController implements Initializable {

    LogListItemModel logListItemModel;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
    }

    public LogListItemController(LogListItemModel logListItemModel) {
        this.logListItemModel = logListItemModel;
    }
}
