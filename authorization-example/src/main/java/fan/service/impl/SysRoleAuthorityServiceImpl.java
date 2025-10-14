package fan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fan.entity.SysRoleAuthority;
import fan.mapper.SysRoleAuthorityMapper;
import fan.service.ISysRoleAuthorityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单多对多关联表 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2025-10-11
 */
@Service
public class SysRoleAuthorityServiceImpl extends ServiceImpl<SysRoleAuthorityMapper, SysRoleAuthority> implements ISysRoleAuthorityService {

}
