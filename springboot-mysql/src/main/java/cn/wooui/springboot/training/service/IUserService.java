package cn.wooui.springboot.training.service;

import cn.wooui.base.service.IBaseService;
import cn.wooui.springboot.training.model.User;

public interface IUserService extends IBaseService<User, Integer>{
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUserName(String username);
}
