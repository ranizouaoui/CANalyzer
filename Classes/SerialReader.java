package Classes;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialReader {
    public static void main(String[] args) {
        // Replace "COMx" with the actual port name on your system
        String portName = "COM13";

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
            int stdId = 146;
            int dlc = 2;
            int data = 50;

            // Send CAN message
            sendCANMessage(serialPort, stdId, dlc, data);

            // Wait and check for received data
            int timeout = 5000; // Timeout in milliseconds
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < timeout) {
                if (serialPort.getInputBufferBytesCount() > 0) {
                    // Read the received data
                    String receivedData = serialPort.readString();
                    System.out.println("Received: " + receivedData);
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

    private static void sendCANMessage(SerialPort serialPort, int stdId, int dlc, int data) throws SerialPortException {
        // Construct the CAN message as a string (adjust as needed)
        String message = String.format("%d%d%d", stdId, dlc, data);
        
        message=FormatStringExample(message);
        
        System.out.println("Sending: " + message);

        // Send the message over the serial port
        serialPort.writeString(message);
    }
    private static String FormatStringExample(String message) {
        if (message.length() < 8) {
            int paddingLength = 8 - message.length();
            String padding = "0".repeat(paddingLength);
            message =  message+padding ;
        }

        return message;
    	
    }
}
