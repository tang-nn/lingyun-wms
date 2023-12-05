package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/30 17:03
 */
@Component
public class RemoteEncodeRuleFallbackFactory implements FallbackFactory<RemoteEncodeRuleService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteEncodeRuleService.class);

    @Override
    public RemoteEncodeRuleService create(Throwable throwable)
    {
        log.error("订单编码服务调用失败:{}", throwable.getMessage());
        return new RemoteEncodeRuleService() {
            @Override
            public R<String> genSpecifyEncoding(int encodingRules, String source) {
                return R.fail("编码生成失败:" + throwable.getMessage());
            }

            @Override
            public R<String> increaseCurrentSerialNumber(int encodingRules, String source) {
                return R.fail("流水号迭代失败:" + throwable.getMessage());
            }
        };
    }
}
