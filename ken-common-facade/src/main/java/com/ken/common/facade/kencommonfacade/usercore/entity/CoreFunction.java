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
public class CoreFunction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CODE")
    private String code;

    @TableField("NAME")
    private String name;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("ACCESS_URL")
    private String accessUrl;

    @TableField("PARENT_ID")
    private Integer parentId;

    @TableField("TYPE")
    private String type;

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
