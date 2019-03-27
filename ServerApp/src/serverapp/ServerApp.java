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
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author La Maquina del Tunas
 */
/////HACE FALTA MANDAR UNA INSTRUCION QUE CREE Y GUARDE LOS DIFERENTES CHATS QUE HAY
public class ServerApp implements Runnable{

    /**
     * @param args the command line arguments
     */
        public static InetAddress iAddress = null;
        public ServerSocket socket;
        public Socket insocket;
        public String message;
        public static ArrayList<String> users = new ArrayList<String>();
        public static ArrayList<String> allMsg = new ArrayList<String>();
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
                System.out.println("Waiting for Instruction...");
                insocket = socket.accept( );
                System.out.println("Acccepted");
                BufferedReader in = new BufferedReader (new 
                InputStreamReader(insocket.getInputStream()));
                PrintWriter out = new PrintWriter (insocket.getOutputStream(), 
                true);
                amMsg++;
               
                instring = in.readLine();
               // String[] arr = instring.split("%");
                System.out.println(instring);
                allMsg.add(instring);//esta sera la base de datos con todos los mensajes que hay y habra por haber siempre 
                ServerApp thrd = new ServerApp();
                thrd.putString(instring);
                thrd.putinSocket(insocket);
                Thread t1 = new Thread(thrd);
                t1.start();
                }
            
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
            if(arrMsg[0].equals("usr"))
            {
            //checa lista de users si no existe lo agrega
            if(users.contains(arrMsg[1])==false)
            {
                users.add(arrMsg[1]);
                //String str =arrMsg[1];
                System.out.println("Added to users: "+ arrMsg[1]);
                String str =   "in%" ;
                out.println(str);
              
            }else{
            
            String str =   "out%" ;
                out.println(str);
            }
            
            }
            if(arrMsg[0].equals("msj"))
            {
                System.out.println("Server Recieved Message");
            String str =   "Message Sent!%" ;
                out.println(str);
            
            }
           if(arrMsg[0].equals("rd"))
            {
                String fullJson="";
                System.out.println("Sending Messages");
                for(int i =0; i<allMsg.size();i++)
                {
                    String cmdNow = allMsg.get(i).split("%")[0];
                    if(cmdNow.equals("msj"))
                    {
                    fullJson = fullJson +allMsg.get(i);
                    }
                }
            String str =   "Sening All Info" ;
                out.println(fullJson);
            
            }
            // System.out.println(message);
          
            
              
              
          
              
              
             // Thread.sleep(500);
            insocket.close();
        } catch (Exception e) {
            System.out.print(e);        }
    }

    
}
