package br.uepa.conectar.util;

import br.uepa.conectar.model.Servico;
import br.uepa.conectar.model.Usuario;

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
        for (Servico servico : servicos) {
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
}
