package br.uepa.conectar.model;

import java.time.LocalDate;
import java.util.Scanner;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private LocalDate dataNascimento;
    private Boolean isAutenticado;

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

    public String getSenha() {
        return senha;
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

    public void fazerLogin(String senha) {
        // compara a senha salva pelo usuário com a senha informada para validação do login
        if (senha.equals(getSenha())) {
            setIsAutenticado(true);
        } else {
            setIsAutenticado(false);
        }
    }

    public void preencherDadosDePerfil() {
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
                cadastroCompleto = true;
            } catch (Exception e) {
                System.out.println("[ERRO] Ocorreu um erro ao realizar o cadastro: " + e.getMessage());
                System.out.println("Reiniciando o preenchimento de dados...");
                System.out.println();
            }
        }

    }

    public void escolherPerfil() {

    }

    public void exibirDetalhes() {

    }

    public void visualizarPropostas() {

    }

    public void visualizarServicos() {

    }

    public void aceitarProposta() {

    }

    public void encerrarLogin() {
        System.out.println();
        System.out.println("Encerrando sessão...");
        setIsAutenticado(false);
    }
}
