/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JMenu;

/**
 * Classe que controla a recepção do número de clientes editando determinado texto.
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class RecebeCont implements Runnable {

    private JMenu contador;
    private ObjectInputStream in2;

    public RecebeCont(JMenu cont, ObjectInputStream in2) {
        this.contador = cont;
        this.in2 = in2;
    }
    /**
     * Thread que controla a contagem.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                int cont = in2.readInt();
                contador.setText("Clientes : " + String.valueOf(cont));
            } catch (IOException ex) {
                contador.setText("Clientes : ");
                return;
            }
        }
    }
}
