/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * Coordena o servidor responsável pelo salvamento do arquivo em edição.
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class RepassaMsg implements Runnable{
    private File file;
    private JTextArea txt;
    private boolean flag;

/**
 * Construtor da classe Server; inicializa as variáveis nome e txt como strings vazias e flag como "true".
 * @param flag 
 */
    public RepassaMsg(JTextArea vis, boolean flag) {
        this.file = null;
        this.txt = vis;
        this.flag = flag;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
                FileWriter str = new FileWriter(file);
                str.write(txt.getText());
                str.close();
                Thread.sleep(1000);
            }catch (IOException ex) {} catch (InterruptedException ex) {
                Logger.getLogger(RepassaMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
