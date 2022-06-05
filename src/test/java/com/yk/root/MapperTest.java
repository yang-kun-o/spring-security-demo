package com.yk.root;

import com.yk.root.domain.User;
import com.yk.root.mapper.MenuMapper;
import com.yk.root.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
    @Test
    public void testBCryptPasswordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode);
        System.out.println(encode2);
        boolean matches = passwordEncoder.matches("1234",encode);
        boolean matches2 = passwordEncoder.matches("1234",encode2);
        System.out.println(matches);
        System.out.println(matches2);
    }

    @Test
    public void testSelectPermsByUserId(){
        List<String> strings = menuMapper.selectPermsByUserId(1L);
        System.out.println(strings);
    }
}
