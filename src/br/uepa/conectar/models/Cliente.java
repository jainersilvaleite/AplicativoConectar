package br.uepa.conectar.models;

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

    public Proposta cadastrarProposta() {
        boolean cadastroCompleto = false; // verifica se o cadastro da proposta foi terminado
        Scanner entradaInformacao = new Scanner(System.in); // armazena a entrada de informação da proposta
        String informacao; // preenchimento da informação da proposta para cadastro
        Proposta novaProposta = new Proposta(); // instância da proposta a ser cadastrada

        while (!cadastroCompleto) {
            System.out.println();
            System.out.println("Preencha os dados da proposta:");
            System.out.println("------------------------------------");

            try {
                System.out.println();
                System.out.print("Título da proposta: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do titulo da proposta
                novaProposta.setTitulo(informacao);

                System.out.print("Tipo da proposta: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do tipo da proposta
                novaProposta.setTipo(informacao);

                System.out.print("Descrição da proposta: ");
                informacao = entradaInformacao.nextLine(); // preenchimento da descrição da proposta
                novaProposta.setDescricao(informacao);

                System.out.println("Proposta cadastrada com sucesso!");
                System.out.println("Aguarde até que um prestador de serviços atenda seu pedido.");
                System.out.println();

                novaProposta.setCliente(this); // define o cliente autor da proposta
                getPropostas().add(novaProposta); // adiciona a proposta cadastrada a lista de propostas do cliente
                cadastroCompleto = true;
            } catch (Exception e) {
                System.out.println("[ERRO] Ocorreu um erro ao realizar o cadastro da proposta: " + e.getMessage());
                System.out.println("Reiniciando o preenchimento de dados...");
                System.out.println();
            }
        }

        return novaProposta;
    }

    public void pesquisarPrestadores(List<Usuario> usuarios) {
        boolean prestadoresVisualizados = false; // verifica se o usuário fechou o menu de visualização de prestadores
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        int opcao; // armazena a opção inserida pelo usuário recentemente
        String texto; // armazena a informação de texto solicitada recentemente

        while (!prestadoresVisualizados) {
            System.out.println();
            System.out.println();
            System.out.print("Insira um prestador para buscar: ");
            texto = entradaTexto.nextLine();

            System.out.println();
            System.out.println("Resultados da busca: " + texto);
            System.out.println("------------------------------------");
            List<Prestador> resultadosPesquisa = super.pesquisarPrestadoresPorNome(usuarios, texto);
            if (!resultadosPesquisa.isEmpty()) {
                for (Prestador prestador: resultadosPesquisa) {
                    System.out.println("-> Id: " + prestador.getId() + " | Nome: " + prestador.getNome());
                    System.out.println("Formações: " + prestador.getFormacoes());
                    System.out.println();
                }
            } else {
                System.out.println("Nenhum prestador encontrado para esta busca!");
            }

            System.out.println("------------------------------------");
            System.out.println("O que gostaria de fazer agora?");
            System.out.println("1 - Visualizar perfil de um prestador.");
            System.out.println("2 - Voltar ao menu anterior.");
            System.out.println("Insira qualquer outro dígito para refazer a busca.");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.print("Insira o id do prestador: ");
                    opcao = entradaOpcao.nextInt();

                    Prestador prestador = super.consultarPrestadorPorId(usuarios, opcao);
                    if (prestador != null) {
                        visualizarPerfilDoPrestador(prestador);
                    } else {
                        System.out.println("[ERRO] O id informado não corresponde a um prestador cadastrado!");
                    }
                    break;
                case 2:
                    prestadoresVisualizados = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void visualizarPerfilDoPrestador(Prestador prestador) {
        System.out.println();
        System.out.println(prestador.getNome());
        System.out.println("------------------------------------");
        System.out.println("CPF/CNPJ: " + prestador.getCnpj());
        System.out.println("Formações: " + prestador.getFormacoes());
        System.out.println("Serviços:");

        var servicosPrestador = prestador.getServicos();
        if (!servicosPrestador.isEmpty()) {
            for (Servico servico: servicosPrestador) {
                servico.exibirDetalhes();
            }

            System.out.println();
        } else {
            System.out.println("Este prestador não cadastrou nenhum serviço por enquanto!");
        }
    }

    public void pesquisarServicos(List<Servico> servicos) {
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        String texto; // armazena a informação de texto solicitada recentemente

        System.out.println();
        System.out.print("Sua busca: ");
        texto = entradaTexto.nextLine();

        System.out.println();
        System.out.println("Resultados da busca: " + texto);
        System.out.println("------------------------------------");
        List<Servico> resultadosPesquisa = super.pesquisarServicosPorTitulo(servicos, texto);
        if (!resultadosPesquisa.isEmpty()) {
            super.visualizarServicos(resultadosPesquisa, new ArrayList<>(), new ArrayList<>());
        } else {
            System.out.println("Nenhum serviço encontrado para esta busca!");
        }
    }

    public void solicitarServico(List<Servico> servicos) {
        Scanner entradaId = new Scanner(System.in); // possibilita a entrada do usuário com algum id de serviço
        int id; // armazena o id do serviço inserida pelo usuário recentemente

        System.out.println();
        System.out.print("Insira o id do serviço: ");
        id = entradaId.nextInt();

        Servico servico = super.consultarServicoPorId(servicos, id);
        if (servico != null) {
            // cadastra uma proposta para o serviço solicitado indiretamente
            Proposta propostaServico = new Proposta();
            propostaServico.setCliente(this);
            propostaServico.setTitulo("Proposta para " + servico.getPrestador().getNome());
            propostaServico.setTipo("Proposta indireta (" + servico.getTipo() + ")");
            propostaServico.setDescricao("Olá, gostaria de solicitar o serviço " + servico.getTitulo());

            // sistema de geração de ids para propostas indiretas
            int idProposta = servico.getPropostas().size() + 1000; // id da proposta de acordo com a quantidade existente
            // o sistema tenta utilizar o idProposta inicial, mas se não conseguir, gera um novo id até que seja possível
            while (super.consultarPropostaPorId(servico.getPropostas(), idProposta) != null) {
                idProposta++;
            }

            // após encontrar um id que não esteja sendo usado, o sistema associa a nova proposta indireta
            propostaServico.setId(idProposta);

            servico.receberProposta(propostaServico);
        } else {
            System.out.println("[ERRO] O id informado não corresponde a um serviço cadastrado!");
        }
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
    public void visualizarServicos(List<Servico> servicos, List<Chat> chats, List<Proposta> propostas) {
        System.out.println();
        System.out.println("Serviços cadastrados no aplicativo");
        System.out.println("------------------------------------");

        super.visualizarServicos(servicos, chats, propostas);

        boolean servicosVisualizados = false; // verifica se o usuário decidiu fechar o menu de visualização de serviços
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        int opcao; // armazena a opção inserida pelo usuário recentemente

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
                    solicitarServico(servicos);
                    break;
                case 2:
                    pesquisarServicos(servicos);
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
    public int visualizarPropostas(List<Proposta> propostas) {
        System.out.println();
        System.out.println("Suas propostas cadastradas no aplicativo");
        System.out.println("------------------------------------");

        super.visualizarPropostas(getPropostas());

        boolean propostasVisualizadas = false; // verifica se o usuário decidiu fechar o menu de visualização de propostas
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        int opcao; // armazena a opção inserida pelo usuário recentemente
        Proposta proposta = new Proposta(); // armazena a proposta em foco
        proposta.setId(-1); // id padrão para o caso de nenhum chat de proposta ter sido acessado

        while (!propostasVisualizadas) {
            System.out.println();
            System.out.println("O que gostaria de fazer agora?");
            System.out.println("1 - Remover proposta.");
            System.out.println("2 - Acessar chat da proposta.");
            System.out.println("3 - Voltar ao menu anterior.");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.print("Insira o id da proposta: ");
                    opcao = entradaOpcao.nextInt();

                    proposta = super.consultarPropostaPorId(propostas, opcao);
                    if (proposta != null) {
                        getPropostas().remove(proposta);
                        System.out.println("Proposta removida com sucesso!");
                    } else {
                        System.out.println("[ERRO] O id informado não corresponde a uma proposta cadastrada!");
                    }
                    break;
                case 2:
                    System.out.println();
                    System.out.print("Insira o id da proposta: ");
                    opcao = entradaOpcao.nextInt();

                    proposta = super.consultarPropostaPorId(propostas, opcao);
                    if (proposta != null) {
                        // finaliza a visualização de propostas para acessar o chat de uma proposta específica
                        propostasVisualizadas = true;
                    } else {
                        System.out.println("[ERRO] O id informado não corresponde a uma proposta cadastrada!");
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
        // retorno do id do chat (-1 se não foi selecionado nenhum)
        if (proposta != null) {
            return proposta.getId();
        } else return -1;
    }
}
