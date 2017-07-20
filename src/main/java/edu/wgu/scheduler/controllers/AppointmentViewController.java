package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static edu.wgu.scheduler.MainApp.dataSource;
import static edu.wgu.scheduler.controllers.AppViewController.lblTableView;
import static edu.wgu.scheduler.controllers.AppViewController.tvTableView;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:20.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentViewController implements Initializable {
    @FXML
    private AnchorPane apAppointmentView;
    @FXML
    private VBox vbAppointmentEditor;
    @FXML
    private ToggleButton tbAppointmentEditor;
    @FXML
    private GridPane gpAppointmentEditor;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblContact;
    @FXML
    private Label lblUrl;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblStart;
    @FXML
    private Label lblEnd;
    @FXML
    private Label lblCreatedDate;
    @FXML
    private Label lblCreatedBy;
    @FXML
    private Label lblLastUpdated;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtUrl;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtStart;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtCreatedDate;
    @FXML
    private TextField txtCreatedBy;
    @FXML
    private TextField txtLastUpdated;
    @FXML
    private ButtonBar btnbarAppointmentEditor;
    @FXML
    private Button btnAppointmentOk;
    @FXML
    private Button btnAppointmentCancel;
    private TableView<IAppointmentView> tvAppointments = new TableView<>();
    private TableColumn<IAppointmentView, String> tcTitle = new TableColumn<>();
    private TableColumn<IAppointmentView, String> tcDescription = new TableColumn<>();
    private TableColumn<IAppointmentView, String> tcLocation = new TableColumn<>();
    private TableColumn<IAppointmentView, String> tcContact = new TableColumn<>();
    private TableColumn<IAppointmentView, String> tcUrl = new TableColumn<>();
    private TableColumn<IAppointmentView, String> tcCustomerName = new TableColumn<>();
    private TableColumn<IAppointmentView, ZonedDateTime> tcStart = new TableColumn<>();
    private TableColumn<IAppointmentView, ZonedDateTime> tcEnd = new TableColumn<>();
    private TableColumn<IAppointmentView, ZonedDateTime> tcCreateDate = new TableColumn<>();
    private TableColumn<IAppointmentView, String> tcCreatedBy = new TableColumn<>();
    private TableColumn<IAppointmentView, Timestamp> tcLastUpdate = new TableColumn<>();
    private MainApp mainApp;
    private ObservableSet<AppointmentView> appointmentViews = FXCollections.emptyObservableSet();
    private ObservableSet<Customer> customers = FXCollections.emptyObservableSet();



    protected static ObservableSet<Appointment> appointments = FXCollections.emptyObservableSet();
    protected static ObservableSet<Reminder> reminders = FXCollections.emptyObservableSet();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.apAppointmentView = new AnchorPane();
        this.vbAppointmentEditor = new VBox();
        this.tbAppointmentEditor = new ToggleButton();
        this.gpAppointmentEditor = new GridPane();
        this.lblTitle = new Label();
        this.lblDescription = new Label();
        this.lblLocation = new Label();
        this.lblContact = new Label();
        this.lblUrl = new Label();
        this.lblCustomerName = new Label();
        this.lblStart = new Label();
        this.lblEnd = new Label();
        this.lblCreatedDate = new Label();
        this.lblCreatedBy = new Label();
        this.lblLastUpdated = new Label();
        this.txtTitle = new TextField();
        this.txtDescription = new TextField();
        this.txtLocation = new TextField();
        this.txtContact = new TextField();
        this.txtUrl = new TextField();
        this.txtCustomerName = new TextField();
        this.txtStart = new TextField();
        this.txtEnd = new TextField();
        this.txtCreatedDate = new TextField();
        this.txtCreatedBy = new TextField();
        this.txtLastUpdated = new TextField();
        this.btnbarAppointmentEditor = new ButtonBar();
        this.btnAppointmentOk = new Button();
        this.btnAppointmentCancel = new Button();

        this.lblTitle.setLabelFor(this.txtTitle);
        this.lblDescription.setLabelFor(this.txtDescription);
        this.lblLocation.setLabelFor(this.txtLocation);
        this.lblContact.setLabelFor(this.txtContact);
        this.lblUrl.setLabelFor(this.txtUrl);
        this.lblCustomerName.setLabelFor(this.txtCustomerName);
        this.lblStart.setLabelFor(this.txtStart);
        this.lblEnd.setLabelFor(this.txtEnd);
        this.lblCreatedDate.setLabelFor(this.txtCreatedDate);
        this.lblCreatedBy.setLabelFor(this.txtCreatedBy);
        this.lblLastUpdated.setLabelFor(this.txtLastUpdated);

        btnbarAppointmentEditor.getButtons().addAll(btnAppointmentOk, btnAppointmentCancel);

        this.vbAppointmentEditor.getChildren().addAll(
                this.gpAppointmentEditor,
                this.lblTitle,
                this.txtTitle,
                this.lblContact,
                this.txtContact,
                this.lblLocation,
                this.txtLocation,
                this.lblDescription,
                this.txtDescription,
                this.lblUrl,
                this.txtUrl,
                this.lblCustomerName,
                this.txtCustomerName,
                this.lblStart,
                this.txtStart,
                this.lblEnd,
                this.txtEnd,
                this.lblCreatedDate,
                this.txtCreatedDate,
                this.lblCreatedBy,
                this.txtCreatedBy,
                this.lblLastUpdated,
                this.txtLastUpdated,
                this.btnbarAppointmentEditor
        );

        this.tvAppointments.getColumns().addAll(this.tcTitle,
                this.tcDescription,
                this.tcLocation,
                this.tcContact,
                this.tcUrl,
                this.tcCustomerName,
                this.tcStart,
                this.tcEnd,
                this.tcCreateDate,
                this.tcCreatedBy,
                this.tcLastUpdate);
        tvTableView = this.tvAppointments;
        lblTableView.setText("Appointments");

        getAppointments();
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    /**
     *  Get list of appointments and customers and populate their respective lists
     */
    private void getAppointments(){
        try(Connection connection = dataSource.getConnection()){
            // Get Views first
            PreparedStatement statement = connection.prepareStatement("  SELECT\n" +
                    "    `ap`.`title`        AS `title`,\n" +
                    "    `ap`.`description`  AS `description`,\n" +
                    "    `ap`.`location`     AS `location`,\n" +
                    "    `ap`.`contact`      AS `contact`,\n" +
                    "    `ap`.`url`          AS `url`,\n" +
                    "    `cu`.`customerName` AS `customerName`,\n" +
                    "    `ap`.`start`        AS `start`,\n" +
                    "    `ap`.`end`          AS `end`,\n" +
                    "    `ap`.`createDate`   AS `createDate`,\n" +
                    "    `ap`.`createdBy`    AS `createdBy`,\n" +
                    "    `ap`.`lastUpdate`   AS `lastUpdate`\n" +
                    "  FROM (`U01gMV`.`appointment` `ap` LEFT JOIN `U01gMV`.`customer` `cu` ON ((\n" +
                    "    (`ap`.`customerId` = `cu`.`customerId`) AND (`ap`.`createDate` = `cu`.`createDate`) AND\n" +
                    "    (`ap`.`createdBy` = `cu`.`createdBy`) AND (`ap`.`lastUpdate` = `cu`.`lastUpdate`))));");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                AppointmentView app = new AppointmentView(rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("url"),
                        rs.getString("customerName"),
                        rs.getDate("start"),
                        rs.getDate("end"),
                        rs.getDate("createDate"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"));
                appointmentViews.add(app);
            }

            statement = connection.prepareStatement("SELECT * FROM customer");
            rs = statement.executeQuery();
            while(rs.next()){
                Customer cu = new Customer(
                        rs.getDate("createDate").toLocalDate(),
                        rs.getInt("customerId"),
                        rs.getByte("active"),
                        rs.getInt("addressId"),
                        rs.getString("createdBy"),
                        rs.getString("customerName"),
                        rs.getString("lastUpdatedBy"),
                        rs.getTimestamp("lastUpdate")
                );
                customers.add(cu);
            }

            statement = connection.prepareStatement("SELECT * FROM appointment");
            rs = statement.executeQuery();
            while (rs.next()){
                Appointment ap = new Appointment(
                        rs.getDate("createDate").toLocalDate().atStartOfDay(ZoneId.of("UTC")),
                        rs.getInt("appointmentId"),
                        rs.getString("contact"),
                        rs.getString("createdBy"),
                        rs.getInt("customerId"),
                        rs.getString("description"),
                        rs.getString("end"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdatedBy"),
                        rs.getString("location"),
                        rs.getString("start"),
                        rs.getString("title"),
                        rs.getString("url")
                );
                appointments.add(ap);
            }

            statement = connection.prepareStatement("SELECT * FROM reminder");
            rs = statement.executeQuery();
            while (rs.next()){
                Reminder re = new Reminder(
                        rs.getInt("reminderId"),
                        rs.getInt("appointmentId"),
                        rs.getString("createdBy"),
                        rs.getDate("createdDate").toLocalDate(),
                        rs.getDate("reminderDate").getTime(),
                        rs.getString("remindercol"),
                        rs.getString("snoozeIncrement"),
                        rs.getString("snoozeIncrementTypeId"));
                reminders.add(re);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class AppointmentView implements IAppointmentView {

        // Interface needs these components
        private String title;
        private String description;
        private String location;
        private String contact;
        private String url;
        private String customerName;
        private Date start;
        private Date end;
        private Date createdDate;
        private String createdBy;
        private Timestamp lastUpdated;

        public AppointmentView(String title, String description, String location, String contact, String url, String customerName, Date start, Date end, Date createdDate, String createdBy, Timestamp lastUpdated) {
            this.title = title;
            this.description = description;
            this.location = location;
            this.contact = contact;
            this.url = url;
            this.customerName = customerName;
            this.start = start;
            this.end = end;
            this.createdDate = createdDate;
            this.createdBy = createdBy;
            this.lastUpdated = lastUpdated;
        }



        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getLocation() {
            return location;
        }

        public String getContact() {
            return contact;
        }

        public String getUrl() {
            return url;
        }

        public String getCustomerName() {
            return customerName;
        }

        public Date getStart() {
            return start;
        }

        public Date getEnd() {
            return end;
        }

        public Date getCreateDate() {
            return createdDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public Timestamp getLastUpdate() {
            return lastUpdated;
        }
    }
}
