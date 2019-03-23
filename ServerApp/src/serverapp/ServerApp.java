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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author La Maquina del Tunas
 */
public class ServerApp implements Runnable{

    /**
     * @param args the command line arguments
     */
        public static InetAddress iAddress = null;
        public ServerSocket socket;
        public Socket insocket;
        public String message;
// este e sun test del commit
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            iAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String ipAddress = iAddress.getHostAddress();  
        System.out.println(ipAddress);
        ServerApp ssse = new ServerApp();
        ssse.execute();
    }
    
    public void putString(String message){
        this.message=message;
        
    }
    public void putinSocket(Socket insocket)
    {
        this.insocket = insocket;
    }
    public void execute(){
        // TODO code application logic here
        try {    
           socket = new ServerSocket(8765,50,iAddress);

            //Accepting the conection, it means that it is TCP
            
            boolean a = true;
            String instring="";
            int amMsg=0;
            while(a){
                System.out.println("Waiting for a connection ...");
                insocket = socket.accept( );

                BufferedReader in = new BufferedReader (new 
                InputStreamReader(insocket.getInputStream()));
                PrintWriter out = new PrintWriter (insocket.getOutputStream(), 
                true);
                amMsg++;
                System.out.println(amMsg);
                instring = in.readLine();
                System.out.println(instring);
                ServerApp thrd = new ServerApp();
                thrd.putString(instring);
                thrd.putinSocket(insocket);
                Thread t1 = new Thread(thrd);
                t1.start();
                }
            //out.println("The server got: " + instring);
            insocket.close();
        }catch (Exception e) {
            System.out.print(e);
        }
    }

 public void run() {
        
         try {
             BufferedReader in = new BufferedReader (new 
                InputStreamReader(insocket.getInputStream()));
            PrintWriter out = new PrintWriter (insocket.getOutputStream(), 
                true);
            String[] arrMsg = message.split("%");
            String userId = arrMsg[1];
            String chatid = arrMsg[2];
            // System.out.println(message);
              out.println(chatid+"%"+userId + "%" + arrMsg[0]);
              Thread.sleep(500);
            insocket.close();
        } catch (Exception e) {
            System.out.print(e);        }
    }

    
}
