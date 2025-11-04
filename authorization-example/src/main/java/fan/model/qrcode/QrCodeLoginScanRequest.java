package fan.model.qrcode;

import lombok.Data;

/**
 * 扫描二维码入参
 *
 * @author Fan
 * @since 2025/11/4 14:25
 */
@Data
public class QrCodeLoginScanRequest {

    /**
     * 二维码id
     */
    private String qrCodeId;

}
