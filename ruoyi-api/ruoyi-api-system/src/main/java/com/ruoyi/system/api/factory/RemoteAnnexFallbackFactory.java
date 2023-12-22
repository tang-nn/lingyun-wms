package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.RemoteAnnexService;
import com.ruoyi.system.api.domain.Annex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/12/7 19:59
 */
@Component
public class RemoteAnnexFallbackFactory implements FallbackFactory<RemoteAnnexService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteAnnexService create(Throwable throwable) {
        log.error("附件服务调用失败: {}", throwable.getMessage());
        return new RemoteAnnexService() {
            @Override
            public R<?> add(List<Annex> annexes) {
                return R.fail("附件插入失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Annex>> list(String type, String formId, String content) {
                return R.fail("查询错误");
            }

            @Override
            public AjaxResult remove(String[] formId, String type) {
                log.error("type: {}，formId: {}", type, Arrays.toString(formId));
                return AjaxResult.error("附件删除失败");
            }
        };
    }
}
