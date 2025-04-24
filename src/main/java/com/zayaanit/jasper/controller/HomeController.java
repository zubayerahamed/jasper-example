package com.zayaanit.jasper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String index(Model model) {
		return "index";
	}
}
