package com.tfm.aseguradora.backend.tfm.policy.config;

import com.tfm.aseguradora.backend.tfm.policy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.tfm.aseguradora.backend.middle.users.dto.RolDto;
import com.tfm.aseguradora.backend.middle.users.dto.UserDto;
import com.tfm.aseguradora.backend.middle.users.dto.UsersListWrapperDto;
import com.tfm.aseguradora.backend.middle.users.client.UserApi;

import java.util.*;

@Service
public class UsuarioDetailsService implements UserDetailsService  {
    // 1.- CREAR UN ENDPOINT EN EL CORE UN ENDPOINT QUE DEVUELVA UN USUARIO CON LOS NOMBRES DE SUS ROLES POR SU MAIL /users?mail=
    // 2.- AQUI SUSTUIR UserJpaRepository POR EL CLIENTE REST DE ESE SEVICIO
    // 3.- AQUI SUSTITUIR EL USO DE UserEntity POR EL UserDto que devuelva
    @Autowired
    private UserApi userApi;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        var username = jwtUtilService.extractUsername(token.replace("Bearer ", ""));
        var userListWrapper = userApi.getUsers(token, null, username);
        var userDto = userListWrapper.getUsers().get(0);
        if (userDto != null) {
            var roles = userDto.getRoles();
            if (roles != null && !roles.isEmpty()) {
                User.UserBuilder userBuilder = User.withUsername(username);
                String encryptedPassword = "nonEmpty";
                String[] rolesListString = roles.stream()
                        .map(RolDto::getNombre)
                        .toArray(String[]::new);
                userBuilder.password(encryptedPassword).username(token).roles(rolesListString);
                return userBuilder.build();
            } else {
                throw new UsernameNotFoundException("Username [" + username + "] has not permissions");
            }
        } else {
            throw new UsernameNotFoundException("Username [" + username + "] does not exist in the system");
        }
    }






}