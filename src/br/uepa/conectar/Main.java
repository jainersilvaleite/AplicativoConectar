package br.uepa.conectar;

import br.uepa.conectar.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // simulação do banco de dados
        List<Usuario> usuarios = new ArrayList<>(); // armazena todos os usuários do aplicativo
        List<Servico> servicos = new ArrayList<>(); // armazena todos os serviços do aplicativo
        List<Proposta> propostas = new ArrayList<>(); // armazena todas as propostas do aplicativo
        List<OrdemDeServico> ordensDeServico = new ArrayList<>(); // armazena todas as ordens de serviço do aplicativo

        // criação do usuário Administrador
        Usuario usuarioAdministrador = new Usuario();
        usuarioAdministrador.setId(1); // id do usuario com privilégios de administrador
        usuarioAdministrador.setNome("Administrador"); // nome do usuario com privilégios de administrador
        usuarioAdministrador.setEmail("conectar.adm@gmail.com"); // email do usuario com privilégios de administrador
        usuarioAdministrador.setSenha(".adm123@"); // senha do usuario com privilégios de administrador
        usuarioAdministrador.setTelefone("99999999999"); // telefone do usuario com privilégios de administrador
        usuarioAdministrador.setEndereco("Avenida das Empresas, 999"); // endereço do administrador
        usuarioAdministrador.setDataNascimento(LocalDate.now()); // data de nascimento provisória do administrador

        usuarioAdministrador.getPerfis().add(new Cliente(usuarioAdministrador)); // geração do perfil de cliente
        usuarioAdministrador.getPerfis().add(new Prestador(usuarioAdministrador)); // geração do perfil de prestador
        usuarioAdministrador.getPerfis().add(new Administrador(usuarioAdministrador)); // geração do perfil de adm

        usuarios.add(usuarioAdministrador);

        // variáveis de controle
        boolean aplicativoAtivo = true; // mantém o aplicativo ativo até que o usuário queira encerrá-lo
        Usuario usuarioLogado = new Usuario(); // armazena o usuário logado atualmente no aplicativo
        Usuario perfilSelecionado = new Usuario(); // armazena o perfil selecionado pelo usuário
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        boolean autenticacaoCompleta = false; // verifica se a autenticação foi terminada para enviá-lo a tela principal
        int idContadorUsuario = 2; // utilizado para geração dos ids dos usuários
        int idContadorProposta = 1; // utilizado para geração dos ids das propostas
        int idContadorServico = 1; // utilizado para geração dos ids dos serviços
        int opcao; // armazena a opção inserida pelo usuário recentemente
        String texto; // armazena a informação de texto solicitada recentemente

        // aplicativo ligado
        while (aplicativoAtivo) {
            // autenticação (login e/ou cadastro)
            if (!autenticacaoCompleta) {
                System.out.println("Bem-vindo(a) ao aplicativo Conectar!");
                System.out.println("1 - É novo por aqui? Crie sua conta.");
                System.out.println("2 - Já possui uma conta? Acesse agora mesmo.");
                System.out.println("3 - Encerrar o aplicativo.");
                System.out.println();
                System.out.print("Sua opção: ");
                opcao = entradaOpcao.nextInt();

                switch (opcao) {
                    // caso o usuário queira criar sua conta, ele precisará preencher seus dados
                    case 1:
                        Usuario novoUsuario = new Usuario(); // instância do usuário a ser criado
                        novoUsuario.preencherDadosDePerfil(usuarios, idContadorUsuario); // preenchimento dos dados do usuário
                        usuarios.add(novoUsuario); // novo usuário é inserido no "banco de dados"
                        idContadorUsuario++; // incremento do contador para geração dos ids de usuários
                        break;
                    // caso o usuário queira logar com sua conta, ele precisará informar e-mail e senha
                    case 2:
                        Usuario usuario = new Usuario(); // instância do usuário a fazer login
                        System.out.println();
                        System.out.println("Preencha seus dados de login:");
                        System.out.println("------------------------------------");
                        System.out.println();
                        System.out.print("Qual seu e-mail? ");
                        texto = entradaTexto.nextLine(); // preenchimento do email para consulta no "banco de dados"
                        usuario = usuario.consultarUsuarioPorEmail(usuarios, texto); // resultado da consulta

                        // continua-se com o login se o usuário for encontrado
                        if (usuario == null) {
                            System.out.println("[ERRO] O e-mail informado não está cadastrado!");
                            System.out.println();
                            break;
                        }

                        System.out.print("Olá, " + usuario.getNome() + "! Qual sua senha? ");
                        texto = entradaTexto.nextLine(); // preenchimento da senha para validação no "banco de dados"
                        usuario.fazerLogin(texto); // realiza o login do usuário se a senha for validada

                        // continua-se para a tela principal se o login do usuário for um sucesso
                        if (!usuario.getIsAutenticado()) {
                            System.out.println("[ERRO] A senha informada não confere!");
                            System.out.println();
                            break;
                        }

                        System.out.println("Login realizado com sucesso, estamos lhe redirecionando...");
                        usuarioLogado = usuario;
                        autenticacaoCompleta = true;
                        break;
                    // caso o usuário queira encerrar o aplicativo
                    case 3:
                        System.out.println();
                        System.out.println("Obrigado por utilizar nosso aplicativo! Encerrando...");
                        aplicativoAtivo = false;
                        break;
                    default:
                        System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                        System.out.println();
                        break;
                }
            }

            // área principal do aplicativo (pós-login)
            if (usuarioLogado.getIsAutenticado()) {
                perfilSelecionado = usuarioLogado.escolherPerfil();

                // exibição de informações e menus de acordo com o perfil selecionado
                if (perfilSelecionado instanceof Cliente) {
                    System.out.println("Perfil atual: Cliente");

                    // telas disponíveis para o perfil Cliente
                    boolean telaPrincipalAtiva = false; // verifica se o cliente saiu da tela principal

                    while (!telaPrincipalAtiva) {
                        System.out.println();
                        System.out.println("------------------------------------");
                        System.out.println("O que gostaria de fazer agora?");
                        System.out.println("1 - Cadastrar proposta.");
                        System.out.println("2 - Solicitar um serviço.");
                        System.out.println("3 - Pesquisar prestadores.");
                        System.out.println("4 - Visualizar meu perfil.");
                        System.out.println("5 - Visualizar minhas propostas.");
                        System.out.println("6 - Encerrar sessão.");
                        System.out.println("7 - Encerrar aplicativo.");
                        System.out.print("Sua opção: ");
                        opcao = entradaOpcao.nextInt();

                        switch (opcao) {
                            // caso o usuário queira cadastrar uma proposta
                            case 1:
                                // após o cadastro, adiciona a nova proposta a lista de propostas principal
                                var novaProposta = ((Cliente) perfilSelecionado).cadastrarProposta();
                                novaProposta.setId(idContadorProposta); // associação da proposta a um id
                                propostas.add(novaProposta);
                                idContadorProposta++; // incremento do contador para geração dos ids de propostas
                                break;
                            // caso o usuário queira solicitar/pesquisar um serviço
                            case 2:
                                perfilSelecionado.visualizarServicos(servicos);
                                break;
                            // caso o usuário queira pesquisar por um prestador
                            case 3:
                                ((Cliente) perfilSelecionado).pesquisarPrestadores(usuarios);
                                break;
                            // caso o usuário queira visualizar seu perfil
                            case 4:
                                perfilSelecionado.exibirDetalhes();
                                break;
                            // caso o usuário queira visualizar suas propostas
                            case 5:
                                perfilSelecionado.visualizarPropostas(propostas);
                                /* verifica se há alguma proposta no "banco de dados"
                                que não existe mais para um cliente, indicando que ele a removeu */
                                var propostasCliente = ((Cliente) perfilSelecionado).getPropostas();

                                /* caso determinada proposta do "banco de dados" não exista
                                para o cliente, ela também será removida */
                                for (Proposta proposta: propostas) {
                                    if (!propostasCliente.contains(proposta)) {
                                        propostas.remove(proposta);
                                        break;
                                    }
                                }
                                break;
                            // caso o usuário queira encerrar a sessão
                            case 6:
                                usuarioLogado.encerrarLogin();
                                autenticacaoCompleta = false;
                                telaPrincipalAtiva = true;
                                break;
                            // caso o usuário queira encerrar o aplicativo
                            case 7:
                                System.out.println();
                                System.out.println("Obrigado por utilizar nosso aplicativo! Encerrando...");
                                aplicativoAtivo = false;
                                telaPrincipalAtiva = true;
                                break;
                            default:
                                System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                                System.out.println();
                                break;
                        }
                    }
                }

                if (perfilSelecionado instanceof Prestador) {
                    System.out.println("Perfil atual: Prestador");

                    // telas disponíveis para o perfil Prestador
                    boolean telaPrincipalAtiva = false; // verifica se o prestador saiu da tela principal

                    while (!telaPrincipalAtiva) {
                        System.out.println();
                        System.out.println("------------------------------------");
                        System.out.println("O que gostaria de fazer agora?");
                        System.out.println("1 - Cadastrar serviço.");
                        System.out.println("2 - Visualizar propostas.");
                        System.out.println("3 - Visualizar meu perfil.");
                        System.out.println("4 - Visualizar meus serviços.");
                        System.out.println("5 - Encerrar sessão.");
                        System.out.println("6 - Encerrar aplicativo.");
                        System.out.print("Sua opção: ");
                        opcao = entradaOpcao.nextInt();

                        switch (opcao) {
                            // caso o usuário queira cadastrar um serviço
                            case 1:
                                // após o cadastro, adiciona um novo serviço a lista de serviços principal
                                var novoServico = ((Prestador) perfilSelecionado).cadastrarServico();
                                novoServico.setId(idContadorServico); // associação do serviço a um id
                                servicos.add(novoServico);
                                idContadorServico++; // incremento do contador para geração dos ids dos serviços
                                break;
                            // caso o usuário queira visualizar as propostas cadastradas diretamente
                            case 2:
                                perfilSelecionado.visualizarPropostas(propostas);
                                break;
                            // caso o usuário queira visualizar seu perfil
                            case 3:
                                perfilSelecionado.exibirDetalhes();
                                break;
                            // caso o usuário queira visualizar seus serviços cadastrados
                            case 4:
                                perfilSelecionado.visualizarServicos(servicos);
                                /* verifica se há algum serviço no "banco de dados"
                                que não existe mais para um prestador, indicando que ele a removeu */
                                var servicosPrestador = ((Prestador) perfilSelecionado).getServicos();

                                /* caso determinado serviço do "banco de dados" não exista
                                para o prestador, ela também será removida */
                                for (Servico servico: servicos) {
                                    if (!servicosPrestador.contains(servico)) {
                                        servicos.remove(servico);
                                        break;
                                    }
                                }
                                break;
                            // caso o usuário queira encerrar a sessão
                            case 5:
                                usuarioLogado.encerrarLogin();
                                autenticacaoCompleta = false;
                                telaPrincipalAtiva = true;
                                break;
                            // caso o usuário queira encerrar o aplicativo
                            case 6:
                                System.out.println();
                                System.out.println("Obrigado por utilizar nosso aplicativo! Encerrando...");
                                aplicativoAtivo = false;
                                telaPrincipalAtiva = true;
                                break;
                            default:
                                System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                                System.out.println();
                                break;
                        }
                    }
                }

                if (perfilSelecionado instanceof Administrador) {
                    System.out.println("Perfil atual: Administrador");
                }

//                perfilSelecionado.exibirDetalhes();
//                usuarioLogado.encerrarLogin();
//                autenticacaoCompleta = false;
            }
        }
    }
}

