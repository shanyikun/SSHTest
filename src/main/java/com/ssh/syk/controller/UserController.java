package com.ssh.syk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssh.syk.paramPOJO.UserInfo;
import com.ssh.syk.service.UserService;

@Controller
public class UserController {
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map register(UserInfo userInfo, RedirectAttributes model,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = userService.register(userInfo.getName(), userInfo.getPassword());
		if(result.get("statCode").toString()=="0") {
			HttpSession session = request.getSession();
			session.setAttribute("userEntity", result.get("userEntity"));
			return result;
		}
		else {
			return result;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map login(UserInfo userInfo, RedirectAttributes model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = userService.login(userInfo.getName(), userInfo.getPassword());
		if(result.get("statCode").toString()=="0") {
			HttpSession session = request.getSession();
			session.setAttribute("userEntity", result.get("userEntity"));
			return result;
		}
		else {
			return result;
		}
	}
	
	@RequestMapping(value= "/userInfo", method = RequestMethod.GET)
	public String getUserInfoPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			model.addAttribute("result", session.getAttribute("userEntity"));
			return "userPage";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Map logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		Object isSuccess = request.getSession(false);
		Map<String, String> result = new HashMap<String, String>();
		if(isSuccess==null) {
			result.put("statCode", "0");
			result.put("message", "logout success!");
		}
		else {
			result.put("statCode", "500");
			result.put("message", "logout fail!");
		}
		return result;
	}
}
