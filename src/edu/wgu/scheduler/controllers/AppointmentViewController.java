package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import edu.wgu.scheduler.models.AppointmentViewProperty.AppointmentView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static edu.wgu.scheduler.MainApp.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:20.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentViewController extends AnchorPane implements Initializable {
    private VBox vbAppointmentEditor;
    private HBox hbEditorBar;
    private ToggleButton tbAppointmentEditor;
    private Button btnNewAppointment;
    private GridPane gpAppointmentEditor;
    private Label lblTitle;
    private Label lblDescription;
    private Label lblLocation;
    private Label lblContact;
    private Label lblUrl;
    private Label lblCustomerName;
    private Label lblStart;
    private Label lblEnd;
    private Label lblCreatedDate;
    private Label lblCreatedBy;
    private Label lblLastUpdated;
    private TextField txtTitle;
    private TextField txtDescription;
    private TextField txtLocation;
    private TextField txtContact;
    private TextField txtUrl;
    private TextField txtCustomerName;
    private HBox hbEndTime;
    private DatePicker datePickerStartTime;
    private ChoiceBox<Integer> choiceBoxStartHour;
    private ChoiceBox<Integer> choiceBoxStartMinute;
    private HBox hbStartTime;
    private DatePicker datePickerEndTime;
    private ChoiceBox<Integer> choiceBoxEndHour;
    private ChoiceBox<Integer> choiceBoxEndMinute;
    private TextField txtCreatedDate;
    private TextField txtCreatedBy;
    private TextField txtLastUpdated;
    private Button btnAppointmentSave;
    private Label lblViewScope;
    private HBox hbViewScope;
    private RadioButton rdoWeekly;
    private RadioButton rdoMonthly;
    private ButtonBar buttonbarAppointmentEditor;
    private Button btnAppointmentReset;
    private TableView<AppointmentViewProperty> tvAppointments = new TableView<AppointmentViewProperty>();
    private TableColumn<AppointmentView, String> tcTitle = new TableColumn<>("Title");
    private TableColumn<AppointmentView, String> tcDescription = new TableColumn<>("Description");
    private TableColumn<AppointmentView, String> tcLocation = new TableColumn<>("Location");
    private TableColumn<AppointmentView, String> tcContact = new TableColumn<>("Contact");
    private TableColumn<AppointmentView, String> tcUrl = new TableColumn<>("URL");
    private TableColumn<AppointmentView, String> tcCustomerName = new TableColumn<>("Customer Name");
    private TableColumn<AppointmentView, ZonedDateTime> tcStart = new TableColumn<>("Start Time");
    private TableColumn<AppointmentView, ZonedDateTime> tcEnd = new TableColumn<>("End Time");
    private TableColumn<AppointmentView, ZonedDateTime> tcCreateDate = new TableColumn<>("Created Date");
    private TableColumn<AppointmentView, String> tcCreatedBy = new TableColumn<>("Created By");
    private TableColumn<AppointmentView, Timestamp> tcLastUpdate = new TableColumn<>("Last Updated");
    private MainApp mainApp;
    private DataViewController dataViewController;
    private boolean isNewAppointment;
    private static AppointmentViewController instance;
    private static ObservableList<ZonedDateTime> hours;
    private static ObservableList<ZonedDateTime> minutes;
    protected static ObservableList<CustomerProperty> customers;
    protected static ObservableList<AppointmentProperty> appointments;
    protected static ObservableList<AppointmentViewProperty> appointmentViews;
    protected static ObservableList<ReminderProperty> reminders;
    public AnchorPane apAppointmentView;

    private AppointmentViewController() {
        initialize(MainApp.class.getResource("/fxml/AppointmentView.fxml"), null);
    }

    public static AppointmentViewController getInstance() {
        if(instance == null){
            new AppointmentViewController();
        }
        return instance;
    }

    public void initialize(URL location, ResourceBundle resourceBundle) {
        instance = this;
        appointmentViews = FXCollections.observableArrayList();
        appointments = FXCollections.observableArrayList();
        customers = FXCollections.observableArrayList();
        reminders = FXCollections.observableArrayList();
        hours = FXCollections.observableList(new LinkedList<>());
        minutes = FXCollections.observableList(new LinkedList<>());
        this.apAppointmentView = new AnchorPane();
        this.apAppointmentView.setPrefHeight(580.0);
        this.vbAppointmentEditor = new VBox();
        this.vbAppointmentEditor.setPrefWidth(985.0);
        this.vbAppointmentEditor.setOpaqueInsets(new Insets(10.0));
        this.vbAppointmentEditor.setPadding(new Insets(5.0));
        this.dataViewController = new DataViewController();
        this.lblViewScope = new Label("Choose View:");

        setupHBoxes();
        setupGridPane();

        this.vbAppointmentEditor.getChildren().addAll(
                this.lblViewScope,
                this.hbViewScope,
                this.hbEditorBar,
                this.gpAppointmentEditor
        );

        this.apAppointmentView.getChildren().add(vbAppointmentEditor);

        setupCollections();
        getAppointments();
        setupDataView();
        setupEventHandlers();
        setupTextFields(true);

    }

    protected void disableTextFields() {
        this.gpAppointmentEditor.getChildren().filtered(n -> {
            if(n instanceof TextField){
                n.setDisable(true);
                ((TextField) n).setEditable(false);
                return true;
            }
            return false;
        });
    }

    private void setupHBoxes() {

        this.hbEditorBar = new HBox();
        this.hbViewScope = new HBox();
        this.hbStartTime = new HBox();
        this.hbEndTime = new HBox();

        this.btnNewAppointment = new Button("New Appointment");
        this.tbAppointmentEditor = new ToggleButton("Edit Appointment");
        this.rdoWeekly = new RadioButton("Weekly");
        this.rdoMonthly = new RadioButton("Monthly");

        this.datePickerStartTime = new DatePicker(LocalDate.now());
        this.datePickerStartTime.setShowWeekNumbers(true);
        this.datePickerStartTime.setTooltip(new Tooltip("Please select a start date for the appointment!"));
        this.choiceBoxStartHour = new ChoiceBox<>();
        this.choiceBoxStartMinute = new ChoiceBox<>();
        this.hbStartTime.getChildren().addAll(datePickerStartTime, choiceBoxStartHour, choiceBoxStartMinute);
        this.hbStartTime.setSpacing(5.0);

        this.datePickerEndTime = new DatePicker(LocalDate.now());
        this.datePickerEndTime.setShowWeekNumbers(true);
        this.datePickerEndTime.setTooltip(new Tooltip("Please select an end date for the appointment!"));
        this.choiceBoxEndHour = new ChoiceBox<>();
        this.choiceBoxEndMinute = new ChoiceBox<>();
        this.hbEndTime.getChildren().addAll(datePickerEndTime, choiceBoxEndHour, choiceBoxEndMinute);
        this.hbEndTime.setSpacing(5.0);
        this.hbEndTime.setPadding(new Insets(5.0));
        setupChoiceBoxes();
        ToggleGroup toggleGroup = new ToggleGroup();

        rdoWeekly.setToggleGroup(toggleGroup);
        rdoWeekly.setSelected(true);
        rdoWeekly.setOpaqueInsets(new Insets(5.0));
        rdoMonthly.setToggleGroup(toggleGroup);
        rdoMonthly.setOpaqueInsets(new Insets(5.0));

        this.tbAppointmentEditor.setText("Enable Edit Mode");
        this.tbAppointmentEditor.setSelected(false);

        this.hbViewScope.getChildren().addAll(rdoWeekly, rdoMonthly);
        this.hbViewScope.setPadding(new Insets(5.0));
        this.hbViewScope.setSpacing(10.0);
        this.hbEditorBar.getChildren().addAll(tbAppointmentEditor, btnNewAppointment);
        this.hbEditorBar.setPadding(new Insets(5));
        this.hbEditorBar.setSpacing(10.0);
    }

    private void setupChoiceBoxes() {
        LinkedList<Integer> hourList = new LinkedList();
        LinkedList<Integer> minuteList = new LinkedList<>();
        
        for (int i = 0; i < 24; i++){
            hourList.add(i);
        }
        
        for (int i=0; i < 60;){
            minuteList.add(i);
            i = i + 5;
        }
        
        choiceBoxEndHour.setItems(FXCollections.observableList(hourList));
        choiceBoxStartHour.setItems(FXCollections.observableList(hourList));
        
        choiceBoxEndMinute.setItems(FXCollections.observableList(minuteList));
        choiceBoxStartMinute.setItems(FXCollections.observableList(minuteList));
    }

    private void setupGridPane() {
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
        this.txtCreatedDate = new TextField();
        this.txtCreatedBy = new TextField();
        this.txtLastUpdated = new TextField();
        this.buttonbarAppointmentEditor = new ButtonBar();
        this.btnAppointmentSave = new Button("Save");
        this.btnAppointmentReset = new Button("Reset");

        this.lblTitle.setLabelFor(this.txtTitle);
        this.lblDescription.setLabelFor(this.txtDescription);
        this.lblLocation.setLabelFor(this.txtLocation);
        this.lblContact.setLabelFor(this.txtContact);
        this.lblUrl.setLabelFor(this.txtUrl);
        this.lblCustomerName.setLabelFor(this.txtCustomerName);
        this.lblStart.setLabelFor(this.hbStartTime);
        this.lblEnd.setLabelFor(this.hbEndTime);
        this.lblCreatedDate.setLabelFor(this.txtCreatedDate);
        this.lblCreatedBy.setLabelFor(this.txtCreatedBy);
        this.lblLastUpdated.setLabelFor(this.txtLastUpdated);

        buttonbarAppointmentEditor.getButtons().addAll(btnAppointmentSave, btnAppointmentReset);

        this.gpAppointmentEditor.add(lblTitle, 0, 0);
        this.gpAppointmentEditor.add(txtTitle, 1, 0);
        this.gpAppointmentEditor.add(lblDescription, 0, 1);
        this.gpAppointmentEditor.add(txtDescription, 1, 1);
        this.gpAppointmentEditor.add(lblLocation, 0, 2);
        this.gpAppointmentEditor.add(txtLocation, 1, 2);
        this.gpAppointmentEditor.add(lblContact, 0, 3);
        this.gpAppointmentEditor.add(txtContact, 1, 3);
        this.gpAppointmentEditor.add(lblUrl, 0, 4);
        this.gpAppointmentEditor.add(txtUrl, 1, 4);
        this.gpAppointmentEditor.add(lblStart, 0, 5);
        this.gpAppointmentEditor.add(hbStartTime, 1, 5);
        this.gpAppointmentEditor.add(lblEnd, 0, 6);
        this.gpAppointmentEditor.add(hbEndTime, 1, 6);
        this.gpAppointmentEditor.add(lblCustomerName, 0, 7);
        this.gpAppointmentEditor.add(txtCustomerName, 1, 7);
        this.gpAppointmentEditor.add(lblCreatedDate, 0, 8);
        this.gpAppointmentEditor.add(txtCreatedDate, 1, 8);
        this.gpAppointmentEditor.add(lblCreatedBy, 0, 9);
        this.gpAppointmentEditor.add(txtCreatedBy, 1, 9);
        this.gpAppointmentEditor.add(lblLastUpdated, 0, 10);
        this.gpAppointmentEditor.add(txtLastUpdated, 1, 10);
        this.gpAppointmentEditor.add(buttonbarAppointmentEditor, 1, 11);

        this.gpAppointmentEditor.setPadding(new Insets(20));
        this.gpAppointmentEditor.setHgap(10.0);
        this.gpAppointmentEditor.setVgap(5.0);
        this.gpAppointmentEditor.getRowConstraints().setAll(new RowConstraints(10.0, 30.0, Integer.MAX_VALUE));
        this.gpAppointmentEditor.getColumnConstraints().setAll(new ColumnConstraints(10, 150.0, Integer.MAX_VALUE));

        this.btnAppointmentReset.setDisable(true);
        this.btnAppointmentSave.setDisable(true);

        setupTextFields(true);
    }

    private void setupTextFields(Boolean disabled) {
        this.gpAppointmentEditor.getChildren().filtered(node -> {
            if(node instanceof TextField){
                ((TextField) node).setPrefWidth(300.0);
                ((TextField) node).setEditable(!disabled);
                node.setDisable(disabled);
                return true;
            }
            return false;
        });
    }

    private void setupCollections() {
        appointments = FXCollections.observableList(new LinkedList<>(), (AppointmentProperty ap) -> new Observable[]{
                ap.titleProperty(),
                ap.descriptionProperty(),
                ap.locationProperty(),
                ap.contactProperty(),
                ap.urlProperty(),
                ap.startProperty(),
                ap.endProperty(),
                ap.customerIdProperty()
        });

        reminders = FXCollections.observableList(new LinkedList<>(), re -> new Observable[]{
                re.remindercolProperty(),
                re.reminderDate(),
                re.snoozeIncrementProperty(),
                re.snoozeIncrementTypeIdProperty()
        });



    }

    private void setupDataView() {
        this.tvAppointments.getItems().addAll(appointmentViews);
        dataViewController.setTableView(this.tvAppointments);
        dataViewController.setLblListView(new Label("Appointments"));
    }

    private void setupEventHandlers(){

        this.tbAppointmentEditor.setOnAction(event -> toggleEditable());
        this.btnNewAppointment.setOnAction(event -> createNewAppointment());
        this.btnAppointmentSave.setOnAction(event -> saveAppointment());
        this.btnAppointmentReset.setOnAction(event -> clearForm());

        this.datePickerStartTime.setOnAction(event -> {
            LocalDate selectedDate = datePickerStartTime.getValue();
            if(selectedDate.isBefore(LocalDate.now())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date!");
                alert.setHeaderText("This date was in the past!");
                alert.setContentText("Please select a valid date!");
                alert.show();
            }
            this.datePickerStartTime.setValue(LocalDate.now());
        });

        this.datePickerEndTime.setOnAction(event -> {
            if(datePickerEndTime.getValue().isBefore(datePickerStartTime.getValue())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date!");
                alert.setHeaderText("End date before Start date!");
                alert.setContentText("Please select a valid date and try again!");
                alert.show();
            }
            this.datePickerEndTime.setValue(LocalDate.now());
        });



        createListeners();
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Appointments");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                AppointmentView app =
                        new AppointmentView(rs.getString("title"),
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
                        rs.getString("lastUpdateBy"),
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
                        rs.getDate("end").toInstant(),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdatedBy"),
                        rs.getString("location"),
                        rs.getDate("start").toInstant(),
                        rs.getString("title"),
                        rs.getString("url")
                );
                appointments.add(new AppointmentProperty(ap));
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

    private void createNewAppointment() {
        isNewAppointment = true;
        // Enable controls and empty them out
        setTextEditable();

        this.btnAppointmentSave.setDisable(true);
        this.btnAppointmentReset.setDisable(true);

        this.gpAppointmentEditor.getChildren().filtered(node -> {
            if (node instanceof TextField) {
                if (node.hashCode() == txtCreatedDate.hashCode()) {
                    ((TextField) node).setEditable(false);
                    return true;
                }
                ((TextField) node).clear();
                return true;
            }
            return false;
        });

        this.txtCustomerName.positionCaret(0);
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
                    if (datePickerStartTime.getValue() != null) {
                        if (choiceBoxStartHour != null && choiceBoxStartMinute != null){
                            if (datePickerEndTime.getValue() != null){
                                if (choiceBoxEndHour != null && choiceBoxEndMinute != null){
                                    this.btnAppointmentSave.setDisable(false);
                                }
                            }
                        }
                    }
                }
            }
        });

        this.tbAppointmentEditor.onMouseClickedProperty().addListener((event)-> {
            if(tbAppointmentEditor.isSelected()){
                tbAppointmentEditor.setText("Enable Read Only");
            }
            else {
                tbAppointmentEditor.setText("Enable Edit Mode");
            }
        });
    }

    // TODO: Implement Update Appointment method
    /**
     * Update appointment
     *
     * @return whether call was successful or not
     */
    private boolean updateAppointment(Connection connection, Appointment app) throws SQLException {
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
        return false;
    }

    private void toggleEditable() {
        if (this.tbAppointmentEditor.isSelected()) {
            setupTextFields(false);
        } else {
            this.gpAppointmentEditor.getChildren().filtered(node -> {
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
        this.gpAppointmentEditor.getChildren().filtered(node -> {
            if (node instanceof TextField) {
                if(!((TextField) node).isEditable())
                    ((TextField) node).setEditable(true);

                if (node.isDisabled())
                    node.setDisable(false);

                return true;
            }
            return false;
        });

//        this.txtCreatedBy.setText(MainApp.user.getUsername());
        this.txtCreatedBy.setEditable(false);
        this.txtCreatedDate.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        this.txtCreatedDate.setEditable(false);
    }

    /**
     * @return
     */
    
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
            alert.setContentText(
                    "The customer you're trying to create an appointment for does not exist! You will now be switched to Customer View to create this customer!");
            alert.getButtonTypes().add(ButtonType.OK);
            alert.showAndWait()
                 .ifPresent(response -> {
                     AppViewController appView = AppViewController.getInstance();
                     Tab customerTab = appView.getTabCustomers();
                     appView.getTpAppPane().getSelectionModel().select(customerTab);
                 });
        }

        LocalDate startDate = datePickerStartTime.getValue();
        LocalTime startTime = LocalTime.of(
                choiceBoxStartHour.getValue().intValue(), choiceBoxStartMinute.getValue().intValue());
        ZonedDateTime startDateTime = ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault());

        LocalDate endDate = datePickerEndTime.getValue();
        LocalTime endTime = LocalTime.of(
                choiceBoxEndHour.getValue().intValue(), choiceBoxEndMinute.getValue().intValue());
        ZonedDateTime endDateTime = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault());

        try {
            if ((startDateTime.getHour() < 8) || (startDateTime.getHour() >= 18)) {
                throw new AppointmentTimeException("The start time is outside of business hours!");
            } else if ((startDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) || (startDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                throw new AppointmentTimeException(
                        "The appointment is not on a business day! Please select an appointment Monday through Friday!");
            }
        }
        catch (AppointmentTimeException ate){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Start Time");
            alert.setContentText(ate.getMessage());
            alert.show();
            return false;
        }

        try {
            if ((endDateTime.getHour() < 8) || (endDateTime.getHour() >= 19)) {
                throw new AppointmentTimeException("The end time is outside of business hours!");
            } else if ((endDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) || (endDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                throw new AppointmentTimeException(
                        "The appointment is not on a business day! Please select an appointment Monday through Friday!");
            }
        }
        catch (AppointmentTimeException ate){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid End Time");
            alert.setContentText(ate.getMessage());
            alert.show();
        }

        Appointment app = new Appointment(
                txtCreatedBy.getText(),
                customerId,
                txtDescription.getText(),
                endDateTime,
                txtLocation.getText(),
                startDateTime,
                txtTitle.getText(),
                txtUrl.getText()
        );

        try (Connection connection = dataSource.getConnection()){
            if (!isNewAppointment){
                return updateAppointment(connection, app);
            }
            else {
                isNewAppointment = false;
                return saveNewAppointment(connection, app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean saveNewAppointment(Connection connection, Appointment app) throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO appointment (customerId, title, description, " +
                                                    "location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy)" +
                                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, app.getCustomerId());
        statement.setString(2, app.getTitle());
        statement.setString(3, app.getDescription());
        statement.setString(4, app.getLocation());
        statement.setString(5, app.getContact());
        statement.setString(6, app.getUrl());
        statement.setTimestamp(7, new Timestamp(app.getStart().toEpochSecond()));
        statement.setTimestamp(8, new Timestamp(app.getEnd().toEpochSecond()));
        statement.setDate(9, new Date(app.getCreateDate().toEpochDay()));
        statement.setString(10, app.getCreatedBy());
        statement.setTimestamp(11, new Timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toEpochSecond()));
        statement.setString(12, app.getLastUpdateBy());

        boolean insertSucceeded = statement.execute();
        if(insertSucceeded){
            PreparedStatement updateAppointments = connection.prepareStatement("SELECT * FROM appointment");
            PreparedStatement updateAppointmentViews = connection.prepareStatement("SELECT * FROM Appointments");

            ResultSet rs = updateAppointments.executeQuery();
            List<AppointmentProperty> appointmentProperties = new ArrayList<>();
            while(rs.next()){
                Appointment appointment = new Appointment(
                        rs.getDate("createDate").toLocalDate(),
                        rs.getInt("appointmentId"),
                        rs.getString("contact"),
                        rs.getString("createdBy"),
                        rs.getInt("customerId"),
                        rs.getString("description"),
                        rs.getTimestamp("end").toInstant(),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdateBy"),
                        rs.getString("location"),
                        rs.getTimestamp("start").toInstant(),
                        rs.getString("title"),
                        rs.getString("url")
                );
                appointmentProperties.add(new AppointmentProperty(appointment));
            }

            appointments = new SortedList<>(FXCollections.observableList(appointmentProperties),
                                            Comparator.comparing(AppointmentProperty::getStart)
                                                      .thenComparing(AppointmentProperty::getCreateDate));

            rs = updateAppointmentViews.executeQuery();
            List<AppointmentViewProperty> appointmentViewProperties = new ArrayList<>();
            while(rs.next()){
                AppointmentView appointmentView = new AppointmentView(
                       rs.getString("title"),
                       rs.getString("description"),
                       rs.getString("location"),
                       rs.getString("contact"),
                       rs.getString("url"),
                       rs.getString("customerName"),
                       rs.getTimestamp("start"),
                       rs.getTimestamp("end"),
                       rs.getDate("createDate"),
                       rs.getString("createdBy"),
                       rs.getTimestamp("lastUpdate")
                );
                appointmentViewProperties.add(new AppointmentViewProperty(appointmentView));
            }
            appointmentViews = new SortedList<>(FXCollections.observableList(appointmentViewProperties),
                                                Comparator.comparing(AppointmentViewProperty::getStart)
                                                          .thenComparing(AppointmentViewProperty::getCreateDate));
        }
        return insertSucceeded;
    }


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

    public DataViewController getDataViewController() {
        return dataViewController;
    }

    public void setDataViewController(DataViewController dataViewController) {
        MainApp.setDataViewController(this.dataViewController);
    }

    private class AppointmentTimeException extends DateTimeException {
        /**
         * Constructs a new date-time exception with the specified message.
         *
         * @param message
         *         the message to use for this exception, may be null
         */
        public AppointmentTimeException(String message) {
            super(message);
        }

        /**
         * Constructs a new date-time exception with the specified message and cause.
         *
         * @param message
         *         the message to use for this exception, may be null
         * @param cause
         *         the cause of the exception, may be null
         */
        public AppointmentTimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
