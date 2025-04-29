package br.uepa.conectar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    @Override
    public void visualizarServicos(List<Servico> servicos) {
        System.out.println();
        System.out.println("Serviços cadastrados no aplicativo");
        System.out.println("------------------------------------");

        super.visualizarServicos(servicos);

        boolean servicosVisualizados = false; // verifica se o usuário decidiu fechar o menu de visualização de serviços
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        int opcao; // armazena a opção inserida pelo usuário recentemente
        String texto; // armazena a informação de texto solicitada recentemente

        while (!servicosVisualizados) {
            System.out.println();
            System.out.println("O que gostaria de fazer agora?");
            System.out.println("1 - Selecionar um serviço.");
            System.out.println("2 - Buscar serviços.");
            System.out.println("3 - Voltar ao menu anterior.");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.print("Insira o id do serviço: ");
                    opcao = entradaOpcao.nextInt();

                    Servico servico = super.consultarServicoPorId(servicos, opcao);
                    if (servico != null) {
                        System.out.println("Serviço solicitado com sucesso! Aguarde o retorno do Prestador");
                    } else {
                        System.out.println("[ERRO] O id informado não corresponde a um serviço cadastrado!");
                    }
                    break;
                case 2:
                    System.out.println();
                    System.out.print("Sua busca: ");
                    texto = entradaTexto.nextLine();

                    System.out.println();
                    System.out.println("Resultados da busca: " + texto);
                    System.out.println("------------------------------------");
                    List<Servico> resultadosPesquisa = super.pesquisarServicosPorTitulo(servicos, texto);
                    if (!resultadosPesquisa.isEmpty()) {
                        super.visualizarServicos(resultadosPesquisa);
                    } else {
                        System.out.println("Nenhum serviço encontrado para esta busca!");
                    }
                    break;
                case 3:
                    servicosVisualizados = true;
                    break;
                default:
                    System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                    System.out.println();
                    break;
            }
        }
    }
}
