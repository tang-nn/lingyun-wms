package com.lingyun.wh.warehouse.service.impl;

import java.util.Date;
import java.util.List;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.WareHouse;
import com.lingyun.wh.warehouse.mapper.WareHouseMapper;
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

import javax.validation.Validator;

/**
 * 仓库Service业务层处理
 *
 * @author lijin
 * @date 2023-12-07
 */
@Service
public class WareHouseServiceImpl implements IWareHouseService {
    private static final Logger log = LoggerFactory.getLogger(WareHouseServiceImpl.class);
    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Autowired
    private RemoteEncodeRuleService remoteEncodeRuleService;

    @Autowired
    protected Validator validator;

    /**
     * 查询仓库
     *
     * @param wId 仓库主键
     * @return 仓库
     */
    @Override
    public List<Map<String, Object>> selectWareHouseByWId(String wId) {
        return wareHouseMapper.selectWareHouseByWId(wId);
    }

    @Override
    public List<WareHouse> lists() {
        return wareHouseMapper.lists();
    }

    /**
     * 查询仓库列表
     *
     * @return 仓库
     */
    @Override
    public List<Map<String, Object>> selectWareHouseList(Map<String, Object> map) {
        return wareHouseMapper.selectWareHouseList(map);
    }

    @Override
    public int changeStatus(Map<String, Object> map) {
        return wareHouseMapper.changeStatus(map);
    }

    /**
     * 新增仓库
     *
     * @param wareHouse 仓库
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWareHouse(WareHouse wareHouse) {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        wareHouse.setCreateTime(nowDate);
        wareHouse.setCreateBy(uid);
        wareHouse.setUpdateTime(nowDate);
        wareHouse.setUpdateBy(uid);
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.WARE_CODE, 1, SecurityConstants.INNER);
        System.out.println("仓库编码获取 res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertGoodsType 仓库编码获取失败");
            throw new RuntimeException("仓库编码获取失败");
        } else {
            wareHouse.setwCode(res.getData()[0]);
        }

        int rows = wareHouseMapper.insertWareHouse(wareHouse);
        String wid = wareHouse.getwId();
        if (rows > 0) {
            R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.WARE_CODE, 1, SecurityConstants.INNER);
            System.out.println("流水号迭代 Res: "+ r);
            if (r == null || r.getCode() != 200) {
                log.error("insertWareHouse 流水号迭代失败");
                throw new RuntimeException("仓库数据插入失败");
            }
        }

        //先获取新增仓库的wid
        wareHouse.setwId(wid);
        //新增库位
        insertStorageLocation(wareHouse);
        return rows;
    }


    /**
     * 新增库位信息信息
     *
     * @param wareHouse 仓库对象
     */
    @Transactional
    public void insertStorageLocation(WareHouse wareHouse) {
        R<String[]> res = null;
        List<StorageLocation> storageLocationList = wareHouse.getStorageLocationList();//获取仓库下的库位集合
        String wId = wareHouse.getwId();
        if (StringUtils.isNotNull(storageLocationList)) {
            List<StorageLocation> list = new ArrayList<StorageLocation>();
            int size = storageLocationList.size();
            res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.STORAGE_CODE, size, SecurityConstants.INNER);
            System.out.println("库位编码获取 res: " + res);
            if (res == null || res.getCode() != 200) {
                log.error("insertGoodsType 库位编码获取失败");
                throw new RuntimeException("库位编码获取失败");
            }
            String[] encodes = res.getData();
            for (int i = 0; i < encodes.length; i++) {
                StorageLocation storageLocation = storageLocationList.get(i);
                storageLocation.setSlCode(encodes[i]);
                storageLocation.setwId(wId);
                storageLocation.setCreateTime(wareHouse.getCreateTime());
                storageLocation.setCreateBy(wareHouse.getCreateBy());
                storageLocation.setUpdateTime(wareHouse.getUpdateTime());
                storageLocation.setUpdateBy(wareHouse.getUpdateBy());
                list.add(storageLocation);
            }
            if (list.size() > 0) {
                int i = wareHouseMapper.batchStorageLocation(list);
                System.out.println("batchStorageLocation i: " + i);
                if (i > 0) {
                    R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.STORAGE_CODE, size, SecurityConstants.INNER);
                    System.out.println("insertStorageLocation 流水号迭代 Res: "+ r);
                    if (r == null || r.getCode() != 200){
                        log.error("insertStorageLocation 流水号迭代失败");
                        throw new RuntimeException("库位数据插入失败");
                    }
                }
            }
        }
    }


    /**
     * 修改仓库
     *
     * @param wareHouse 仓库
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWareHouse(WareHouse wareHouse) {
        wareHouse.setUpdateTime(DateUtils.getNowDate());
        wareHouseMapper.deleteStorageLocationByWId(wareHouse.getwId());
        insertStorageLocation(wareHouse);
        return wareHouseMapper.updateWareHouse(wareHouse);
    }

    /**
     * 批量删除仓库
     *
     * @param wIds 需要删除的仓库主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWareHouseByWIds(String[] wIds) {
        wareHouseMapper.deleteStorageLocationByWIds(wIds);
        return wareHouseMapper.deleteWareHouseByWIds(wIds);
    }

    /**
     * 批量删除库位信息
     *
     * @param wIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteStorageLocationByWIds(String[] wIds) {
        return wareHouseMapper.deleteStorageLocationByWIds(wIds);
    }

    /**
     * 删除仓库信息
     *
     * @param wId 仓库主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWareHouseByWId(String wId) {
        wareHouseMapper.deleteStorageLocationByWId(wId);
        return wareHouseMapper.deleteWareHouseByWId(wId);
    }

    @Override
    public List<StorageLocation> selectStorageListfindByWid(Map<String, Object> map) {
        return wareHouseMapper.selectStorageListfindByWid(map);
    }


}
