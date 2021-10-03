package com.sangam.sangam.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@RequestMapping
    public String taskDetails(@RequestParam Map<String,String> listParams) {
        return "task";
    }
}
