package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static edu.wgu.scheduler.MainApp.*;
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
    private HBox hbEditorBar;
    @FXML
    private ToggleButton tbAppointmentEditor;
    @FXML
    private Button btnNewAppointment;
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
    private Button btnAppointmentSave;
    @FXML
    private Label lblViewScope;
    @FXML
    private HBox hbViewScope;
    @FXML
    private RadioButton rdoWeekly;
    @FXML
    private RadioButton rdoMonthly;
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
    @FXML
    private ButtonBar buttonbarAppointmentEditor;
    @FXML
    private Button btnAppointmentReset;

    protected static ObservableList<CustomerProperty> customers;
    protected static ObservableList<AppointmentProperty> appointments;
    protected static ObservableList<AppointmentViewProperty> appointmentViews;
    protected static ObservableList<ReminderProperty> reminders;

    public AppointmentViewController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.apAppointmentView = new AnchorPane();
        this.vbAppointmentEditor = new VBox();
        this.hbEditorBar = new HBox();
        this.btnNewAppointment = new Button();
        this.tbAppointmentEditor = new ToggleButton();
        this.gpAppointmentEditor = new GridPane();
        this.lblViewScope = new Label("Choose View:");
        this.lblTitle = new Label("Title:");
        this.lblDescription = new Label("Description:");
        this.lblLocation = new Label("Location:");
        this.lblContact = new Label("Contact:");
        this.lblUrl = new Label("Url:");
        this.lblCustomerName = new Label("Customer Name:");
        this.lblStart = new Label("Start Time:");
        this.lblEnd = new Label("End Time:");
        this.lblCreatedDate = new Label("Created Date:");
        this.lblCreatedBy = new Label("Created By:");
        this.lblLastUpdated = new Label("Last Updated:");
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
        this.buttonbarAppointmentEditor = new ButtonBar();
        this.btnAppointmentSave = new Button();
        this.btnAppointmentReset = new Button();

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

        this.tbAppointmentEditor.setText("Enable Edit Mode");
        this.tbAppointmentEditor.setSelected(false);

        buttonbarAppointmentEditor.getButtons().addAll(btnAppointmentSave, btnAppointmentReset);

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
                this.buttonbarAppointmentEditor
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

        appointments = FXCollections.observableList(new LinkedList<>(), (AppointmentProperty ap) -> new Observable[]{
                ap.title(),
                ap.description(),
                ap.location(),
                ap.contact(),
                ap.url(),
                ap.start(),
                ap.end(),
                ap.customerId()
        });

        reminders = FXCollections.observableList(new LinkedList<>(), re -> new Observable[]{

        });
        getAppointments();
    }

    private void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    /**
     *  Get list of appointments and customers and populate their respective lists
     */
    private void getAppointments(){
        try(Connection connection = dataSource.getConnection()){
            // Get Views first
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Appointments");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                edu.wgu.scheduler.models.AppointmentView app = new AppointmentView(rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("url"),
                        rs.getString("customerName"),
                        rs.getTimestamp("start"),
                        rs.getTimestamp("end"),
                        rs.getDate("createDate"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"));
                appointmentViews.add(new AppointmentViewProperty(app));
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
                customers.add(new CustomerProperty(cu));
            }

            statement = connection.prepareStatement("SELECT * FROM appointment");
            rs = statement.executeQuery();
            while (rs.next()){
                Appointment ap = new Appointment(
                        rs.getDate("createDate").toLocalDate(),
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
                boolean add = appointments.add(new AppointmentProperty(ap));
            }

            statement = connection.prepareStatement("SELECT * FROM reminder");
            rs = statement.executeQuery();
            while (rs.next()){
                Reminder re = new Reminder(
                        rs.getInt("reminderId"),
                        rs.getInt("appointmentId"),
                        rs.getString("createdBy"),
                        rs.getDate("createdDate").toLocalDate(),
                        ZonedDateTime.ofInstant(rs.getDate("reminderDate").toInstant(), ZoneId.systemDefault()),
                        rs.getString("remindercol"),
                        rs.getInt("snoozeIncrement"),
                        rs.getInt("snoozeIncrementTypeId"));
                reminders.add(new ReminderProperty(re));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createNewAppointment() {
        // Enable controls and empty them out
        setTextEditable();

        this.btnAppointmentSave.setDisable(true);
        this.btnAppointmentReset.setDisable(true);

        createListeners();
    }

    /**
     * Creates listeners to enable the reset and save buttons
     */
    private void createListeners() {
        txtCustomerName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().length() < 5) {
                btnAppointmentSave.setDisable(true);
            } else {
                btnAppointmentSave.setDisable(false);
                btnAppointmentReset.setDisable(false);
            }
        });

        txtDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().length() < 10) {
                btnAppointmentSave.setDisable(true);
            } else {
                btnAppointmentSave.setDisable(false);
                btnAppointmentReset.setDisable(false);
            }
        });

        txtTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().length() < 4) {
                btnAppointmentSave.setDisable(true);
            } else {
                btnAppointmentReset.setDisable(false);
                if (txtDescription.getText().trim().length() >= 10) {
                    if (txtStart.getText().trim().isEmpty() == false) {

                    }
                }
            }
        });

        txtStart.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        txtEnd.textProperty().addListener((observable, oldValue, newValue) -> {

        });

    }

    // TODO: Implement Update Appointment method
    /**
     * Update appointment
     *
     * @return whether call was successful or not
     */
    private boolean updateAppointment() {
        throw new NotImplementedException();
    }

    @FXML
    private void toggleEditable() {
        if (this.tbAppointmentEditor.isSelected()) {
            setTextEditable();
        } else {
            this.vbAppointmentEditor.getChildren().filtered(node -> {
                if (node instanceof TextField) {
                    ((TextField) node).setEditable(false);
                    return true;
                }
                return false;
            });
        }
    }

    /**
     *
     */
    private void setTextEditable() {
        this.vbAppointmentEditor.getChildren().filtered(node -> {
            if (node instanceof TextField) {
                ((TextField) node).setEditable(true);
                return true;
            }
            return false;
        });

        this.txtCreatedBy.setText(MainApp.user.getUsername());
        this.txtCreatedBy.setEditable(false);
        this.txtCreatedDate.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        this.txtCreatedDate.setEditable(false);
    }

    /**
     * @return
     */
    @FXML
    private boolean saveAppointment() {
        int customerId = 0;
        int x = 0;
        for (CustomerProperty customer : customers) {
            if (customer.getCustomerName().equals(txtCustomerName.getText())) {
                customerId = customer.getCustomerId();
                break;
            }
            x++;
        }

        if (x >= customers.size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Customer does not exist!");
            alert.setContentText("The customer you're trying to create an appointment for does not exist! You will now be switched to Customer View to create this customer!");
            alert.getButtonTypes().add(ButtonType.OK);
            alert.showAndWait()
                    .ifPresent(response -> {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(MainApp.class.getResource("/fxml/CustomerView.fxml"));
                        CustomerViewController controller = loader.getController();
                        try {
                            rootPane = loader.load();
                        } catch (IOException e) {
                            e.getLocalizedMessage();
                        }

                        Scene scene = new Scene(rootPane);
                        scene.getStylesheets().add("/styles/Styles.css");

                        this.setMainApp(this.mainApp);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    });
        }

        Appointment app = new Appointment(txtCreatedBy.getText(),
                customerId,
                txtDescription.getText(),
                ZonedDateTime.of(LocalDateTime.parse(txtEnd.getText()), ZoneId.systemDefault()),
                txtLocation.getText(),
                ZonedDateTime.of(LocalDateTime.parse(txtStart.getText()), ZoneId.systemDefault()),
                txtTitle.getText(),
                txtUrl.getText());

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE appointment\n" +
                    "SET title = ?, description = ?, location = ?, contact = ?, url = ?, start = ?, lastUpdatedBy = ?, end = ? \n" +
                    "WHERE appointmentId = ?");
            statement.setString(1, app.getTitle());
            statement.setString(2, app.getDescription());
            statement.setString(3, app.getLocation());
            statement.setString(4, app.getContact());
            statement.setString(5, app.getUrl());
            statement.setTime(6, new Time(app.getStart().toEpochSecond()));
            statement.setString(7, user.getUsername());
            statement.setTime(8, new Time(app.getEnd().toEpochSecond()));
            statement.setInt(9, app.getAppointmentId());

            switch (statement.executeUpdate()) {
                case 1: {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Update Succeeded");
                    alert.setContentText("Appointment was updated successfully!");
                    alert.show();
                    return true;
                }
                case 0: {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Unchanged");
                    alert.setContentText("There were no changes to the appointment");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void clearForm() {
        this.vbAppointmentEditor.getChildren().filtered(node -> {
            if (node instanceof TextField) {
                ((TextField) node).setText(null);
                return true;
            }
            return false;
        });

        this.btnAppointmentSave.setDisable(true);
        this.btnAppointmentReset.setDisable(true);
    }
}
