package com.yk.root.service;

import com.yk.root.domain.ResponseResult;
import com.yk.root.domain.User;

public interface LoginService {
    ResponseResult<User> login(User user);

    ResponseResult logout();
}
