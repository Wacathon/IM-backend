package com.namecard.config.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerRedirector {

    @RequestMapping("/swagger")
    public RedirectView swagger() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
