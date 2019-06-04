package redis.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import redis.beans.User;
import redis.controllers.UserController;
import redis.dao.UserDAO;
import redis.redisConfig.RedisConfig;

public class Main {
	public static void main(String[] args) {
	   AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	   context.register(RedisConfig.class);
	   context.refresh();
	   
	   
	   // Testing 
	   UserDAO userDAO = context.getBean(UserDAO.class);
	   UserController userController = new UserController(userDAO);
	   	   
	   System.out.println("Usertest");
	   long user1 = userController.create();
	   long user2 = userController.create();
	   
	   userController.remove(user1);	   
	   System.out.println(userController.find(user1));
	   
	   System.out.println(userController.getCredits(user2));
	   userController.addCredits(user2, 25);	   
	   System.out.println(userController.getCredits(user2));
	   
	   userController.substractCredits(user2, 10);	   
	   System.out.println(userController.getCredits(user2));
	   
	   System.out.println(userController.find(user2));
	   userController.remove(user2);	   
	   System.out.println(userController.find(user2));
  
    }
}
