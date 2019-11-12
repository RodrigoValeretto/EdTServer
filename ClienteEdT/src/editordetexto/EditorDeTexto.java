/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Rodrigo Augusto Valeretto e Leonardo Cerce Guioto
 */
package editordetexto;

import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * Controla todo o funcionamento do editor em si, e tem as funções vitais do mesmo.
 * @author Rodrigo Valeretto e Leonardo Cerce
 */
public class EditorDeTexto extends JFrame{
    private Texto t;
    private LinkedList<Alteracoes> desfaz;
    private LinkedList<Alteracoes> refaz;

    public Texto getT() {
        return t;
    }

    public void setT(Texto t) {
        this.t = t;
    }

    public LinkedList<Alteracoes> getDesfaz() {
        return desfaz;
    }

    public void setDesfaz(LinkedList<Alteracoes> desfaz) {
        this.desfaz = desfaz;
    }

    public LinkedList<Alteracoes> getRefaz() {
        return refaz;
    }

    public void setRefaz(LinkedList<Alteracoes> refaz) {
        this.refaz = refaz;
    }
    /**
     * Imprime na tela do usuário o texto completo por ele editado.
     */
    public void exibetexto()
    {
        for(char l : this.t.getText())
        {
            System.out.print(l);
        }
        
        System.out.print("\n");
    }
    
    /**
     * Função que desfaz a última alteração feita pelo usuário, salvando-a na lista de possíveis ações a serem refeitas.
     * @throws NullPointerException 
     */
    public void desfazer() throws NullPointerException
    {
        if(this.desfaz.isEmpty())
        {
            throw new NullPointerException("\nNão há operações para serem desfeitas!\n");
        }
        
        Alteracoes e;
        String aux = this.desfaz.getLast().getComando();
        if(aux.equals("1"))
        {
            e = this.desfaz.removeLast();
            try 
            {
                this.removetexto(e.getStr().length());
            }catch(InvalidPropertiesFormatException ex)
            {
                System.out.println(ex.getMessage());
            }
            e.setComando("2");
            this.refaz.add(e);
        }
        
        if(aux.equals("2"))
        {
            e = this.desfaz.removeLast();
            this.inseretexto(e.getStr());
            e.setComando("1");
            this.refaz.add(e);
            
        }
            
    }
    
    /**
     * Função que refaz a última modificação que foi desfeita pelo usuário.
     * @throws NullPointerException 
     */
        public void refazer() throws NullPointerException
    {
        if(this.refaz.isEmpty())
        {
            throw new NullPointerException("\nNão há operações para serem refeitas!\n");
        }
        
        Alteracoes e;
        String aux = this.refaz.getLast().getComando();
        if(aux.equals("1"))
        {
            e = this.refaz.removeLast();
            
            try 
            {
                this.removetexto(e.getStr().length());
            }catch(InvalidPropertiesFormatException ex) 
            {
                System.out.println(ex.getMessage());
            }
            
            e.setComando("2");
            this.desfaz.add(e);
        }
        
        if(aux.equals("2"))
        {
            e = this.refaz.removeLast();
            this.inseretexto(e.getStr());
            e.setComando("1");
            this.desfaz.add(e);
            
        }
            
    }

    /**
     * Função que remove uma determinada quantidade de caracteres, especificada pelo usuário.
     * @param num
     * @return String
     * @throws InvalidPropertiesFormatException 
     */
    public String removetexto(int num) throws InvalidPropertiesFormatException
    {
        if(num > this.t.getText().size())
        {
            throw new InvalidPropertiesFormatException("\nNumero maior do que o tamanho total do texto!\n");
        }
        
        StringBuffer x = new StringBuffer();
        String aux;
        
        for(int i = 0; i < num; i++)
        {
          x = x.append(this.t.getText().removeLast());
        }
        
        aux = x.reverse().toString();
        
        return aux;
    }
    
    /**
     * Função que insere o texto digitado pelo usuário até que ele pressione "Enter".
     * @param str
     * @throws NullPointerException 
     */
    public void inseretexto(String str) throws NullPointerException
    {
        if(str.isEmpty())
        {
            throw new NullPointerException("\nNenhum texto digitado!\n");
        }
        
        int i;          
        for(i = 0; i < str.length(); i++)
        {
            this.t.getText().add(str.charAt(i));
        }
    }

    /**
     * Função que insere um elemento da classe Alterações em uma lista encadeada de alterações passada na função
     * @param a
     * @param str
     * @param n 
     */
    public void inserealteracao(LinkedList<Alteracoes> a, String str, String n)
    {
        Alteracoes e = new Alteracoes(str, n);
        a.add(e);
    }
    
    /**
     * Função que remove o último elemento da classe Alterações de uma lista encadeada de alterações passada na função
     * @param a
     * @return Retorna o elemento removido da lista
     */
    public Alteracoes removealteracao(LinkedList<Alteracoes> a)
    {
        Alteracoes e = a.removeLast();
        return e;
    }
    
    /**
     * Construtor da classe EditorDeTexto
     */
    public EditorDeTexto() {
        this.t = new Texto();
        this.desfaz = new LinkedList();
        this.refaz = new LinkedList();
    }
    
    /**
     * Programa principal do editor de texto
     * @param args the command line arguments
     */    
    public static void main(String[] args) {
        EditorDeTexto e = new EditorDeTexto();
        Scanner scan = new Scanner(System.in);
        String aux;
        int num;
        String n;
        String op;
        GUI g = new GUI(e);
        
        g.setVisible(true);
        
        
        while(true)
        {
            System.out.println("---------------------------------------------");
            System.out.println("Selecione a opção desejada:");
            System.out.println("0 - Sair do programa");
            System.out.println("1 - Insere Texto");
            System.out.println("2 - Exclui texto");
            System.out.println("3 - Exibe Texto");
            System.out.println("4 - Pula Linha");
            System.out.println("5 - Desfazer");
            System.out.println("6 - Refazer");
            System.out.println("---------------------------------------------");
            
            switch(op = scan.nextLine())
            {
                case "0":
                    return;
                
                case "1":
                    aux = scan.nextLine();
                    try
                    {
                        e.inseretexto(aux);
                        e.inserealteracao(e.desfaz,aux, op);
                        e.refaz.clear();
                    }catch(NullPointerException f)
                    {
                        System.out.println(f.getMessage());
                    }
                    break;
                
                case "2":
                    System.out.println("Quantos caracteres deseja remover:");
                    num = scan.nextInt();
                    scan.nextLine();
                {
                    try 
                    {
                        aux = e.removetexto(num);
                        e.inserealteracao(e.desfaz,aux, op);
                        e.refaz.clear();
                    }catch(InvalidPropertiesFormatException f)
                    {
                        System.out.println(f.getMessage());
                    }
                }
                    break;

                    
                case "3":
                    e.exibetexto();
                    break;
                    
                case "4":
                    e.inseretexto("\n");
                    e.inserealteracao(e.desfaz,"\n", "1");
                    e.refaz.clear();
                    break;
                    
                case "5":
                    try
                    {
                        e.desfazer();
                    }catch(NullPointerException f)
                    {
                        System.out.println(f.getMessage());
                    }
                    break;
                    
                case "6":
                    try
                    {
                        e.refazer();
                    }catch(NullPointerException f)
                    {
                        System.out.println(f.getMessage());
                    }
                    break;
            }
        }
        
        
    }
    
}
