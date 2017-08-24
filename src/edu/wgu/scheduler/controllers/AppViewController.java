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
    protected static CustomerViewController customerViewController = new CustomerViewController();
    @FXML
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
    private MenuItem aboutMenuItem;
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
    protected static DataViewController dataViewController = new DataViewController();
    @FXML
    private static AppointmentViewController appointmentViewController = new AppointmentViewController();
    @FXML
    protected BorderPane rootPane;

    private MainApp mainApp;

    public AppViewController() {
        initialize(MainApp.class.getResource("fxml/AppView.fxml"), null);
    }

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
        this.rootPane = new BorderPane();
        MainApp.rootPane = this.rootPane;
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu();
        this.editMenu = new Menu();
        this.helpMenu = new Menu();
        this.closeMenuItem = new MenuItem();
        this.copyMenuItem = new MenuItem();
        this.aboutMenuItem = new MenuItem();
        this.vbAppView = new VBox();
        this.tpAppPane = new TabPane();
        this.tabCustomers = new Tab();
        this.spCustomerEditor = new ScrollPane();
        this.tabAppointments = new Tab();
        this.spAppointmentEditor = new ScrollPane();


        // populate menus and menuBar and add them to top pane

        this.fileMenu.getItems().setAll(closeMenuItem);
        this.editMenu.getItems().setAll(copyMenuItem);
        this.helpMenu.getItems().setAll(aboutMenuItem);

        this.menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        this.rootPane.setTop(menuBar);

        // populate scroll panes with included views
        this.spAppointmentEditor.setContent(appointmentViewController.apAppointmentView);
        this.spCustomerEditor.setContent(customerViewController.apCustomerView);

        // populate tab panes and controllers and add them to the center pane

        this.tabAppointments.setContent(spAppointmentEditor);
        this.tabCustomers.setContent(spCustomerEditor);
        this.tpAppPane.getTabs().addAll(tabAppointments, tabCustomers);

        vbAppView.getChildren().addAll(tpAppPane);
        this.rootPane.setCenter(vbAppView);

        // add data view to bottom pane
        this.rootPane.setBottom(dataViewController.tabPane);

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
