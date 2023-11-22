package com.lingyun.wh.common.mapper;

import com.lingyun.wh.common.domain.Annex;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 附件Mapper接口
 * @CreateDate : 2023/11/20 18:33
 */
public interface AnnexMapper {

    /**
     * 查询附件
     *
     * @param aId 附件主键
     * @return 附件
     */
    public Annex selectAnnexByAId(Long aId);

    /**
     * 查询附件列表
     *
     * @param annex 附件
     * @return 附件集合
     */
    public List<Annex> selectAnnexList(Annex annex);

    /**
     * 新增附件
     *
     * @param annex 附件
     * @return 结果
     */
    public int insertAnnex(Annex annex);

    /**
     * 修改附件
     *
     * @param annex 附件
     * @return 结果
     */
    public int updateAnnex(Annex annex);

    /**
     * 删除附件
     *
     * @param aId 附件主键
     * @return 结果
     */
    public int deleteAnnexByAId(Long aId);

    /**
     * 批量删除附件
     *
     * @param aIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAnnexByAIds(Long[] aIds);

}
