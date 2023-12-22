package com.ruoyi.system.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.factory.RemoteAnnexFallbackFactory;
import com.ruoyi.system.api.domain.Annex;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/12/7 19:52
 */
@FeignClient(contextId = "remoteAnnexService", value = ServiceNameConstants.WH_COMM_SERVICE, fallbackFactory = RemoteAnnexFallbackFactory.class)
public interface RemoteAnnexService {


    @PostMapping("/whcomm/annex")
    R<?> add(@RequestBody List<Annex> annexes);

    @GetMapping("/whcomm/annex/list")
    R<List<Annex>> list(@RequestParam("type") String type, @RequestParam("formId") String formId, @RequestParam(value = "content", required = false, defaultValue = "") String content);

    @DeleteMapping("/whcomm/annex/rm")
    AjaxResult remove(@RequestParam("formId") String[] formId, @RequestParam("type") String type);
}
