/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * Coordena o servidor responsável pelo salvamento do arquivo em edição.
 *
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class RecebeMsg implements Runnable {

    private EditorDeTexto ed;
    private ObjectInputStream in;
    private JTextArea visor;
    private boolean abriu;

    /**
     * Construtor da classe Server; inicializa as variáveis nome e txt como
     * strings vazias e flag como "true".
     *
     * @param flag
     */
    public RecebeMsg(EditorDeTexto ed, ObjectInputStream in, JTextArea visor, boolean abriu) {
        this.ed = ed;
        this.in = in;
        this.visor = visor;
        this.abriu = abriu;
    }

    /**
     * Implementação da função da interface Runnable; controla o comportamento
     * da thread durante a execução do programa. Quando ocorre interrupção,
     * salva o texto num arquivo e espera até que um novo seja salvo.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String str = in.readUTF();
                ed.getT().getText().clear();
                ed.getRefaz().clear();
                ed.getDesfaz().clear();
                ed.inseretexto(str);
                visor.setText(str);
            } catch (IOException ex) {
                
                //ARRUMAR ESSA PARTE DE ABRIU AQUI PARA O DISCONNECT DO SERVIDOR
                this.abriu = false;
                return;
            }
        }
    }
}
