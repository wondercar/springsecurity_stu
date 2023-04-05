package com.wondercar.entity;

import lombok.Data;

/**
 * 菜单实体类
 *
 * @Author: wondercar
 * @Since: 2023-03-20
 */
@Data
public class Menu {
    private Long id;
    private String name;
    private String url;
    private Long parentId;
    private String permission;
}
