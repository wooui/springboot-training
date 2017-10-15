package cn.wooui.springboot.training.mybatisplus.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.wooui.springboot.training.mybatisplus.model.User;
import cn.wooui.springboot.training.mybatisplus.service.IUserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMyabatisApplicationTest {
	@Autowired
	private IUserService service;
	
	@Test
	public void insert() {
		User user = new User();
		user.setUsername("jasonyifei");
		user.setPassword("222222");
		user.setNickName("chenjs");
		user.setSex(1);
		user.setRegisterDate(new Date());
		service.insert(user);
	}
	
	@Test
	public void getByPK() {
		
		User user = service.selectById(11);
		System.out.println(user.toString());
	}
}
