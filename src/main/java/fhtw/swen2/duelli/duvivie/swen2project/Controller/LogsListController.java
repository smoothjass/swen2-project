package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.LogsListModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LogsListController implements Initializable {

    private LogsListModel logsListModel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public LogsListController(LogsListModel logsListModel) {
        this.logsListModel = logsListModel;
    }
}
