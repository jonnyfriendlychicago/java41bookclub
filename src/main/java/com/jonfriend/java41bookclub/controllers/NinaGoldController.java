//find/replace instances of 'java22displaydate' with trueName of project
package com.jonfriend.java41bookclub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;



import javax.servlet.http.HttpSession;


//import Item.java; 

import java.util.Random;  

@Controller

public class NinaGoldController {
	

	@RequestMapping(value = "/processLogin", method=RequestMethod.POST)
	public String processLogin(
		@RequestParam(value="elNumero") String elNumero,
	    @RequestParam(value="city") String city,
	    @RequestParam(value="person") String person,
	    @RequestParam(value="profHobb") String profHobb,
	    @RequestParam(value="animal") String animal,
	    @RequestParam(value="nicety") String nicety,
		HttpSession session
		, RedirectAttributes redirectAttributes) 
		{
	    	// CODE TO PROCESS FORM ie. check email and password
//			String emailSubmitted = email;
			
			session.setAttribute("elNumero", elNumero) ;
			session.setAttribute("city", city) ;
			session.setAttribute("person", person) ;
			session.setAttribute("profHobb", profHobb) ;
			session.setAttribute("animal", animal) ;
			session.setAttribute("nicety", nicety) ;
			
			redirectAttributes.addFlashAttribute("displayMessage", "Thanks for the good submission!");
	    	return "redirect:/dashboard"; // <-- we'll change this when we learn redirecting
	}
	
	@RequestMapping("/dashboard") 
	public String displayDashboard(Model model, HttpSession session) {
		// get any info needed out of session and add to the view model to render on the page.
        
//    	String elNumero = (String) session.getAttribute("elNumero");
//		model.addAttribute("elNumero", elNumero);
//		
//		String city = (String) session.getAttribute("city");
//		model.addAttribute("city", city);
//		
//		String person = (String) session.getAttribute("person");
//		model.addAttribute("person", person);
		return "dashboard.jsp"; 
	}
	
	@RequestMapping("/processGeld")
	public String processGeld(
			HttpSession session
			, @RequestParam(value="geldLocation") String geldLocation
	) 
	{
//		SimpleDateFormat format = new SimpleDateFormat("MMMM d Y h:mm a");
//		ArrayList<String> actions = new ArrayList<>();
		
		Integer currentCountGeld = (Integer) session.getAttribute("currentCountGeld");

		Random random = new Random();
		Integer min = null;
		Integer max = null;
		Integer geldChange = null; 

//		System.out.println("geldLocation: " + geldLocation); 
		if (geldLocation.equals("farm") ){
			min = 10;
			max = 21; 
		} else if (geldLocation.equals("cave")) {
			min = 5;
			max = 11; 
		} else if (geldLocation.equals("house")) {
			min = 2;
			max = 6; 
		} else if (geldLocation.equals("quest")) {
			min = -50;
			max = 51; 
		} else {
			min = -20;
			max = -4; 
		}
		geldChange = random.nextInt(max - min) + min;
		System.out.println("geldLocation: " + geldLocation + " geldChange: " + geldChange);		 
		currentCountGeld += geldChange;	
		
		session.setAttribute("currentCountGeld", currentCountGeld);
		return "redirect:/"; 
	}
	
	@RequestMapping("/resetGeld")
	public String resetGeld(HttpSession session
//			, Model model
//			, HttpServletRequest request
	) {
		
		session.setAttribute("currentCountGeld", 0);

		return "redirect:/";
	}
	
	
	
	
	
	
// end of methods
}
