package com.lingyun.wh.goods.doman.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lingyun.wh.goods.doman.GoodsType;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.domain.SysMenu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    /**
     * 货品类型树结构
     *
     * @param goodsType
     */
    public TreeSelect(GoodsType goodsType) {
        this.id = goodsType.getGtId();
        this.label = goodsType.getGtName();
        List<GoodsType> children = goodsType.getChildren();
        if (children != null && children.size() > 0){
            this.children = children.stream().map(TreeSelect::new).collect(Collectors.toList());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
