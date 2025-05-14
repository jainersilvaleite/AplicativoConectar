package br.uepa.conectar.utils;

import br.uepa.conectar.models.*;

import java.util.ArrayList;
import java.util.List;

public interface Consultavel {
    default Usuario consultarUsuarioPorEmail(List<Usuario> usuarios, String email) {
        // percorre todos os usuários existentes e busca por um com email correspondente
        for (Usuario usuario: usuarios) {
            if (email.equals(usuario.getEmail())) return usuario;
        }
        return null;
    }

    default Servico consultarServicoPorId(List<Servico> servicos, int id) {
        // percorre todos os serviços existentes e busca por um com id correspondente
        for (Servico servico: servicos) {
            if (id == servico.getId()) return servico;
        }
        return null;
    }

    default List<Servico> pesquisarServicosPorTitulo(List<Servico> servicos, String pesquisa) {
        List<Servico> resultadosPesquisa = new ArrayList<>();
        // percorre todos os serviços e seleciona aqueles cujos titulos contém o trecho pesquisado
        for (Servico servico : servicos) {
            if (servico.getTitulo().toLowerCase().contains(pesquisa.toLowerCase())) {
                resultadosPesquisa.add(servico);
            }
        }
        return resultadosPesquisa;
    }

    default Prestador consultarPrestadorPorId(List<Usuario> usuarios, int id) {
        // percorre todos os usuários existentes e busca por um com id correspondente
        for (Usuario usuario: usuarios) {
            if (id == usuario.getId()) return (Prestador) usuario.getPerfis().get(1);
        }
        return null;
    }

    default List<Prestador> pesquisarPrestadoresPorNome(List<Usuario> usuarios, String pesquisa) {
        List<Prestador> resultadosPesquisa = new ArrayList<>();
        // percorre todos os usuários e seleciona aqueles cujos nomes contém o trecho pesquisado
        for (Usuario usuario: usuarios) {
            if (usuario.getNome().toLowerCase().contains(pesquisa.toLowerCase())) {
                resultadosPesquisa.add((Prestador) usuario.getPerfis().get(1));
            }
        }
        return resultadosPesquisa;
    }

    default Proposta consultarPropostaPorId(List<Proposta> propostas, int id) {
        // percorre todas as propostas existentes e busca por uma com id correspondente
        for (Proposta proposta: propostas) {
            if (id == proposta.getId()) return proposta;
        }
        return null;
    }

    default Proposta consultarPropostaPorChat(Chat chat) {
        // percorre todos os serviços de um prestador e suas propostas e busca pelo chat correspondente ao informado
        for (Servico servico: chat.getPrestador().getServicos()) {
            for (Proposta proposta: servico.getPropostas()) {
                if (proposta.getId() == chat.getId()
                        && proposta.getCliente().getId() == chat.getCliente().getId()) {
                    return proposta;
                }
            }
        }
        return null;
    }

    default List<Proposta> pesquisarPropostasPorTitulo(List<Proposta> propostas, String pesquisa) {
        List<Proposta> resultadosPesquisa = new ArrayList<>();
        // percorre todas as propostas e seleciona aquelas cujos titulos contém o trecho pesquisado
        for (Proposta proposta : propostas) {
            if (proposta.getTitulo().toLowerCase().contains(pesquisa.toLowerCase())) {
                resultadosPesquisa.add(proposta);
            }
        }
        return resultadosPesquisa;
    }

    default List<Proposta> pesquisarPropostasPorNomeCliente(List<Proposta> propostas, String pesquisa) {
        List<Proposta> resultadosPesquisa = new ArrayList<>();
        // percorre todas as propostas e seleciona aquelas cujos nomes de Clientes contém o trecho pesquisado
        for (Proposta proposta : propostas) {
            if (proposta.getCliente().getNome().toLowerCase().contains(pesquisa.toLowerCase())) {
                resultadosPesquisa.add(proposta);
            }
        }
        return resultadosPesquisa;
    }

    default Chat consultarChatPorId(List<Chat> chats, int id) {
        // percorre todos os chats existentes e busca por um com id correspondente
        for (Chat chat: chats) {
            if (id == chat.getId()) return chat;
        }
        return null;
    }

    default OrdemDeServico consultarOrdemDeServicoPorId(List<OrdemDeServico> ordensDeServicos, int id) {
        // percorre todos as ordens de serviço existentes e busca por uma com id correspondente
        for (OrdemDeServico ordemDeServico: ordensDeServicos) {
            if (id == ordemDeServico.getId()) return ordemDeServico;
        }
        return null;
    }
}
