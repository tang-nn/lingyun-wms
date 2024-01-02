package com.lingyun.wh.order.api;

import com.lingyun.wh.goods.api.factory.RemoteGoodsFallbackFactory;
import com.lingyun.wh.order.api.domain.PurchaseOrder;
import com.lingyun.wh.order.api.factory.RemotePurchaseOrderFallbackFactory;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : Tang
 * @CreateDate : 2023/12/24 23:50
 */
@FeignClient(contextId = "remotePurchaseOrderService", value = ServiceNameConstants.WH_ORDER_SERVICE, fallbackFactory = RemotePurchaseOrderFallbackFactory.class)
public interface RemotePurchaseOrderService {

    @GetMapping(value = "/order/purchase/calibra")
    public R<PurchaseOrder> getCalibraData(@RequestParam("poId") String poId, @RequestParam("dids") String[] dids);

    @PutMapping(value = "/order/purchase/status")
    R<Boolean> updateStatus(@RequestBody PurchaseOrder purchaseOrder, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
