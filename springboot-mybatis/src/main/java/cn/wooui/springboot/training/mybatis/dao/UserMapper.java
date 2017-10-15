package cn.wooui.springboot.training.mybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import cn.wooui.springboot.training.mybatis.model.User;

@Mapper
public interface UserMapper{
	@Insert(value = "insert into user (username, password, nick_name, sex, register_date) values (#{username,jdbcType=VARCHAR}, #{password, jdbcType=VARCHAR}, #{nickName, jdbcType=VARCHAR}, #{sex, jdbcType=INTEGER}, #{registerDate,jdbcType=TIMESTAMP})")
	int insert(User record);
	
	@Select(value = "select id, username, password, nick_name, sex, register_date from user where id = #{id,jdbcType=INTEGER}")
	@Results(value = { @Result(column = "nick_name", property = "nickName", jdbcType = JdbcType.VARCHAR), @Result(column = "register_date", property = "registerDate", jdbcType = JdbcType.TIMESTAMP) })
	User selectByPrimaryKey(Integer id);
}
