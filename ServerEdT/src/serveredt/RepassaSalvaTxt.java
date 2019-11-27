/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveredt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodrigo
 */
public class RepassaSalvaTxt implements Runnable {

    Vector<RepassaSalvaTxt> clientes;
    private String nome;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public RepassaSalvaTxt(Vector<RepassaSalvaTxt> clientes, String nome, Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        this.clientes = clientes;
        this.nome = nome;
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        int cont = 0;
        while (!Thread.interrupted()) {
            cont = 0;
            for(int i = 0; i < clientes.size(); i++)
            {
                if(clientes.get(i).nome.equals(this.nome))
                    cont = cont + 1;
            }
            
            try {
                String txt = input.readUTF();
                for (RepassaSalvaTxt i : clientes) {
                    if (i.nome.equals(this.nome) && (i.input != this.input)) {
                        i.output.writeInt(cont);
                        i.output.writeUTF(txt);
                        i.output.flush();
                    }else{
                        if(i.input == this.input)
                            i.output.writeInt(cont);
                    }
                }

                FileWriter str = new FileWriter(nome);
                str.write(txt);
                str.close();
                Thread.sleep(1000);
            } catch (IOException ex) {
                try {
                    clientes.remove(this);
                    this.input.close();
                    this.output.close();
                    this.socket.close();
                } catch (IOException ex1) {
                } finally {
                    return;
                }
            } catch (InterruptedException ex) {
                try {
                    clientes.remove(this);
                    this.input.close();
                    this.output.close();
                    this.socket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(RepassaSalvaTxt.class.getName()).log(Level.SEVERE, null, ex1);
                }finally{return;}
            }
        }
    }
}
