package br.uepa.conectar.model;

import br.uepa.conectar.util.Consultavel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servico implements Consultavel {
    private int id;
    private Prestador prestador;
    private String titulo;
    private String tipo;
    private String descricao;
    private double preco;
    private List<Proposta> propostas;
    private List<Avaliacao> avaliacoes;

    public Servico() {
        this.propostas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

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

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void receberProposta(Proposta proposta) {
        getPropostas().add(proposta);
        System.out.println("Serviço solicitado com sucesso! Aguarde o retorno do Prestador");
    }

    public void visualizarAvaliacoes() {

    }

    public void visualizarPropostas() {
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        String texto; // armazena a informação de texto solicitada recentemente

        System.out.println();
        System.out.println("Propostas para o serviço selecionado");
        System.out.println("------------------------------------");

        if (!getPropostas().isEmpty()) {
            for (Proposta proposta: getPropostas()) {
                proposta.exibirDetalhes();
            }

            System.out.println();
        } else {
            System.out.println("Nenhuma proposta foi enviada para este serviço!");
        }

        boolean propostasVisualizadas = false; // verifica se o usuário decidiu fechar o menu de visualização de propostas
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        int opcao; // armazena a opção inserida pelo usuário recentemente

        while (!propostasVisualizadas) {
            System.out.println();
            System.out.println("O que gostaria de fazer agora?");
            System.out.println("1 - Acessar chat da proposta.");
            System.out.println("2 - Buscar propostas.");
            System.out.println("3 - Voltar ao menu anterior.");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                case 1:
                    // acessar chat da proposta
                    break;
                case 2:
                    System.out.println();
                    System.out.println();
                    System.out.print("Sua busca: ");
                    texto = entradaTexto.nextLine();

                    System.out.println();
                    System.out.println("Resultados da busca: " + texto);
                    System.out.println("------------------------------------");
                    List<Proposta> resultadosPesquisa = pesquisarPropostasPorTitulo(getPropostas(), texto);
                    if (!resultadosPesquisa.isEmpty()) {
                        for (Proposta proposta: resultadosPesquisa) {
                            proposta.exibirDetalhes();
                        }
                    } else {
                        System.out.println("Nenhuma proposta encontrada para esta busca!");
                    }
                    break;
                case 3:
                    propostasVisualizadas = true;
                    break;
                default:
                    System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                    System.out.println();
                    break;
            }
        }
    }

    public void exibirDetalhes() {
        System.out.println("-> Id: " + getId() + " | Título: " + getTitulo());
        System.out.println("De: " + getPrestador().getNome());
        System.out.println("Tipo: " + getTipo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Preço: " + getPreco() + " R$");
        System.out.println();
    }
}
