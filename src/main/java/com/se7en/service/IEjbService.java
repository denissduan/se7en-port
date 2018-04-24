package com.se7en.service;

import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 2017/6/28.
 */
public interface IEjbService<T> {

    List<T> queryHql(String hql, Object[] params);

    T get(Serializable id);

    boolean save(T bean);

    boolean update(T bean);

    boolean saveOrUpdate(T bean);

    boolean delete(T bean);

    boolean batchSave(Collection<T> collection);

    boolean batchUpdate(Collection<T> collection);

    boolean batchDelete(Collection<T> collection);

    boolean batchSaveOrUpdate(Collection<T> collection);

    PageView modelQuery(HttpServletRequest request, QueryView view);

}
