/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveredt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author rodrigo
 */
public class ServerEdT extends JFrame{
    private JButton disc = new JButton("Desconectar");
    private Vector<String> nomes;
    private ServerSocket server;
    
    public ServerEdT() throws IOException
    {
        super("Servidor EdT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(225,75);
        this.add(disc);
        
        server = new ServerSocket(1234);
        nomes = new Vector();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ServerEdT edt = new ServerEdT();
        Vector<RecebeSalvaTxt> clientes = new Vector();
        String nome = "";
        RecebeSalvaTxt cliente;
        Thread t;
        Boolean achou = false;
        
        while(true){
            System.out.println("Aguardando conexões...");
      
            Socket socket = edt.server.accept();
            
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());            

            System.out.println("Conectado");
            
            int op = in.readInt();
            switch(op)
            {
                case 0:
                    out.writeObject(edt.nomes);
                    out.flush();
                    if(edt.nomes.isEmpty()){break;}
                    nome = in.readUTF();
                    out.writeUTF(edt.abrir(nome));
                    out.flush();
                    cliente = new RecebeSalvaTxt(clientes, nome, in, out);
                    clientes.add(cliente);
                    t = new Thread(cliente);
                    t.start();
                    break;
                case 1:
                    nome = in.readUTF();
                    for(String i : edt.nomes)
                        if(nome.equals(i))
                        {
                            achou = true;
                        }
                    if(!achou)
                    {
                        edt.nomes.add(nome);
                    }
                    cliente = new RecebeSalvaTxt(clientes, nome, in, out);
                    clientes.add(cliente);
                    t = new Thread(cliente);
                    t.start();
                    break;
            }
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
    
    public String abrir(String name)
    {
        String str = "";
        int i;
        FileReader in = null;
        try {
            in = new FileReader(name);
            i = in.read();
            while(i != -1)
            {
                str = str.concat(String.valueOf((char)i));
                i = in.read();
            }
        }
        catch (FileNotFoundException ex) {return null;} 
        catch (IOException ex) {}
        finally {
            try {
                in.close();
            } catch (IOException ex) {}
        }
        
        return str;
    }
    
}
