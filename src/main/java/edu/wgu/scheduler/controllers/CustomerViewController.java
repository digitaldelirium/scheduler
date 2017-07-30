package edu.wgu.scheduler.controllers;

import com.sun.corba.se.impl.orb.ParserTable;
import com.sun.org.apache.xml.internal.security.Init;
import edu.wgu.scheduler.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:21.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewController implements Initializable {
    @FXML
    private AnchorPane apCustomerView;
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
        this.gpCustomerEditor = new GridPane();
        this.tbCustomerEditMode = new ToggleButton();
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
        this.btnCustomerOk = new Button();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
