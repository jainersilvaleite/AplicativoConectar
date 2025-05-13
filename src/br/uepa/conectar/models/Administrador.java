package br.uepa.conectar.models;

public class Administrador extends Usuario {
    // gera um perfil de administrador baseado nas informações originais do usuário
    public Administrador(Usuario usuario) {
        setId(usuario.getId());
        setNome(usuario.getNome());
        setEmail(usuario.getEmail());
        setTelefone(usuario.getTelefone());
        setEndereco(usuario.getEndereco());
        setDataNascimento(usuario.getDataNascimento());
        setIsAutenticado(usuario.getIsAutenticado());
    }

    public void atualizarUsuario() {

    }

    public void removerUsuario() {

    }

    public void removerServico() {

    }

    public void removerProposta() {

    }

    public void visualizarOrdensDeServico() {

    }

    public void exibirDetalhesDesempenho() {

    }
}
