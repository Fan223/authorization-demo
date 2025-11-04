package fan.model.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成二维码响应
 *
 * @author Fan
 * @since 2025/11/4 14:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeGenerateResponse {

    /**
     * 二维码id
     */
    private String qrCodeId;

    /**
     * 二维码base64值(这里响应一个链接好一些)
     */
    private String imageData;
}
