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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodrigo
 */
public class RepassaSalvaTxt implements Runnable{
    Vector <RepassaSalvaTxt> clientes;
    private String nome;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    
    public RepassaSalvaTxt(Vector <RepassaSalvaTxt> clientes, String nome, ObjectInputStream input, ObjectOutputStream output)
    {
        this.clientes = clientes;
        this.nome = nome;
        this.input = input;
        this.output = output;
    }
    
    @Override
    public void run() {
        while(true)
        {            
            try {
                String txt = input.readUTF();
                for(RepassaSalvaTxt i : clientes)
                    {
                        if(i.nome == this.nome)
                        {
                            i.output.writeUTF(txt);
                            i.output.flush();
                        }
                    }
                
                FileWriter str = new FileWriter(nome);
                str.write(txt);
                str.close();
                Thread.sleep(1000);
            }catch (IOException ex) {
                try{
                    clientes.remove(this);
                    this.input.close();
                    this.output.close();
                }catch(IOException ex1){}
                finally{return;}
            } catch (InterruptedException ex) {}
        } 
    }
}
