package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.wgu.scheduler.MainApp.*;

public class AppViewController extends BorderPane implements Initializable {


    private BorderPane rootPane;
    private MenuBar menuBar;
    private Menu fileMenu;
    private Menu editMenu;
    private Menu reportMenu;
    private Menu helpMenu;
    private MenuItem closeMenuItem;
    private MenuItem copyMenuItem;
    private MenuItem monthlyAppointmentReportMenuItem;
    private MenuItem consultantScheduleMenuItem;
    private MenuItem customersByCountryMenuItem;
    private MenuItem aboutMenuItem;
    private VBox vbAppView;
    private TabPane tpAppPane;
    private Tab tabCustomers;
    private ScrollPane spCustomerEditor;
    private Tab tabAppointments;
    private ScrollPane spAppointmentEditor;
    private MainApp mainApp;
    private static AppViewController instance;


    private AppViewController() {
        initialize(MainApp.class.getResource("/fxml/AppView.fxml"), null);
    }

    public static AppViewController getInstance() {
        if (instance == null) {
            new AppViewController();
        }
        return instance;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     **/

    public void initialize(URL location, ResourceBundle resourceBundle) {
        instance = this;
        this.rootPane = new BorderPane();
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu("_File");
        this.editMenu = new Menu("_Edit");
        this.reportMenu = new Menu("_Report");
        this.helpMenu = new Menu("_Help");
        this.closeMenuItem = new MenuItem("Close");
        this.copyMenuItem = new MenuItem("Copy");
        this.monthlyAppointmentReportMenuItem = new MenuItem("Monthly Appointment Report");
        this.consultantScheduleMenuItem = new MenuItem("Consultant Schedule Report");
        this.customersByCountryMenuItem = new MenuItem("Customers by Country Report");
        this.aboutMenuItem = new MenuItem("About");
        this.vbAppView = new VBox();
        this.tpAppPane = new TabPane();
        this.tabCustomers = new Tab("Customers");
        this.tabCustomers.setClosable(false);
        this.spCustomerEditor = new ScrollPane();
        this.tabAppointments = new Tab("Appointments");
        this.tabAppointments.setClosable(false);
        this.spAppointmentEditor = new ScrollPane();

        // populate menus and menuBar and add them to top pane

        this.fileMenu.getItems().setAll(closeMenuItem);
        this.fileMenu.setMnemonicParsing(true);
        this.editMenu.getItems().setAll(copyMenuItem);
        this.editMenu.setMnemonicParsing(true);
        this.reportMenu
                .getItems()
                .setAll(monthlyAppointmentReportMenuItem, consultantScheduleMenuItem, customersByCountryMenuItem);
        this.reportMenu.setMnemonicParsing(true);
        this.helpMenu.getItems().setAll(aboutMenuItem);
        this.helpMenu.setMnemonicParsing(true);

        this.menuBar.getMenus().addAll(fileMenu, editMenu, reportMenu, helpMenu);
        this.rootPane.setTop(menuBar);

        // populate scroll panes with included views
        this.spAppointmentEditor.setContent(getAppointmentView());
        this.spCustomerEditor.setContent(getCustomerView());

        // populate tab panes and controllers and add them to the center pane
        this.tabAppointments.setContent(spAppointmentEditor);
        this.tabCustomers.setContent(spCustomerEditor);
        this.tpAppPane.getTabs().addAll(tabAppointments, tabCustomers);

        vbAppView.getChildren().addAll(tpAppPane);
        this.rootPane.setCenter(vbAppView);

        // add data view to bottom pane
        this.rootPane.setBottom(getDataView());

        setupEventHandlers(this);
    }

    private void setupEventHandlers(AppViewController appViewController) {

        this.tabCustomers.setOnSelectionChanged((event -> {
            if (tabCustomers.isSelected()) {
                // Change to Customer View
                setCustomerView();
            } else {
                setAppointmentView();
            }
        }));

        this.tabAppointments.setOnSelectionChanged(event -> {
            if (tabAppointments.isSelected()) {
                setAppointmentView();
            } else {
                setCustomerView();
            }
        });

        this.closeMenuItem.setOnAction(event -> quitApp());
    }

    private void setCustomerView() {
        CustomerViewController customerViewController = CustomerViewController.getInstance();
        setDataViewController(customerViewController.getDataViewController());
        customerViewController.getGpCustomerEditor().getChildren().filtered(node -> toggleTextFields(node, true));

    }

    private void setAppointmentView() {
        AppointmentViewController controller = AppointmentViewController.getInstance();
        setDataViewController(controller.getDataViewController());
        controller.getGpAppointmentEditor().getChildren().filtered(node -> toggleTextFields(node, true));
    }

    /***
     * Sets whether text fields are enabled or disabled
     * true = disable Text Fields
     * false = enable Text Fields
     * @param node the child nodes of the editor
     * @param disabled whether to disable or enable the text fields
     * @return whether node was affected or not
     */
    public static boolean toggleTextFields(Node node, boolean disabled) {
        if (node instanceof TextField) {
            ((TextField) node).setEditable(!disabled);
            node.setDisable(disabled);
            return true;
        }
        return false;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void quitApp() {
        Platform.exit();
    }

    public Parent getBorderPane() {
        return rootPane;
    }

    public TabPane getTpAppPane() {
        return tpAppPane;
    }

    public Tab getTabCustomers() {
        return tabCustomers;
    }

    public Tab getTabAppointments() {
        return tabAppointments;
    }
}
