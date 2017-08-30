package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 8/19/17.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class DataViewController {

//    private static DataViewController instance;
    public TabPane tabPane;
    private Tab tabTableView;
    private Tab tabListView;
    private VBox vbListView;
    private VBox vbTableView;
    private Label lblListView;
    private Label lblTableView;
    protected ScrollPane spListView;
    protected ScrollPane spTableView;
    private ListView<?> listView;
    private TableView<?> tableView;
    private MainApp mainApp;

    public DataViewController() {
        initialize();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     */
    public void initialize() {
//        this.instance = this;
        this.tabPane = new TabPane();
        this.tabPane.setPrefHeight(250.0);
        this.tabPane.setMaxHeight(400.0);
        this.tabPane.setMaxWidth(Integer.MAX_VALUE);
        this.tabTableView = new Tab("Table View");
        this.tabTableView.setClosable(false);
        this.tabListView = new Tab("List View");
        this.tabListView.setClosable(false);
        this.spListView = new ScrollPane();
        this.spTableView = new ScrollPane();
        this.lblListView = new Label();
        this.lblTableView = new Label();
        this.vbListView = new VBox();
        this.vbTableView = new VBox();
        this.listView = new ListView<>();
        this.tableView = new TableView<>();

        this.vbListView.getChildren().setAll(this.lblListView, this.listView);
        this.spListView.setContent(this.listView);
        this.tabListView.setContent(this.spListView);


        this.vbTableView.getChildren().setAll(this.lblTableView, this.spTableView);
        this.spTableView.setContent(this.tableView);
        this.tabTableView.setContent(vbTableView);

        this.tabPane.getTabs().setAll(tabListView, tabTableView);
    }

    public Label getLblListView() {
        return lblListView;
    }

    public void setLblListView(Label lblListView) {
        this.lblListView = lblListView;
    }

    public Label getLblTableView() {
        return lblTableView;
    }

    public void setLblTableView(Label lblTableView) {
        this.lblTableView = lblTableView;
    }

    public ListView<?> getListView() {
        return listView;
    }

    public void setListView(ListView<?> listView) {
        this.listView = listView;
    }

    public TableView<?> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<?> tableView) {
        this.tableView = tableView;
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
}
