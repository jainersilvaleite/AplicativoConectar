package br.uepa.conectar.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String cpf;
    private List<Proposta> propostas;

    // gera um perfil de cliente baseado nas informações originais do usuário
    public Cliente(Usuario usuario) {
        setId(usuario.getId());
        setNome(usuario.getNome());
        setEmail(usuario.getEmail());
        setTelefone(usuario.getTelefone());
        setEndereco(usuario.getEndereco());
        setDataNascimento(usuario.getDataNascimento());
        setIsAutenticado(usuario.getIsAutenticado());
        this.propostas = new ArrayList<>(); // define o cliente como sem propostas por padrão

        if (getId() == 1) {
            this.cpf = "999.999.999-99"; // cpf fictício do usuário administrador da Conectar
        } else {
            this.cpf = "Não informado."; // define o cliente como sem cpf por padrão
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public void cadastrarProposta() {

    }

    public void pesquisarPrestadores() {

    }

    public void visualizarPerfilDoPrestador() {

    }

    public void pesquisarServicos() {

    }

    public void solicitarServico() {

    }

    public void confirmarServico() {

    }

    public void avaliarServico() {

    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("CPF: " + getCpf());
    }
}
