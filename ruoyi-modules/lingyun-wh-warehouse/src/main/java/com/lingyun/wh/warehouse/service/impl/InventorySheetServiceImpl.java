package com.lingyun.wh.warehouse.service.impl;

import java.util.Date;
import java.util.List;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.service.IWareHouseService;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.mapper.InventorySheetMapper;
import com.lingyun.wh.warehouse.domain.InventorySheet;
import com.lingyun.wh.warehouse.service.IInventorySheetService;

import javax.validation.Validator;

/**
 * 盘点单Service业务层处理
 * 
 * @author LiJin
 * @date 2023-12-13
 */
@Service
public class InventorySheetServiceImpl implements IInventorySheetService 
{
    private static final Logger log = LoggerFactory.getLogger(IInventorySheetService.class);

    @Autowired
    private InventorySheetMapper inventorySheetMapper;

    @Autowired
    private RemoteEncodeRuleService remoteEncodeRuleService;

    @Autowired
    protected Validator validator;

    /**
     * 查询盘点单
     * 
     * @param isId 盘点单主键
     * @return 盘点单
     */
    @Override
    public InventorySheet selectInventorySheetByIsId(String isId)
    {
        return inventorySheetMapper.selectInventorySheetByIsId(isId);
    }

    /**
     * 查询盘点单列表
     * 
     * @param inventorySheet 盘点单
     * @return 盘点单
     */
    @Override
    public List<InventorySheet> selectInventorySheetList(Map<String,Object>map)
    {
        return inventorySheetMapper.selectInventorySheetList(map);
    }

    /**
     * 根据仓库查询下面的盘点货品
     * @return
     */
    @Override
    public List<Map<String, Object>> selectInventorySheetByWid(Map<String,Object>map) {
        return inventorySheetMapper.selectInventorySheetByWid(map);
    }

    /**
     * 新增盘点单
     * 
     * @param inventorySheet 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertInventorySheet(InventorySheet inventorySheet)
    {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        inventorySheet.setCreateTime(nowDate);
        inventorySheet.setCreateBy(uid);
        inventorySheet.setUpdateBy(uid);
        inventorySheet.setUpdateTime(nowDate);
        inventorySheet.setCreateTime(DateUtils.getNowDate());
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.INVENTORY_ORDER, 1, SecurityConstants.INNER);
        System.out.println("盘点编码获取 res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertGoodsType 盘点编码获取失败");
            throw new RuntimeException("盘点编码获取失败");
        } else {
            inventorySheet.setIsCode(res.getData()[0]);
        }
        int rows = inventorySheetMapper.insertInventorySheet(inventorySheet);//新曾盘点单
        if (rows > 0) {
            R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.INVENTORY_ORDER, 1, SecurityConstants.INNER);
            System.out.println("流水号迭代 Res: "+ r);
            inventorySheet.setIsId(inventorySheet.getIsId());//获取刚插入盘点单的自增id
            insertInventoryDetails(inventorySheet);//盘点明细


            if (r == null || r.getCode() != 200) {
                log.error("insertWareHouse 流水号迭代失败");
                throw new RuntimeException("盘点单数据插入失败");
            }
        }
        return rows;

    }


    /**
     * 新增盘点明细信息
     *
     * @param inventorySheet 盘点单对象
     */
    @Transactional
    public void insertInventoryDetails(InventorySheet inventorySheet)
    {
        List<InventoryDetails> inventoryDetailsList = inventorySheet.getInventoryDetailsList();
        String isId = inventorySheet.getIsId();
        if (StringUtils.isNotNull(inventoryDetailsList))
        {
            List<InventoryDetails> list = new ArrayList<InventoryDetails>();
            for (InventoryDetails inventoryDetails : inventoryDetailsList)
            {
                inventoryDetails.setIsId(isId);
                list.add(inventoryDetails);
            }
            if (list.size() > 0)
            {
                inventorySheetMapper.batchInventoryDetails(list);
            }
        }
    }


    /**
     * 修改盘点单
     * 
     * @param inventorySheet 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateInventorySheet(InventorySheet inventorySheet)
    {
        inventorySheet.setUpdateTime(DateUtils.getNowDate());
        inventorySheetMapper.deleteInventoryDetailsByIsId(inventorySheet.getIsId());
        insertInventoryDetails(inventorySheet);
        return inventorySheetMapper.updateInventorySheet(inventorySheet);
    }

    /**
     * 批量删除盘点单
     * 
     * @param isIds 需要删除的盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInventorySheetByIsIds(String[] isIds)
    {
        //批量删除盘点明细
        deleteInventoryDetailsByIsIds(isIds);
        return inventorySheetMapper.deleteInventorySheetByIsIds(isIds);
    }

    /**
     * 批量删除盘点明细
     *
     * @param isIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteInventoryDetailsByIsIds(String[] isIds) {
        return inventorySheetMapper.deleteInventoryDetailsByIsIds(isIds);
    }

    /**
     * 删除盘点单信息
     *
     * @param isId 盘点单主键
     * @return 结果
     */
    @Override
    public int deleteInventoryDetailsByIsId(String isId) {
        return inventorySheetMapper.deleteInventoryDetailsByIsId(isId);
    }



}
