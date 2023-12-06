package com.example.cloudlearn;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.cloudlearn.entity.UserEntity;
import com.example.cloudlearn.repository.UserRepository;
import com.example.cloudlearn.util.CSVReaderUtil;

@Component
public class DatabaseInitializer {
	
	private static final Logger logger = LogManager.getLogger(CSVReaderUtil.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public void initializeDatabase() {
        try {
        	List<Map<String, String>> csvData = CSVReaderUtil.readCSV("/opt/user.csv");
        	for (Map<String, String> rowdata : csvData) {
        		String firstname = rowdata.get("firstname");
        		String lastname = rowdata.get("lastname");
                String email = rowdata.get("email");
                String password = rowdata.get("password");

                UserEntity existingUser = userRepository.findByEmail(email);
                if (existingUser == null) {
                	UserEntity newUser = new UserEntity();
                	newUser.setFirstname(firstname);
                	newUser.setLastname(lastname);
                    newUser.setEmail(email);
                    newUser.setPassword(password);
                    userRepository.save(newUser);
                }
            }
        } catch (IOException e) {
        	logger.error("Error occurred: ", e.getMessage());
        }
    }

}
