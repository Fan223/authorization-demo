package fan.model.qrcode;

import lombok.Data;

/**
 * 二维码登录确认入参
 *
 * @author Fan
 * @since 2025/11/4 14:27
 */
@Data
public class QrCodeLoginConsentRequest {

    /**
     * 二维码id
     */
    private String qrCodeId;

    /**
     * 扫码二维码后产生的临时票据(仅一次有效)
     */
    private String qrCodeTicket;
}
