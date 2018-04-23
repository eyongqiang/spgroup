package spgroup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SocialHttpController {
	@RequestMapping("/")
    public String greeting( @RequestParam(value="name", required=false, defaultValue="World") String message, Model model) {
        model.addAttribute("message", message);
        return "greeting";
    }
	
	
}
