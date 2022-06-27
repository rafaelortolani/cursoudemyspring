package com.exemplo.service.impl;

import com.exemplo.domain.entity.Usuario;
import com.exemplo.domain.repository.UsuarioRepository;
import com.exemplo.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@EnableWebSecurity
public class UsuarioServiceImpl implements UserDetailsService {

        @Bean
        public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }

        @Autowired
        private UsuarioRepository repository;

        @Transactional
        public Usuario salvar(Usuario usuario){
            return repository.save(usuario);
        }

        public UserDetails autenticar( Usuario usuario ){
            UserDetails user = loadUserByUsername(usuario.getLogin());
            boolean senhasBatem = encoder().matches( usuario.getSenha(), user.getPassword() );
            if(senhasBatem){
                return user;
            }

            throw new SenhaInvalidaException();
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Usuario usuario = repository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

            String[] roles = usuario.isAdmin() ?
                    new String[]{"ADMIN", "USER"} : new String[]{"USER"};

            return User
                    .builder()
                    .username(usuario.getLogin())
                    .password(usuario.getSenha())
                    .roles(roles)
                    .build();
        }

}