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
 *
 * @author rodrigo
 */
public class Client {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    
    public Client() throws IOException
    {
        socket = new Socket("127.0.0.1", 1234);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
}
