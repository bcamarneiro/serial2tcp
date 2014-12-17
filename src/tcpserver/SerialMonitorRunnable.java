/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class SerialMonitorRunnable implements Runnable, SerialPortEventListener {
	private String threadName = "Serial Monitor";
	private Thread t;
	public ArrayList<TcpClientHandler> clientsArray;
	private Scanner sc;
	static CommPortIdentifier portId;
	static Enumeration portList;

	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
		"/dev/ttyS0", // Linux
		"COM7", // Windows
	};
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 38400;
	
	private String inputLine = new String();

	SerialMonitorRunnable() {
		System.out.println("Creating " + threadName);
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void run() {
		System.out.println("Running " + threadName);
		// sc = new Scanner(System.in);
		// String xxx = null;

		// TO-DO Here listen to serial port

		// while (true) {
		// System.out.println("Write something!");
		// xxx = sc.nextLine();
		// TcpServer.R1.notifyClients(xxx);
		// }

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException ie) {
		}

		// System.out.println("Thread " + threadName + " exiting.");
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {

		switch(oEvent.getEventType()) {
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
	            //outputBufferEmpty(oEvent);
	            break;
	
	        case SerialPortEvent.DATA_AVAILABLE:
	        	try {
					int data = serialPort.getInputStream().read();

					if(data == 13) {
						TcpServer.R1.notifyClients(inputLine);
						System.out.println(inputLine);
						inputLine = "";
					}
					else {
						inputLine += (char)data;
					}				
				} catch (Exception e) {
					System.err.println(e.toString());
				}
	            break;
	    }
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
