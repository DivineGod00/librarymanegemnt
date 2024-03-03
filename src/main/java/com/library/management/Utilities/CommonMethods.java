package com.library.management.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.dao.LoginRepo;
import com.library.management.sql.model.LoginMaster;

@Service
public class CommonMethods {

	private Logger logger = LoggerFactory.getLogger(CommonMethods.class);

	@Autowired
	private LoginRepo loginRepo;
	
	
	public boolean isEmptyOrNull(String... string) {
		for (String s : string) {
			if ((null == s) || (s.isEmpty()))
				return true;
		}
		return false;
	}

	public boolean isEqualsOrNot(String name, String password) {
		LoginMaster credentials = loginRepo.findByUsernameAndPassword(name, password);
		if (credentials != null && name.equals(credentials.getUsername())
				&& password.equals(credentials.getPassword())) {
			logger.info("--------- Matched Credentials --------");
			return true;
		}

		logger.info("---------- Not Matched Credentials ---------");
		return false;
	}
	
	
//	public boolean checkingCredentials(String name, String password) throws Exception {
//		
//		boolean nm = isEqualsOrNot(name,password);
//		
//		if (( && methods.isEmptyOrNull(password)) || methods.isEmptyOrNull(name) || methods.isEmptyOrNull(password)) {
//			logger.info("Null is name : " + name + " & Password : " + password);
//			return false;
//		}
//		boolean isValidNamePassword = methods.isEqualsOrNot(name, password);
//		logger.info("Is Valid User :: " + isValidNamePassword);
//		return isValidNamePassword;
//	}
	
	
}
