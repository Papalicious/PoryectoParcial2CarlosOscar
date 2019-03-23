/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author La Maquina del Tunas
 */
public class ServerApp {

    /**
     * @param args the command line arguments
     */
        public static InetAddress iAddress = null;

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            iAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String ipAddress = iAddress.getHostAddress();    
        ServerApp ssse = new ServerApp();
        ssse.execute();
    }
    
      
    public void execute(){
        // TODO code application logic here
        try {    
           ServerSocket socket = new ServerSocket(8765,50,iAddress);

            //Accepting the conection, it means that it is TCP
            System.out.println("Waiting for a connection ...");
            Socket insocket = socket.accept( );

            BufferedReader in = new BufferedReader (new 
                InputStreamReader(insocket.getInputStream()));
            PrintWriter out = new PrintWriter (insocket.getOutputStream(), 
                true);
            boolean a = true;
            String instring="";
            while(a){
            instring = in.readLine();
            }
            out.println("The server got: " + instring);
            insocket.close();
        }catch (Exception e) {
            System.out.print(e);
        }
    }
    
}
