package br.uepa.conectar;

import br.uepa.conectar.model.*;

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
        int idContador = 2; // utilizado para geração dos ids dos usuários
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
                        novoUsuario.preencherDadosDePerfil(usuarios); // preenchimento dos dados do usuário
                        novoUsuario.setId(idContador); // associação do usuário a um id
                        usuarios.add(novoUsuario); // novo usuário é inserido no "banco de dados"
                        idContador++; // incremento do contador para geração dos ids de usuários
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
                        System.out.println("1 - Solicitar um serviço.");
                        System.out.println("2 - Pesquisar prestadores.");
                        System.out.println("3 - Encerrar sessão.");
                        System.out.println("4 - Encerrar aplicativo.");
                        System.out.print("Sua opção: ");
                        opcao = entradaOpcao.nextInt();

                        switch (opcao) {
                            // caso o usuário queira solicitar/pesquisar um serviço
                            case 1:
                                perfilSelecionado.visualizarServicos(servicos);
                                break;
                            case 2:
                                break;
                            // caso o usuário queira encerrar a sessão
                            case 3:
                                usuarioLogado.encerrarLogin();
                                autenticacaoCompleta = false;
                                telaPrincipalAtiva = true;
                                break;
                            // caso o usuário queira encerrar o aplicativo
                            case 4:
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
