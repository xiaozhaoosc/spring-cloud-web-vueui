package com.ken.common.facade.kencommonfacade.usercore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ken.common.facade.kencommonfacade.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CoreMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CODE")
    private String code;

    @TableField("NAME")
    private String name;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("FUNCTION_ID")
    private Integer functionId;

    /**
     * 1,系统，2 导航 3 菜单项（对应某个功能点）
     */
    @TableField("TYPE")
    private String type;

    @TableField("PARENT_MENU_ID")
    private Integer parentMenuId;

    @TableField("SEQ")
    private Integer seq;

    /**
     * 图标
     */
    @TableField("ICON")
    private String icon;

    /**
     * 逻辑已删除值(默认为 1)-逻辑未删除值(默认为 0)
     */
    private Integer deleted;

    /**
     * 版本，也可用于乐观锁
     */
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime cTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime uTime;


}
