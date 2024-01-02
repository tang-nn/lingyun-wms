package com.lingyun.wh.goods.api;

import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.goods.api.factory.RemoteGoodsFallbackFactory;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/12/10 20:31
 */
@FeignClient(contextId = "remoteGoodsService", value = ServiceNameConstants.WH_GOODS_SERVICE, fallbackFactory = RemoteGoodsFallbackFactory.class)
public interface RemoteGoodsService {

    @GetMapping(value = "/goods/inbound/")
    R<ArrayList<Goods>> getInfoByIds(@RequestParam("ids") String[] ids, @RequestParam(required = false) Map<String, Object> params);

}
