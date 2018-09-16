package com.sy.huangniao.service;

import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.pojo.RobOrder;

import java.util.List;

/**
 * Created by huchao on 2018/9/12.
 */
public interface IDaoService<T> {

    /**
     * 需要操作的表名
     * @return
     */
    public String  tableName();

    /**
     * 保存对象
     * @param t
     * @return
     */
    public int save(T t,SqlTypeEnum sqlTypeEnum);

    /**
     * 删除对象
     * @param t
     * @return
     */
    public int deleteByObject(T t,SqlTypeEnum sqlType);

    /**
     * 修改对象
     * @param t
     * @return
     */
    public int updateObject(T t,SqlTypeEnum sqlType);

    /**
     * 查询集合
     * @param t
     * @return
     */
    public List<T> selectList(T t,SqlTypeEnum sqlType);


    /**
     * 查询对象
     * @param t
     * @return
     */
    public T selectObject(T t,SqlTypeEnum sqlType);
}
