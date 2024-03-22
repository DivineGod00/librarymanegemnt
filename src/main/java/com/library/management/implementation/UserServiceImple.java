package com.library.management.implementation;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.library.management.dao.BookAddRepo;
import com.library.management.dao.BookIssueReturnRepo;
import com.library.management.dao.LoginRepo;
import com.library.management.dao.UserRepo;
import com.library.management.dto.UserRegisterDto;
import com.library.management.encryption.EncryptionAndDecryption;
import com.library.management.interfaces.ApplicationProcess;
import com.library.management.service.UserService;
import com.library.management.sql.model.BookIssueReturn;
import com.library.management.sql.model.BooksMaster;
import com.library.management.sql.model.LoginMaster;
import com.library.management.sql.model.UserMaster;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImple implements ApplicationProcess{

	private final Logger logger = LoggerFactory.getLogger(UserServiceImple.class);
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookAddRepo bookAddRepo;

	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BookIssueReturnRepo bookIssueReturnRepo;
	
	@Autowired
	private EncryptionAndDecryption encryptAndDecrypt;
	
	@Override
	public String loginProcess(HttpServletRequest httpServletRequest)throws Exception
	{
		logger.info("-------- Log In ------");
		String page = userService.loginPage();
		return page;
	}
	
	@Override
	public String afterLogin(@RequestParam String name, @RequestParam String password, ModelMap model,
			MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
		logger.info("username : "+name);
		logger.info("password : "+encryptAndDecrypt.encryptStr(password));
		logger.info("Decrypt : "+encryptAndDecrypt.decryptStr(encryptAndDecrypt.encryptStr(password)));
		String page = userService.afterLogin(name, encryptAndDecrypt.encryptStr(password), model);
		LoginMaster lm = loginRepo.findByUsernameAndPassword(name, encryptAndDecrypt.encryptStr(password));

		UserMaster user = userRepo.findById(lm.getUserId().getId()).orElse(null);
		httpServletRequest.getSession().setAttribute("LoggedInUserId", user.getId());

		List<BookIssueReturn> issuedBooks = bookIssueReturnRepo.findByUserId(user.getId());
		List<BooksMaster> dm = bookAddRepo.findAll();

		for (BooksMaster book : dm) {
			byte[] imageData = book.getImageData();
			if (imageData != null) {
				String base64Image = Base64Utils.encodeToString(imageData);
				byte b[] = base64Image.getBytes();
				book.setBase64ImageData(base64Image);
			}
		}

		httpServletRequest.getSession().setAttribute("LoggedInUser", user);
		model.addAttribute("Book", dm);
		logger.info("-------- Successfully Log In ---------");
		return page;
	}
	
	@Override
	public String issueBook( Long bookId, ModelMap model, HttpServletRequest httpServletRequest)throws Exception
	{
		logger.info("------- Book Issue ------");
		String page = userService.issueBook(bookId, model, httpServletRequest);
		return page;
	}
	
	@Override
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status)throws Exception
	{
		logger.info("-------- Log Out -------");
		String page = userService.logOut(request, response, status);
		return page;
		
	}
	@Override
	public String bookView(ModelMap model, HttpServletRequest httpServletRequest)throws Exception
	{
		logger.info("-------- Book View ---------");
		String page = userService.bookView(model, httpServletRequest);
		return page;
	}
	
	@Override
	public String returnBook(Long bookId, ModelMap model, HttpServletRequest httpServletRequest)throws Exception 
	{
		logger.info("------- Book Return --------");
		String page = userService.returnBook(bookId, model, httpServletRequest);
		return page;
	}
	
	@Override
	public String searchBook(String searchQuery, ModelMap model, HttpServletRequest httpServletRequest) throws Exception
	{
		
		logger.info("------ Search Book --------");
		String page = userService.searchBook(searchQuery, model, httpServletRequest);
		return page;
	}

	@Override
	public String register(ModelMap model, HttpServletRequest httpServletRequest)throws Exception
	{
		logger.info("----------------- New User Registration ---------------");
		String page = userService.register(model, httpServletRequest);
		return page;
	}

	@Override
	public String registerUser(UserRegisterDto bd,
			ModelMap model,HttpServletRequest httpServletRequest)throws IOException,Exception
	{
		logger.info("------- Register User ------");
		String page = userService.registerUser(bd, model, httpServletRequest);
		return page;
	}
}