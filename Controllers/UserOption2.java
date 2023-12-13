package Controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import Classes.CANFrameTransmission;
import Classes.FrameModel;
import helpers.DataBase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class UserOption2 implements Initializable {
	@FXML
    private Label noFrameTypeLabel;
	@FXML
    private Label errorLabel;
    @FXML
    private ComboBox<String> serialPortComboBox;
    @FXML
    private ComboBox<String> frameTypeComboBox;
    @FXML
    private Label noPortLabel;
    @FXML
    private TextField dataTextField;
    @FXML
    private TextField rtrTextField;
    @FXML
    private TextField IDENTIFIERTextField;
    @FXML
    private TextField DLCTextField;
    @FXML
    private TextField frameNameTextField;
    @FXML
    private Button sendButton;
 // Assuming FrameTable is a TableView<FrameModel>
    @FXML
    TableView<FrameModel> frameTable;
    @FXML
    TableColumn<FrameModel, String> indexColumn;
    @FXML
    TableColumn<FrameModel, String> identifierColumn;
    @FXML
    TableColumn<FrameModel, String> dlcColumn;
    @FXML
    TableColumn<FrameModel, String> dataColumn;
    @FXML
    TableColumn<FrameModel, String> frameNameColumn;
    FrameModel Frame = null;
    String query = null;
    Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

    ObservableList<FrameModel> FrametList = FXCollections.observableArrayList();
    
    private String selectedFrameType;
    private String selectedSerialPort;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // Call the method to populate the table with frames
    	loadDate();

    	
        initializeFrameTypeComboBox(frameTypeComboBox, noFrameTypeLabel);
     // Initialize the ComboBox for serial ports
        initializeComboBox(serialPortComboBox, noPortLabel);
        updateSerialPorts() ;
     // Add a listener to dataField to update dlcField on change
        dataTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidBinary(newValue)) {
            	errorLabel.setVisible(false); // hide error message
                String dlc = calculateDLC(newValue);
                DLCTextField.setText(dlc);
            } else {
            	errorLabel.setText("Invalid binary input. Please enter only 0s and 1s.");
                errorLabel.setVisible(true); // Show error message
                // Reset DLC field if input is not a valid binary
            	DLCTextField.clear();
            }
        });
    }


    private void initializeFrameTypeComboBox(ComboBox<String> comboBox, Label noFrameTypeLabel) {
        // Initialize the ComboBox with frame types (Data Frame and Remote Frame)
        ObservableList<String> frameTypeItems = FXCollections.observableArrayList("Data Frame", "Remote Frame");
        comboBox.setItems(frameTypeItems);

        // Set a listener to update the selectedFrameType variable
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedFrameType = newValue;
          //  System.out.println(selectedFrameType);
            noFrameTypeLabel.setVisible(newValue == null);
            if (newValue != null && newValue.equals("Remote Frame")) {
                // If "Remote Frame" is selected, hide the dataTextField
            	dataTextField.setText("0");
                dataTextField.setVisible(false);
                rtrTextField.setText("1");
                DLCTextField.setText("0000");
            } else {
                // If anything else is selected (e.g., "Data Frame"), show the dataTextField
            	dataTextField.setText("01010101");
                dataTextField.setVisible(true);
                rtrTextField.setText("0");
                DLCTextField.setText("0001");
            }
        });

    }
    private void initializeComboBox(ComboBox<String> comboBox, Label noPortLabel) {
        // Get a list of available serial ports using JSSC
        String[] portNames = SerialPortList.getPortNames();

        // Update the ComboBox with the available ports
        ObservableList<String> portItems = FXCollections.observableArrayList(portNames);
        comboBox.setItems(portItems);

        // Set a listener to update the selectedSerialPort variable
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedSerialPort = newValue;
          //  System.out.println(selectedSerialPort);
            noPortLabel.setVisible(newValue == null);
            // You can add any other actions you want to perform when the user selects a port here
        });

        if(portNames.length<=0) {
        	noPortLabel.setVisible(true);
        }
    }
    
    private void updateSerialPorts() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {
                    // Get a list of available serial ports using JSSC
                    String[] portNames = SerialPortList.getPortNames();

                    Platform.runLater(() -> {
                    	initializeComboBox(serialPortComboBox, noPortLabel);
                    });

                    try {
                        Thread.sleep(2000); // Sleep for 2 seconds
                    } catch (InterruptedException e) {
                        // Handle the exception
                    }
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    @FXML
    private void handleSendButtonAction() {
        // Retrieve values from text fields
        String identifier = IDENTIFIERTextField.getText();
        String dlc = DLCTextField.getText();
        String data = dataTextField.getText();
        String frameName = "SEND_"+selectedFrameType;
        // Create an instance of Database
        DataBase database = new DataBase();
        if (selectedSerialPort != null && !selectedSerialPort.isEmpty() && selectedFrameType !=null ) {
        	   errorLabel.setVisible(false); // Show error message
            // Call the insertFrame method
            database.insertFrame(identifier, dlc, data, frameName);
        	SendFromSerial(selectedSerialPort,identifier,dlc,data,selectedFrameType);
         
        } else {

            errorLabel.setText("Verify the chosen port and the frame type.");
            errorLabel.setVisible(true); // Show error message
        }

        loadDate();
        
    }
	void refreshTable() {
		try {
			FrametList.clear();

			query = "SELECT * FROM `can_frames`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				FrametList.add(new FrameModel(resultSet.getInt("index"), resultSet.getString("IDENTIFIER"),
						resultSet.getString("DLC"), resultSet.getString("DATA"), resultSet.getString("Frame_Name")));
	        	frameTable.setItems(FrametList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(UserOption2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        identifierColumn.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        dlcColumn.setCellValueFactory(new PropertyValueFactory<>("dlc"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        frameNameColumn.setCellValueFactory(new PropertyValueFactory<>("frameName"));

		// add cell of button edit

        frameTable.setItems(FrametList);

	}
    private void SendFromSerial(String SelectedportName,String identifier1,String dlc1,String data1,String selectedFrameType1) {
        // Replace "COMx" with the actual port name on your system
        String portName = SelectedportName;

        SerialPort serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
            );

            // Define CAN message data
            String stdId = BinaryToHexadecimal(identifier1);
            String dlc = BinaryToHexadecimal(dlc1);
            String data = BinaryToHexadecimal(data1);
            String rtr;
            if(selectedFrameType1.equals("Remote Frame")) {
            rtr="1";	
            }else {
            rtr="0";
            }

            // Send CAN message
            sendCANMessage(serialPort, stdId, dlc, data,rtr);

            // Wait and check for received data
            int timeout = 4000; // Timeout in milliseconds
            long startTime = System.currentTimeMillis();
         // Create an instance of Database
            DataBase database = new DataBase();
            
            while (System.currentTimeMillis() - startTime < timeout) {
                if (serialPort.getInputBufferBytesCount() > 0) {
                    // Read the received data
                    String receivedData = serialPort.readString();
                    System.out.println("Received: " + receivedData);
                    // Call the insertFrame method
                    
                    database.insertFrame(identifier1,convertDecimalToBinary(receivedData.length()) , DecimalToBinaryConverter(receivedData), "RECEIVED_Data Frame");
                    loadDate();
                    break; // Exit the loop after receiving data
                }

                // Add a small delay to avoid excessive CPU usage
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Close the serial port when done
            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
    private static void sendCANMessage(SerialPort serialPort, String stdId, String dlc, String data,String rtr) throws SerialPortException {
        // Construct the CAN message as a string (adjust as needed)
        String message = rtr+dlc+stdId+data;
        message =FormatCanMessage(message);
        System.out.println("Sending: " + message);

        // Send the message over the serial port
        serialPort.writeString(message);
    }
    
    private String BinaryToHexadecimal(String binaryNumber) {
    	
    	// Convertir la chaîne binaire en nombre BigInteger
        BigInteger decimalNumber = new BigInteger(binaryNumber, 2);

        // Convertir le nombre décimal en représentation hexadécimale
        String hexadecimalString = decimalNumber.toString(16);
        return hexadecimalString;

    }
    private static  String FormatCanMessage(String message) {
        if (message.length() < 8) {
            int paddingLength = 8 - message.length();
            String padding = "0".repeat(paddingLength);
            message =  message+padding ;
        }

        return message;
    	
    }
    private static String convertDecimalToBinary(int decimalNumber) {
        // Utiliser Integer.toBinaryString() pour la conversion
        String BinNum= Integer.toBinaryString(decimalNumber);
        if(BinNum.length()<4) {
        	int paddingLength = 4 - BinNum.length();
        	String padding = "0".repeat(paddingLength);
        	BinNum=padding+BinNum;
        }
        return BinNum;
    }
    private static String DecimalToBinaryConverter(String decimalString) {

    	// Parse the decimal string to an integer
        int decimalNumber = Integer.parseInt(decimalString);
        // Convert the decimal number to binary
        String binaryRepresentation = Integer.toBinaryString(decimalNumber);

        return binaryRepresentation;
    }
    public static String calculateDLC(String data) {
        // Calculate the number of bytes needed to represent the binary data
        int bytesNeeded = (int) Math.ceil((double) data.length() / 8);

        // Convert the number of bytes to a 4-bit binary string (DLC)
        String dlcBinary = String.format("%04d", Integer.parseInt(Integer.toBinaryString(bytesNeeded)));

        return dlcBinary;
    }
    private boolean isValidBinary(String input) {
        // Check if the input consists only of binary digits (0s and 1s)
        return input.matches("[01]*");
    }
    @FXML
    private void showChart() {
    	handleSendButtonAction();
        // Create an instance of CANFrameTransmission
        CANFrameTransmission chart = new CANFrameTransmission();

        // Create a new stage for the chart
        Stage chartStage = new Stage();

        // Call the initialize method to set up the chart
        chart.initialize(chartStage);

        // Set the chart stage size
        chartStage.setWidth(800);
        chartStage.setHeight(600);

        // Show the chart stage
        chartStage.show();
    }
}
