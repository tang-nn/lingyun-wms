package com.ruoyi.system.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.factory.RemoteAnnexFallbackFactory;
import com.ruoyi.system.api.model.Annex;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.factory.RemoteEncodeRuleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    TableDataInfo list(@RequestParam("annex") Annex annex);
}
