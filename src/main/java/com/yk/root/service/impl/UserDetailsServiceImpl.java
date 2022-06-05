package com.yk.root.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yk.root.domain.LoginUser;
import com.yk.root.domain.User;
import com.yk.root.mapper.MenuMapper;
import com.yk.root.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 数据库查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //todo 数据库没有用户信息，抛出异常
        if(user == null) {
            throw new RuntimeException("未找到该用户");
        }
        // 数据查询库用户权限
        Long id = user.getId();
        List<String> strings = menuMapper.selectPermsByUserId(id);
        System.out.println("用户权限："+strings);
        // 把user数据封装成UserDetails
        return new LoginUser(user,strings);
    }
}
