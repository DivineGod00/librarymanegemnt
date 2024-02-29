package com.library.management.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.SessionStatus;

import com.library.management.Utilities.CommonMethods;
import com.library.management.dao.BookAddRepo;
import com.library.management.dao.BookIssueReturnRepo;
import com.library.management.dao.LoginRepo;
import com.library.management.dao.UserRepo;
import com.library.management.dto.UserRegisterDto;
import com.library.management.sql.model.BookIssueReturn;
import com.library.management.sql.model.BooksMaster;
import com.library.management.sql.model.LoginMaster;
import com.library.management.sql.model.UserMaster;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

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
	private BookIssueReturnRepo bookIssueReturnRepo;

	
	
	
	public String loginPage()throws Exception
	{
		return "UserLogin";
	}
	
	
	public String afterLogin(String name, String password, ModelMap model) throws Exception {

		LoginMaster dm = loginRepo.findByUsernameAndPassword(name, password);
		UserMaster user = userRepo.findById(dm.getUserId().getId()).orElse(null);
		if(dm.getUserId().getRole().equalsIgnoreCase("STUDENT"))
		{
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
				model.put("name", name);
				model.addAttribute("LoggedInUser", user);
				return "User";
			}
		}
		return "UserLogin";

	}

	public String issueBook(Long bookId, ModelMap model, HttpServletRequest httpServletRequest)
	{

		BooksMaster dm = bookAddRepo.findById(bookId).orElse(null);
		LocalDate localDate = LocalDate.now();
		UserMaster lm = (UserMaster) httpServletRequest.getSession().getAttribute("LoggedInUser");
		List<BookIssueReturn> issuedBooks = bookIssueReturnRepo.findByUserId(lm.getId());
		BookIssueReturn bookPresent = bookIssueReturnRepo.findByIssuedBookIdAndUserId(dm, lm.getId());
		if (bookPresent != null) {
	        logger.info("Book Already Issued");
	      
	    } else {
	        BookIssueReturn issueBook = new BookIssueReturn();
	        issueBook.setIssuedBookId(dm);
	        issueBook.setIssued_date(LocalDate.now());
	        issueBook.setReturnDate(localDate.plusDays(3));
	        issueBook.setUserId(lm.getId());

	        bookIssueReturnRepo.save(issueBook);
	        logger.info("Book Issued Successfully to user "+lm.getName());
	    }
		return "redirect:/library/bookview";
	}
	
	public String bookView(ModelMap model, HttpServletRequest httpServletRequest)throws Exception
	{
		UserMaster lm = (UserMaster) httpServletRequest.getSession().getAttribute("LoggedInUser");
		Long userId = (Long) httpServletRequest.getSession().getAttribute("LoggedInUserId");
		logger.info("Issued/Return Book  User "+lm.getName());
		if (userId != null) {
			List<BookIssueReturn> returnBook = bookIssueReturnRepo.findByUserId(userId);
			model.addAttribute("Book", returnBook);
			model.addAttribute("LoggedInUser", lm);
		} 
		else {
			return "redirect:/library/userLogIn";
		}

		return "Issued";
	}
	
//	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status)throws Exception
//	{
//
//		HttpSession session = request.getSession(false);
//		if (session != null || session.getAttribute("loggedInUser") == null) {
//			session.invalidate();
//		}
//		status.setComplete();
//		logger.info("User Log Out ");
//		return "redirect:userLogIn";
//	}
	
	
	public String logOut(HttpServletRequest request, HttpServletResponse response, SessionStatus status) throws Exception {
	    // Check if the user is logged in by checking for the presence of a specific cookie
		  Cookie[] cookies = request.getCookies();
		    boolean isLoggedIn = false;
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		        	logger.info("=====>   "+cookie.getName().equals("JSESSIONID"));
		            if (cookie.getName().equals("JSESSIONID")) {
		                isLoggedIn = true;
		                cookie.setMaxAge(0); // Set expiration time to 0 to delete the cookie
	                    response.addCookie(cookie);
		                logger.info("=====>   "+cookie.getName().equals("JSESSIONID")+ " ====> "+isLoggedIn);
		                break;
		            }
		        }
		    }
		    
		    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "0");

		    if (isLoggedIn) {
		        // If the user is logged in, redirect to the logout screen
		        return "redirect:userLogIn";
		    } else {
		        // If the user is not logged in, redirect to the login screen
		        return "redirect:userLogIn";
		    }
		
	}
	    
	
	
	public String returnBook(Long bookId, ModelMap model, HttpServletRequest httpServletRequest)throws Exception 
	{
		BooksMaster dm = bookAddRepo.findById(bookId).orElse(null);
		UserMaster lm = (UserMaster) httpServletRequest.getSession().getAttribute("LoggedInUser");
		BookIssueReturn issuedBooks = bookIssueReturnRepo.findByIssuedBookIdAndUserId(dm, lm.getId());

		logger.info("Book Return details:   " + issuedBooks);
		bookIssueReturnRepo.delete(issuedBooks);

		return "redirect:/library/bookview";
	}
	
	public String register(ModelMap model, HttpServletRequest httpServletRequest)throws Exception {

//		return applicationProcess.register(model, httpServletRequest);
		List<UserMaster> categories = userRepo.findAll();
		model.addAttribute("categories", categories);
		return "Register";
	}
	
	public String registerUser(UserRegisterDto bd,
			ModelMap model,HttpServletRequest httpServletRequest)throws IOException,Exception {
		
		LoginMaster addUser = new LoginMaster();
		addUser.setUsername(bd.getRollno());
		addUser.setPassword(bd.getPassword());
		UserMaster bc = userRepo.findById(bd.getId()).orElse(null);
		if (bc != null) {

			addUser.setUserId(bc);
			logger.info("UserMaster: " + addUser);
			logger.info("UserMaster: " + addUser);
			loginRepo.save(addUser);

			return "redirect:/library/register";
		} 
		else {
			return "redirect:/library/register";
		}
	}

	public String searchBook(String searchQuery, ModelMap model, HttpServletRequest httpServletRequest) throws Exception
	{
		 UserMaster lm = (UserMaster) httpServletRequest.getSession().getAttribute("LoggedInUser");
		    logger.info("Search :: " + searchQuery);
		    
		    // Retrieve a list of books based on the search query
		    List<BooksMaster> books = bookAddRepo.findByBookName(searchQuery);
		    
		    logger.info("Books data :: " + books);
		    model.addAttribute("LoggedInUser", lm);
		    
		    // Check if any books are found
		    if (books != null && !books.isEmpty()) {
		        for (BooksMaster dm : books) {
		            byte[] imageData = dm.getImageData();
		            if (imageData != null) {
		                String base64Image = Base64Utils.encodeToString(imageData);
		                dm.setBase64ImageData(base64Image);
		            }
		        }
		    }
		       
//		    } else {
//		        // No books found for the search query
//		        model.addAttribute("SearchBook", Collections.emptyList());
//		    }
		    model.addAttribute("SearchBook", books);
		    return "User";
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
