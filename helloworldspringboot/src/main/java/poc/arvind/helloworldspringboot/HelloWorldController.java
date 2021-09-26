package poc.arvind.helloworldspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "message", required = false) String message ){
        if(message != null && message.trim().length() != 0){
            return "Hello " + message + "!";
        }
        return "Hello World!";
    }

}
