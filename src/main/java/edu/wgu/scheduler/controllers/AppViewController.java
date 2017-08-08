package edu.wgu.scheduler.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.ApplicationView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static edu.wgu.scheduler.models.ApplicationView.APPOINTMENT;
import static edu.wgu.scheduler.models.ApplicationView.CUSTOMER;

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
    static ListView<?> lvListView;
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
        lblListView = new Label();
        this.spListScroller = new ScrollPane();
        lvListView = new ListView<>();
        this.vbTableView = new VBox();
        lblTableView = new Label();
        this.spTableScroller = new ScrollPane();
        tvTableView = new TableView<>();

        this.tabAppointments.setContent(spAppointmentEditor);
        this.tabCustomers.setContent(spCustomerEditor);
        this.tpAppPane.getTabs().addAll(tabAppointments, tabCustomers);

        this.spListScroller.setContent(lvListView);
        this.vbListView.getChildren().addAll(lblListView, this.spListScroller);

        this.spTableScroller.setContent(tvTableView);
        this.vbTableView.getChildren().addAll(lblTableView, spTableScroller);

        this.tabTableView.setContent(vbTableView);
        this.tabListView.setContent(vbListView);
        this.tpViewPane.getTabs().addAll(tabListView, tabTableView);

        vbAppView.getChildren().addAll(tpAppPane, tpViewPane);

        setupEventHandlers(this);

        this.setMainApp(this.mainApp);
    }

    private void setupEventHandlers(AppViewController appViewController) {
        
        this.tabCustomers.setOnSelectionChanged((event -> {
            if (tabCustomers.isSelected()) {
                // Change to Customer View
                setApplicationView(CUSTOMER);
            }
        }));

        this.tabAppointments.setOnSelectionChanged(event -> {
            if (tabAppointments.isSelected()) {
                setApplicationView(APPOINTMENT);
            }
        });
    }

    protected void setApplicationView(Enum<ApplicationView> view) {
        // TODO: Setup FXML Loader

        switch (view.name()) {
            case "APPOINTMENT":

                break;
            case "CUSTOMER":
                break;
            default:
                // TODO: See if user is logged in and call Login if not
        }
    }


    private void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
}
