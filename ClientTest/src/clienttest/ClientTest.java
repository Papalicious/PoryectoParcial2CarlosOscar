/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttest;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author La Maquina del Tunas
 */
public class ClientTest extends Frame implements Runnable {

    /**
     * @param args the command line arguments
     */
    public static String myUserName = "kudoss";
    public static Socket socket=null;
    public static void main(String argv[]){
      
        try{
            int character;
            
            //Here the connection is established with the server
            //"127.0.0.1"
            

            
            boolean a = true;
             Scanner myObj = new Scanner(System.in);
            ////////////////////////////////////////////WHILE DE ESCRITURA////////////////////////
            while(a){
                socket = new Socket("192.168.1.70", 8765);
                InputStream inSocket = socket.getInputStream();
                OutputStream outSocket = socket.getOutputStream();
                

                 String str = myObj.nextLine();
                 String orMsg = str;
               //estructura de mensaje enviado :
                // MENSAJE + ID DEL QUE ENVIA + IDCHAT
               str = str + "%" + "Client"+"%" + "01" + "%"+"\n";
              // str = "[YOU]:" + str;
                 System.out.println("[YOU]:"+ orMsg);     
                byte buffer[] = str.getBytes();
                outSocket.write(buffer);
                String outPutLine ="";
                while ((character = inSocket.read()) != -1) {
                    outPutLine = outPutLine + (char)character;
                    //System.out.print((char) character);
                }
                // ID CHAT + ID DE QUIEN ENVIO + MENSAJE 
                String[] arrMsg = outPutLine.split("%");
                System.out.println("["+ arrMsg[1] +"]:"+arrMsg[2]);
                 //INPUT DE COMO REIBE MENSAJE
            // ID CHAT + ID DE QUIEN ENVIO + MENSAJE 
             
                
               // System.out.println(arrMsg[1]  + " == " + myUserName);
               /*  ESTO ES PARA QUE TODOS SEAN SERVIDOR Y MENSAJEROS AL MISMO TIEMPOO////////////
               String[] arrMsg = outPutLine.split("%");
                if(arrMsg[1].equals(myUserName))
                    System.out.println("[YOU]:" + arrMsg[2]);
                else
                    System.out.println("["+arrMsg[1]+"]:" + arrMsg[2]);
               *//////////////////////////////
               // socket.close();
            }
            //////////////////////////////////////////CIERRA WHILE ESCRITURA////////////////////////////
           
            socket.close();
        }
        catch(Exception ex){        
            System.out.println("You must first start the server socket"); 
            System.out.println("(YourServer.java) at the command prompt.");
            System.out.println(ex);
        }        
    
    }
    
    public void run(){
        int character;/*
        InputStream inSocket = socket.getInputStream();
        String outPutLine ="";
                while ((character = inSocket.read()) != -1) {
                    outPutLine = outPutLine + (char)character;
                    //System.out.print((char) character);
                }
                 //INPUT DE COMO REIBE MENSAJE
            // ID CHAT + ID DE QUIEN ENVIO + MENSAJE 
               // System.out.println("-------------"+outPutLine+"---------------");
                String[] arrMsg = outPutLine.split("%");
               // System.out.println(arrMsg[1]  + " == " + myUserName);
                if(arrMsg[1].equals(myUserName))
                    System.out.println("[YOU]:" + arrMsg[2]);
                else
                    System.out.println("["+arrMsg[1]+"]:" + arrMsg[2]);*/
                
    }
    
}
