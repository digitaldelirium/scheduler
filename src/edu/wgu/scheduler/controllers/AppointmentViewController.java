package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import edu.wgu.scheduler.models.AppointmentView;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static edu.wgu.scheduler.MainApp.*;
import static edu.wgu.scheduler.controllers.AppViewController.toggleTextFields;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:20.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentViewController extends AnchorPane {
    private VBox vbAppointmentEditor;
    private HBox hbEditorBar;
    private ToggleButton tbAppointmentEditor;
    private Button btnNewAppointment;
    private GridPane gpAppointmentEditor;
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
    private Label lblViewScope = new Label("Choose View:");
    private HBox hbViewScope;
    private Button btnAppointmentReset;
    private TableView<AppointmentView> tvAppointments = new TableView<>();
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
    private boolean isNewAppointment;
    private DataViewController dataViewController;
    private static Parent dataView;
    private static AppointmentViewController instance;
    protected static ObservableList<ICustomer> customers;
    protected static ObservableList<Appointment> appointments;
    protected static ObservableList<AppointmentView> appointmentViews;
    protected static ObservableList<Reminder> reminders;
    public AnchorPane apAppointmentView;
    private ListView<AppointmentView> lvListView;

    private AppointmentViewController() {
        initialize();
    }

    public static AppointmentViewController getInstance() {
        if(instance == null){
            new AppointmentViewController();
        }
        return instance;
    }

    public void initialize() {
        instance = this;
        this.apAppointmentView = new AnchorPane();
        this.apAppointmentView.setPrefHeight(580.0);
        this.vbAppointmentEditor = new VBox();
        this.vbAppointmentEditor.setPrefWidth(985.0);
        this.vbAppointmentEditor.setOpaqueInsets(new Insets(10.0));
        this.vbAppointmentEditor.setPadding(new Insets(5.0));
        this.dataViewController = new DataViewController();
        this.dataView = dataViewController.tabPane;
        this.lblViewScope = new Label("Choose View:");

        setupHBoxes();
        setupGridPane();

        this.vbAppointmentEditor.getChildren().addAll(
                this.lblViewScope,
                this.hbViewScope,
                this.hbEditorBar,
                this.gpAppointmentEditor
//                this.dataViewController.tabPane
        );

        this.apAppointmentView.getChildren().add(vbAppointmentEditor);

        setupCollections();
        setupDataView();
        getAppointments();
        setupEventHandlers();
        setupTextFields(true);



        this.tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.tcTitle.setVisible(true);
        this.tcTitle.setMinWidth(50);
        
        this.tcCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.tcCustomerName.setVisible(true);
        this.tcCustomerName.setMinWidth(40);
        
        this.tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.tcDescription.setVisible(true);
        this.tcDescription.setMinWidth(100);
        
        this.tcLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        this.tcLocation.setVisible(true);
        this.tcLocation.setMinWidth(40);

        this.tcContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        this.tcContact.setVisible(true);
        this.tcContact.setMinWidth(40);

        this.tcUrl.setCellValueFactory(new PropertyValueFactory<>("url".toUpperCase()));
        this.tcUrl.setVisible(true);
        this.tcUrl.setMinWidth(40);

        this.tcStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.tcStart.setVisible(true);
        this.tcStart.setMinWidth(40);

        this.tcEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.tcEnd.setVisible(true);
        this.tcEnd.setMinWidth(40);

        this.tcCreateDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        this.tcCreateDate.setVisible(true);
        this.tcCreateDate.setMinWidth(40);

        this.tcCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        this.tcCreatedBy.setVisible(true);
        this.tcCreatedBy.setMinWidth(40);

        this.tcLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        this.tcLastUpdate.setVisible(true);
        this.tcLastUpdate.setMinWidth(40);
        
        this.tvAppointments = new TableView<>();
        this.tvAppointments.setItems(appointmentViews);
        this.tvAppointments.getColumns().addAll(
                tcTitle,
                tcDescription,
                tcLocation,
                tcContact,
                tcUrl,
                tcCustomerName,
                tcStart,
                tcEnd,
                tcCreateDate,
                tcCreatedBy,
                tcLastUpdate);


        this.lvListView = new ListView<>();
        this.lvListView.setItems(appointmentViews);

        this.dataViewController.setTableView(this.tvAppointments);
        this.dataViewController.setLblListView(new Label("Appointment List"));
        this.dataViewController.setListView(this.lvListView);
        this.dataViewController.setLblTableView(new Label("Appointments"));

//        System.out.println(this.dataViewController);

    }

    private void setupHBoxes() {

        this.hbEditorBar = new HBox();
        this.hbViewScope = new HBox();
        this.hbStartTime = new HBox();
        this.hbEndTime = new HBox();

        this.btnNewAppointment = new Button("New Appointment");
        this.tbAppointmentEditor = new ToggleButton("Edit Appointment");
        RadioButton rdoWeekly = new RadioButton("Weekly");
        RadioButton rdoMonthly = new RadioButton("Monthly");

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
        LinkedList<Integer> hourList = new LinkedList<>();
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
        Label lblTitle = new Label("Title:");
        Label lblDescription = new Label("Description:");
        Label lblLocation = new Label("Location:");
        Label lblContact = new Label("Contact:");
        Label lblUrl = new Label("Url:");
        Label lblCustomerName = new Label("Customer Name:");
        Label lblStart = new Label("Start Time:");
        Label lblEnd = new Label("End Time:");
        Label lblCreatedDate = new Label("Created Date:");
        Label lblCreatedBy = new Label("Created By:");
        Label lblLastUpdated = new Label("Last Updated:");
        this.txtTitle = new TextField();
        this.txtDescription = new TextField();
        this.txtLocation = new TextField();
        this.txtContact = new TextField();
        this.txtUrl = new TextField();
        this.txtCustomerName = new TextField();
        this.txtCreatedDate = new TextField();
        this.txtCreatedBy = new TextField();
        this.txtLastUpdated = new TextField();
        ButtonBar buttonbarAppointmentEditor = new ButtonBar();
        this.btnAppointmentSave = new Button("Save");
        this.btnAppointmentReset = new Button("Reset");

        lblTitle.setLabelFor(this.txtTitle);
        lblDescription.setLabelFor(this.txtDescription);
        lblLocation.setLabelFor(this.txtLocation);
        lblContact.setLabelFor(this.txtContact);
        lblUrl.setLabelFor(this.txtUrl);
        lblCustomerName.setLabelFor(this.txtCustomerName);
        lblStart.setLabelFor(this.hbStartTime);
        lblEnd.setLabelFor(this.hbEndTime);
        lblCreatedDate.setLabelFor(this.txtCreatedDate);
        lblCreatedBy.setLabelFor(this.txtCreatedBy);
        lblLastUpdated.setLabelFor(this.txtLastUpdated);

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
            if(toggleTextFields(node, disabled)) {
                if (node.hashCode() == txtLastUpdated.hashCode()) {
                    ((TextField) node).setEditable(false);
                    return true;
                }
                return true;
            }
            return false;
        });
    }

    private void setupCollections() {
        appointments = FXCollections.observableList(new LinkedList<>(), (Appointment ap) -> {
            return new Observable[]{
                    ap.titleProperty(),
                    ap.descriptionProperty(),
                    ap.locationProperty(),
                    ap.contactProperty(),
                    ap.urlProperty(),
                    ap.startProperty(),
                    ap.endProperty(),
                    ap.customerIdProperty()
            };
        });

        reminders = FXCollections.observableList(new LinkedList<>(), re -> new Observable[]{
                re.remindercolProperty(),
                re.reminderDateProperty(),
                re.snoozeIncrementProperty(),
                re.snoozeIncrementTypeIdProperty()
        });



    }

    private void setupDataView() {
        List<Appointment> appointmentList = new LinkedList<>();
        List<Reminder> reminderList = new LinkedList<>();
        List<AppointmentView> appointmentViewList = new LinkedList<>();

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from appointment");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Appointment property = new Appointment(
                                rs.getTimestamp("createDate"),
                                rs.getInt("appointmentId"),
                                rs.getString("contact"),
                                rs.getString("createdBy"),
                                rs.getInt("customerId"),
                                rs.getString("description"),
                                rs.getTimestamp("end"),
                                rs.getTimestamp("lastUpdate"),
                                rs.getString("lastUpdateBy"),
                                rs.getString("location"),
                                rs.getTimestamp("start"),
                                rs.getString("title"),
                                rs.getString("url")
                );
                appointmentList.add(property);
            }
            appointments = FXCollections.observableList(appointmentList);

            statement = connection.prepareStatement("SELECT * FROM reminder");
            rs = statement.executeQuery();
            while (rs.next()){
                Reminder reminderProperty = new Reminder(
                        rs.getInt("reminderId"),
                        rs.getInt("appointmentId"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("reminderDate"),
                        rs.getString("remindercol"),
                        rs.getInt("snoozeIncrement"),
                        rs.getInt("snoozeIncrementTypeId")
                );
                reminderList.add(reminderProperty);
            }
            reminders = FXCollections.observableList(reminderList);

            statement = connection.prepareStatement("SELECT * from Appointments");
            rs = statement.executeQuery();
            while (rs.next()){
                AppointmentView avp = new AppointmentView(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("url"),
                        rs.getString("customerName"),
                        rs.getTimestamp("start"),
                        rs.getTimestamp("end"),
                        rs.getTimestamp("createDate"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate")
                );
                appointmentViewList.add(avp);
            }
            appointmentViews = FXCollections.observableList(appointmentViewList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupEventHandlers(){

        this.tbAppointmentEditor.setOnAction(event -> toggleEditable());
        this.btnNewAppointment.setOnAction(event -> createNewAppointment());
        this.btnAppointmentSave.setOnAction(event -> {
            try {
                saveAppointment();
            } catch (InvalidCustomerException ice){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Customer Exception");
                alert.setContentText(ice.getMessage());
                alert.showAndWait().ifPresent(buttonType -> {
                    Platform.runLater(() -> txtCustomerName.requestFocus());
                    CustomerViewController customerViewController = CustomerViewController.getInstance();
                    customerViewController.setTxtCustomerName(new TextField(txtCustomerName.getText()));
                    AppViewController appViewController = AppViewController.getInstance();
                    appViewController.getTpAppPane().getSelectionModel().select(appViewController.getTabCustomers());
                });
            }

        });
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
            else if (selectedDate.isAfter(LocalDate.now())){
                this.datePickerStartTime.setValue(selectedDate);
                return;
            }
            this.datePickerStartTime.setValue(LocalDate.now());
        });

        this.datePickerEndTime.setOnAction(event -> {
            LocalDate selectedDate = datePickerEndTime.getValue();
            if(selectedDate.isBefore(datePickerStartTime.getValue())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date!");
                alert.setHeaderText("End date before Start date!");
                alert.setContentText("Please select a valid date and try again!");
                alert.show();
            }
            else if(selectedDate.isAfter(datePickerStartTime.getValue()) | selectedDate.equals(datePickerStartTime.getValue())){
                this.datePickerEndTime.setValue(selectedDate);
                return;
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
        customers = FXCollections.observableArrayList();
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
                                          rs.getTimestamp("createDate"),
                                          rs.getString("createdBy"),
                                          rs.getTimestamp("lastUpdate"));
                appointmentViews.add(app);
            }

            statement = connection.prepareStatement("SELECT * FROM customer");
            rs = statement.executeQuery();
            while(rs.next()){
                Customer cu = new Customer(
                        rs.getTimestamp("createDate"),
                        rs.getInt("customerId"),
                        rs.getByte("active"),
                        rs.getInt("addressId"),
                        rs.getString("createdBy"),
                        rs.getString("customerName"),
                        rs.getString("lastUpdateBy"),
                        rs.getTimestamp("lastUpdate")
                );
                customers.add(cu);
            }

            statement = connection.prepareStatement("SELECT * FROM appointment");
            rs = statement.executeQuery();
            while (rs.next()){
                Appointment appointment = new Appointment(
                        rs.getTimestamp("createDate"),
                        rs.getInt("appointmentId"),
                        rs.getString("contact"),
                        rs.getString("createdBy"),
                        rs.getInt("customerId"),
                        rs.getString("description"),
                        rs.getTimestamp("end"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdateBy"),
                        rs.getString("location"),
                        rs.getTimestamp("start"),
                        rs.getString("title"),
                        rs.getString("url")
                );
                appointments.add(appointment);
            }

            statement = connection.prepareStatement("SELECT * FROM reminder");
            rs = statement.executeQuery();
            while (rs.next()){
                Reminder re = new Reminder(
                        rs.getInt("reminderId"),
                        rs.getInt("appointmentId"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("reminderDate"),
                        rs.getString("remindercol"),
                        rs.getInt("snoozeIncrement"),
                        rs.getInt("snoozeIncrementTypeId"));
                reminders.add(re);
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
            toggleTextFields(node, false);
            if (node.hashCode() == txtCreatedDate.hashCode()) {
                    ((TextField) node).setEditable(false);
                    return true;
            }
            else if (node.hashCode() == txtLastUpdated.hashCode()){
                ((TextField) node).setEditable(false);
                node.setDisable(true);
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
            if (newValue.trim().length() < 10 | txtCustomerName.getText().trim().length() < 5) {
                btnAppointmentSave.setDisable(true);
            } else {
                btnAppointmentSave.setDisable(false);
                btnAppointmentReset.setDisable(false);
            }
        });

        txtTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().length() < 4 | txtCustomerName.getText().trim().length() < 5) {
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
            statement.setTimestamp(6, new Timestamp(app.getStart().toEpochSecond() * 1000));
            statement.setString(7, user.getUsername());
            statement.setTimestamp(8, new Timestamp(app.getEnd().toEpochSecond() * 1000));
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
        this.gpAppointmentEditor.getChildren().filtered(node -> toggleTextFields(node, false));

        this.txtCreatedBy.setText(MainApp.user.getUsername());
        this.txtCreatedBy.setEditable(false);
        this.txtCreatedDate.setText(LocalDate.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_DATE));
        this.txtCreatedDate.setEditable(false);
        this.txtLastUpdated.setDisable(true);
        this.txtLastUpdated.setEditable(false);
    }

    /**
     * @return
     */
    
    private boolean saveAppointment() throws InvalidCustomerException {
        int customerId = 0;
        int x = 0;
        for (ICustomer customer : customers) {
            if (customer.getCustomerName().toLowerCase().equals(txtCustomerName.getText().toLowerCase().trim())) {
                customerId = customer.getCustomerId();
                break;
            }
            else if (txtCustomerName.getText().trim().isEmpty()){
                throw new InvalidCustomerException("The customer name cannot be blank!");
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
                txtUrl.getText(),
                txtContact.getText()
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
        ZonedDateTime startTime = app.getStart().withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endTime = app.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO appointment (customerId, title, description, " +
                                                    "location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, UTC_DATE, ?, UTC_TIMESTAMP, ?);");
        statement.setInt(1, app.getCustomerId());
        statement.setString(2, app.getTitle());
        statement.setString(3, app.getDescription());
        statement.setString(4, app.getLocation());
        statement.setString(5, app.getContact());
        statement.setString(6, app.getUrl());
        statement.setTimestamp(7, Timestamp.from(startTime.toInstant()));
        statement.setTimestamp(8, Timestamp.from(endTime.toInstant()));
        statement.setString(9, app.getCreatedBy());
        statement.setString(10, app.getLastUpdateBy());

        // Since execute can return boolean (true if it has ResultSet, expect false)
        boolean failed = statement.execute();
        System.out.println(statement.getUpdateCount());
        if(!failed){
            statement = connection.prepareStatement("SELECT * FROM appointment " +
                                                            "WHERE customerId = ? AND title = ? AND start = ? AND end = ?" +
                                                            "AND url = ? AND description = ?");
            statement.setInt(1, app.getCustomerId());
            statement.setString(2, app.getTitle());
            statement.setTimestamp(3, Timestamp.from(startTime.toInstant()));
            statement.setTimestamp(4, Timestamp.from(endTime.toInstant()));
            statement.setString(5, app.getUrl());
            statement.setString(6, app.getDescription());
            ResultSet executeQuery = statement.executeQuery();

            if (executeQuery.next()){
                app = new Appointment(
                        executeQuery.getTimestamp("createDate"),
                        executeQuery.getInt("appointmentId"),
                        executeQuery.getString("contact"),
                        executeQuery.getString("createdBy"),
                        executeQuery.getInt("customerId"),
                        executeQuery.getString("description"),
                        executeQuery.getTimestamp("end"),
                        executeQuery.getTimestamp("lastUpdate"),
                        executeQuery.getString("lastUpdateBy"),
                        executeQuery.getString("location"),
                        executeQuery.getTimestamp("start"),
                        executeQuery.getString("title"),
                        executeQuery.getString("url")
                );
            }
            boolean reminderSaved = saveNewReminder(connection, app);
            PreparedStatement updateAppointments = connection.prepareStatement("SELECT * FROM appointment");
            PreparedStatement updateAppointmentViews = connection.prepareStatement("SELECT * FROM Appointments");

            ResultSet rs = updateAppointments.executeQuery();
            List<Appointment> appointmentProperties = new ArrayList<>();
            while(rs.next()){
                Appointment appointment = new Appointment(
                        rs.getTimestamp("createDate"),
                        rs.getInt("appointmentId"),
                        rs.getString("contact"),
                        rs.getString("createdBy"),
                        rs.getInt("customerId"),
                        rs.getString("description"),
                        rs.getTimestamp("end"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdateBy"),
                        rs.getString("location"),
                        rs.getTimestamp("start"),
                        rs.getString("title"),
                        rs.getString("url")
                );
                appointmentProperties.add(appointment);
            }
            updateAppointments.close();

            appointments = new SortedList<>(
                    FXCollections.observableList(appointmentProperties),
                    Comparator.comparing(Appointment::getStart)
                              .thenComparing(Appointment::getCreateDate)
            );

            rs = updateAppointmentViews.executeQuery();
            List<AppointmentView> appointmentViewProperties = new ArrayList<>();
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
                       rs.getTimestamp("createDate"),
                       rs.getString("createdBy"),
                       rs.getTimestamp("lastUpdate")
                );
                appointmentViewProperties.add(appointmentView);
            }
            updateAppointmentViews.close();
            appointmentViews = new SortedList<>(FXCollections.observableList(appointmentViewProperties),
                                                                Comparator.comparing(AppointmentView::getStart)
                                                          .thenComparing(AppointmentView::getCreateDate));
        }
        // since a successful insert results in a false statement, we need to return the opposite.
        return !failed;
    }

    private boolean saveNewReminder(Connection connection, Appointment app) throws SQLException {
        ZonedDateTime reminderTime = app.getStart().minusMinutes(15);
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO reminder (reminderDate, snoozeIncrement, snoozeIncrementTypeId, appointmentId, createdBy, createdDate, remindercol) \n" +
                                                    "VALUES (?, ?, ?, ?, ?, UTC_DATE, ?);");
        statement.setTimestamp(1, new Timestamp(reminderTime.toInstant().toEpochMilli()));
        statement.setInt(2, 5);
        statement.setInt(3, 1);
        statement.setInt(4, app.getAppointmentId());
        statement.setString(5, app.getCreatedBy());
        statement.setString(6, "Reminder Text");
        boolean failedInsert = statement.execute();
        if(failedInsert){
            throw new SQLException("Failed to insert reminder! Received ResultSet instead");
        }
        return !failedInsert;
    }


    private void clearForm() {
        this.gpAppointmentEditor.getChildren().filtered(node -> {
            if (node instanceof TextField) {
                ((TextField) node).clear();
                node.setDisable(true);
                return true;
            }
            else if (node instanceof ChoiceBox){
                ((ChoiceBox) node).setDisable(true);
                return true;
            }
            return false;
        });

        this.btnAppointmentSave.setDisable(true);
        this.btnAppointmentReset.setDisable(true);
        isNewAppointment = false;
    }

    public DataViewController getDataViewController() {
        return dataViewController;
    }

    public static Parent getDataView() {
        return dataView;
    }

    public static void setDataView(Parent dataView) {
        AppointmentViewController.dataView = dataView;
    }

    public GridPane getGpAppointmentEditor() {
        return gpAppointmentEditor;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AppointmentViewController{")
                .append("\nvbAppointmentEditor=")
                .append(vbAppointmentEditor)
                .append(",\n hbEditorBar=")
                .append(hbEditorBar)
                .append(",\n tbAppointmentEditor=")
                .append(tbAppointmentEditor)
                .append(",\n btnNewAppointment=")
                .append(btnNewAppointment)
                .append(",\n gpAppointmentEditor=")
                .append(gpAppointmentEditor)
                .append(",\n txtTitle=")
                .append(txtTitle)
                .append(",\n txtDescription=")
                .append(txtDescription)
                .append(",\n txtLocation=")
                .append(txtLocation)
                .append(",\n txtContact=")
                .append(txtContact)
                .append(",\n txtUrl=")
                .append(txtUrl)
                .append(",\n txtCustomerName=")
                .append(txtCustomerName)
                .append(",\n hbEndTime=")
                .append(hbEndTime)
                .append(",\n datePickerStartTime=")
                .append(datePickerStartTime)
                .append(",\n choiceBoxStartHour=")
                .append(choiceBoxStartHour)
                .append(",\n choiceBoxStartMinute=")
                .append(choiceBoxStartMinute)
                .append(",\n hbStartTime=")
                .append(hbStartTime)
                .append(",\n datePickerEndTime=")
                .append(datePickerEndTime)
                .append(",\n choiceBoxEndHour=")
                .append(choiceBoxEndHour)
                .append(",\n choiceBoxEndMinute=")
                .append(choiceBoxEndMinute)
                .append(",\n txtCreatedDate=")
                .append(txtCreatedDate)
                .append(",\n txtCreatedBy=")
                .append(txtCreatedBy)
                .append(",\n txtLastUpdated=")
                .append(txtLastUpdated)
                .append(",\n btnAppointmentSave=")
                .append(btnAppointmentSave)
                .append(",\n lblViewScope=")
                .append(lblViewScope)
                .append(",\n hbViewScope=")
                .append(hbViewScope)
                .append(",\n btnAppointmentReset=")
                .append(btnAppointmentReset)
                .append(",\n tvAppointments=")
                .append(tvAppointments.getColumns() + "\n" + tvAppointments.getItems())
                .append(",\n tcTitle=")
                .append(tcTitle)
                .append(",\n tcDescription=")
                .append(tcDescription)
                .append(",\n tcLocation=")
                .append(tcLocation)
                .append(",\n tcContact=")
                .append(tcContact)
                .append(",\n tcUrl=")
                .append(tcUrl)
                .append(",\n tcCustomerName=")
                .append(tcCustomerName)
                .append(",\n tcStart=")
                .append(tcStart)
                .append(",\n tcEnd=")
                .append(tcEnd)
                .append(",\n tcCreateDate=")
                .append(tcCreateDate)
                .append(",\n tcCreatedBy=")
                .append(tcCreatedBy)
                .append(",\n tcLastUpdate=")
                .append(tcLastUpdate)
                .append(",\n isNewAppointment=")
                .append(isNewAppointment)
                .append(",\n dataViewController=")
                .append(dataViewController)
                .append(",\n apAppointmentView=")
                .append(apAppointmentView)
                .append(",\n lvListView=")
                .append(lvListView)
                .append("\n}")
                .toString();
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

    private class InvalidCustomerException extends RuntimeException {
        public InvalidCustomerException(String message) {
            super(message);
        }
    }
}
