package com.wondercar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wondercar.entity.Menu;
import com.wondercar.entity.Role;
import com.wondercar.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wondercar
 * @since: 2023-03-20
 */
@Repository
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 根据用户id查询角色
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 根据用户id查询菜单
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Long userId);
}
