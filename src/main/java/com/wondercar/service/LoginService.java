package com.wondercar.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wondercar.entity.Menu;
import com.wondercar.entity.Role;
import com.wondercar.entity.Users;
import com.wondercar.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wondercar
 * @since: 2023-03-20
 */
@Service("userDetailsService")
public class LoginService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users users = usersMapper.selectOne(wrapper);
        if (users == null){
            throw new UsernameNotFoundException("用户名不存在!");
        }
        // 根据用户id查询角色信息
        List<Role> roles = usersMapper.selectRoleByUserId(users.getId());
        // 根据用户id查询菜单信息
        List<Menu> menus = usersMapper.selectMenuByUserId(users.getId());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        // 添加角色信息(ROLE_XX)
        for (Role role : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority("ROLE_" + role.getName());
            grantedAuthorityList.add(simpleGrantedAuthority);
        }
        // 处理菜单(xx)
        for (Menu menu : menus) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(menu.getPermission()));
        }
        // 打印当前登录用户
        //System.out.println(users);
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,role,ROLE_admin");
        return new User(users.getUsername(),
                users.getPassword(),
                grantedAuthorityList);
    }
}
