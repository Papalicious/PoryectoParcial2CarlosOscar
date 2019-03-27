/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.visual;





import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author La Maquina del Tunas
 */
public class ClientAppVisual implements Runnable{

    /**
     * @param args the command line arguments
     */
    public int cmd;
    public static String ip="10.7.7.90";
    public static int hasSent=0;
    public static String chatNow;
    public static Socket socket=null;
    public static  InputStream inSocket;
    public static OutputStream outSocket;
    public static String myUserName;
    public void setCmd(int cmd){
    this.cmd = cmd;
    }
     public void setChat(String chatNow){
    this.chatNow = chatNow;
    }
     public void setJField(javax.swing.JTextField  JField){
     this.jTextField1 = JField;
     }
     public void setJTextArea(javax.swing.JTextArea  JArea){
     this.jTextArea1 = JArea;
     }
     public void setUserName(String myUserName){
     this.myUserName = myUserName;
     }
    
    
    public void run(){
        try {
            if(cmd==0){
                socket = new Socket(ip, 8765);
                inSocket = socket.getInputStream();
                OutputStream outSocket = socket.getOutputStream();
                int character;
                while(true){
                System.out.println("Write Your Username to Start: ");
                Scanner myObj = new Scanner(System.in);
                 String str = myObj.nextLine();
                 String orMsg = str;
                 myUserName = str;
               //estructura de mensaje enviado :
                // MENSAJE + ID DEL QUE ENVIA + IDCHAT
                 str = "usr%" + str + "%"+"@\n";
              // str = "[YOU]:" + str;
                System.out.println(orMsg);     
                byte buffer[] = str.getBytes();
                outSocket.write(buffer);
                String outPutLine ="";
                while ((character = inSocket.read()) != -1) {
                    outPutLine = outPutLine + (char)character;
                    //System.out.print((char) character);
                }
                // ID CHAT + ID DE QUIEN ENVIO + MENSAJE 
                String[] arrMsg = outPutLine.split("%");
               // System.out.println("["+ arrMsg[1] +"]:"+arrMsg[2]);
               if(arrMsg[0].equals("in")){
                  System.out.println("UserName Accepted");

                   break;
               }
                }
    }
            if(cmd ==1){//send message
            hasSent=1;
                socket = new Socket(ip, 8765);
                inSocket = socket.getInputStream();
                OutputStream outSocket = socket.getOutputStream();
                int character;
               
                //System.out.println("Write Your Username to Start: ");
               
                 String str =jTextField1.getText();
                 String orMsg = str;
               //estructura de mensaje enviado :
                // MENSAJE + ID DEL QUE ENVIA + IDCHAT
                 str = "msj%" + str + "%"+myUserName+"%"+chatNow+"@\n";
                 /////////////////////////////////////////////////jTextArea1.append("[YOU]:"+ orMsg+"\n");
              // str = "[YOU]:" + str;
                System.out.println(orMsg);     
                byte buffer[] = str.getBytes();
                outSocket.write(buffer);
                String outPutLine ="";
                while ((character = inSocket.read()) != -1) {
                    outPutLine = outPutLine + (char)character;
                    //System.out.print((char) character);
                }
                // ID CHAT + ID DE QUIEN ENVIO + MENSAJE 
                String[] arrMsg = outPutLine.split("%");
                System.out.println(arrMsg[0]);
               // System.out.println("["+ arrMsg[1] +"]:"+arrMsg[2]);
               
            
            
            }
            if(cmd ==2&& hasSent>0){//send message
                ////////////////////////////////////////////PONER UN IF SI NO HA MANDADO NI UN MENSAJE QUE NO PUEDA LEER
                
                String chatATM = chatNow;
                socket = new Socket(ip, 8765);
                inSocket = socket.getInputStream();
                OutputStream outSocket = socket.getOutputStream();
                int character;
               
                //System.out.println("Write Your Username to Start: ");
               
                 String str ="";
                
               //estructura de mensaje enviado :
                // MENSAJE + ID DEL QUE ENVIA + IDCHAT
                 str = "rd%"+myUserName +"%@\n";
              // str = "[YOU]:" + str;
                     
                byte buffer[] = str.getBytes();
                outSocket.write(buffer);
                String outPutLine ="";
                while ((character = inSocket.read()) != -1) {
                    outPutLine = outPutLine + (char)character;
                    //System.out.print((char) character);
                }
                // ID CHAT + ID DE QUIEN ENVIO + MENSAJE 
                //String[] arrMsg = outPutLine.split("%");
                System.out.println("Full text: " +outPutLine);
                String[] allMessages = outPutLine.split("@");
                System.out.println("Size:" + allMessages.length);
                //////////////////////////////////////////////////////////////AQUI VACIAR EL TEXTO QUE HAY Y LLENARLO CON LO NUEVO
                jTextArea1.setText("");
                if(allMessages.length>1)
                for(int i=0; i<allMessages.length-1;i++)
                {
                    //if con su nomre ha de tener nuevos mensajes
                    System.out.println(allMessages[i]);
                    String[] msgNow =allMessages[i].split("%");
                    if(chatATM.equals(msgNow[3]))
                    {
                    if(msgNow[2].equals(myUserName))
                    jTextArea1.append("[YOU]: "+msgNow[1]+"\n");
                    else
                    jTextArea1.append("["+msgNow[2]+"]: "+msgNow[1]+"\n");
                    
                    }
                }
               // System.out.println("["+ arrMsg[1] +"]:"+arrMsg[2]);
               
            
            
            }
            /*
            int character;
           // socket = new Socket("192.168.1.70", 8765);
            //InputStream inSocket = socket.getInputStream();
            //OutputStream outSocket = socket.getOutputStream();
            String outPutLine ="";
            while ((character = inSocket.read()) != -1) {
                outPutLine = outPutLine + (char)character;
                //System.out.print((char) character);
            }
            // ID CHAT + ID DE QUIEN ENVIO + MENSAJE
            String[] arrMsg = outPutLine.split("%");
            System.out.println("["+ arrMsg[1] +"]:"+arrMsg[2]);
            jTextArea1.append("["+ arrMsg[1] +"]:"+arrMsg[2]+"\n");*/
        } catch (IOException ex) {
            Logger.getLogger(NewJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    public static  javax.swing.JTextField jTextField1;
    public  static  javax.swing.JTextArea jTextArea1;
}
