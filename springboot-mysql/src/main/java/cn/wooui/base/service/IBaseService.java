package cn.wooui.base.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;

public interface IBaseService<T, ID extends Serializable> {
	public abstract T find(ID id);

    public abstract List<T> findAll();

    public abstract List<T> findList(String ids);

    public abstract List<T> findList(ID[] ids);

    public abstract List<T> findList(Iterable<ID> ids);

    public abstract PageInfo<T> findAll(Object example);

    public abstract PageInfo<T> findAll(Object example, RowBounds rowBounds);

    public abstract Integer count();

    public abstract Integer count(Object example);

    public abstract Boolean exists(ID id);

    public abstract void save(T entity);

    public abstract Integer update(T entity);

    public abstract void delete(ID id);

    public abstract void deleteByIds(@SuppressWarnings("unchecked") ID... ids);

    public abstract void delete(T[] entitys);

    public void delete(Iterable<T> entitys);

    public abstract void delete(T entity);

    public abstract void deleteByCondition(Object condition);

    public List<T> findList(Object example);

}
