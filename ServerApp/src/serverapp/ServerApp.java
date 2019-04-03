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
        public static ArrayList<String> groups = new ArrayList<String>();
        public static ArrayList<String> groupInfo = new ArrayList<String>();
        
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
           if(arrMsg[0].equals("rd"))//checar  tambien que si es NewGroup que lo cheque 
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
                //agregar al final el admin del grupo y si tu eres el admin ya chingaste
                fullJson = fullJson + "$";
                for(int i =0; i<allMsg.size();i++)//aqui buscara al admin
                {
                    String cmdNow = allMsg.get(i).split("%")[0];
                    if(cmdNow.equals("newGrp"))
                    {
                    fullJson = fullJson +allMsg.get(i);
                    }
                }
                //////////////////////
            String str =   "Sening All Info" ;
                out.println(fullJson);
            
            }
           if(arrMsg[0].equals("newGrp")){
               System.out.println("Server Recieved Group");
               groups.add(arrMsg[1].split("\\r?\\n")[0]);
               groupInfo.add(arrMsg[1].split("\\r?\\n")[0] + "$"+arrMsg[2]+"$");
            String str =   "New Group Created!%:" +  arrMsg[1].split("\\r?\\n")[0] + "@";
                out.println(str);
               
           }
           if(arrMsg[0].equals("rdGrp")){
               System.out.println("Sending Groups");
                String str =   "" ;
               for(int i =0 ; i < groups.size();i++)
               {
                   str = str + "%" + groups.get(i);
               }
               str =str +"+" ;
               for(int i =0 ; i < groupInfo.size();i++)
               {
                   str = str + groupInfo.get(i)+"@";
               }
               //groups.add(arrMsg[1]);
           
                out.println(str);
               
           }
           if(arrMsg[0].equals("rdUsr")){
               System.out.println("Sending Users");
                String str =   "" ;
               for(int i =0 ; i < users.size();i++)
               {
                   str = str + "%" + users.get(i);
               }
               //groups.add(arrMsg[1]);
           
                out.println(str);//revisar si sirve aun sin endline
               
           }
           if(arrMsg[0].equals("addUsr")){
               System.out.println("Added User: " + arrMsg[1].split("\\$")[0]);
                String str =   "Added User: " + arrMsg[1] ;
                String us = arrMsg[1].split("\\$")[0];
                String groupName = arrMsg[1].split("\\$")[1].split("\\@")[0];
                if(groupInfo.size()>0)
               for(int i =0 ; i < groupInfo.size();i++)
               {
                   System.out.println("This is the grpup Info i  got: "+ groupInfo.get(i));
                  String[] groupie = groupInfo.get(i).split("\\$");
                  // System.out.println("Groupie Name is : " + groupie[0]);
                   System.out.println(groupie[0] + " == "+ groupName);
                  if(groupie[0].equals(groupName))// si el valor de el arraylsit hasta el frente es igual al valor del chat
                  {
                    String tmp = groupInfo.get(i);
                    groupInfo.remove(i);
                    groupInfo.add(tmp + us +"$");
                    str = tmp + us +"$";
                  }
               }
               //groups.add(arrMsg[1]);
           
                out.println(str);
               
           }
           if(arrMsg[0].equals("remUsr")){
               System.out.println("Remove User");
                String str =  "" ;
                String userToDel = arrMsg[1].split("\\$")[0];
                 String groupName = arrMsg[1].split("\\$")[1].split("\\@")[0];
               for(int i =0 ; i < groupInfo.size();i++)
               {
                  String[] groupie = groupInfo.get(i).split("\\$");
                  if(groupie.length>0)
                  if(groupie[0].equals(groupName))// si el valor de el arraylsit hasta el frente es igual al valor del chat
                  {
                      
                    String tmp = groupie[0] + "$";
                    groupInfo.remove(i);
                    for(int x =1;x<groupie.length;x++)
                    {
                        if(!groupie[x].equals(userToDel))
                        {
                            tmp = tmp + groupie[x] + "$";
                        }
                    }
                    groupInfo.add(tmp);
                    str = tmp;
                  }
               }
               //groups.add(arrMsg[1]);
           
                out.println("Deletion ended this: "+str);
               
           }
            // System.out.println(message);
          
            
              
              
          
              
              
             // Thread.sleep(500);
            insocket.close();
        } catch (Exception e) {
            System.out.print(e);        }
    }

    
}
