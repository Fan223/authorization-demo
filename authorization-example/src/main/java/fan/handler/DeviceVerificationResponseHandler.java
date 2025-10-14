package fan.handler;

import fan.model.Result;
import fan.util.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static fan.constant.SecurityConstants.DEVICE_ACTIVATED_URI;

/**
 * 校验设备码成功响应类
 *
 * @author Fan
 * @since 2025/10/13 9:18
 */
public class DeviceVerificationResponseHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (UrlUtils.isAbsoluteUrl(DEVICE_ACTIVATED_URI)) {
            // 写回json数据
            Result<Object> result = Result.success(DEVICE_ACTIVATED_URI);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JsonUtils.objectCovertToJson(result));
            response.getWriter().flush();
        } else {
            response.sendRedirect(DEVICE_ACTIVATED_URI);
        }
    }
}
