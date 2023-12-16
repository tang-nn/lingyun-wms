package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.factory.RemoteEncodeRuleFallbackFactory;
import com.ruoyi.system.api.factory.RemoteFileFallbackFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/30 16:58
 */
@FeignClient(contextId = "remoteEncodeRuleService", value = ServiceNameConstants.WH_COMM_SERVICE, fallbackFactory = RemoteEncodeRuleFallbackFactory.class)
public interface RemoteEncodeRuleService {

    /**
     * 生成订单编码
     */
    @GetMapping("/whcomm/encoding/genEncode/{rulesId}/{num}")
    R<String[]> genSpecifyEncoding(@PathVariable("rulesId") int rulesId, @PathVariable(value = "num", required = false) int num, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 生成订单编码后，某个服务使用编码，
     * 调用此服务进行将该编码规则的当前流水号增加
     */
    @GetMapping("/whcomm/encoding/incrEncode/{rulesId}/{num}")
    R<String> increaseCurrentSerialNumber(@PathVariable("rulesId") int rulesId, @PathVariable(value = "num", required = false) int num, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
