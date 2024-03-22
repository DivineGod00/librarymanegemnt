package com.library.management.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.library.management.dto.UserRegisterDto;
import com.library.management.interfaces.ApplicationProcess;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/library")
@SessionAttributes("name")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	
	@Autowired
	private ApplicationProcess applicationProcess;

	
	@GetMapping("/")
	public String logIn(HttpServletRequest httpServletRequest)throws Exception{
		return applicationProcess.loginProcess(httpServletRequest);
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status) throws Exception{
		return applicationProcess.logOut(request, response, status);
	}

	@PostMapping(value = "/")
	public String verifyLogin(@RequestParam String name, @RequestParam String password, ModelMap model,
			MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
		return applicationProcess.afterLogin(name, password, model, file, httpServletRequest);
	}

	@PostMapping("/userLogIn/issue/{bookId}")
	public String issueBook(@PathVariable Long bookId, ModelMap model, HttpServletRequest httpServletRequest) throws Exception{
		
		return applicationProcess.issueBook(bookId, model, httpServletRequest);
	}

	@GetMapping(value = "/bookview")
	public String bookView(ModelMap model, HttpServletRequest httpServletRequest)throws Exception {
	
		return applicationProcess.bookView(model, httpServletRequest) ;
	}

	@PostMapping("/bookview/return/{bookId}")
	public String returnBook(@PathVariable Long bookId, ModelMap model, HttpServletRequest httpServletRequest)throws Exception {
		return applicationProcess.returnBook(bookId, model, httpServletRequest);
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(name = "search", required=false) String searchQuery, ModelMap model, HttpServletRequest httpServletRequest) throws Exception
	{
		return applicationProcess.searchBook(searchQuery, model, httpServletRequest);
	}

	@GetMapping("/register")
	public String register(ModelMap model, HttpServletRequest httpServletRequest)throws Exception {

		return applicationProcess.register(model, httpServletRequest);

	}
	
	@PostMapping("/register")
	public String addBook(@ModelAttribute UserRegisterDto bd,
			ModelMap model,HttpServletRequest httpServletRequest)throws IOException,Exception 
	{
		return applicationProcess.registerUser(bd, model, httpServletRequest);
	}
	
	 

}
