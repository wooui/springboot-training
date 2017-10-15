package cn.wooui.springboot.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.waka.framework.util.WakaUtils;
import cn.wooui.base.common.WakaMapper;
import cn.wooui.base.service.impl.BaseServiceImpl;
import cn.wooui.springboot.training.model.User;
import cn.wooui.springboot.training.service.IUserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService{
	
	@Autowired
	private WakaMapper<User> userMapper;

	@Override
	public User findByUserName(String username) {
		if(WakaUtils.string.isEmpty(username)) {
			//throw new Exception();
			return null;
		}
		User user = new User();
		user.setUsername(username);
		return getMapper().selectOne(user);
	}

	@Override
	public WakaMapper<User> getMapper() {
		return userMapper;
	}

}
