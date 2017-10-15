package cn.wooui.springboot.training.mybatisplus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wooui.springboot.training.mybatisplus.dao.UserMapper;
import cn.wooui.springboot.training.mybatisplus.model.User;
import cn.wooui.springboot.training.mybatisplus.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService{

}
