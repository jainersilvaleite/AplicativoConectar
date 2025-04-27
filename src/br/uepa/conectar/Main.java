package br.uepa.conectar;

import br.uepa.conectar.model.Proposta;
import br.uepa.conectar.model.Servico;
import br.uepa.conectar.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // simulação do banco de dados
        List<Usuario> usuarios = new ArrayList<>(); // armazena todos os usuários do aplicativo
        List<Servico> servicos = new ArrayList<>(); // armazena todos os serviços do aplicativo
        List<Proposta> propostas = new ArrayList<>(); // armazena todas as propostas do aplicativo

        // variáveis de controle
        Usuario usuarioLogado = null; // armazena o usuário logado atualmente no aplicativo
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        boolean autenticacaoCompleta = false; // verifica se a autenticação foi terminada para enviá-lo a tela principal
        int opcao; // armazena a opção inserida pelo usuário recentemente
        String texto; // armazena a informação de texto solicitada recentemente

        // autenticação (login e/ou cadastro)
        while (!autenticacaoCompleta) {
            System.out.println("Bem-vindo(a) ao aplicativo Conectar!");
            System.out.println("1 - É novo por aqui? Crie sua conta.");
            System.out.println("2 - Já possui uma conta? Acesse agora mesmo.\n");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                // caso o usuário queira criar sua conta, ele precisará preencher seus dados
                case 1:
                    Usuario novoUsuario = new Usuario(); // instância do usuário a ser criado
                    novoUsuario.preencherDadosDePerfil(); // preenchimento dos dados do usuário
                    usuarios.add(novoUsuario); // novo usuário é inserido no "banco de dados"
                    break;
                case 2:
                    Usuario usuario; // instância do usuário a fazer login
                    System.out.println();
                    System.out.print("Qual seu e-mail? ");
                    texto = entradaTexto.nextLine(); // preenchimento do email para consulta no "banco de dados"
                    usuario = consultarUsuarioPorEmail(usuarios, texto); // resultado da consulta

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
                default:
                    System.out.println("[ERRO] Opção inválida, selecione uma das opções disponíveis!");
                    System.out.println();
            }
        }

        // tela principal
        while (usuarioLogado.getIsAutenticado()) {
            usuarioLogado.encerrarLogin();
        }
    }

    public static Usuario consultarUsuarioPorEmail(List<Usuario> usuarios, String email) {
        // percorre todos os usuários existentes e busca por um com email
        for (Usuario usuario: usuarios) {
            if (email.equals(usuario.getEmail())) return usuario;
        }
        return null;
    }
}
