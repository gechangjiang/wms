package com.tdt.modular.outstore.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tdt.core.common.constant.Constants;
import com.tdt.core.util.SequenceBuilder;
import com.tdt.modular.base.entity.Commodity;
import com.tdt.modular.base.service.CommodityService;
import com.tdt.modular.outstore.entity.*;
import com.tdt.modular.outstore.mapper.OutorderDetailMapper;
import com.tdt.modular.outstore.mapper.OutorderMapper;
import com.tdt.modular.outstore.mapper.OutorderTagMapper;
import com.tdt.modular.outstore.mapper.PicktaskLockDetailMapper;
import com.tdt.modular.repertory.entity.Stock;
import com.tdt.modular.repertory.entity.StockLog;
import com.tdt.modular.repertory.mapper.StockLogMapper;
import com.tdt.modular.repertory.mapper.StockMapper;
import com.tdt.modular.repertory.service.StockService;
import com.tdt.sys.core.shiro.ShiroKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class WellenService {

    @Autowired
    private OutorderService outorderService;

    @Resource
    private OutorderMapper outorderMapper;

    @Resource
    private OutorderTagMapper outorderTagMapper;

    @Resource
    private OutorderDetailMapper outorderDetailMapper;

    @Autowired
    private OutorderTagService outorderTagService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private StockService stockService;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private StockLogMapper stockLogMapper;

    @Autowired
    private PicktaskService picktaskService;

    @Autowired
    private PicktaskOutorderService picktaskOutorderService;

    @Autowired
    private PicktaskDetailService picktaskDetailService;

    @Autowired
    private PicktaskLockDetailService picktaskLockDetailService;

    @Resource
    private PicktaskLockDetailMapper picktaskLockDetailMapper;

    public JSONObject add(String checkValue,Integer qty){
        JSONObject result = new JSONObject();
        String[] _checkValue = checkValue.split(",");
        String[] _specialValue = {"oneAndOne","oneAndAny","anyAndOne","anyAndAny"};
        StringBuffer sql= new StringBuffer("");
        StringBuffer responseData= new StringBuffer("");
        //????????????????????????sql????????????
        for(int i=0;_checkValue!=null&&i<_checkValue.length;i++){
            if(!"".equals(sql.toString())){
                sql.append(",");
            }
            sql.append(_checkValue[i]);
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????type?????????sql????????????
        for(int i=0;_specialValue!=null&&i<_specialValue.length;i++){
            if(sql.indexOf("type")==-1){
                if (sql.lastIndexOf(_specialValue[i])!=-1){
                    sql.replace(sql.lastIndexOf(_specialValue[i]),sql.lastIndexOf(_specialValue[i])+_specialValue[i].length(),"type");
                }
            }else{
                if (sql.lastIndexOf(_specialValue[i])!=-1){
                    sql.replace(sql.lastIndexOf(_specialValue[i]),sql.lastIndexOf(_specialValue[i])+_specialValue[i].length()+1,"");
                }
            }
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        for(int i=0;_specialValue!=null&&i<_specialValue.length;i++){
            List<Map<String, Object>> pccQtyMapList=new ArrayList<Map<String, Object>>();
            String errormsg="";
            if (Arrays.asList(_checkValue).contains(_specialValue[i])) {
                String typevalue="";
                if(_specialValue[i]=="oneAndOne"){
                    typevalue=Constants.OutorderTagType.ONEANDONE;
                    errormsg="????????????????????????????????????";
                }else if(_specialValue[i]=="oneAndAny"){
                    typevalue=Constants.OutorderTagType.ONEANDANY;
                    errormsg="????????????????????????????????????";
                } else if(_specialValue[i]=="anyAndOne"){
                    typevalue=Constants.OutorderTagType.ANYANDONE;
                    errormsg="????????????????????????????????????";
                } else if(_specialValue[i]=="anyAndAny"){
                    typevalue=Constants.OutorderTagType.ANYANDANY;
                    errormsg="????????????????????????????????????";
                }
                //?????????????????????????????????????????????????????????map??????
                pccQtyMapList = outorderTagMapper.groupList(ShiroKit.getUser().getWarehouseId(), typevalue, sql.toString(), qty);
            }
            if (pccQtyMapList.size()>0){
                int flagIsTrue = 0;
                String[] str = sql.toString().split(",");
                for (int j=0;j<pccQtyMapList.size();j++){
                    Map<String,String> map=new HashMap();
                    Map<String, Object> pccQtyMap = pccQtyMapList.get(j);
                    for(int n=0;str!=null&&n<str.length;n++){
                        if (Arrays.asList(str).contains(str[n])){
                            map.put(str[n],pccQtyMap.get(str[n]).toString());
                        }
                    }
                    while (1==1){
                        List<Long> ids = outorderMapper.pcctOutorderList(map,0,qty);
                        if (ids==null||qty>ids.size()){
                            break;
                        } else {
                            String[] _ids = new String[ids.size()];
                            for(int x=0;x<ids.size();x++){
                                _ids[x]=ids.get(x).toString();
                            }
                            boolean flag = picktaskTag(ids,_ids);
                            if (flag==true){
                                flagIsTrue++;
                                break;
                            }
                        }
                    }
                }
                responseData.append(errormsg+flagIsTrue+"???");
            }
        }
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (Arrays.asList(_checkValue).contains("oneAndOne")||Arrays.asList(_checkValue).contains("oneAndAny")||Arrays.asList(_checkValue).contains("anyAndOne")||Arrays.asList(_checkValue).contains("anyAndAny")){

        }else {
            List<Map<String, Object>> pccQtyMapList = outorderTagMapper.groupList(ShiroKit.getUser().getWarehouseId(),null,sql.toString(),qty);
            if (pccQtyMapList.size()>0){
                int flagIsTrue = 0;
                String[] str = sql.toString().split(",");
                for (int j=0;j<pccQtyMapList.size();j++){
                    Map<String,String> map=new HashMap();
                    Map<String, Object> pccQtyMap = pccQtyMapList.get(j);
                    for(int n=0;str!=null&&n<str.length;n++){
                        if (Arrays.asList(str).contains(str[n])){
                            map.put(str[n],pccQtyMap.get(str[n]).toString());
                        }
                    }
                    while (1==1){
                        List<Long> ids = outorderMapper.pcctOutorderList(map,0,qty);
                        if (ids==null||qty>ids.size()){
                            break;
                        } else {
                            String[] _ids = new String[ids.size()];
                            for(int x=0;x<ids.size();x++){
                                _ids[x]=ids.get(x).toString();
                            }
                            boolean flag = picktaskTag(ids,_ids);
                            if (flag==true){
                                flagIsTrue++;
                                break;
                            }
                        }
                    }
                }
                responseData.append("????????????????????????????????????"+flagIsTrue+"???");
            }
        }
        result.put("code", 200);
        result.put("message", "??????????????????,"+responseData);
        return result;
    }

    private boolean picktaskTag(List<Long> ids,String[] _ids){
        //????????????????????????????????????????????????????????????id?????????????????????????????????????????????????????????
        List<Long> noenough = outorderDetailMapper.noenough(_ids);
        if(noenough!=null&&noenough.size()>0){
            return false;
        }
        List<Outorder> outorders = (List<Outorder>)outorderService.listByIds(ids);
        for(int i=0;outorders!=null&&i<outorders.size();i++){
            outorders.get(i).setState(Constants.OutorderState.DISTRIBUTED);
        }
        outorderService.updateBatchById(outorders);
        outorderTagService.remove(new QueryWrapper<OutorderTag>().in("pid",ids));
        //??????picktask?????????
        Picktask picktask = new Picktask();
        String picktaskno = SequenceBuilder.generateNo(Constants.PREFIX_NO.PICKTASK_NO);
        picktask.setPicktaskno(picktaskno);
        picktask.setWarehouseid(ShiroKit.getUser().getWarehouseId());
        picktask.setCreateid(ShiroKit.getUser().getId());
        picktask.setCreator(ShiroKit.getUser().getName());
        picktask.setCreatetime(new Date());
        picktaskService.save(picktask);
        //?????????????????????????????????id
        List<PicktaskOutorder> picktaskOutorders=new ArrayList<PicktaskOutorder>();
        for(int i=0;outorders!=null&&i<outorders.size();i++){
            Outorder outorder=outorders.get(i);
            //??????picktaskoutorder?????????
            PicktaskOutorder picktaskOutorder = new PicktaskOutorder();
            picktaskOutorder.setPid(picktask.getId());
            picktaskOutorder.setOutorderid(outorder.getId());
            picktaskOutorder.setOutorderno(outorder.getOutorderno());
            picktaskOutorders.add(picktaskOutorder);
        }
        picktaskOutorderService.saveBatch(picktaskOutorders);
        List<Map<String, Object>> commodityQtyMapList = outorderDetailMapper.countCommodityQty(_ids);
        //?????????????????????picktaskdetail??????
        for (int p=0;p<commodityQtyMapList.size();p++){
            //??????????????????????????????????????????
            Long commodityId = Long.valueOf(commodityQtyMapList.get(p).get("commodityid").toString());
            Integer qtySum = Integer.valueOf(commodityQtyMapList.get(p).get("sqty").toString());
            //?????????????????????????????????
            //?????????????????????????????????pid,commodityid,commoditybar,commodityname???
            PicktaskDetail picktaskDetail = new PicktaskDetail();
            picktaskDetail.setPid(picktask.getId());
            picktaskDetail.setCommodityid((Long)commodityQtyMapList.get(p).get("commodityid"));
            picktaskDetail.setCommoditybar(commodityQtyMapList.get(p).get("commodityid").toString());
            picktaskDetail.setCommodityname(commodityQtyMapList.get(p).get("commodityid").toString());
            //??????????????????????????????????????????????????????list??????
            List<Stock> stockList = stockMapper.queryCommonLocatorStock(commodityId);
            //????????????????????????????????????
            for (Stock stock:stockList){
                //???????????????????????????????????????????????????????????????
                if (stock.getUqty()>=qtySum){
                    stock.setUqty(stock.getUqty()-qtySum);
                    stock.setLqty(stock.getLqty()+qtySum);
                    stock.setUpdateid(ShiroKit.getUser().getId());
                    stock.setUpdator(ShiroKit.getUser().getName());
                    stock.setUpdatetime(new Date());
                    StockLog stockLog = stockService.addStockLog(picktask.getPicktaskno(),stock.getWarehouseid(),stock.getLocatorid(),
                            stock.getLocatorcode(),stock.getLocatorname(),stock.getCommodityid(),stock.getCommoditybar(),
                            stock.getCommodityname(),qtySum,Constants.StockLogType.LOCKSTOCK);
                    //????????????????????????????????????stockid,stocklogid,qty,locatorid,locatorcode,locatorname???
                    picktaskDetail.setId(null);
                    picktaskDetail.setStockid(stock.getId());
                    picktaskDetail.setStocklogid(stockLog.getId());
                    picktaskDetail.setQty(qtySum);
                    picktaskDetail.setLocatorid(stock.getLocatorid());
                    picktaskDetail.setLocatorcode(stock.getLocatorcode());
                    picktaskDetail.setLocatorname(stock.getLocatorname());
                    picktaskDetailService.save(picktaskDetail);
                    break;
                } else {
                    picktaskDetail.setId(null);
                    picktaskDetail.setQty(stock.getUqty());
                    stock.setLqty(stock.getLqty()+stock.getUqty());
                    qtySum = qtySum-stock.getUqty();
                    stock.setUqty(0);
                    stock.setUpdateid(ShiroKit.getUser().getId());
                    stock.setUpdator(ShiroKit.getUser().getName());
                    stock.setUpdatetime(new Date());
                    StockLog stockLog = stockService.addStockLog(picktask.getPicktaskno(),stock.getWarehouseid(),stock.getLocatorid(),
                            stock.getLocatorcode(),stock.getLocatorname(),stock.getCommodityid(),stock.getCommoditybar(),
                            stock.getCommodityname(),stock.getUqty(),Constants.StockLogType.LOCKSTOCK);
                    picktaskDetail.setStockid(stock.getId());
                    picktaskDetail.setStocklogid(stockLog.getId());
                    picktaskDetail.setLocatorid(stock.getLocatorid());
                    picktaskDetail.setLocatorcode(stock.getLocatorcode());
                    picktaskDetail.setLocatorname(stock.getLocatorname());
                    picktaskDetailService.save(picktaskDetail);
                }
            }
            stockService.updateBatchById(stockList);
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //??????????????????,???????????????????????????????????????????????????????????????????????????????????????????????????
        List<StockLog> stockLogList = stockLogMapper.selectListByBillno(picktaskno,Constants.StockLogState.NORMAL,Constants.StockLogType.LOCKSTOCK);
        int qty = 0;
        for (StockLog stockLog:stockLogList){
            int stockLogQty = stockLog.getQty();
            //??????????????????id??????????????????id????????????????????????????????????????????????
            List<OutorderDetail> outorderDetailList = outorderDetailMapper.selectListByCommodityIdAndOutorderId(stockLog.getCommodityid(),_ids);
            List<PicktaskLockDetail> picktaskLockDetails=new ArrayList<PicktaskLockDetail>();
            for (OutorderDetail outorderDetail:outorderDetailList){
                //????????????????????????????????????picktasklockdetail???????????????
                //???????????????????????????????????????????????????
                if (outorderDetail.getQty()!=qty){
                    int outorderdetailQty = outorderDetail.getQty()-qty;
                    //??????picktask_lock_detail?????????
                    PicktaskLockDetail picktaskLockDetail = new PicktaskLockDetail();
                    picktaskLockDetail.setPid(picktask.getId());
                    picktaskLockDetail.setPicktaskno(picktaskno);
                    picktaskLockDetail.setOutorderid(outorderDetail.getPid());
                    picktaskLockDetail.setOutorderdetailid(outorderDetail.getId());
                    picktaskLockDetail.setCommodityid(stockLog.getCommodityid());
                    picktaskLockDetail.setCommoditybar(stockLog.getCommoditybar());
                    picktaskLockDetail.setCommodityname(stockLog.getCommodityname());
                    picktaskLockDetail.setLocatorid(stockLog.getLocatorid());
                    picktaskLockDetail.setLocatorcode(stockLog.getLocatorcode());
                    picktaskLockDetail.setLocatorname(stockLog.getLocatorname());
                    //?????????????????????????????????????????????????????????????????????
                    if (stockLogQty>=outorderdetailQty){
                        picktaskLockDetail.setQty(outorderdetailQty);
                        qty=qty+outorderdetailQty;
                        picktaskLockDetails.add(picktaskLockDetail);
                        stockLogQty = stockLogQty - outorderdetailQty;
                        if (stockLogQty==0){
                            break;
                        }
                    } else {
                        picktaskLockDetail.setQty(stockLogQty);
                        picktaskLockDetails.add(picktaskLockDetail);
                        qty=0;
                        break;
                    }
                }
            }
            picktaskLockDetailService.saveBatch(picktaskLockDetails);
            picktaskLockDetailService.updateoutorderno();
        }
        return true;
    }
}

