package com.lingyun.wh.contactunits.service.impl;

import com.lingyun.wh.contactunits.domain.Supplier;
import com.lingyun.wh.contactunits.mapper.SupplierMapper;
import com.lingyun.wh.contactunits.service.ISupplierService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 供应商Service业务层处理
 * @CreateDate : 2023/11/21 16:43
 */
@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 查询供应商
     *
     * @param sId 供应商主键
     * @return 供应商
     */
    @Override
    public Supplier selectSupplierBySId(String sId) {
        return supplierMapper.selectSupplierBySId(sId);
    }

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier) {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier) {
        supplier.setCreateTime(DateUtils.getNowDate());
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier) {
        supplier.setUpdateTime(DateUtils.getNowDate());
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 批量删除供应商
     *
     * @param sIds 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierBySIds(String[] sIds) {
        return supplierMapper.deleteSupplierBySIds(sIds);
    }

    /**
     * 删除供应商信息
     *
     * @param sId 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierBySId(String sId) {
        return supplierMapper.deleteSupplierBySId(sId);
    }

    @Override
    public List<Supplier> supplierQurey() {
        return null;
    }

    @Override
    public boolean supUpdate(String sid) {
        return supplierMapper.supUpdate(sid) >0;
    }

}
