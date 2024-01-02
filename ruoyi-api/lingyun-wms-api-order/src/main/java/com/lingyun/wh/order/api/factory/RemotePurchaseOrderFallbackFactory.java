package com.lingyun.wh.order.api.factory;

import com.lingyun.wh.goods.api.factory.RemoteGoodsFallbackFactory;
import com.lingyun.wh.order.api.RemotePurchaseOrderService;
import com.lingyun.wh.order.api.domain.PurchaseOrder;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Author : Tang
 * @CreateDate : 2023/12/25 0:13
 */
@Component
public class RemotePurchaseOrderFallbackFactory implements FallbackFactory<RemotePurchaseOrderService> {

    private static final Logger log = LoggerFactory.getLogger(RemotePurchaseOrderFallbackFactory.class);

    @Override
    public RemotePurchaseOrderService create(Throwable throwable) {
        log.error("订单服务调用失败: {}", throwable.getMessage());
        return new RemotePurchaseOrderService() {
            @Override
            public R<PurchaseOrder> getCalibraData(String poId, String[] dids) {
                return R.fail("进货订单获取失败");
            }

            @Override
            public R<Boolean> updateStatus(PurchaseOrder purchaseOrder, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
                R<Boolean> fail = R.fail(false);
                fail.setMsg("进货订单状态更新失败");
                return fail;
            }
        };
    }
}
