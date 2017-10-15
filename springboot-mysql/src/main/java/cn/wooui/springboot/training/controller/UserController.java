package cn.wooui.springboot.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wooui.base.common.JsonResult;
import cn.wooui.base.controller.BaseController;
import cn.wooui.springboot.training.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = LIST)
	public JsonResult list() {
		return JsonResult.success(userService.findAll());
	}
	
	@RequestMapping("/find/{username}")
	public JsonResult findByUsername(@PathVariable String username) {
		return JsonResult.success(userService.findByUserName(username));
	}
}
