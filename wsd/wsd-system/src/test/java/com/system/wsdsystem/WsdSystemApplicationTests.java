package com.system.wsdsystem;

import com.system.dao.model.User;
import com.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsdSystemApplicationTests {

	@Test
	public void contextLoads() {

//		List<User> list = userService.selectAll();
//		for (User user:list){
//			System.out.println(user.toString());
//		}
	}

}
