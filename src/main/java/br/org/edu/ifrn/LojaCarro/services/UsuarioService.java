package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final LogService logService;

    public UsuarioService(UsuarioRepository repository, LogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    public Usuario criar(Usuario usuario) {
        Usuario salvo = repository.save(usuario);
        logService.info("CADASTRO", "USUARIO", salvo.getId(), "SUCESSO", "NOME: " + salvo.getNome() + " | TIPO: " + salvo.getClass().getSimpleName());
        return salvo;
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = repository.findAll();
        logService.info("LISTAGEM", "USUARIOS", null, "SUCESSO", "QUANTIDADE: " + usuarios.size());
        return usuarios;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isEmpty()) {
            logService.warn("CONSULTA", "USUARIO", id, "ERRO", "USUARIO NAO ENCONTRADO");
        }
        return usuario;
    }

    public boolean excluir(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isEmpty()) {
            logService.warn("EXCLUSAO", "USUARIO", id, "ERRO", "USUARIO NAO ENCONTRADO");
            return false;
        }
        repository.deleteById(id);
        logService.info("EXCLUSAO", "USUARIO", id, "SUCESSO", "NOME: " + usuario.get().getNome());
        return true;
    }
}