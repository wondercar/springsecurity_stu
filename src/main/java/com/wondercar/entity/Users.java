package com.wondercar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 *
 * @author: wondercar
 * @since: 2023-03-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("users")
public class Users {
    private Long id;
    private String username;
    private String password;
}
