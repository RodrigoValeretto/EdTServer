/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

/**
 * Armazena as alterações que foram feitas pelas funções de inserir, remover e copiar texto.
 * @author Rodrigo Valeretto e Leonardo Cerce
 */
public class Alteracoes {
    private String str;
    private String comando;

    /**
     * Construtor da classe Alterações
     * @param str
     * @param comando 
     */
    public Alteracoes(String str, String comando) {
        this.str = str;
        this.comando = comando;
    }

    /**
     * Retorna a string correspondente ao parâmetro "str" da classe Alterações
     * @return String 
     */
    public String getStr() {
        return str;
    }

    /**
     * Atribui ao parâmetro "str" da classe Alterações uma string que é passada na função
     * @param str 
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * Retorna a string correspondente ao parâmetro "comando" da classe Alterações
     * @return String 
     */
    public String getComando() {
        return comando;
    }

    /**
     * Atribui ao parâmetro "comando" da classe Alterações uma string que é passada na função
     * @param comando 
     */
    public void setComando(String comando) {
        this.comando = comando;
    }
    
    
    
}
