package fan.filter;

import fan.constant.SecurityConstants;
import fan.exception.InvalidCaptchaException;
import fan.support.RedisOperator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;

import static fan.constant.RedisConstants.IMAGE_CAPTCHA_PREFIX_KEY;

/**
 * 验证码校验过滤器
 *
 * @author vains
 */
@Slf4j
public class CaptchaAuthenticationFilter extends GenericFilterBean {

    private final RedisOperator<String> redisOperator;
    private final RequestMatcher requiresAuthenticationRequestMatcher;
    private AuthenticationFailureHandler failureHandler;

    /**
     * 初始化该过滤器，设置拦截的地址
     *
     * @param defaultFilterProcessesUrl 拦截的地址
     */
    public CaptchaAuthenticationFilter(RedisOperator<String> redisOperator, String defaultFilterProcessesUrl) {
        this.redisOperator = redisOperator;
        Assert.hasText(defaultFilterProcessesUrl, "defaultFilterProcessesUrl cannot be null.");
        requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(defaultFilterProcessesUrl);
        failureHandler = new SimpleUrlAuthenticationFailureHandler(defaultFilterProcessesUrl + "?error");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 检验是否是post请求并且是需要拦截的地址
        if (!this.requiresAuthenticationRequestMatcher.matches(request) || !request.getMethod().equals(HttpMethod.POST.toString())) {
            chain.doFilter(request, response);
            return;
        }

        // 获取当前登录方式
        String loginType = request.getParameter("loginType");
        if (!Objects.equals(loginType, SecurityConstants.PASSWORD_LOGIN_TYPE)) {
            // 只要不是密码登录都不需要校验图形验证码
            log.info("It isn't necessary captcha authenticate.");
            chain.doFilter(request, response);
            return;
        }

        // 开始校验验证码
        log.info("Authenticate captcha...");

        // 获取参数中的验证码
        String code = request.getParameter("code");
        if (ObjectUtils.isEmpty(code)) {
            throw new InvalidCaptchaException("The captcha cannot be empty.");
        }

        String captchaId = request.getParameter(SecurityConstants.CAPTCHA_ID_NAME);
        // 获取缓存中存储的验证码
        String captchaCode = redisOperator.getAndDelete((IMAGE_CAPTCHA_PREFIX_KEY + captchaId));
        if (!ObjectUtils.isEmpty(captchaCode)) {
            if (!captchaCode.equalsIgnoreCase(code)) {
                throw new InvalidCaptchaException("The captcha is incorrect.");
            }
        } else {
            throw new InvalidCaptchaException("The captcha is abnormal. Obtain it again.");
        }

        log.info("Captcha authenticated.");
        // 验证码校验通过开始执行接下来的逻辑
        chain.doFilter(request, response);
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }
}
