package com.library.management.interfaces;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.library.management.dto.BookDto;
import com.library.management.dto.UserRegisterDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public interface AdminProcess {

	public String loginProcess(HttpServletRequest httpServletRequest) throws Exception ;
	
	public String afterLogin(String name, String password, ModelMap model,
			MultipartFile file, HttpServletRequest httpServletRequest) throws Exception  ;
	
	public String book(ModelMap model, HttpServletRequest httpServletRequest)throws Exception ;

	public String addBook(BookDto bd, MultipartFile file,HttpServletRequest httpServletRequest)
			throws IOException, Exception ;
	public String user(ModelMap model,HttpServletRequest httpServletRequest)throws Exception ;

	public String addUser(UserRegisterDto bd,MultipartFile file ,HttpServletRequest httpServletRequest) 
			throws IOException,Exception ;
	
	
	
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status)throws Exception;
	
}
