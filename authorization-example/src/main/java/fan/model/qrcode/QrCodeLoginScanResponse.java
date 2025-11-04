package fan.model.qrcode;

import lombok.Data;

import java.util.Set;

/**
 * 扫描二维码响应bean
 *
 * @author Fan
 * @since 2025/11/4 14:26
 */
@Data
public class QrCodeLoginScanResponse {

    /**
     * 扫描临时票据
     */
    private String qrCodeTicket;

    /**
     * 二维码状态
     */
    private Integer qrCodeStatus;

    /**
     * 是否已过期
     */
    private Boolean expired;

    /**
     * 待确认scope
     */
    private Set<String> scopes;
}
