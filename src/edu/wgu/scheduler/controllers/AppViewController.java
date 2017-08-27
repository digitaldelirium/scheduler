package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.wgu.scheduler.MainApp.appointmentViewController;
import static edu.wgu.scheduler.MainApp.customerViewController;
import static edu.wgu.scheduler.MainApp.dataViewController;

public class AppViewController extends BorderPane implements Initializable {
    @FXML
    protected BorderPane rootPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu editMenu;
    @FXML
    private Menu reportMenu;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem copyMenuItem;
    @FXML
    private MenuItem monthlyAppointmentReportMenuItem;
    @FXML
    private MenuItem consultantScheduleMenuItem;
    @FXML
    private MenuItem customersByCountryMenuItem;
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
    private MainApp mainApp;

    public AppViewController() {
        dataViewController = new DataViewController();
        initialize(MainApp.class.getResource("fxml/AppView.fxml"), null);

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location
     *         The locationProperty used to resolve relative paths for the root object, or
     *         <tt>null</tt> if the locationProperty is not known.
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
        this.reportMenu = new Menu();
        this.helpMenu = new Menu();
        this.closeMenuItem = new MenuItem();
        this.copyMenuItem = new MenuItem();
        this.monthlyAppointmentReportMenuItem = new MenuItem();
        this.consultantScheduleMenuItem = new MenuItem();
        this.customersByCountryMenuItem = new MenuItem();
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
        this.reportMenu.getItems().setAll(monthlyAppointmentReportMenuItem, consultantScheduleMenuItem, customersByCountryMenuItem);
        this.helpMenu.getItems().setAll(aboutMenuItem);

        this.menuBar.getMenus().addAll(fileMenu, editMenu, reportMenu, helpMenu);
        this.rootPane.setTop(menuBar);

        appointmentViewController  = new AppointmentViewController();
        customerViewController = new CustomerViewController();

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
    }

    private void setupEventHandlers(AppViewController appViewController) {

        this.tabCustomers.setOnSelectionChanged((event -> {
            if (tabCustomers.isSelected()) {
                // Change to Customer View
                setCustomerView();
            }
            else {
                setAppointmentView();
            }
        }));

        this.tabAppointments.setOnSelectionChanged(event -> {
            if (tabAppointments.isSelected()) {
                setAppointmentView();
            }
            else {
                setCustomerView();
            }
        });
    }

    @FXML
    private void setCustomerView() {
        MainApp.dataViewController = customerViewController.getDataViewController();
    }

    @FXML
    private void setAppointmentView() {
        MainApp.dataViewController = appointmentViewController.getDataViewController();
        appointmentViewController.disableTextFields();
    }


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void quitApp(){
        System.exit(0);
    }
}
