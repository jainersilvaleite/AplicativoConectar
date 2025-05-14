package br.uepa.conectar.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Chat {
    private int id;
    private Cliente cliente;
    private Prestador prestador;
    private List<Mensagem> mensagens;

    public Chat() {
        this.mensagens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void enviarMensagem(Usuario perfilSelecionado, String conteudo) {
        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setId(getMensagens().size());
        novaMensagem.setAutor(perfilSelecionado);
        novaMensagem.setConteudo(conteudo);
        novaMensagem.setDataEnvio(LocalDate.now());

        getMensagens().add(novaMensagem);
    }

    public OrdemDeServico visualizarMensagens(Servico servico, Proposta proposta, Usuario perfilSelecionado) {
        boolean mensagensVisualizadas = false; // verifica se o usuário saiu do chat
        Scanner entradaOpcao = new Scanner(System.in); // possibilita a entrada do usuário com alguma das opções
        Scanner entradaTexto = new Scanner(System.in); // possibilita a entrada com informações de texto solicitadas
        int opcao; // armazena a opção inserida pelo usuário recentemente
        String texto; // armazena a informação de texto solicitada recentemente
        OrdemDeServico ordemDeServico = null; // ordem de serviço a ser gerada

        // nome do chat de acordo com o perfil selecionado atualmente
        if (perfilSelecionado instanceof Prestador) {
            System.out.println("Chat: " + getCliente().getNome());
        } else {
            System.out.println("Chat: " + getPrestador().getNome());
        }
        System.out.println("------------------------------------");

        while (!mensagensVisualizadas) {
            if (!getMensagens().isEmpty()) {
                for (Mensagem mensagem: getMensagens()) {
                    if ((perfilSelecionado instanceof Cliente && mensagem.getAutor() instanceof Cliente)
                    || (perfilSelecionado instanceof Prestador && mensagem.getAutor() instanceof Prestador)) {
                        System.out.println("-> Você");
                        System.out.println(mensagem.getConteudo());
                        System.out.println("Enviado em " + mensagem.getDataEnvio().toString());
                        System.out.println();
                    } else {
                        if (mensagem.getAutor() instanceof Cliente) {
                            System.out.println("-> Id: " + getPrestador().getId() + " | Nome: " + getPrestador().getNome());
                            System.out.println(mensagem.getConteudo());
                            System.out.println("Enviado em " + mensagem.getDataEnvio().toString());
                            System.out.println();
                        } else {
                            System.out.println("-> Id: " + getCliente().getId() + " | Nome: " + getCliente().getNome());
                            System.out.println(mensagem.getConteudo());
                            System.out.println("Enviado em " + mensagem.getDataEnvio().toString());
                            System.out.println();
                        }

                    }
                }
            } else {
                System.out.println("Nenhuma mensagem por enquanto!");
            }

            System.out.println("------------------------------------");
            System.out.println("O que gostaria de fazer agora?");
            System.out.println("1 - Enviar mensagem.");
            System.out.println("2 - Aceitar proposta.");
            System.out.println("3 - Sair do chat.");
            if (perfilSelecionado instanceof Prestador) System.out.println("4 - Gerar ordem de serviço.");
            System.out.print("Sua opção: ");
            opcao = entradaOpcao.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.print("Sua mensagem: ");
                    texto = entradaTexto.nextLine();

                    enviarMensagem(perfilSelecionado, texto);
                    break;
                case 2:
                    if (perfilSelecionado instanceof Cliente) {
                        proposta.setAceiteCliente(true);
                    } else {
                        proposta.setAceitePrestador(true);
                    }

                    System.out.println();
                    break;
                case 3:
                    mensagensVisualizadas = true;
                    break;
                case 4:
                    if (perfilSelecionado instanceof Prestador) {
                        // a ordem de serviço só será gerada se o ambos concordarem
                        if (proposta.getAceiteCliente() && proposta.getAceitePrestador()) {
                            // preenchimento da ordem de serviço a ser gerada
                            ordemDeServico = ((Prestador) perfilSelecionado).gerarOrdemDeServico(servico, perfilSelecionado, getCliente(), proposta);
                        } else {
                            System.out.println("[ERRO] A ordem de serviço só pode ser gerada com a aceitação de ambas as partes!");
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

        return ordemDeServico;
    }

    public void fechar() {

    }
}

