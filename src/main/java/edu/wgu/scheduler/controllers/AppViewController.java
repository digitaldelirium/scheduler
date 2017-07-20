package edu.wgu.scheduler.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import edu.wgu.scheduler.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AppViewController implements Initializable {
    @FXML
    private BorderPane rootPane;
/*    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu editMenu;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem copyMenuItem;
    @FXML
    private MenuItem aboutMenuItem; */
    @FXML
    private VBox vbAppView;
    @FXML
    private TabPane tpAppPane;
    @FXML
    private Tab tabCustomers;
    @FXML
    private ScrollPane spCustomerEditor;
    @FXML
    private Tab tabAppointments;
    @FXML
    private ScrollPane spAppointmentEditor;
    @FXML
    private TabPane tpViewPane;
    @FXML
    private Tab tabListView;
    @FXML
    private Tab tabTableView;
    @FXML
    private VBox vbListView;
    @FXML
    static Label lblListView;
    @FXML
    private ScrollPane spListScroller;
    @FXML
    ListView<?> lvListView;
    @FXML
    private VBox vbTableView;
    @FXML
    static Label lblTableView;
    @FXML
    private ScrollPane spTableScroller;
    @FXML
    static TableView<?> tvTableView;

    private MainApp mainApp;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location
     *         The location used to resolve relative paths for the root object, or
     *         <tt>null</tt> if the location is not known.
     * @param resources
     *         The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rootPane = MainApp.rootPane;
/*        this.menuBar = new MenuBar();
        this.fileMenu = new Menu();
        this.editMenu = new Menu();
        this.helpMenu = new Menu();
        this.closeMenuItem = new MenuItem();
        this.copyMenuItem = new MenuItem();
        this.aboutMenuItem = new MenuItem(); */
        this.vbAppView = new VBox();
        this.tpAppPane = new TabPane();
        this.tabCustomers = new Tab();
        this.spCustomerEditor = new ScrollPane();
        this.tabAppointments = new Tab();
        this.spAppointmentEditor = new ScrollPane();
        this.tabListView = new Tab();
        this.vbListView = new VBox();
        this.lblListView = new Label();
        this.spListScroller = new ScrollPane();
        this.lvListView = new ListView<>();
        this.vbTableView = new VBox();
        this.lblTableView = new Label();
        this.spTableScroller = new ScrollPane();
        this.tvTableView = new TableView<>();

    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
}
