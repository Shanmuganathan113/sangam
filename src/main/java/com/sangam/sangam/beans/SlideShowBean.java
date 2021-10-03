package com.sangam.sangam.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope("request")
@Data
public class SlideShowBean {
//	
//	@Autowired
//	InternalSlideShowService slideShowService;
//	
//	SlideShowDTO slideShow= null;
	String slideShowDetails = "";
	String id;
	String slideShowName = "";
	String name;
	
	public void loadSlideshow() {
		//SlideShowDTO slideShow =  slideShowService.fetchSlideShow(id);
//		slideShowDetails = slideShow.getSlides().stream().map( e -> e.getSlideContent()).reduce(String::concat).get();
	}
	
	public void loadSlideShowByName() {
//		SlideShowDTO slideShow =  slideShowService.fetchSlideShowByName(name);
//		slideShowName = slideShow.getSlideShowName();
//		System.out.println("Name "+slideShowName);
//		System.out.println("slideshow "+slideShow );
//		if(slideShow.getSlides() != null && slideShow.getSlides().size() > 0 ) 
//		slideShowDetails = slideShow.getSlides().stream()
											//	.map( e -> e.getSlideContent()).reduce(String::concat).get();
	}

}
