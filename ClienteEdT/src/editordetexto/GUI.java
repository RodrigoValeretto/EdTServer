/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Classe correspondente a interface gráfica do editor de texto.
 *
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
public class GUI extends JFrame {

    private boolean abriu = false;
    private JFileChooser choose;
    private Client cliente;
    private RecebeMsg rmsg;
    private Thread t;
    private Lock lock = new ReentrantLock();
    private EditorDeTexto ed;
    private String copiado;
    private JPanel painel = new JPanel();
    private JButton redo = new JButton("Refazer");
    private JButton undo = new JButton("Desfazer");
    private JButton insert = new JButton("Inserir Texto");
    private JButton remove = new JButton("Remover Texto");
    private JButton copy = new JButton("Copiar");
    private JButton cut = new JButton("Recortar");
    private JButton paste = new JButton("Colar");
    private JTextArea visor = new JTextArea();
    private JTextArea com = new JTextArea();
    private JScrollPane scroll = new JScrollPane(visor);
    private JScrollPane scroll2 = new JScrollPane(com);
    private JMenuBar barra = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenuItem save = new JMenuItem("Salvar");
    private JMenuItem open = new JMenuItem("Abrir");
    private JMenuItem disconnect = new JMenuItem("Desconectar");
    private JMenu contador = new JMenu("Clientes :");

    /**
     * Construtor da classe GUI; inicializa as variáveis que definem a interface
     * gráfica do editor, além de definir o que ocorre ao clicar nos botões da
     * mesma.
     *
     * @param n
     */
    public GUI(EditorDeTexto n) throws IOException {
        super("Editor De Texto");
        this.ed = n;
        this.copiado = "";
        choose = null;

        painel.setLayout(new GridLayout(1, 8));

        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());

        painel.add(undo);
        painel.add(redo);
        painel.add(insert);
        painel.add(remove);
        painel.add(copy);
        painel.add(cut);
        painel.add(paste);
        painel.add(save);
        painel.add(disconnect);

