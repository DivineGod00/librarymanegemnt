package com.library.management.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.library.management.Utilities.CommonMethods;
import com.library.management.dao.BookAddRepo;
import com.library.management.dao.BookCategoryRepo;
import com.library.management.dao.LoginRepo;
import com.library.management.dao.UserRepo;
import com.library.management.dto.BookDto;
import com.library.management.dto.UserRegisterDto;
import com.library.management.sql.model.BookCategory;
import com.library.management.sql.model.BooksMaster;
import com.library.management.sql.model.LoginMaster;
import com.library.management.sql.model.UserMaster;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Service
public class AdminService {

	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private CommonMethods methods;

	@Autowired
	private LoginRepo loginRepo;
	
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BookAddRepo bookAddRepo;

	@Autowired
	private BookCategoryRepo bookCategoryRepo;
	

	
	
	public String loginPage()throws Exception
	{
		return "Admin";
	}
	
	
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status)throws Exception
	{

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		status.setComplete();
		logger.info("User Log Out ");
		return loginPage();
	}
	
	public String afterLogin(String name, String password, ModelMap model) throws Exception {

		LoginMaster dm = loginRepo.findByUsernameAndPassword(name, password);
		if(dm.getUserId().getRole().equalsIgnoreCase("ADMIN"))
		{
			logger.info("In if Home ::");
			UserMaster user = userRepo.findById(dm.getUserId().getId()).orElse(null);
			if (user != null) {
				byte[] imageData = user.getImageData();
				if (imageData != null) {
					String base64Image = Base64Utils.encodeToString(imageData);
					user.setBase64ImageData(base64Image);
			}
		} 
		else {
			logger.warn("User not found with ID: {}", dm.getUserId().getId());
		}
			boolean isValid = checkingCredentials(name, password);
			if (isValid) {
				model.addAttribute("Login", user);
				
				return "AdminHome";
			}
		}
		return "Admin";

	}
	
	public String book(ModelMap model, HttpServletRequest httpServletRequest)throws Exception {
		
		UserMaster lm = (UserMaster) httpServletRequest.getSession().getAttribute("LoggedInUser");
		model.addAttribute("LoggedInUser", lm);
		logger.info("User :: "+lm);
		List<BooksMaster> book = bookAddRepo.findAll();
		model.addAttribute("Books", book);
		List<BookCategory> categories = bookCategoryRepo.findAll();
		model.addAttribute("categories", categories);	
		return "AddBook";
	}
	
	public String addBook(BookDto bd, MultipartFile file,HttpServletRequest httpServletRequest)
			throws IOException {
		BooksMaster lm = new BooksMaster();
		lm.setBookName(bd.getBookName());
		lm.setBook_author(bd.getBook_author());
		lm.setBook_description(bd.getBook_description());
		bd.setCreatedby(1l);
		bd.setIsActive(true);
		lm.setCreatedBy(bd.getCreatedby());
		lm.setIsActive(bd.getIsActive());

		BookCategory bc = bookCategoryRepo.findById(bd.getBook_category_id()).orElse(null);
		if (bc != null) {

			lm.setBook_category_id(bc);
			logger.info("BooksMaster: " + lm);
			byte[] bytes = file.getBytes();
			lm.setImageData(bytes);
			bookAddRepo.save(lm);

			return "redirect:book";
		} else {

			return "redirect:book";
		}
	}
	
	public String user(ModelMap model,HttpServletRequest httpServletRequest)throws Exception {

		UserMaster lm = (UserMaster) httpServletRequest.getSession().getAttribute("LoggedInUser");
		model.addAttribute("LoggedInUser", lm);
		List<UserMaster> user = userRepo.findAll();
		model.addAttribute("user", user);
		
		return "Member";
	}
	
	public String addUser(UserRegisterDto bd, MultipartFile file ,HttpServletRequest httpServletRequest) 
			throws IOException,Exception {

		UserMaster user = new UserMaster();
		user.setName(bd.getName());
		user.setAbout(bd.getAbout());
		user.setCousre(bd.getCourse());
		user.setEmail(bd.getMail());
		user.setUniversity_college(bd.getUniversity_college());
		user.setRole(bd.getRole());

		byte[] bytes = file.getBytes();
		user.setImageData(bytes);
		logger.info("UsersMaster: " + bd);
		userRepo.save(user);

		return "redirect:Member";

	}


	
	
	
	
	
	
	
	
	
	
	
	public boolean checkingCredentials(String name, String password) throws Exception {
		if ((methods.isEmptyOrNull(name) && methods.isEmptyOrNull(password)) || methods.isEmptyOrNull(name) || methods.isEmptyOrNull(password)) {
			logger.info("Null is name : " + name + " & Password : " + password);
			return false;
		}
		boolean isValidNamePassword = methods.isEqualsOrNot(name, password);
		logger.info("Is Valid User :: " + isValidNamePassword);
		return isValidNamePassword;
	}

	
}
