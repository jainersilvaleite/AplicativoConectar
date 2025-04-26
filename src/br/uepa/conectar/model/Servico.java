package br.uepa.conectar.model;

import java.util.List;

public class Servico {
    private int id;
    private Prestador prestador;
    private String titulo;
    private String tipo;
    private String descricao;
    private double preco;
    private Boolean isCompleto;
    private List<Proposta> propostas;
    private List<Avaliacao> avaliacoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Boolean getCompleto() {
        return isCompleto;
    }

    public void setCompleto(Boolean completo) {
        isCompleto = completo;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void receberProposta() {

    }

    public void visualizarAvaliacoes() {

    }

    public void exibirDetalhes() {

    }
}
