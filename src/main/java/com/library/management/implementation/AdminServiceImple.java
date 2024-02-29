package com.library.management.implementation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.library.management.dao.LoginRepo;
import com.library.management.dao.UserRepo;
import com.library.management.dto.BookDto;
import com.library.management.dto.UserRegisterDto;
import com.library.management.interfaces.AdminProcess;
import com.library.management.service.AdminService;
import com.library.management.sql.model.LoginMaster;
import com.library.management.sql.model.UserMaster;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AdminServiceImple implements AdminProcess{

	private final Logger logger = LoggerFactory.getLogger(AdminServiceImple.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private LoginRepo loginRepo;

	
	@Override
	public String loginProcess(HttpServletRequest httpServletRequest)throws Exception
	{
		logger.info("-------------Admin Login Page---------");
		String page = adminService.loginPage();
		return page;
	}
	
	@Override
	public String afterLogin(String name, String password, ModelMap model,
			MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
		logger.info("------------- Successfully Logged In ---------");
		LoginMaster lm = loginRepo.findByUsernameAndPassword(name, password);
		String page = adminService.afterLogin(name, password, model);
		UserMaster user = userRepo.findById(lm.getUserId().getId()).orElse(null);
//		httpServletRequest.getSession().setAttribute("LoggedInUserId", user.getId());
		httpServletRequest.getSession().setAttribute("LoggedInUser", user);
		
		return page;
	
	}
	
	@Override
	public String book(ModelMap model, HttpServletRequest httpServletRequest)throws Exception
	{
		logger.info("------------- Book Bank ---------");
		String page = adminService.book(model, httpServletRequest);
		
		return page;
	}
	
	@Override
	public String addBook(@ModelAttribute BookDto bd, @RequestParam("imageData") MultipartFile file,HttpServletRequest httpServletRequest)
			throws IOException, Exception
	{
		logger.info("------------- Add Book ---------");
		String page = adminService.addBook(bd, file, httpServletRequest);
		return page;
	}
	@Override
	public String user(ModelMap model,HttpServletRequest httpServletRequest)throws Exception 
	{
		logger.info("------------- User Lists ---------");
		String page = adminService.user(model, httpServletRequest);
		return page;
	}
	
	public String addUser(UserRegisterDto bd, MultipartFile file ,HttpServletRequest httpServletRequest) 
			throws IOException,Exception 
	{
		logger.info("------------- User Lists ---------");
		String page = adminService.addUser(bd, file, httpServletRequest);
		return page;
		}
	
	
	@Override
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status)throws Exception
	{

		String page = adminService.logOut(request, response, status);
		return page;
		
	}
}
