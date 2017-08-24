package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static edu.wgu.scheduler.MainApp.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:21.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewController implements Initializable {
    @FXML
    AnchorPane apCustomerView;
    @FXML
    private VBox vbCustomerEditor;
    @FXML
    private ToggleButton tbCustomerEditMode;
    @FXML
    private GridPane gpCustomerEditor;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAddress2;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblState;
    @FXML
    private Label lblPostalCode;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblCustomerSince;
    @FXML
    private Label lblCustomerCreatedDate;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblPrefix;
    @FXML
    private ButtonBar btnbarCustomerEditor;
    @FXML
    private Button btnCustomerOk;
    @FXML
    private Button btnCustomerCancel;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtPostalCode;
    @FXML
    private TextField txtCountry;
    @FXML
    private TextField txtPhone;
    @FXML
    private HBox hbPhone;
    @FXML
    private CheckBox cbActive;
    private MainApp mainApp;
    private TableView<ICustomerView> tvCustomerView;
    private TableColumn<ICustomerView, String> tcCustomerName = new TableColumn<>("Customer Name");
    private TableColumn<ICustomerView, String> tcAddress = new TableColumn<>("Address");
    private TableColumn<ICustomerView, String> tcAddress2 = new TableColumn<>("Address 2");
    private TableColumn<ICustomerView, String> tcCity = new TableColumn<>("City");
    private TableColumn<ICustomerView, String> tcPostalCode = new TableColumn<>("Postal Code");
    private TableColumn<ICustomerView, String> tcCountry = new TableColumn<>("Country");
    private TableColumn<ICustomerView, String> tcPhone = new TableColumn<>("Phone");
    private TableColumn<ICustomerView, Byte> tcActive = new TableColumn<>("Active");
    private ObservableMap<ZonedDateTime, ICustomerView> omCustomerView;
    private DataViewController dataViewController;

    public CustomerViewController() {
        dataViewController = new DataViewController();
        initialize(MainApp.class.getResource("/fxml/CustomerView.fxml"), null);
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
        this.apCustomerView = new AnchorPane();
        this.vbCustomerEditor = new VBox();
        this.tbCustomerEditMode = new ToggleButton();
        this.tbCustomerEditMode.setSelected(false);

        setupGridPane();

        // Add gpCustomerEditor and add it to vbCustomerEditor and apCustomerEditor to complete view
        this.vbCustomerEditor.getChildren().addAll(tbCustomerEditMode, gpCustomerEditor);
        this.apCustomerView.getChildren().add(vbCustomerEditor);

        setupDataView();
    }

    private void setupGridPane() {
        this.gpCustomerEditor = new GridPane();
        this.lblCustomerName = new Label();
        this.lblAddress = new Label();
        this.lblAddress2 = new Label();
        this.lblCity = new Label();
        this.lblState = new Label();
        this.lblPostalCode = new Label();
        this.lblCountry = new Label();
        this.lblPhone = new Label();
        this.lblCustomerSince = new Label();
        this.lblCustomerCreatedDate = new Label();
        this.cbActive = new CheckBox();
        this.cbActive.setSelected(false);
        this.lblPrefix = new Label();
        this.txtCustomerName = new TextField();
        this.txtAddress = new TextField();
        this.txtAddress2 = new TextField();
        this.txtCity = new TextField();
        this.txtState = new TextField();
        this.txtPostalCode = new TextField();
        this.txtCountry = new TextField();
        this.txtPhone = new TextField();
        this.hbPhone = new HBox(5.0, lblPrefix, txtPhone);
        this.btnbarCustomerEditor = new ButtonBar();
        this.btnCustomerCancel = new Button();
        this.btnCustomerCancel.setCancelButton(true);
        this.btnCustomerOk = new Button();
        this.btnCustomerOk.setDefaultButton(true);

        this.lblCustomerName.setLabelFor(txtCustomerName);
        this.lblAddress.setLabelFor(txtAddress);
        this.lblAddress2.setLabelFor(txtAddress2);
        this.lblCity.setLabelFor(txtCity);
        this.lblState.setLabelFor(txtState);
        this.lblPostalCode.setLabelFor(txtPostalCode);
        this.lblCountry.setLabelFor(txtCountry);
        this.lblPhone.setLabelFor(hbPhone);
        this.lblPrefix.setLabelFor(txtPhone);
        this.lblCustomerSince.setLabelFor(lblCustomerCreatedDate);

        // Setup child containers of gpCustomerEditor
        this.btnbarCustomerEditor.getButtons().addAll(btnCustomerOk, btnCustomerCancel);

        this.gpCustomerEditor.add(lblCustomerName, 0, 0);
        this.gpCustomerEditor.add(txtCustomerName, 1, 0);
        this.gpCustomerEditor.add(lblAddress, 0, 1);
        this.gpCustomerEditor.add(txtAddress, 1, 1);
        this.gpCustomerEditor.add(lblAddress2, 0, 2);
        this.gpCustomerEditor.add(txtAddress2, 1, 2);
        this.gpCustomerEditor.add(lblCity, 0, 3);
        this.gpCustomerEditor.add(txtCity, 1, 3);
        this.gpCustomerEditor.add(lblState, 0, 4);
        this.gpCustomerEditor.add(txtState, 1, 4);
        this.gpCustomerEditor.add(lblPostalCode, 0, 5);
        this.gpCustomerEditor.add(txtPostalCode, 1, 5);
        this.gpCustomerEditor.add(lblCountry, 0, 6);
        this.gpCustomerEditor.add(txtCountry, 1, 6);
        this.gpCustomerEditor.add(lblPhone, 0, 7);
        this.gpCustomerEditor.add(hbPhone, 1, 7);
        this.gpCustomerEditor.add(lblCustomerSince, 0, 8);
        this.gpCustomerEditor.add(lblCustomerCreatedDate, 1, 8);
        this.gpCustomerEditor.add(cbActive, 1, 9);
        this.gpCustomerEditor.add(btnbarCustomerEditor, 1, 10);
    }

    private void setupDataView() {
        dataViewController.setLblListView(new Label("Customer List View"));
        dataViewController.setLblTableView(new Label("Customer Table View"));
        dataViewController.setListView(new ListView<ICustomerView>());

        try {
            getCustomerData();
        } catch (SQLException e) {
            System.out.println("Could not get customer data due to SQL error!");
            e.printStackTrace();
        }

        try {
            getAddressData();
        }
        catch (SQLException e){
            System.out.println("Could not obtain address data!");
            e.getLocalizedMessage();
        }

        try {
            getCityData();
        }
        catch (SQLException e){
            System.out.println("Could not get city data!");
            e.getLocalizedMessage();
        }

        try {
            getCountryData();
        }
        catch (SQLException e){
            System.out.println("Could not get country data!");
            e.getLocalizedMessage();
        }

        try {
            getCustomerViewData();
        }
        catch (SQLException e) {
            System.out.println("Could not get customer view information");
            e.getLocalizedMessage();
        }

        this.tvCustomerView = new TableView<>();
        this.tvCustomerView.setItems(getCustomerList());
        tvCustomerView.getColumns().addAll(
                tcCustomerName,
                tcAddress,
                tcAddress2,
                tcCity,
                tcPostalCode,
                tcCountry,
                tcPhone,
                tcActive
        );

        dataViewController.setTableView(this.tvCustomerView);
    }

    private void getCustomerViewData() throws SQLException {
        List<ICustomerView> customerViews = new LinkedList<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from Customers");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Boolean active;

                switch(rs.getByte(8)){
                    case 0:
                        active = false;
                        break;
                    default:
                        active = true;
                        break;
                }

                CustomerViewProperty view = new CustomerViewProperty(
                        new CustomerView(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        active,
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        ZonedDateTime.ofInstant(rs.getTimestamp(12).toInstant(), ZoneId.systemDefault()))
                );

                customerViews.add(view);
            }
            // Populate MainApp collection customerList
            setCustomerList(FXCollections.observableList(customerViews));
        }
    }

    private void getCountryData() throws  SQLException {
        HashMap<Integer, ICountry> countryHashMap = new HashMap<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM country");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                CountryProperty country = new CountryProperty(
                    rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toInstant(),
                        rs.getTimestamp(5),
                        rs.getString(6)
                );

                countryHashMap.put(country.getCountryId(), country);
            }
            setCountries(FXCollections.observableMap(countryHashMap));
        }
    }

    private void getCityData() throws SQLException {
        HashMap<Integer, ICity> cityHashMap = new HashMap<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM city");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                CityProperty city = new CityProperty(
                        rs.getInt("cityId"),
                        rs.getString("city"),
                        rs.getInt("countryId"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdatedBy"),
                        ZonedDateTime.ofInstant(rs.getDate("createDate").toInstant(), ZoneId.systemDefault())
                );

                cityHashMap.put(city.getCityId(), city);
            }
            setCities(FXCollections.observableMap(cityHashMap));
        }

    }

    private void getAddressData() throws SQLException {
        HashMap<Integer, IAddress> addressHashMap = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM address");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                AddressProperty address = new AddressProperty(
                        rs.getInt("cityId"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getString("postalCode"),
                        rs.getString("phone"),
                        rs.getString("createdBy")
                );
                addressHashMap.put(address.getAddressId(), address);
            }
            setAddresses(addressHashMap);

        }
    }

    private void getCustomerData() throws SQLException {
        HashMap<Integer, ICustomer> customerHashMap = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from customer");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                CustomerProperty customer = new CustomerProperty(
                        resultSet.getDate("createDate").toLocalDate(),
                        resultSet.getInt("customerId"),
                        resultSet.getByte("active"),
                        resultSet.getInt("addressId"),
                        resultSet.getString("createdBy"),
                        resultSet.getString("customerName"),
                        resultSet.getString("lastUpdateBy"),
                        resultSet.getTimestamp("lastUpdate")
                );

                customerHashMap.put(resultSet.getInt("customerId"), customer);
            }
            setCustomers(customerHashMap);
        }
    }

    public Label getLblPrefix() {
        return lblPrefix;
    }

    public void setLblPrefix(Label lblPrefix) {
        this.lblPrefix = lblPrefix;
    }

    public TextField getTxtCustomerName() {
        return txtCustomerName;
    }

    public void setTxtCustomerName(TextField txtCustomerName) {
        this.txtCustomerName = txtCustomerName;
    }

    public TextField getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(TextField txtAddress) {
        this.txtAddress = txtAddress;
    }

    public TextField getTxtAddress2() {
        return txtAddress2;
    }

    public void setTxtAddress2(TextField txtAddress2) {
        this.txtAddress2 = txtAddress2;
    }

    public TextField getTxtCity() {
        return txtCity;
    }

    public void setTxtCity(TextField txtCity) {
        this.txtCity = txtCity;
    }

    public TextField getTxtState() {
        return txtState;
    }

    public void setTxtState(TextField txtState) {
        this.txtState = txtState;
    }

    public TextField getTxtPostalCode() {
        return txtPostalCode;
    }

    public void setTxtPostalCode(TextField txtPostalCode) {
        this.txtPostalCode = txtPostalCode;
    }

    public TextField getTxtCountry() {
        return txtCountry;
    }

    public void setTxtCountry(TextField txtCountry) {
        this.txtCountry = txtCountry;
    }

    public TextField getTxtPhone() {
        return txtPhone;
    }

    public void setTxtPhone(TextField txtPhone) {
        this.txtPhone = txtPhone;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
