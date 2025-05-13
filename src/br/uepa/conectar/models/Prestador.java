package br.uepa.conectar.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public Servico cadastrarServico() {
        boolean cadastroCompleto = false; // verifica se o cadastro do serviço foi terminado
        Scanner entradaInformacao = new Scanner(System.in); // armazena a entrada de informação do serviço
        String informacao; // preenchimento da informação do serviço para cadastro
        double precoServico; // preenchimento do preço do serviço para cadastro
        Servico novoServico = new Servico(); // instância do serviço a ser cadastrada

        while (!cadastroCompleto) {
            System.out.println();
            System.out.println("Preencha os dados do serviço:");
            System.out.println("------------------------------------");

            try {
                System.out.println();
                System.out.print("Título do serviço: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do titulo do serviço
                novoServico.setTitulo(informacao);

                System.out.print("Tipo do serviço: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do tipo do serviço
                novoServico.setTipo(informacao);

                System.out.print("Descrição do serviço: ");
                informacao = entradaInformacao.nextLine(); // preenchimento da descrição do serviço
                novoServico.setDescricao(informacao);

                System.out.print("Preço do serviço: ");
                precoServico = entradaInformacao.nextDouble(); // preenchimento do preço do serviço
                novoServico.setPreco(precoServico);

                System.out.println("Serviço cadastrado com sucesso!");
                System.out.println();

                novoServico.setPrestador(this); // define o prestador autor do serviço
                getServicos().add(novoServico); // adiciona o serviço cadastrado a lista de propostas do cliente
                cadastroCompleto = true;
            } catch (Exception e) {
                System.out.println("[ERRO] Ocorreu um erro ao realizar o cadastro do serviço: " + e.getMessage());
                System.out.println("Reiniciando o preenchimento de dados...");
                System.out.println();
            }
        }

        return novoServico;
    }

    public void pesquisarPropostas(List<Proposta> propostas) {
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        String texto; // armazena a informação de texto solicitada recentemente

        System.out.println();
        System.out.print("Sua busca: ");
        texto = entradaTexto.nextLine();

        System.out.println();
        System.out.println("Resultados da busca: " + texto);
        System.out.println("------------------------------------");
        List<Proposta> resultadosPesquisa = super.pesquisarPropostasPorTitulo(propostas, texto);
        if (!resultadosPesquisa.isEmpty()) {
            super.visualizarPropostas(resultadosPesquisa);
        } else {
            System.out.println("Nenhuma proposta encontrada para esta busca!");
        }
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

    @Override
    public void visualizarServicos(List<Servico> servicos) {
        System.out.println();
        System.out.println("Seus serviços cadastrados no aplicativo");
        System.out.println("------------------------------------");

        super.visualizarServicos(getServicos());

        boolean servicosVisualizados = false; // verifica se o usuário decidiu fechar o menu de visualização de serviços
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        int opcao; // armazena a opção inserida pelo usuário recentemente
        Servico servico; // armazena o serviço selecionado pelo usuário

        while (!servicosVisualizados) {
            System.out.println();
            System.out.println("O que gostaria de fazer agora?");
            System.out.println("1 - Remover serviço.");
            System.out.println("2 - Visualizar propostas de um serviço.");
            System.out.println("3 - Voltar ao menu anterior.");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.print("Insira o id do serviço: ");
                    opcao = entradaOpcao.nextInt();

                    servico = super.consultarServicoPorId(servicos, opcao);
                    if (servico != null) {
                        getServicos().remove(servico);
                        System.out.println("Serviço removido com sucesso!");
                    } else {
                        System.out.println("[ERRO] O id informado não corresponde a um serviço cadastrado!");
                    }
                    break;
                case 2:
                    System.out.println();
                    System.out.print("Insira o id do serviço: ");
                    opcao = entradaOpcao.nextInt();

                    servico = super.consultarServicoPorId(servicos, opcao);
                    if (servico != null) {
                        servico.visualizarPropostas();
                    } else {
                        System.out.println("[ERRO] O id informado não corresponde a um serviço cadastrado!");
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

    @Override
    public void visualizarPropostas(List<Proposta> propostas) {
        System.out.println();
        System.out.println("Propostas cadastradas no aplicativo");
        System.out.println("------------------------------------");

        super.visualizarPropostas(propostas);

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
                    pesquisarPropostas(propostas);
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
}
