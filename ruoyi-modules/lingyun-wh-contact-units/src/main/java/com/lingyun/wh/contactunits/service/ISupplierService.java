package com.lingyun.wh.contactunits.service;

import com.lingyun.wh.contactunits.domain.Supplier;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 供应商Service接口
 * @CreateDate : 2023/11/21 16:43
 */
public interface ISupplierService {
    /**
     * 查询供应商
     *
     * @param sId 供应商主键
     * @return 供应商
     */
    public Supplier selectSupplierBySId(String sId);

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 批量删除供应商
     *
     * @param sIds 需要删除的供应商主键集合
     * @return 结果
     */
    public int deleteSupplierBySIds(String[] sIds);

    /**
     * 删除供应商信息
     *
     * @param sId 供应商主键
     * @return 结果
     */
    public int deleteSupplierBySId(String sId);
}
