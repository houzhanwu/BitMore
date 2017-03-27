package com.bestcfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bestcfm.bean.Emp;
import com.bestcfm.service.EmpService;

@Controller
@RequestMapping("/emp")
@SessionAttributes("loginEmp")
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam("phone")String phone,@RequestParam("empPassword")String empPassword,ModelMap map) {
		System.out.println(phone+empPassword);
		if(phone == null || empPassword == null){
			map.put("error", "无效的请求参数！");
			return "empLogin";
		}
		Emp loginEmp = empService.queryEmpByPhoneAndPassword(phone, empPassword);
		if(loginEmp == null){
			map.put("error", "用户名或密码错误");
			return "empLogin";
		}
		else {
			map.put("loginEmp", loginEmp);
			int empRoot = loginEmp.getEmpRoot();
			if(empRoot == 0){
				return "cookCenter";
			}
			else if(empRoot == 1){
				return "waiterCenter";
			}
			else if(empRoot == 2){
				return "adminCenter";
			}
			else{
				map.put("error", "无效的权限标识");
				return "empLogin";
			}
		}	
	} 

}
