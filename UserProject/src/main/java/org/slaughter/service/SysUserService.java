package org.slaughter.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xinyang
 * @since 2024-01-29
 */
public interface SysUserService extends IService<org.slaughter.entity.SysUserEntity> {

    /**
     * 查询有效用户
     * @return 有效用户合集
     */
    List<org.slaughter.entity.SysUserEntity>  effectiveUser();

}