        visor.setEditable(false);
        visor.setLineWrap(true);
        scroll.setBorder(new TitledBorder(new EtchedBorder(), "Texto"));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(640, 400));

        com.setEditable(true);
        com.setLineWrap(true);
        scroll2.setBorder(new TitledBorder(new EtchedBorder(), "Comandos"));
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setPreferredSize(new Dimension(640, 320));

        barra.add(file);
        barra.add(contador);
        file.add(open);
        file.add(save);
        file.add(disconnect);

        this.setJMenuBar(barra);
        this.add(painel, BorderLayout.SOUTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(scroll2, BorderLayout.NORTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        try {
            cliente = new Client();
            Vector<String> nomes = (Vector) cliente.in.readObject();
            JFrame janela = new JFrame("Escolha o arquivo ou crie um novo");
            JButton abrir = new JButton("Abrir");
            JButton novo = new JButton("Novo arquivo");
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 2));
            DefaultComboBoxModel model = new DefaultComboBoxModel(nomes.toArray());
            JComboBox box = new JComboBox();
            box.setModel(model);
            panel.add(abrir);
            panel.add(novo);
            janela.setLayout(new BorderLayout());
            janela.add(box, BorderLayout.NORTH);
            janela.add(panel, BorderLayout.SOUTH);
            janela.setSize(200, 80);
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setVisible(true);

            abrir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e1) {
                    String str = "";
                    try {
                        cliente.out.writeInt(0);
                        cliente.out.flush();
                        cliente.out.writeUTF(box.getSelectedItem().toString());
                        cliente.out.flush();
                        str = cliente.in.readUTF();

                        ed.getT().getText().clear();
                        ed.getRefaz().clear();
                        ed.getDesfaz().clear();
                        if (!str.isEmpty()) {
                            ed.inseretexto(str);
                            visor.setText(str);
                        }
                        janela.setVisible(false);
                        rmsg = new RecebeMsg(ed, cliente.in, visor, contador);
                        t = new Thread(rmsg);
                        t.start();
                        abriu = true;
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            novo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        cliente.out.writeInt(1);
                        cliente.out.flush();
                        JFrame janela2 = new JFrame("Digite o nome do arquivo que deseja salvar:");
                        JButton salvar = new JButton("Salvar");
                        JTextField nome = new JTextField(25);
                        janela2.setLayout(new BorderLayout());
                        janela2.add(nome, BorderLayout.NORTH);
                        janela2.add(salvar, BorderLayout.SOUTH);
                        janela2.pack();
                        janela2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        janela2.setVisible(true);
                        salvar.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    cliente.out.writeUTF(nome.getText());
                                    cliente.out.flush();
                                    cliente.out.writeUTF(visor.getText());
                                    cliente.out.flush();
                                    rmsg = new RecebeMsg(ed, cliente.in, visor, contador);
                                    t = new Thread(rmsg);
                                    t.start();
                                    abriu = true;
                                    janela.setVisible(false);
                                    janela2.setVisible(false);
                                } catch (IOException ex) {
                                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    } catch (IOException ex) {
                    }
                }
            });

        } catch (FileNotFoundException ex) {
        } catch (IOException | ClassNotFoundException ex) {
        }

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (!t.isAlive()) {
                    abriu = false;
                }
                String str = "";
                lock.lock();
                try {
                    ed.desfazer();
                    for (char i : ed.getT().getText()) {
                        str = str.concat(String.valueOf(i));
                    }
                    visor.setText(str);
                    if (abriu) {
                        cliente.out.writeUTF(visor.getText());
                        cliente.out.flush();
                    }
                } catch (NullPointerException f) {
                    System.out.println(f.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    lock.unlock();
                }

            }
        });

        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (!t.isAlive()) {
                    abriu = false;
                }
                String str = "";
                lock.lock();
                try {
                    ed.refazer();
                    for (char i : ed.getT().getText()) {
                        str = str.concat(String.valueOf(i));
                    }
                    visor.setText(str);
                    if (abriu) {
                        cliente.out.writeUTF(visor.getText());
                        cliente.out.flush();
                    }
                } catch (NullPointerException f) {
                    System.out.println(f.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    lock.unlock();
                }

            }
        });

        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!t.isAlive()) {
                        abriu = false;
                    }
                    String str = "";
                    lock.lock();
                    try {
                        ed.inseretexto(com.getText());
                        ed.inserealteracao(ed.getDesfaz(), com.getText(), "1");
                        ed.getRefaz().clear();
                    } catch (NullPointerException f) {
                        System.out.println(f.getMessage());
                    } finally {
                        lock.unlock();
                    }

                    for (char i : ed.getT().getText()) {
                        str = str.concat(String.valueOf(i));
                    }
                    visor.setText(str);
                    if (abriu) {
                        cliente.out.writeUTF(visor.getText());
                        cliente.out.flush();
                    }
                    com.setText("");
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!t.isAlive()) {
                        abriu = false;
                    }
                    String str = "";
                    lock.lock();
                    try {
                        if (!com.getText().isEmpty()) {
                            String aux = ed.removetexto(Integer.parseInt(com.getText()));
                            ed.inserealteracao(ed.getDesfaz(), aux, "2");
                            ed.getRefaz().clear();
                        } else {
                            throw new InvalidPropertiesFormatException("\nNenhum valor digitado!\n");
                        }
                    } catch (InvalidPropertiesFormatException | NumberFormatException f) {
                        System.out.println(f.getMessage());
                    } finally {
                        lock.unlock();
                    }

                    for (char i : ed.getT().getText()) {
                        str = str.concat(String.valueOf(i));
                    }
                    visor.setText(str);
                    if (abriu) {
                        cliente.out.writeUTF(visor.getText());
                        cliente.out.flush();
                    }
                    com.setText("");
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lock.lock();
                try {
                    copiar();
                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    lock.unlock();
                }

            }
        });

        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.isAlive()) {
                    abriu = false;
                }
                lock.lock();
                try {
                    recortar();
                    ed.getRefaz().clear();
                    ed.getDesfaz().clear();
                    if (abriu) {
                        cliente.out.writeUTF(visor.getText());
                        cliente.out.flush();
                    }
                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    lock.unlock();
                }
            }
        });

        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.isAlive()) {
                    abriu = false;
                }
                lock.lock();
                try {
                    colar();
                    ed.getRefaz().clear();
                    ed.getDesfaz().clear();
                    if (abriu) {
                        cliente.out.writeUTF(visor.getText());
                        cliente.out.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    lock.unlock();
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.isAlive()) {
                    abriu = false;
                }

                if (abriu) {
                    return;
                }
                try {
                    cliente = new Client();
                    Vector<String> nomes = (Vector) cliente.in.readObject();
                    cliente.out.writeInt(1);
                    cliente.out.flush();
                    JFrame janela = new JFrame("Digite o nome do arquivo que deseja salvar:");
                    JButton salvar = new JButton("Salvar");
                    JTextField nome = new JTextField(25);
                    janela.setLayout(new BorderLayout());
                    janela.add(nome, BorderLayout.NORTH);
                    janela.add(salvar, BorderLayout.SOUTH);
                    janela.pack();
                    janela.setVisible(true);
                    janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    salvar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                cliente.out.writeUTF(nome.getText());
                                cliente.out.flush();
                                cliente.out.writeUTF(visor.getText());
                                cliente.out.flush();
                                rmsg = new RecebeMsg(ed, cliente.in, visor, contador);
                                t = new Thread(rmsg);
                                t.start();
                                abriu = true;
                                janela.setVisible(false);
                            } catch (IOException ex) {
                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                } catch (IOException ex) {
                } catch (ClassNotFoundException ex) {
                }
            }
        });

        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!abriu || !t.isAlive()) {
                    abriu = false;
                    return;
                }
                try {
                    ed.getT().getText().clear();
                    ed.getDesfaz().clear();
                    ed.getRefaz().clear();
                    copiado = "";
                    visor.setText("");
                    com.setText("");
                    choose = null;
                    abriu = false;
                    t.interrupt();
                    cliente.in.close();
                    cliente.out.close();
                    cliente.socket.close();
                } catch (IOException ex) {
                }
                /*finally {
                    try {
                        cliente = new Client();
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.isAlive()) {
                    abriu = false;
                }

                if (abriu) {
                    return;
                }
                try {
                    cliente = new Client();
                    Vector<String> nomes = (Vector) cliente.in.readObject();
                    if (nomes.isEmpty()) {
                        return;
                    }
                    JFrame janela = new JFrame("Escolha o arquivo");
                    JButton abrir = new JButton("Abrir");
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nomes.toArray());
                    JComboBox box = new JComboBox();
                    box.setModel(model);
                    janela.setLayout(new BorderLayout());
                    janela.add(box, BorderLayout.NORTH);
                    janela.add(abrir, BorderLayout.SOUTH);
                    janela.setSize(200, 80);
                    janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    janela.setVisible(true);

                    abrir.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e1) {
                            String str = "";
                            try {
                                cliente.out.writeInt(0);
                                cliente.out.flush();
                                cliente.out.writeUTF(box.getSelectedItem().toString());
                                cliente.out.flush();
                                str = cliente.in.readUTF();

                                ed.getT().getText().clear();
                                ed.getRefaz().clear();
                                ed.getDesfaz().clear();
                                if (!str.isEmpty()) {
                                    ed.inseretexto(str);
                                    visor.setText(str);
                                }
                                janela.setVisible(false);
                                rmsg = new RecebeMsg(ed, cliente.in, visor, contador);
                                t = new Thread(rmsg);
                                t.start();
                                abriu = true;
                            } catch (IOException ex) {
                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    });
                    
                } catch (FileNotFoundException ex) {
                } catch (IOException | ClassNotFoundException ex) {
                }
            }
        });
    }

    /**
     * Copia um texto selecionado no visor para uma variável da classe GUI.
     *
     * @throws NullPointerException
     */
    public void copiar() {
        if (visor.getSelectedText() == null) {
            throw new NullPointerException("Nenhum Texto Selecionado");
        }

        this.copiado = visor.getSelectedText();
        visor.select(0, 0);
    }

    /**
     * Recorta um texto selecionado no visor para uma variável da classe GUI.
     *
     * @throws NullPointerException
     */
    public void recortar() {
        if (visor.getSelectedText() == null) {
            throw new NullPointerException("Nenhum Texto Selecionado");
        }

        String str = "";
        this.copiado = visor.getSelectedText();
        int ini = visor.getSelectionStart();
        int fim = visor.getSelectionEnd();

        for (int i = ini; i < fim; i++) {
            this.ed.getT().getText().remove(ini);
        }

        for (char i : this.ed.getT().getText()) {
            str = str.concat(String.valueOf(i));
        }

        visor.setText("");
        visor.setText(str);
        visor.select(0, 0);
    }

    /**
     * Cola uma string salva na área de transferência no fim do texto.
     */
    public void colar() {
        String str = this.visor.getText();

        str = str.concat(this.copiado);

        ed.inseretexto(this.copiado);

        visor.setText("");
        visor.setText(str);
    }
}
