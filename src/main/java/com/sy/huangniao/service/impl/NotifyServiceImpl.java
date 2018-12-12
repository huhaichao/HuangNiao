package com.sy.huangniao.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huchao on 2018/11/18.
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    HNContext hnContext;

    @Override
    public int save(Notify notify) {
        IDaoService<Notify> iDaoService= hnContext.getDaoService(Notify.class.getSimpleName());
        if(iDaoService.save(notify, SqlTypeEnum.DEAFULT)!=1){
            log.info("保存通知信息失败 notify={}",notify);
            throw new HNException(RespondMessageEnum.SAVENOTIFYINFOERROR);
        }
        return 1;
    }

    @Override
    public List<Notify> selectList(JSONObject jsonObject) {
        Notify notify = jsonObject.toJavaObject(Notify.class);
        log.info("notify={} 查询通知消息....",notify);
        int pageNum  = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        page.setOrderBy(" create_date  ");
        IDaoService<Notify> iDaoService= hnContext.getDaoService(Notify.class.getSimpleName());
        return iDaoService.selectList(notify,SqlTypeEnum.DEAFULT);
    }

    @Override
    public int update(Notify notifyUpdate) {
        IDaoService<Notify> iDaoService= hnContext.getDaoService(Notify.class.getSimpleName());
        if (iDaoService.updateObject(notifyUpdate,SqlTypeEnum.DEAFULT)!=1){
            log.info("修改通知信息失败 notify={}",notifyUpdate);
            throw new HNException(RespondMessageEnum.UPDATENOTIFYINFOERROR);
        }
        return 1;
    }

    @Override
    public Notify selectObject(Notify queryNotify) {
        return null;
    }
}
