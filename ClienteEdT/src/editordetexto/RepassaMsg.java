/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Coordena o servidor responsável pelo salvamento do arquivo em edição.
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class RepassaMsg implements Runnable{
    private String nome;
    private String txt;
    private boolean flag;

/**
 * Construtor da classe Server; inicializa as variáveis nome e txt como strings vazias e flag como "true".
 * @param flag 
 */
    public RepassaMsg(boolean flag) {
        this.nome = "";
        this.txt = "";
        this.flag = flag;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
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
            try
            {
                while(!Thread.interrupted())
                {
                    Thread.sleep(1);
                }
            }catch(InterruptedException i){}
            
            try {
                FileWriter str = new FileWriter(this.nome);
                str.write(txt);
                str.close();
            }catch (IOException ex) {}
        }
    } 
}
