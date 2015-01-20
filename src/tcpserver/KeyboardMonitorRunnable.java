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
public class KeyboardMonitorRunnable implements Runnable {
	private String threadName = "Keyboard Monitor";
	private Thread t;
	public ArrayList<TcpClientHandler> clientsArray;
	private Scanner sc = new Scanner(System.in);

	KeyboardMonitorRunnable() {
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);
		
		String xxx = null;

		while (true) {
			System.out.println("Write something!");
			xxx = sc.nextLine();
			TcpServer.R1.notifyClients(xxx);
			if(xxx == "EXIT"){break;}
		}
		
		close();
	}

	public synchronized void close() {
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
