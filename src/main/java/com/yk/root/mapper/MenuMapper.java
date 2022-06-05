package com.yk.root.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yk.root.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}