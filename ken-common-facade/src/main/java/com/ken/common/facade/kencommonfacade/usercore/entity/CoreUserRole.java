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
 * 用户角色关系表
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CoreUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("USER_ID")
    private Integer userId;

    @TableField("ROLE_ID")
    private Integer roleId;

    @TableField("ORG_ID")
    private Integer orgId;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

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
