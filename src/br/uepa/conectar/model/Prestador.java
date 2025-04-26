package br.uepa.conectar.model;

import java.util.List;

public class Prestador {
    private String cnpj;
    private String formacoes;
    private List<Servico> servicos;

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
}
