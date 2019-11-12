/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.util.LinkedList;

/**
 * Controla o armazenamento dos caracteres que compõem o texto.
 * @author Rodrigo Valeretto e Leonardo Cerce
 */
public class Texto {
    private LinkedList<Character> text;

    /**
     * Construtor da classe Texto que aloca uma lista encadeada ao parâmetro "text"
     */
    public Texto() {
        this.text = new LinkedList();
    }

    /**
     * Retorna uma lista encadeada de caracteres correspondentes ao parametro "text" da classe Texto
     * @return Lista encadeada de caracteres correspondentes à "text"
     */
    public LinkedList<Character> getText() {
        return text;
    }

    /**
     * Atribui à lista encadeada correspondente ao parâmetro "text" uma nova lista encadeada passada na função
     * @param text 
     */
    public void setText(LinkedList<Character> text) {
        this.text = text;
    }

    /**
     * Converte para string a lista encadeada do parâmetro "text" da classe Texto
     * @return String
     */
    @Override
    public String toString() {
        return "Texto{" + "text=" + text + '}';
    }
}
