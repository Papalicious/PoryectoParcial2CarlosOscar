
package hichat.cliente;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

//Establece la conexion y crea la ventana y la clase de control.

public class ClienteChat {
    // Socket con el servidor del chat
    private Socket socket;

    // Panel con la ventana del cliente
    private PanelCliente panel;

    // Corre el Cliente de chat.
    public static void main(String[] args) {
        new ClienteChat();
    }

    // Crea la ventana, establece la conexion e instancia al controlador.

    public ClienteChat() {
        try {
            creaYVisualizaVentana();
            socket = new Socket("localhost", 5557);
            ControlCliente control = new ControlCliente(socket, panel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una ventana, le mete dentro el panel para el cliente y la visualiza
     */
    private void creaYVisualizaVentana() {
        JFrame v = new JFrame();
        panel = new PanelCliente(v.getContentPane());
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
