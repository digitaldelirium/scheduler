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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static edu.wgu.scheduler.MainApp.*;

public class AppViewController extends BorderPane {


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
    private static AppointmentViewController appointmentViewController = AppointmentViewController.getInstance();
    private static CustomerViewController customerViewController = CustomerViewController.getInstance();
    private static DataViewController dataViewController;

    private AppViewController() {
        initialize();
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

    public void initialize() {
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
        this.rootPane.setBottom(AppointmentViewController.getDataView());

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

        this.tpAppPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.getText().toLowerCase().equals("Customer".toLowerCase())){
                setCustomerView();
            }

            if (newValue.getText().toLowerCase().equals("Appointments".toLowerCase())){
                setAppointmentView();
            }
        });

        this.customersByCountryMenuItem.setOnAction(event -> createCustomersByCountryReport());

        this.consultantScheduleMenuItem.setOnAction(event -> createConsultantScheduleReport());

        this.monthlyAppointmentReportMenuItem.setOnAction(event -> createMonthlyAppointmentReport());
    }

    private void createMonthlyAppointmentReport() {
        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("/tmp/MonthlyAppointmentReport.csv")))) {
            try(Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Appointments\n" +
                                                                                  "WHERE start BETWEEN UTC_DATE AND DATE_ADD(UTC_DATE, INTERVAL 1 MONTH);");
                ResultSet rs = statement.executeQuery();
                StringBuilder builder = new StringBuilder();
                while (rs.next()){
                    builder.append(rs.getTimestamp("start")
                                     .toInstant()
                                     .atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    builder.append(rs.getTimestamp("end")
                                     .toInstant()
                                     .atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)+ ",");
                    builder.append(rs.getString("title") + ",");
                    builder.append(rs.getString("description") + ",");
                    builder.append(rs.getString("location") + ",");
                    builder.append(rs.getString("contact") + ",");
                    builder.append(rs.getString("url") + ",");
                    builder.append(rs.getString("customerName") + ",");
                    builder.append(rs.getTimestamp("createDate")
                                     .toInstant()
                                     .atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .toLocalDate()
                                     .format(DateTimeFormatter.ISO_DATE) + ",");
                    builder.append(rs.getString("createdBy") + ",");
                    builder.append(rs.getTimestamp("lastUpdate")
                                     .toInstant()
                                     .atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    builder.append(rs.getString("lastUpdateBy") + "\n");
                }
                stream.write(builder.toString().getBytes());
            } catch (SQLException e) {
                e.printStackTrace();
                stream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createConsultantScheduleReport() {
        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("/tmp/ConsultantScheduleReport.csv")))){
            try(Connection connection = dataSource.getConnection()){
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Appointments ORDER BY Appointments.createdBy DESC, Appointments.start DESC");
                ResultSet rs = statement.executeQuery();
                StringBuilder builder = new StringBuilder();
                while (rs.next()){
                    builder.append(rs.getString("createdBy") + ",");
                    builder.append(rs.getString("title") + ",");
                    builder.append(rs.getString("description") + ",");
                    builder.append(rs.getString("location") + ",");
                    builder.append(rs.getString("contact") + ",");
                    builder.append(rs.getString("url") + ",");
                    builder.append(rs.getTimestamp("start")
                                     .toInstant()
                                     .atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    builder.append(rs.getTimestamp("end")
                                     .toInstant()
                                     .atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    builder.append(rs.getString("customerName") + ",");
                    builder.append(rs.getTimestamp("createDate")
                                  .toInstant()
                                  .atZone(ZoneId.of("UTC"))
                                  .withZoneSameInstant(ZoneId.systemDefault())
                                  .toLocalDate()
                                  .format(DateTimeFormatter.ISO_DATE));
                    builder.append(rs.getTimestamp("lastUpdate")
                                     .toInstant().atZone(ZoneId.of("UTC"))
                                     .withZoneSameInstant(ZoneId.systemDefault())
                                     .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    builder.append(rs.getString("lastUpdateBy") + "\n");
                    stream.write(builder.toString().getBytes());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCustomersByCountryReport() {
        File report = new File("/tmp/CustomersByCountryReport.csv");
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(report))){
            StringBuffer buffer = new StringBuffer();
            try(Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("SELECT * from Customers ORDER BY country ASC");
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    boolean active;
                    switch (rs.getByte("active")){
                        case 0:
                            active = false;
                            break;
                        default:
                            active = true;
                            break;
                    }
                    buffer.append(rs.getString("country") + ",");
                    buffer.append(rs.getString("customerName") + ",");
                    buffer.append(rs.getString("address") + ",");
                    buffer.append(rs.getString("address2") + ",");
                    buffer.append(rs.getString("city") +"," );
                    buffer.append(rs.getString("postalCode") + ",");
                    buffer.append(rs.getString("phone") + ",");
                    buffer.append(rs.getTimestamp("createDate")
                                    .toInstant()
                                    .atZone(ZoneId.of("UTC"))
                                    .withZoneSameInstant(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    buffer.append(rs.getString("createdBy") + ",");
                    buffer.append(rs.getTimestamp("lastUpdate")
                                 .toInstant()
                                 .atZone(ZoneId.of("UTC"))
                                 .withZoneSameInstant(ZoneId.systemDefault())
                                 .format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + ",");
                    buffer.append(rs.getString("lastUpdateBy") + ",");
                    buffer.append(active);
                    buffer.append("\n");
                }
                stream.write(String.valueOf(buffer.toString()).getBytes());
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerView() {
        CustomerViewController customerViewController = CustomerViewController.getInstance();
        setDataView(customerViewController.getDataViewController().tabPane);
        customerViewController.getGpCustomerEditor().getChildren().filtered(node -> toggleTextFields(node, true));

    }

    private void setAppointmentView() {
        AppointmentViewController controller = AppointmentViewController.getInstance();
        setDataView(controller.getDataViewController().tabPane);
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppViewController{");
        sb.append("\nrootPane=").append(rootPane);
        sb.append(",\n menuBar=").append(menuBar);
        sb.append(",\n fileMenu=").append(fileMenu);
        sb.append(",\n editMenu=").append(editMenu);
        sb.append(",\n reportMenu=").append(reportMenu);
        sb.append(",\n helpMenu=").append(helpMenu);
        sb.append(",\n closeMenuItem=").append(closeMenuItem);
        sb.append(",\n copyMenuItem=").append(copyMenuItem);
        sb.append(",\n monthlyAppointmentReportMenuItem=").append(monthlyAppointmentReportMenuItem);
        sb.append(",\n consultantScheduleMenuItem=").append(consultantScheduleMenuItem);
        sb.append(",\n customersByCountryMenuItem=").append(customersByCountryMenuItem);
        sb.append(",\n aboutMenuItem=").append(aboutMenuItem);
        sb.append(",\n vbAppView=").append(vbAppView);
        sb.append(",\n tpAppPane=").append(tpAppPane);
        sb.append(",\n tabCustomers=").append(tabCustomers);
        sb.append(",\n spCustomerEditor=").append(spCustomerEditor);
        sb.append(",\n tabAppointments=").append(tabAppointments);
        sb.append(",\n spAppointmentEditor=").append(spAppointmentEditor);
        sb.append(",\n dataViewController=").append(dataViewController);
        sb.append("\n}");
        return sb.toString();
    }

    public void setDataViewController(DataViewController dataViewController) {
        this.dataViewController = dataViewController;
    }

    public DataViewController getDataViewController() {
        return dataViewController;
    }
}
