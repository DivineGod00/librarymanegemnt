package com.library.management.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.library.management.Utilities.CommonMethods;
import com.library.management.dao.BookAddRepo;
import com.library.management.dao.BookCategoryRepo;
import com.library.management.dao.LoginRepo;
import com.library.management.dao.UserRepo;
import com.library.management.dto.BookDto;
import com.library.management.dto.UserRegisterDto;
import com.library.management.interfaces.AdminProcess;
import com.library.management.sql.model.BookCategory;
import com.library.management.sql.model.BooksMaster;
import com.library.management.sql.model.LoginMaster;
import com.library.management.sql.model.UserMaster;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/library/admin")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminProcess adminProcess;
	
	@Autowired
	private CommonMethods methods;
	
	@Autowired
	private LoginRepo loginRepo;

	@Autowired
	private BookAddRepo bookAddRepo;

	@Autowired
	private BookCategoryRepo bookCategoryRepo;

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/login")
	public String login(HttpServletRequest httpServletRequest)throws Exception
	{
		return adminProcess.loginProcess(httpServletRequest);
	}
	
	@GetMapping("/logout")
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status) throws Exception
	{
		return adminProcess.logOut(request, response, status);
	}

	@PostMapping("/login")
	public String home(@RequestParam String name, @RequestParam String password,ModelMap model,HttpServletRequest httpServletRequest)throws Exception 
	{	
		return adminProcess.afterLogin(name, password, model, null, httpServletRequest);
	}
	
	@GetMapping("/book")
	public String admin(ModelMap model, HttpServletRequest httpServletRequest)throws Exception 
	{
		return adminProcess.book(model, httpServletRequest);
	}

	@PostMapping("/book")
	public String addBook(@ModelAttribute BookDto bd, @RequestParam("imageData") MultipartFile file,HttpServletRequest httpServletRequest)
			throws IOException, Exception 
	{
		return adminProcess.addBook(bd, file, httpServletRequest);
	}

	@GetMapping(value = "/Member")
	public String addUser(ModelMap model,HttpServletRequest httpServletRequest)throws Exception 
	{
		
		return adminProcess.user(model, httpServletRequest);
	}

	@PostMapping("/Member")
	public String addUser(@ModelAttribute UserRegisterDto bd, @RequestParam("imageData") MultipartFile file ,HttpServletRequest httpServletRequest) 
			throws IOException,Exception {
		return adminProcess.addUser(bd, file, httpServletRequest);
	}
	
	
//	@PostMapping("/register")
//	public String addBook(@ModelAttribute UserRegisterDto bd, @RequestParam("imageData") MultipartFile file,
//			ModelMap model,HttpServletRequest httpServletRequest)throws IOException,Exception {
//
//		LoginMaster addUser = new LoginMaster();
//		addUser.setUsername(bd.getRollno());
//		addUser.setPassword(bd.getPassword());
//		UserMaster bc = userRepo.findById(bd.getId()).orElse(null);
//		if (bc != null) {
//
//			addUser.setUserId(bc);
//			logger.info("UserMaster: " + addUser);
//			logger.info("UserMaster: " + addUser);
//			loginRepo.save(addUser);
//
//			return "redirect:Register";
//		} else {
//			return "redirect:Register";
//		}
//
//	}
	

}
