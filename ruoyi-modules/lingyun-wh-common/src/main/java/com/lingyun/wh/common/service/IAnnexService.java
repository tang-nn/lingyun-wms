package com.lingyun.wh.common.service;

import com.ruoyi.system.api.model.Annex;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 附件Service接口
 * @CreateDate : 2023/12/7 19:34
 */
public interface IAnnexService {
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
     * @param annexes 附件
     * @return 结果
     */
    @Transactional
    public int insertAnnex(List<Annex> annexes);

    /**
     * 修改附件
     *
     * @param annex 附件
     * @return 结果
     */
    public int updateAnnex(Annex annex);

    /**
     * 批量删除附件
     *
     * @param aIds 需要删除的附件主键集合
     * @return 结果
     */
    public int deleteAnnexByAIds(Long[] aIds);

    /**
     * 删除附件信息
     *
     * @param aId 附件主键
     * @return 结果
     */
    public int deleteAnnexByAId(Long aId);
}
