package springbootrestserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootRestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestServerApplication.class, args);
	}
	
    @GetMapping("/")
    public User index(final Model model) {
    	
    	User user = new User();
    	user.setName("World");
    	user.setTitle("Spring Boot");
    	return user;
    }
    
    private static class User {
    	private String name;
    	private String title;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
    	
    }
}
