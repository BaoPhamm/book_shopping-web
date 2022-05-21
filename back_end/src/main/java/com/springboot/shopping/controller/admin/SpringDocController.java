package com.springboot.shopping.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index.html")
public class SpringDocController {

	@GetMapping
	public String MyshopDocs() {
		return "index";
	}
}