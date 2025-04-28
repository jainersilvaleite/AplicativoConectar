package br.uepa.conectar.model;

import java.util.ArrayList;
import java.util.List;

public class Prestador extends Usuario {
    private String cnpj;
    private String formacoes;
    private List<Servico> servicos;

    // gera um perfil de prestador baseado nas informações originais do usuário
    public Prestador(Usuario usuario) {
        setId(usuario.getId());
        setNome(usuario.getNome());
        setEmail(usuario.getEmail());
        setTelefone(usuario.getTelefone());
        setEndereco(usuario.getEndereco());
        setDataNascimento(usuario.getDataNascimento());
        setIsAutenticado(usuario.getIsAutenticado());
        this.servicos = new ArrayList<>(); // define o prestador como sem serviços por padrão

        if (getId() == 1) {
            this.cnpj = "99.999.999/0001-99"; // cpnj fictício da Conectar
            this.formacoes = "Técnico em Tecnologia da Informação"; // formação do administrador da Conectar
        } else {
            this.cnpj = "Não informado."; // define o prestador como sem cpf/cnpj por padrão
            this.formacoes = "Não informado."; // define o prestador como sem formações por padrão
        }
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(String formacoes) {
        this.formacoes = formacoes;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public void cadastrarServico() {

    }

    public void pesquisarPropostas() {

    }

    public void atualizarStatusDeServico() {

    }

    public void gerarOrdemDeServico() {

    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("CPF/CNPJ: " + getCnpj());
        System.out.println("Formações: " + getFormacoes());
    }
}
