package fan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fan.entity.Oauth2ThirdAccount;

/**
 * <p>
 * 三方登录账户信息表 服务类
 * </p>
 *
 * @author Fan
 * @since 2025-10-11
 */
public interface IOauth2ThirdAccountService extends IService<Oauth2ThirdAccount> {

    /**
     * 检查是否存在该用户信息，不存在则保存，暂时不做关联基础用户信息，由前端引导完善/关联基础用户信息
     *
     * @param basicOauth2User 用户信息
     */
    void checkAndSaveUser(Oauth2ThirdAccount basicOauth2User);
}
