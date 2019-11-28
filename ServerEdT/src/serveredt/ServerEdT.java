/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveredt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author rodrigo
 */
public class ServerEdT extends JFrame {

    private JButton disc = new JButton("Desconectar");
    private Vector<String> nomes;
    private ServerSocket server;
    private ServerSocket server2;
    private Vector<RepassaSalvaTxt> clientes;
    private Vector<Thread> ts;

    public ServerEdT() throws IOException {
        super("Servidor EdT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(225, 75);
        this.add(disc);
        this.clientes = new Vector();
        this.ts = new Vector();

        server = new ServerSocket(1234);
        server2 = new ServerSocket(1235);
        nomes = new Vector();
        
        disc.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(RepassaSalvaTxt i : clientes)
                {
                    try {
                        i.getInput().close();
                        i.getOutput().close();
                        i.getSocket().close();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerEdT.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                for(Thread i : ts)
                {
                    i.interrupt();
                }
                clientes.clear();
                ts.clear();
            }
        });
    }

    public void EscreveNomesNoReg() {
        try {
            BufferedWriter fp = new BufferedWriter(new FileWriter("ArquivosDoServer.txt"));
            for (String i : this.nomes) {
                fp.append(i + "\n");
            }
            fp.close();
        } catch (IOException ex) {
        }
    }

    public void LeNomesDoReg() {
        try {
            this.nomes.clear();
            BufferedReader fp = new BufferedReader(new FileReader("ArquivosDoServer.txt"));
            String str = fp.readLine();
            while (str != null) {
                this.nomes.add(str);
                str = fp.readLine();
            }
            fp.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ServerEdT edt = new ServerEdT();
        String nome = "";
        RepassaSalvaTxt cliente;
        Thread t;
        boolean achou = false;

        while (true) {
            edt.LeNomesDoReg();
            achou = false;
            
            System.out.println("Aguardando conexões...");

            Socket socket = edt.server.accept();
            
            Socket socket2 = edt.server2.accept();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());

            System.out.println("Conectado");
            
            out.writeObject(edt.nomes);
            out.flush();
            int op;
            try{
                op = in.readInt();
            }catch(EOFException ex){op = 2;}
            switch (op) {
                case 0:
                    if (edt.nomes.isEmpty()) {
                        break;
                    }
                    nome = in.readUTF();
                    if (nome.equals("")) {
                        break;
                    }
                    out.writeUTF(edt.abrir(nome));
                    out.flush();
                    cliente = new RepassaSalvaTxt(edt.clientes, nome, socket, in, out, out2);
                    edt.clientes.add(cliente);
                    t = new Thread(cliente);
                    t.start();
                    break;
                case 1:
                    try{
                        nome = in.readUTF();
                    }catch(EOFException ex){break;}
                    for (String i : edt.nomes) {
                        if (nome.equals(i)) {
                            achou = true;
                        }
                    }
                    if (!achou) {
                        edt.nomes.add(nome);
                    }
                    cliente = new RepassaSalvaTxt(edt.clientes, nome, socket, in, out, out2);
                    edt.clientes.add(cliente);
                    t = new Thread(cliente);
                    edt.ts.add(t);
                    t.start();
                    break;
                    
                case 2:
                    break;
            }

            edt.EscreveNomesNoReg();

            /*
                    IDEIA DE SERVER:
                MANTER OS ARQUIVOS ARMAZENADOS NO SERVIDOR
                ABRIMOS OS ARQUIVOS NO SERVIDOR E ENVIAMOS
                PARA O CLIENTE TODO O TEXTO CONTIDO NO ARQUIVO
                O CLIENTE RECEBE ESSE TEXTO E O INSERE
                A FUNÇÃO DE SALVAR DO CLIENTE ATIVA UMA FUNÇÃO DE SALVAR
                NO SERVIDOR QUE FICA SALVANDO CONSTANTEMENTE O ARQUIVO
                O CLIENTE TAMBEM TEM Q PASSAR TODA A STRING PARA O SERVIDOR
                QUE REESCREVE O ARQUIVO Q TEM EM DISCO
             */
        }
    }

    public String abrir(String name) {
        String str = "";
        int i;
        FileReader in = null;
        try {
            in = new FileReader(name);
            i = in.read();
            while (i != -1) {
                str = str.concat(String.valueOf((char) i));
                i = in.read();
            }
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }

        return str;
    }

}
