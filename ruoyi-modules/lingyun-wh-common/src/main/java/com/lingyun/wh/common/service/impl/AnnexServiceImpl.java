package com.lingyun.wh.common.service.impl;

import com.ruoyi.system.api.domain.Annex;
import com.lingyun.wh.common.mapper.AnnexMapper;
import com.lingyun.wh.common.service.IAnnexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/12/7 19:34
 */
@Service
public class AnnexServiceImpl implements IAnnexService {
    @Autowired
    private AnnexMapper annexMapper;

    /**
     * 查询附件
     *
     * @param aId 附件主键
     * @return 附件
     */
    @Override
    public Annex selectAnnexByAId(Long aId) {
        return annexMapper.selectAnnexByAId(aId);
    }

    /**
     * 查询附件列表
     *
     * @param annex 附件
     * @return 附件
     */
    @Override
    public List<Annex> selectAnnexList(Annex annex) {
        System.out.println("annex: "+ annex);
        return annexMapper.selectAnnexList(annex);
    }

    /**
     * 新增附件
     *
     * @param annexes 附件
     * @return 结果
     */
    @Override
    public int insertAnnex(List<Annex> annexes) {
        int rows = annexMapper.insertAnnex(annexes);
        System.out.println("rows: " + rows);
        return rows;
    }

    /**
     * 修改附件
     *
     * @param annex 附件
     * @return 结果
     */
    @Override
    public int updateAnnex(Annex annex) {
        return annexMapper.updateAnnex(annex);
    }

    /**
     * 批量删除附件
     *
     * @param aIds 需要删除的附件主键
     * @return 结果
     */
    @Override
    public int deleteAnnexByAIds(String[] aIds, String type) {
        if (aIds == null || aIds.length == 0) {
            return 0;
        }
        return annexMapper.deleteAnnexByAids(aIds, type);
    }

    /**
     * 删除附件信息
     *
     * @param aId 附件主键
     * @return 结果
     */
    @Override
    public int deleteAnnexByAId(String aId) {
        return annexMapper.deleteAnnexByAId(aId);
    }
}
