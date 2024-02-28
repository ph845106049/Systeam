package org.slaughter.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slaughter.entity.SysUserEntity;
import org.slaughter.mapper.SysUserMapper;
import org.slaughter.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xinyang
 * @since 2024-01-29
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, org.slaughter.entity.SysUserEntity>  implements SysUserService {

    @Override
    public List<SysUserEntity> effectiveUser() {
        return baseMapper.selectList(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getDeletion,1));
    }




}
