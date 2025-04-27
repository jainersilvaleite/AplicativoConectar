package br.uepa.conectar.util;

import br.uepa.conectar.model.Usuario;

import java.util.List;

public interface Consultavel {
    default Usuario consultarUsuarioPorEmail(List<Usuario> usuarios, String email) {
        // percorre todos os usuários existentes e busca por um com email correspondente
        for (Usuario usuario: usuarios) {
            if (email.equals(usuario.getEmail())) return usuario;
        }
        return null;
    }
}
