package br.uepa.conectar.models;

import br.uepa.conectar.utils.Consultavel;

import java.lang.module.FindException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario implements Consultavel {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private LocalDate dataNascimento;
    private Boolean isAutenticado;
    private List<Usuario> perfis;

    // define o usuário como não autenticado e sem perfis por padrão
    public Usuario() {
        this.isAutenticado = false;
        this.perfis = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getIsAutenticado() {
        return isAutenticado;
    }

    public void setIsAutenticado(Boolean isAutenticado) {
        this.isAutenticado = isAutenticado;
    }

    public List<Usuario> getPerfis() {
        return perfis;
    }

    public void fazerLogin(String senhaInformada) {
        // compara a senha salva pelo usuário com a senha informada para validação do login
        if (senhaInformada.equals(senha)) {
            setIsAutenticado(true);
            // atualiza a informação para todos os perfis que o usuário possua
            for (Usuario perfil: perfis) {
                perfil.setIsAutenticado(true);
            }
        } else {
            setIsAutenticado(false);
            // atualiza a informação para todos os perfis que o usuário possua
            for (Usuario perfil: perfis) {
                perfil.setIsAutenticado(false);
            }
        }
    }

    public void preencherDadosDePerfil(List<Usuario> usuarios, int idContadorUsuario) {
        boolean cadastroCompleto = false; // verifica se o cadastro foi terminado
        Scanner entradaInformacao = new Scanner(System.in); // armazena a entrada de informação do usuário
        String informacao; // preenchimento da informação do usuário para cadastro

        while (!cadastroCompleto) {
            System.out.println();
            System.out.println("Preencha seus dados de cadastro:");
            System.out.println("------------------------------------");

            try {
                System.out.println();
                System.out.print("Seu nome: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do nome do usuário
                setNome(informacao);

                System.out.print("Seu email: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do email do usuário
                setEmail(informacao);

                if (consultarUsuarioPorEmail(usuarios, email) != null) {
                    throw new FindException("O e-mail informado já existe!");
                }

                System.out.print("Sua senha: ");
                informacao = entradaInformacao.nextLine(); // preenchimento da senha do usuário
                setSenha(informacao);

                System.out.print("Seu telefone: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do telefone do usuário
                setTelefone(informacao);

                System.out.print("Seu endereço: ");
                informacao = entradaInformacao.nextLine(); // preenchimento do endereço do usuário
                setEndereco(informacao);

                System.out.print("Sua data de nascimento: ");
                informacao = entradaInformacao.nextLine(); // preenchimento da data de nascimento do usuário
                setDataNascimento(LocalDate.parse(informacao));

                System.out.println("Cadastro realizado com sucesso!");
                System.out.println();

                setId(idContadorUsuario); // preenchimento automático do id do usuário

                getPerfis().add(new Cliente(this)); // gera um perfil de cliente para o usuário recém-criado
                getPerfis().add(new Prestador(this)); // gera um perfil de prestador para o usuário recém-criado
                cadastroCompleto = true;
            } catch (Exception e) {
                System.out.println("[ERRO] Ocorreu um erro ao realizar o cadastro: " + e.getMessage());
                System.out.println("Tente utilizar o formato ANO-MÊS(zero incluso)-DIA.");
                System.out.println("Reiniciando o preenchimento de dados...");
                System.out.println();
            }
        }
    }

    public Usuario escolherPerfil() {
        boolean escolhaDePerfilCompleta = false; // verifica se a escolha do perfil foi terminada
        Usuario perfilSelecionado = new Usuario(); // armazena o perfil selecionado pelo usuário
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        int opcao; // armazena a opção inserida pelo usuário recentemente
        String texto; // armazena a informação de texto solicitada recentemente

        System.out.println();
        System.out.println("Com qual perfil deseja acessar o aplicativo?");
        System.out.println("------------------------------------------------");

        while (!escolhaDePerfilCompleta) {
            System.out.println("1 - Cliente.");
            System.out.println("2 - Prestador.");
            if (getId() == 1) System.out.println("3 - Administrador.");
            System.out.println();
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            // retorna o perfil selecionado pelo usuário para acessar o aplicativo
            switch (opcao) {
                // caso o usuário selecione o perfil de Cliente, ele deve informar seu cpf, se não tiver
                case 1:
                    Cliente cliente = (Cliente) getPerfis().getFirst(); // lista de perfis (ordem): cliente, prestador, administrador

                    // caso possua cpf
                    if (!cliente.getCpf().equals("Não informado.")) {
                        System.out.println();
                        System.out.println("Você selecionou o perfil de Cliente. Redirecionando...");
                        perfilSelecionado = cliente;
                        escolhaDePerfilCompleta = true;
                        break;
                    }

                    // caso não possua cpf
                    System.out.println();
                    System.out.print("Para prosseguir, insira seu CPF/CNPJ: ");
                    texto = entradaTexto.nextLine();

                    cliente.setCpf(texto);
                    getPerfis().set(0, cliente);
                    System.out.println("Você selecionou o perfil de Cliente. Redirecionando...");
                    perfilSelecionado = cliente;
                    escolhaDePerfilCompleta = true;
                    break;
                // caso o usuário selecione o perfil de Prestador, ele deve informar seu cnpj e formações, se não tiver
                case 2:
                    Prestador prestador = (Prestador) getPerfis().get(1);

                    // caso não possua cnpj
                    if (prestador.getCnpj().equals("Não informado.")) {
                        System.out.println();
                        System.out.print("Para prosseguir, insira seu CPF/CNPJ: ");
                        texto = entradaTexto.nextLine();

                        prestador.setCnpj(texto);
                    }

                    // caso não possua formações
                    if (prestador.getFormacoes().equals("Não informado.")) {
                        System.out.println();
                        System.out.print("Para prosseguir, insira suas formações: ");
                        texto = entradaTexto.nextLine();

                        prestador.setFormacoes(texto);
                    }

                    getPerfis().set(1, prestador);
                    System.out.println("Você selecionou o perfil de Prestador. Redirecionando...");
                    perfilSelecionado = prestador;
                    escolhaDePerfilCompleta = true;
                    break;
                case 3:
                    if (getId() == 1) {
                        System.out.println("Você selecionou o perfil de Administrador. Redirecionando...");
                        perfilSelecionado = getPerfis().getLast();
                        escolhaDePerfilCompleta = true;
                        break;
                    } else {
                        System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                        System.out.println();
                        break;
                    }
                default:
                    System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                    System.out.println();
                    break;
            }
        }

        return perfilSelecionado;
    }

    public void exibirDetalhes() {
        System.out.println();
        System.out.println("Detalhes da sua conta:");
        System.out.println("------------------------------------");
        System.out.println("Nome: " + getNome());
        System.out.println("E-mail: " + getEmail());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Nascimento: " + getDataNascimento().toString());
    }

    // visualiza propostas e possívelmente retorna o id de um chat
    public int visualizarPropostas(List<Proposta> propostas) {
        if (!propostas.isEmpty()) {
            for (Proposta proposta: propostas) {
                proposta.exibirDetalhes();
            }

            System.out.println();
        } else {
            System.out.println("Nenhuma proposta foi cadastrada por enquanto!");
        }
        return -1;
    }

    public void visualizarServicos(List<Servico> servicos, List<Chat> chats, List<Proposta> propostas) {
        if (!servicos.isEmpty()) {
            for (Servico servico: servicos) {
                servico.exibirDetalhes();
            }

            System.out.println();
            if (!(this instanceof Prestador)) {
                System.out.println("Também quer ter seus serviços na Conectar? Acesse seu perfil de Prestador!");
            }
        } else {
            System.out.println("Nenhum serviço foi cadastrado por enquanto!");
            if (!(this instanceof Prestador)) {
                System.out.println("Que tal ser o primeiro? Acesse seu perfil de Prestador e comece já!");
            }
        }
    }

    public void aceitarProposta() {

    }

    public void encerrarLogin() {
        System.out.println();
        System.out.println("Encerrando sessão...");
        System.out.println();
        setIsAutenticado(false);
        // atualiza a informação para todos os perfis que o usuário possua
        for (Usuario perfil: perfis) {
            perfil.setIsAutenticado(false);
        }
    }
}
