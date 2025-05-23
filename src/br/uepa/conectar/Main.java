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
        List<Chat> chats = new ArrayList<>(); // armazena todos os chats gerados pelo aplicativo

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
                        System.out.println("8 - Visualizar ordens de serviço.");
                        System.out.println("9 - Visualizar meus chats.");
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
                                perfilSelecionado.visualizarServicos(servicos, chats, propostas);
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
                                // visualiza as propostas cadastradas pelo cliente e retorna o id do chat de uma das propostas
                                int idChat = perfilSelecionado.visualizarPropostas(propostas);
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

                                // se o cliente acessou o chat de alguma proposta
                                if (idChat != -1) {
                                    Chat chat = perfilSelecionado.consultarChatPorId(chats, idChat);
                                    // proposta associada ao chat
                                    Proposta proposta = perfilSelecionado.consultarPropostaPorId(propostas, idChat);

                                    // caso o chat já exista, acessá-lo e visualizar suas mensagens
                                    if (chat != null) {
                                        chat.visualizarMensagens(null, proposta, perfilSelecionado);
                                    } else {
                                        // caso o chat não exista, avisar para o cliente aguardar um Prestador entrar em contato
                                        System.out.println("[ERRO] O Chat para esta proposta ainda não foi iniciado!");
                                        System.out.println("Aguarde até que um Prestador o atenda.");
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
                            // caso o usuário queira visualizar as ordens de serviço
                            case 8:
                                System.out.println(perfilSelecionado.getNome() + ": Ordens de Serviço");
                                System.out.println("------------------------------------");

                                for (OrdemDeServico ordemDeServico: ordensDeServico) {
                                    if (ordemDeServico.getCliente().getId() == perfilSelecionado.getId()) {
                                        System.out.println("-> Id: " + ordemDeServico.getId());
                                        System.out.println("Serviço: " + ordemDeServico.getServico().getTitulo());
                                        System.out.println("Prestador: " + ordemDeServico.getServico().getPrestador().getNome());
                                        System.out.println("Cliente: " + ordemDeServico.getCliente().getNome());
                                        System.out.println("Status: " + ordemDeServico.getStatus());
                                        System.out.println();
                                    }
                                }

                                boolean ordensVisualizadas = false; // verifica se as ordens de serviço foram visualizadas
                                OrdemDeServico ordemDeServico = null; // ordem de serviço selecionada

                                while (!ordensVisualizadas) {
                                    System.out.println("------------------------------------");
                                    System.out.println("O que gostaria de fazer agora?");
                                    System.out.println("1 - Cancelar serviço.");
                                    System.out.println("2 - Confirmar serviço.");
                                    System.out.println("3 - Voltar ao menu anterior.");
                                    System.out.print("Sua opção: ");
                                    opcao = entradaOpcao.nextInt();

                                    switch (opcao) {
                                        case 1:
                                            System.out.println();
                                            System.out.print("Insira o id da ordem de serviço: ");
                                            opcao = entradaOpcao.nextInt();

                                            ordemDeServico = ordemDeServico.consultarOrdemDeServicoPorId(ordensDeServico, opcao);
                                            if (ordemDeServico != null) {
                                                // cancelar serviço
                                            } else {
                                                System.out.println("[ERRO] O id informado não corresponde a uma ordem de serviço cadastrada!");
                                            }
                                            break;
                                        case 2:
                                            System.out.println();
                                            System.out.print("Insira o id da ordem de serviço: ");
                                            opcao = entradaOpcao.nextInt();

                                            ordemDeServico = ordemDeServico.consultarOrdemDeServicoPorId(ordensDeServico, opcao);
                                            if (ordemDeServico != null) {
                                                // confirmar serviço
                                            } else {
                                                System.out.println("[ERRO] O id informado não corresponde a uma ordem de serviço cadastrada!");
                                            }
                                            break;
                                        case 3:
                                            ordensVisualizadas = true;
                                            break;
                                        default:
                                            break;
                                    }
                                }

                                break;
                            // caso o usuário queira visualizar seus chats
                            case 9:
                                System.out.println(perfilSelecionado.getNome() + ": Chats");
                                System.out.println("------------------------------------");

                                for (Chat chat: chats) {
                                    if (chat.getCliente().getId() == perfilSelecionado.getId()) {
                                        System.out.println("-> Id: " + chat.getId() + " | " + chat.getPrestador().getNome());
                                        System.out.println("Mensagens: " + (chat.getMensagens().size() + 1));
                                        System.out.println();
                                    }
                                }

                                boolean chatsVisualizados = false; // verifica se os chats foram visualizados

                                while (!chatsVisualizados) {
                                    System.out.println("------------------------------------");
                                    System.out.println("O que gostaria de fazer agora?");
                                    System.out.println("1 - Acessar um chat.");
                                    System.out.println("2 - Voltar ao menu anterior.");
                                    System.out.print("Sua opção: ");
                                    opcao = entradaOpcao.nextInt();

                                    switch (opcao) {
                                        case 1:
                                            System.out.println();
                                            System.out.print("Insira o id do chat: ");
                                            opcao = entradaOpcao.nextInt();

                                            // se o cliente acessou o chat de alguma proposta
                                            Chat chat = perfilSelecionado.consultarChatPorId(chats, opcao);

                                            if (chat != null) {
                                                // proposta associada ao chat
                                                Proposta proposta = perfilSelecionado.consultarPropostaPorChat(chat);
                                                chat.visualizarMensagens(null, proposta, perfilSelecionado);
                                            } else {
                                                // caso o chat não exista, avisar para o cliente aguardar um Prestador entrar em contato
                                                System.out.println("[ERRO] O Chat para esta proposta ainda não foi iniciado!");
                                                System.out.println("Aguarde até que um Prestador o atenda.");
                                            }

                                            break;
                                        case 2:
                                            chatsVisualizados = true;
                                            break;
                                        default:
                                            break;
                                    }
                                }
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
                        System.out.println("7 - Visualizar ordens de serviço.");
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
                                int idChat = perfilSelecionado.visualizarPropostas(propostas);

                                // se o prestador acessou o chat de alguma proposta
                                if (idChat != -1) {
                                    Chat chat = perfilSelecionado.consultarChatPorId(chats, idChat);
                                    // proposta associada ao chat
                                    Proposta proposta = perfilSelecionado.consultarPropostaPorId(propostas, idChat);
                                    // ordem de serviço
                                    OrdemDeServico ordemDeServico;

                                    // caso o chat já exista, acessá-lo e visualizar suas mensagens
                                    if (chat != null) {
                                        // visualização das mensagens e coleta da ordem de serviço possivelmente gerada
                                        ordemDeServico = chat.visualizarMensagens(null, proposta, perfilSelecionado);

                                        // se uma ordem de serviço for gerada
                                        if (ordemDeServico != null) {
                                            // adicionar a ordem de serviço no banco de dados
                                            ordensDeServico.add(ordemDeServico);
                                        }
                                    } else {
                                        // caso o chat não exista, criá-lo
                                        Chat novoChat = proposta.iniciarChat((Prestador) perfilSelecionado); // chat entre cliente e prestador
                                        chats.add(novoChat); // adiciona o novo chat ao banco de dados
                                        ordemDeServico = novoChat.visualizarMensagens(null, proposta, perfilSelecionado); // visualiza as mensagens do chat criado

                                        // se uma ordem de serviço for gerada
                                        if (ordemDeServico != null) {
                                            // adicionar a ordem de serviço no banco de dados
                                            ordensDeServico.add(ordemDeServico);
                                        }
                                    }
                                }
                                break;
                            // caso o usuário queira visualizar seu perfil
                            case 3:
                                perfilSelecionado.exibirDetalhes();
                                break;
                            // caso o usuário queira visualizar seus serviços cadastrados
                            case 4:
                                perfilSelecionado.visualizarServicos(servicos, chats, propostas);
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
                            case 7:
                                System.out.println(perfilSelecionado.getNome() + ": Ordens de Serviço");
                                System.out.println("------------------------------------");

                                for (OrdemDeServico ordemDeServico: ordensDeServico) {
                                    if (ordemDeServico.getServico().getPrestador().getId() == perfilSelecionado.getId()) {
                                        System.out.println("-> Id: " + ordemDeServico.getId());
                                        System.out.println("Serviço: " + ordemDeServico.getServico().getTitulo());
                                        System.out.println("Prestador: " + ordemDeServico.getServico().getPrestador().getNome());
                                        System.out.println("Cliente: " + ordemDeServico.getCliente().getNome());
                                        System.out.println("Status: " + ordemDeServico.getStatus());
                                        System.out.println();
                                    }
                                }

                                boolean ordensVisualizadas = false; // verifica se as ordens de serviço foram visualizadas
                                OrdemDeServico ordemDeServico = null; // ordem de serviço selecionada

                                while (!ordensVisualizadas) {
                                    System.out.println("------------------------------------");
                                    System.out.println("O que gostaria de fazer agora?");
                                    System.out.println("1 - Atualizar status do serviço.");
                                    System.out.println("2 - Voltar ao menu anterior.");
                                    System.out.print("Sua opção: ");
                                    opcao = entradaOpcao.nextInt();

                                    switch (opcao) {
                                        case 1:
                                            System.out.println();
                                            System.out.print("Insira o id da ordem de serviço: ");
                                            opcao = entradaOpcao.nextInt();

                                            ordemDeServico = ordemDeServico.consultarOrdemDeServicoPorId(ordensDeServico, opcao);
                                            if (ordemDeServico != null) {
                                                // atualizar ordem de serviço
                                            } else {
                                                System.out.println("[ERRO] O id informado não corresponde a uma ordem de serviço cadastrada!");
                                            }
                                            break;
                                        case 2:
                                            ordensVisualizadas = true;
                                            break;
                                        default:
                                            System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                                            System.out.println();
                                            break;
                                    }
                                }
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

