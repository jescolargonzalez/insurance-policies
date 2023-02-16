package com.tfm.aseguradora.backend.tfm.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioDetailsService implements UserDetailsService  {

  @Autowired
  private UserJpaRepository userJpaRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<UserEntity> userEntityOpt = userJpaRepository.findByMail(username);

    if (userEntityOpt.isPresent()) {
      var userEntity = userEntityOpt.get();
      var roles = userEntity.getRoles();
      if (roles != null && !roles.isEmpty()) {
        User.UserBuilder userBuilder = User.withUsername(username);
        String encryptedPassword = userEntity.getPass();

        String [] rolesListString = roles.stream()
                .map(RolEntity::getNombre)
                .toArray(String[]::new);

        userBuilder.password(encryptedPassword).roles(rolesListString);
        return userBuilder.build();
      } else {
        throw new UsernameNotFoundException("Username [" + username + "] has not permissions");
      }
    }
    else {
      throw new UsernameNotFoundException("Username [" + username + "] does not exist in the system");
    }
  }

}