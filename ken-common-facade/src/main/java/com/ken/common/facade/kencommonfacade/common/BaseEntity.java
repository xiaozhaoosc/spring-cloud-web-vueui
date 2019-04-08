package com.ken.common.facade.kencommonfacade.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description: 实体类的公共父类
 *
 * @author kenzhao
 * @date 2019/3/29 18:28
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
    private Integer id;
    // 这2个参数由layui页面初始化list表单时，传过来
    @TableField(exist=false)
    private Integer pageIndex = 1;// 当前页码
    @TableField(exist=false)
    private Integer pageSize = 10;// 当前页条数

    // 菜单名称
    @TableField(exist=false)
    private String menuNames;
    // 按钮操作功能名称
    @TableField(exist=false)
    private String functionNames;
}
