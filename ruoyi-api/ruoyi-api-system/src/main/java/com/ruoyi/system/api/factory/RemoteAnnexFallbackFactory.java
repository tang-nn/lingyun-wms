package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteAnnexService;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import com.ruoyi.system.api.model.Annex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/12/7 19:59
 */
@Component
public class RemoteAnnexFallbackFactory implements FallbackFactory<RemoteAnnexService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteAnnexService create(Throwable throwable) {
        log.error("附件服务调用失败: {}", throwable.getMessage());
        return annexes -> R.fail("编码生成失败:" + throwable.getMessage());
    }
}
