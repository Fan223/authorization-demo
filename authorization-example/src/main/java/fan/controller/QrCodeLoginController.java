package fan.controller;

import fan.model.Result;
import fan.model.qrcode.*;
import fan.service.IQrCodeLoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 二维码登录接口
 *
 * @author Fan
 * @since 2025/11/4 14:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qrCode")
public class QrCodeLoginController {

    private final IQrCodeLoginService iQrCodeLoginService;

    @GetMapping("/login/generateQrCode")
    public Result<QrCodeGenerateResponse> generateQrCode() {
        // 生成二维码
        return Result.success(iQrCodeLoginService.generateQrCode());
    }

    @GetMapping("/login/fetch/{qrCodeId}")
    public Result<QrCodeLoginFetchResponse> fetch(@PathVariable String qrCodeId) {
        // 轮询二维码状态
        return Result.success(iQrCodeLoginService.fetch(qrCodeId));
    }


    @PostMapping("/scan")
    public Result<QrCodeLoginScanResponse> scan(@RequestBody QrCodeLoginScanRequest loginScan) {
        // app 扫码二维码
        return Result.success(iQrCodeLoginService.scan(loginScan));
    }

    @PostMapping("/consent")
    public Result<String> consent(@RequestBody QrCodeLoginConsentRequest loginConsent) {
        // app 确认登录
        iQrCodeLoginService.consent(loginConsent);
        return Result.success();
    }
}
