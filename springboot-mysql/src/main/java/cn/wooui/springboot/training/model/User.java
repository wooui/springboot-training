package cn.wooui.springboot.training.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.wooui.base.model.BaseEntity;

@Entity
@Table(name="user")
public class User extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	@Column(name="nick_name")
	private String nickName;
	
	private Integer sex;
	
	@Column(name="register_date")
	private Date registerDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
}
