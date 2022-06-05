package com.yk.root.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private User user;// 用户数据库信息
    private List<String> permissions;// 用户权限(字符串集合)
    @JSONField(serialize=false)// 取消序列化
    private List<SimpleGrantedAuthority> authorities;// SpringSecurity需要的简单授权对象集合

    public LoginUser(User user, List<String> permissions) {
        this.user=user;
        this.permissions=permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        // 把permission权限信息，封装成SimpleGrantedAuthority类
        // 方法一
        ArrayList<SimpleGrantedAuthority> newList = new ArrayList<>();
        for (String permission : permissions) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
            newList.add(simpleGrantedAuthority);
        }
        this.authorities=newList;
        // 方法二
//        authorities = permissions.stream().
//                map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
