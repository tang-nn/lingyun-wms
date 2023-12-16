package com.lingyun.wh.warehouse.service.impl;

import com.lingyun.wh.warehouse.domain.InboundManager;
import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.mapper.InboundManagerMapper;
import com.lingyun.wh.warehouse.service.IInboundManagerService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Tang
 * @CreateDate : 2023/12/12 13:21
 */
@Service
public class InboundManagerServiceImpl implements IInboundManagerService {
    @Autowired
    private InboundManagerMapper inboundManagerMapper;

    /**
     * 查询入库管理
     *
     * @param inId 入库管理主键
     * @return 入库管理
     */
    @Override
    public InboundManager selectInboundManagerByInId(String inId) {
        InboundManager inboundManager = new InboundManager();
        inboundManager.setInId(inId);
        List<InboundManager> inboundManagers = inboundManagerMapper.selectInboundManagerList(inboundManager);
        if (inboundManagers != null && inboundManagers.size() > 0) {
            return inboundManagers.get(0);
        }
        throw new RuntimeException("入库记录不存在");
    }

    /**
     * 查询入库管理列表
     *
     * @param inboundManager 入库管理
     * @return 入库管理
     */
    @Override
    public List<InboundManager> selectInboundManagerList(InboundManager inboundManager) {
        return inboundManagerMapper.selectInboundManagerList(inboundManager);
    }

    /**
     * 新增入库管理
     *
     * @param inboundManager 入库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertInboundManager(InboundManager inboundManager) {
        inboundManager.setCreateTime(DateUtils.getNowDate());
        int rows = inboundManagerMapper.insertInboundManager(inboundManager);
        insertInventoryDetails(inboundManager);
        return rows;
    }

    /**
     * 修改入库管理
     *
     * @param inboundManager 入库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateInboundManager(InboundManager inboundManager) {
        inboundManager.setUpdateTime(DateUtils.getNowDate());
        inboundManagerMapper.deleteInventoryDetailsByInId(inboundManager.getInId());
        insertInventoryDetails(inboundManager);
        return inboundManagerMapper.updateInboundManager(inboundManager);
    }

    /**
     * 批量删除入库管理
     *
     * @param inIds 需要删除的入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundManagerByInIds(String[] inIds) {
        inboundManagerMapper.deleteInventoryDetailsByInIds(inIds);
        return inboundManagerMapper.deleteInboundManagerByInIds(inIds);
    }

    /**
     * 删除入库管理信息
     *
     * @param inId 入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundManagerByInId(String inId) {
        inboundManagerMapper.deleteInventoryDetailsByInId(inId);
        return inboundManagerMapper.deleteInboundManagerByInId(inId);
    }

    /**
     * 新增入库管理明细信息
     *
     * @param inboundManager 入库管理对象
     */
    public void insertInventoryDetails(InboundManager inboundManager) {
        List<InventoryDetails> inventoryDetailsList = inboundManager.getInventoryDetailsList();
        String inId = inboundManager.getInId();
        if (StringUtils.isNotNull(inventoryDetailsList)) {
            List<InventoryDetails> list = new ArrayList<InventoryDetails>();
            for (InventoryDetails inventoryDetails : inventoryDetailsList) {
                inventoryDetails.setInId(inId);
                list.add(inventoryDetails);
            }
            if (!list.isEmpty()) {
                inboundManagerMapper.batchInventoryDetails(list);
            }
        }
    }
}
