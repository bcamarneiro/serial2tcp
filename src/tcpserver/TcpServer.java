/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

public class TcpServer {
    static TcpServerRunnable R1;    
    
    public static void main(String[] args) {
        System.out.println("Starting RedeRia TCP Server");

        R1 = new TcpServerRunnable(3333);
        R1.start();

        //SerialMonitorRunnable R2 = new SerialMonitorRunnable();
        //R2.start();
    }
}


