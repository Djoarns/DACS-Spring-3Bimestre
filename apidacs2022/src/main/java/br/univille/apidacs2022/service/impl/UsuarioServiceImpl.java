package br.univille.apidacs2022.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.univille.coredacs2022.entity.Usuario;
import br.univille.coredacs2022.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = repository.findByUsuario(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            return new User(usuario.getUsuario(), usuario.getSenha(), new ArrayList<>());
        }

        return null;
    }

}
