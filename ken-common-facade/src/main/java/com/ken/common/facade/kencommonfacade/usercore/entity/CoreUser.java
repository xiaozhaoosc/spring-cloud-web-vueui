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
 * 
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CoreUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CODE")
    private String code;

    @TableField("NICK_NAME")
    private String nickName;

    @TableField("NAME")
    private String name;

    @TableField("PHONE")
    private String phone;

    @TableField("PASSWORD")
    private String password;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("ORG_ID")
    private Integer orgId;

    /**
     * 用户状态 1:启用 0:停用
     */
    @TableField("STATE")
    private String state;

    @TableField("JOB_TYPE1")
    private String jobType1;

    /**
     * 用户删除标记 0:未删除 1:已删除
     */
    @TableField("DEL_FLAG")
    private Integer delFlag;

    @TableField("update_Time")
    private LocalDateTime updateTime;

    @TableField("JOB_TYPE0")
    private String jobType0;

    private String attachmentId;

    @TableField("LANGUAGE")
    private String language;

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
