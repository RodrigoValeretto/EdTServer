/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * Coordena o servidor responsável pelo salvamento do arquivo em edição.
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class RecebeMsg implements Runnable{
    private ObjectOutputStream out
    private JTextArea txt;
    private boolean flag;

/**
 * Construtor da classe Server; inicializa as variáveis nome e txt como strings vazias e flag como "true".
 * @param flag 
 */
    public RecebeMsg(ObjectOutputStream out,JTextArea vis, boolean flag) {
        this.out = out;
        this.txt = vis;
        this.flag = flag;
    }

    public JTextArea getTxt() {
        return txt;
    }

    public void setTxt(JTextArea txt) {
        this.txt = txt;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    /**
     * Implementação da função da interface Runnable; controla o comportamento da thread durante a execução do programa. Quando ocorre interrupção, salva o texto num arquivo e espera até que um novo seja salvo.
     */
    @Override
    public void run() {
        while(flag)
        {            
            try {
                
                Thread.sleep(1000);
            }catch (IOException ex) {} catch (InterruptedException ex) {
                Logger.getLogger(RecebeMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
