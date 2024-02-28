/**
 * Copyright (c) 2020 云好药 All rights reserved.
 *
 * http://www.yunhaoyao.com
 *
 * 版权所有，侵权必究！
 */

package org.slaughter.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类，所有实体都需要继承
 *
 * @author admin
 */
@Data
public abstract class BaseEntity extends Model implements Serializable {
    /**
     * id
     */
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
}