package br.uepa.conectar.model;

import br.uepa.conectar.util.Consultavel;

public class Proposta implements Consultavel {
    private int id;
    private Cliente cliente;
    private String titulo;
    private String tipo;
    private String descricao;
    private Boolean aceiteCliente;
    private Boolean aceitePrestador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAceiteCliente() {
        return aceiteCliente;
    }

    public void setAceiteCliente(Boolean aceiteCliente) {
        this.aceiteCliente = aceiteCliente;
    }

    public Boolean getAceitePrestador() {
        return aceitePrestador;
    }

    public void setAceitePrestador(Boolean aceitePrestador) {
        this.aceitePrestador = aceitePrestador;
    }

    public void iniciarChat() {

    }

    public void exibirDetalhes() {
        System.out.println("-> Id: " + getId() + " | Título: " + getTitulo());
        System.out.println("De: " + getCliente().getNome());
        System.out.println("Tipo: " + getTipo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println();
    }
}
