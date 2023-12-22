package com.lingyun.wh.goods.api.factory;

import com.lingyun.wh.goods.api.RemoteGoodsService;
import com.lingyun.wh.goods.api.domain.Goods;
import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/12/10 20:32
 */
@Component
public class RemoteGoodsFallbackFactory implements FallbackFactory<RemoteGoodsService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteGoodsFallbackFactory.class);

    @Override
    public RemoteGoodsService create(Throwable throwable) {
        log.error("货品服务调用失败: {}", throwable.getMessage());
        return new RemoteGoodsService() {
            @Override
            public R<ArrayList<Goods>> getInfoByIds(String[] ids) {
                return R.fail(HttpStatus.ERROR, "货物数据获取失败");
            }
        };
    }
}
