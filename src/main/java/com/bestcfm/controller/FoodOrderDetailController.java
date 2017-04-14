package com.bestcfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bestcfm.bean.FoodOrderDetail;
import com.bestcfm.bean.User;
import com.bestcfm.service.FoodOrderDetailService;

/**
 * Author:陈方明
 * Email:cfmmail@sina.com
 * Created on 2017 	下午3:24:46
 */

@Controller
@SessionAttributes("loginUser")
@RequestMapping("/foodOrderDetail")
public class FoodOrderDetailController {
	 @Autowired
	 private FoodOrderDetailService  foodOrderDetailService;
	 
	 @RequestMapping("/shoppingCar")
	 public String ShoppingCar(@RequestParam("userId") int userId,ModelMap map){
		List<FoodOrderDetail> FoodOrderDetailList = foodOrderDetailService.queryFoodOrderDetailListByUserId(userId);
		int sum = 0;
		for(FoodOrderDetail e:FoodOrderDetailList){
			sum += e.getTotal();
		}
		if(sum > 0){
			map.put("money", sum+" ￥");
		}
		map.put("myShoppingCarFoodList",FoodOrderDetailList);
		return "shoppingCar";	 
	 }
	 
	 @RequestMapping("/doAddShoppingCar")
	 @ResponseBody
	 public String doAddShoppingCar(@RequestParam("foodId") int foodId,@RequestParam("userId") int userId){
		 boolean result = foodOrderDetailService.doAddShoppingCar(foodId, userId);
		 String data = result == true?"添加成功":"添加失败";
		 return data;
	 }
	 
	 @RequestMapping("/operateCar")
	 @ResponseBody
	 public String operateCar(@RequestParam("operateId") int operateId,@RequestParam("userId") int userId,@RequestParam("operate") int operate){
		 boolean result = foodOrderDetailService.operateCar(operateId, userId, operate);
		 String data = result == true?"操作成功":"操作失败";
		 return data;
	 }
	 
	 @RequestMapping("/doPay")
	 @ResponseBody
	 public String doPay(ModelMap map,@RequestParam("deskNum") int deskNum){
		 User loginUser = (User) map.get("loginUser");
		 return foodOrderDetailService.doPay(loginUser.getId(), deskNum) == true?"支付成功":"支付失败";
	 }
}
