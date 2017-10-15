package cn.wooui.base.common;

import cn.wooui.base.model.BaseEntity;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface WakaMapper<T extends BaseEntity>
		extends 
		Mapper<T>, 
		MySqlMapper<T>, 
//		BaseMapper<T>, 
//		ConditionMapper<T>, 
//		InsertListMapper<T>,
		IdsMapper<T>  {

}
