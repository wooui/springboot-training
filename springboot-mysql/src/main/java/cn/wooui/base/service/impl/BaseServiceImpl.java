package cn.wooui.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;

import cn.waka.framework.util.WakaUtils;
import cn.wooui.base.common.Base;
import cn.wooui.base.common.WakaMapper;
import cn.wooui.base.model.BaseEntity;
import cn.wooui.base.service.IBaseService;

public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> extends Base implements IBaseService<T, ID> {
	public abstract WakaMapper<T> getMapper();

    @Override
    public T find(ID id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public List<T> findAll() {
        return getMapper().select(null);
    }

    @Override
    public List<T> findList(String ids) {
        return getMapper().selectByIds(ids);
    }


    @Override
    public List<T> findList(ID[] ids) {
        String idsString = WakaUtils.array.join(",", ids);
        return getMapper().selectByIds(idsString);
    }

    @Override
    public List<T> findList(Iterable<ID> ids) {
        String idsString = WakaUtils.array.join(",", ids);
        return getMapper().selectByIds(idsString);
    }

    @Override
    public Integer count() {
        return getMapper().selectCountByExample(null);//FIXME
    }

    @Override
    public Boolean exists(ID id) {
        T entity = getMapper().selectByPrimaryKey(id);
        return entity != null;
    }

    @Override
    public void save(T entity) {
        getMapper().insert(entity);
    }

    @Override
    public Integer update(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    @Override
    public void delete(ID id) {
        getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(@SuppressWarnings("unchecked") ID... ids) {
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                ID id = ids[i];
                this.delete(id);
            }
        }
    }

    @Override
    public void delete(T[] entitys) {
        //TODO		getMapper().de
    }

    @Override
    public void delete(Iterable<T> entitys) {
        //TODO getMapper().del
    }


    @Override
    public void deleteByCondition(Object condition) {
        getMapper().deleteByExample(condition);
    }

    @Override
    public void delete(T entity) {
        getMapper().delete(entity);

    }

    @Override
    public PageInfo<T> findAll(Object example) {
        return new PageInfo<T>(getMapper().selectByExample(example));
    }

    @Override
    public PageInfo<T> findAll(Object example, RowBounds rowBounds) {
        return new PageInfo<T>(getMapper().selectByExampleAndRowBounds(example, rowBounds));
    }

    @Override
    public Integer count(Object example) {
        return getMapper().selectCountByExample(example);
    }

    @Override
    public List<T> findList(Object example) {
        return getMapper().selectByExample(example);
    }
}
