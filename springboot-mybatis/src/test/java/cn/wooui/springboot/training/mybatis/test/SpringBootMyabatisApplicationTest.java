package cn.wooui.springboot.training.mybatis.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.wooui.springboot.training.mybatis.dao.UserMapper;
import cn.wooui.springboot.training.mybatis.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMyabatisApplicationTest {
	@Autowired
	private UserMapper mapper;
	
	/*@Test
	public void insert() {
		User user = new User();
		user.setUsername("jason");
		user.setPassword("111111");
		user.setNickName("chenjs");
		user.setSex(1);
		user.setRegisterDate(new Date());
		mapper.insert(user);
	}
	*/
	@Test
	public void getByPK() {
		User user = mapper.selectByPrimaryKey(11);
		System.out.println(user.toString());
	}
}
