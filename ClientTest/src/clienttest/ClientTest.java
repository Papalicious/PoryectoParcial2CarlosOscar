/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author La Maquina del Tunas
 */
public class ClientTest implements Runnable{

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
                //estructura de mensaje enviado :
                // MENSAJE + ID DEL QUE ENVIA + IDCHAT

                 String str = myObj.nextLine();
                //String str = "Hello Server, this is a simple TCP example!";
                str = str + "%" + "kudos"+"%" + "01" + "%"+"\n";
                        //if el string que recibes tiene el codigo de tu chat que entre sino que se regrese a escuchar 
                        //if el id es igual a tu que idga tu si no que diga quien es el que lo envio
                byte buffer[] = str.getBytes();
                outSocket.write(buffer);
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
                    System.out.println("["+arrMsg[1]+"]:" + arrMsg[2]);
                socket.close();
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
        
    }
    
}