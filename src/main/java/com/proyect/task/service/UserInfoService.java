package com.proyect.task.service;

import com.proyect.task.config.util.TokenUtils;
import com.proyect.task.model.UserInfo;
import com.proyect.task.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail  = repository.findByName(username);

        if (userDetail.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserInfoDetails(userDetail.get().getName(), userDetail.get().getPassword());
    }


    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }


}
