package br.uepa.conectar.model;

import java.util.List;

public class Chat {
    private int id;
    private Cliente cliente;
    private Prestador prestador;
    private List<Mensagem> mensagens;

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

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public void enviarMensagem() {

    }

    public void visualizarMensagens() {

    }

    public void fechar() {

    }
}
