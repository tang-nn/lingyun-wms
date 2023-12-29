package com.lingyun.wh.warehouse.controller;

import com.lingyun.wh.warehouse.domain.InventorySheet;
import org.apache.commons.collections4.MapUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.TransferDetails;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.service.ITransferService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 调拨单Controller
 *
 * @author LIJin
 * @date 2023-12-18
 */
@RestController
@RequestMapping("/transfer")
public class TransferController extends BaseController {
    @Autowired
    private ITransferService transferService;


    /**
     * 查询调拨单列表
     */
    @RequiresPermissions("transfer:transfer:list")
    @GetMapping("/list")
    public TableDataInfo list(Transfer transfer) {
        startPage();
        List<Transfer> list = transferService.selectTransferList(transfer);
        if(list != null && !list.isEmpty()){
            Transfer t = list.get(0); // 获取第一个Transfer对象
            String w_id = t.getInWId(); // 获取入库仓库inWId属性值
            List<TransferDetails> transferDetailsList = t.getTransferDetailsList(); // 获取TransferDetails列表
            if (transferDetailsList != null && transferDetailsList.size() > 0)
                for (TransferDetails transferDetails : transferDetailsList) {
                    String gId = transferDetails.getGoods().getGId(); // 获取gId属性值
                    transferDetails.setTotalItemQuantity(transferService.InItemQuantity(w_id, gId));
                }
        }
        return getDataTable(list);
    }

    /**
     * 获取调拨单详细信息
     */
    @RequiresPermissions("transfer:transfer:query")
    @GetMapping(value = "/{tId}")
    public AjaxResult getInfo(@PathVariable("tId") String tId)
    {

        Transfer transfer = transferService.transferByTid(tId);
        List<TransferDetails> t = transfer.getTransferDetailsList();
        if (t != null && t.size() > 0)
            for (TransferDetails transferDetails : t) {
                String gId = transferDetails.getGoods().getGId(); // 获取gId属性值
                transferDetails.setTotalItemQuantity(transferService.InItemQuantity(transfer.getInWId(), gId));
//                List<StorageLocation> location = transferService.getLocation(transfer.getInWId(), gId);
//                System.out.println("location======="+location+";;;;"+transfer.getInWId()+"[][][]"+gId);
//
//                if (location!=null){
//                    for (StorageLocation s : location) {
//                        String slId = s.getSlId();
//                        transferDetails.setInLocationId(slId);
//                    }
//                }

            }
        return success(transfer);
    }


    /**
     * 新增调拨单
     */
    @RequiresPermissions("transfer:transfer:add")
    @Log(title = "调拨单", businessType = BusinessType.INSERT)
    @PostMapping("/addtransfer")
    public AjaxResult add(@RequestBody Transfer transfer) {
        return toAjax(transferService.insertTransfer(transfer));
    }


    /**
     * 修改调拨单
     */
    @RequiresPermissions("transfer:transfer:edit")
    @Log(title = "调拨单", businessType = BusinessType.UPDATE)
    @PutMapping("/editTransfer")
    public AjaxResult edit(@RequestBody Transfer transfer) {
        return toAjax(transferService.updateTransfer(transfer));
    }

    /**
     * 删除调拨单
     */
    @RequiresPermissions("transfer:transfer:remove")
    @Log(title = "调拨单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tids}")
    public AjaxResult remove(@PathVariable String[] tids) {
        return toAjax(transferService.deleteTransferByTids(tids));
    }


    /**
     * 删除调拨单明细
     */
    @RequiresPermissions("transfer:transfer:remove")
    @Log(title = "调拨单", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteDetails/{tdId}")
    public AjaxResult deleteDetails(@PathVariable String[] tdId) {
        return toAjax(transferService.deleteTransferDetailsByTdIds(tdId));
    }

    /*根据调拨仓库id查询所有库位*/
    @PostMapping(value = "/{w_id}")
    public AjaxResult getslName(@PathVariable("w_id") String w_id) {
        return success(transferService.location(w_id));
    }

//    @PostMapping("/inNumsPlans")
//    public AjaxResult inNumsPlan(@RequestBody Map<String, Object> map) {
//        System.out.println("map-------------------------" + map);
//        String w_id = MapUtils.getString(map, "w_id");//入库仓库id
//
//        List<String> slId = (List<String>) map.get("sl_id");
//        List<String> gids = (List<String>) map.get("g_id");
//
//        if(slId.length != gids.length){
//
//        }
//
//        for (int i = 0; i < sl_id.length && i < g_id.length; i++) {
//            String slId = sl_id[i];
//            String gId = g_id[i];
//        }
//      return success(transferService.numberPlansIn(w_id, sl_id, g_id));
//    }


    /**
     * 导出调拨单列表
     */
    @RequiresPermissions("transfer:transfer:export")
    @Log(title = "调拨单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Transfer transfer) {
        List<Transfer> list = transferService.selectTransferList(transfer);
        ExcelUtil<Transfer> util = new ExcelUtil<Transfer>(Transfer.class);
        util.exportExcel(response, list, "调拨单数据");
    }


    /**
     * 审核调拨单
     */
    @RequiresPermissions("transfer:transfer:reviewInventory")
    @PostMapping("/reviewInventory")
    public AjaxResult reviewInventory(@RequestBody Transfer transfer)
    {
        return toAjax(transferService.reviewInventory(transfer));
    }
}
