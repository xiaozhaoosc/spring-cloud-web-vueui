package com.ken.common.facade.kencommonfacade.usercore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ken.common.facade.kencommonfacade.common.BaseEntity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Data
@Accessors(chain = true)
public class CoreRole {
//public class CoreRole extends BaseEntity {

//    // 这2个参数由layui页面初始化list表单时，传过来
//    @TableField(exist=false)
//    private Integer pageIndex = 1;// 当前页码
//    @TableField(exist=false)
//    private Integer pageSize = 10;// 当前页条数
//
//    // 菜单名称
//    @TableField(exist=false)
//    private String menuNames;
//    // 按钮操作功能名称
//    @TableField(exist=false)
//    private String functionNames;


    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 角色名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 1 可以配置 2 固定权限角色
     */
    @TableField("TYPE")
    private String type;

//    /**
//     * 逻辑已删除值(默认为 1)-逻辑未删除值(默认为 0)
//     */
//    @TableLogic(value = "0", delval = "1")
//    @TableField(select = false)
//    private Integer deleted;

//    /**
//     * 版本，也可用于乐观锁
//     */
//    @Version
//    private Integer version;
//
//    /**
//     * 创建时间
//     */
//    private LocalDateTime cTime;
//
//    /**
//     * 最后修改时间
//     */
//    private LocalDateTime uTime;
//
//
//    @TableField(exist = false)
//    private String functionIds;

//    @TableField(exist = false)
//    private Object delegate;
}
