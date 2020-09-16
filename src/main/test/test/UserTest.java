package test;

import com.skh.Application;
import com.skh.po.User;
import com.skh.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class UserTest {
    @Resource
    private UserService userService;

    @Test
    public void selectTest(){
        User user=userService.getUser (1L);
        System.out.println (user);
    }

    @Test
    public void insetTest(){

        User user= new User ();
        user.setId (10086L);
        user.setName ("名字");
        user.setAge (18);
        userService.saveUser (user);
        System.out.println (user);
    }

}
