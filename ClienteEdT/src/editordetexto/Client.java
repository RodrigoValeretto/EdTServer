/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Controla a conexão de um cliente.
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class Client {
    Socket socket;
    Socket socket2;
    ObjectInputStream in;
    ObjectInputStream in2;
    ObjectOutputStream out;
    
    /**
     * Construtor da classe Client.
     * @throws IOException 
     */
    public Client() throws IOException
    {
        socket = new Socket("127.0.0.1", 1234);

        socket2 = new Socket("127.0.0.1", 1235);
        
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        in2 = new ObjectInputStream(socket2.getInputStream());
    }
}
