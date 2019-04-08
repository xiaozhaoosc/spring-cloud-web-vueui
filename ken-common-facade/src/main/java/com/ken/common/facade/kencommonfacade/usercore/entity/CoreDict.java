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
 * 字典表
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CoreDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("VALUE")
    private String value;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 字典编码
     */
    @TableField("TYPE")
    private String type;

    /**
     * 类型描述
     */
    @TableField("TYPE_NAME")
    private String typeName;

    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 父id
     */
    @TableField("PARENT")
    private Integer parent;

    /**
     * 删除标记
     */
    @TableField("DEL_FLAG")
    private Integer delFlag;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 创建时间
     */
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
