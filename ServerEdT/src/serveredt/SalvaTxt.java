/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveredt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodrigo
 */
public class SalvaTxt implements Runnable{
    private String nome;
    private String txt;
    
    public SalvaTxt(String nome, String txt)
    {
        this.nome = nome;
        this.txt = txt;
    }
    
    @Override
    public void run() {
        while(true)
        {            
            try {
                FileWriter str = new FileWriter(nome);
                str.write(txt);
                str.close();
                Thread.sleep(1000);
            }catch (IOException ex) {} catch (InterruptedException ex) {
                Logger.getLogger(SalvaTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}
