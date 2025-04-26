package br.uepa.conectar.model;

import java.time.LocalDate;

public class Mensagem {
    private int id;
    private Usuario autor;
    private LocalDate dataEnvio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public void exibirDetalhes() {

    }
}
