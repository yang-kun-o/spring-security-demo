package com.yk.root.service.impl;

import com.yk.root.domain.LoginUser;
import com.yk.root.domain.ResponseResult;
import com.yk.root.domain.User;
import com.yk.root.service.LoginService;
import com.yk.root.utils.JwtUtil;
import com.yk.root.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    // 登录认证服务
    @Override
    public ResponseResult login(User user) {
        // AuthenticationManager Authenticat 用户登录认证
        // 把用户名和密码封装到 Authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // todo 用户未通过认证返回提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("认证失败");
        }
        // 认证成功
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //把 userId:loginUser 放入 redis 中
        redisCache.setCacheObject("login:"+userId,loginUser);
        //使用 userId 生成一个 jwt ,放入 ResponseResult
        String jwt = JwtUtil.createJWT(userId);
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }

    // 登录退出服务
    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String id = String.valueOf(loginUser.getUser().getId());
        //删除redis中的 (login:userId):loginUser
        redisCache.deleteObject("login:"+id);
        return new ResponseResult(200,"退出登录成功");
    }
}
