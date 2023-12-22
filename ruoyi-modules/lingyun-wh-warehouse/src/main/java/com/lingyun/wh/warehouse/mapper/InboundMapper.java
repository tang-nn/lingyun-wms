package com.lingyun.wh.warehouse.mapper;

import java.util.List;

import com.lingyun.wh.warehouse.domain.Inbound;
import com.lingyun.wh.warehouse.domain.InboundDetails;
import org.apache.ibatis.annotations.Param;

/**
 * 入库管理Mapper接口
 *
 * @author Tang
 * @date 2023-12-18
 */
public interface InboundMapper {
    /**
     * 查询入库管理
     *
     * @param inid 入库 ID
     * @return 入库管理
     */
    public Inbound selectInboundByInid(@Param("inid") String inid);

    /**
     * 查询入库管理列表
     *
     * @param inbound 入库管理
     * @return 入库管理集合
     */
    public List<Inbound> selectInboundList(Inbound inbound);

    /**
     * 新增入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    public int insertInbound(Inbound inbound);

    /**
     * 修改入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    public int updateInbound(Inbound inbound);

    /**
     * 删除入库管理
     *
     * @param inid 入库管理主键
     * @return 结果
     */
    public int deleteInboundByInid(String inid);

    /**
     * 批量删除入库管理
     *
     * @param inids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundByInids(String[] inids);

    /**
     * 批量删除入库管理明细
     *
     * @param inids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundDetailsByInIds(String[] inids);

    /**
     * 批量新增入库管理明细
     *
     * @param inboundDetailsList 入库管理明细列表
     * @return 结果
     */
    public int batchInboundDetails(List<InboundDetails> inboundDetailsList);


    /**
     * 通过入库管理主键删除入库管理明细信息
     *
     * @param inid 入库管理ID
     * @return 结果
     */
    public int deleteInboundDetailsByInId(String inid);
}
