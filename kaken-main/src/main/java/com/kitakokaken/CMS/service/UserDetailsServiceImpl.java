package com.kitakokaken.CMS.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("★★★ UserDetailsServiceImpl が生成されました");
    }

    @Override
    public UserDetails loadUserByUsername(String mail)
            throws UsernameNotFoundException {

        System.out.println("★★★ ログイン試行 mail = " + mail);

        User user = userRepository.findByMail(mail)
                .orElseThrow(() -> {
                    System.out.println("★★★ ユーザーが見つからない");
                    return new UsernameNotFoundException("ユーザーが見つかりません");
                });

        System.out.println("★★★ ユーザー発見 mail = " + user.getMail());
        System.out.println("★★★ user_id = " + user.getUser_id());
        System.out.println("★★★ yakuwari = " + user.getYakuwari());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getMail())
                .password(user.getPass())
                .roles(user.getYakuwari().name())
                .build();
    }
}