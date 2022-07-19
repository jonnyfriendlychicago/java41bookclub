//find/replace instances of 'java22displaydate' with trueName of project
package com.jonfriend.java41bookclub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

//import org.springframework.web.bind.annotation.PathVariable;
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.time.LocalDateTime; 
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.text.SimpleDateFormat; 
import java.util.ArrayList; 

//import Item.java; 
import com.jonfriend.java41bookclub.models.Item;
import com.jonfriend.java41bookclub.models.LoginUserMdl;
import com.jonfriend.java41bookclub.models.UserMdl;
import com.jonfriend.java41bookclub.services.UserSrv;

@Controller
public class HomeController {

	// Add once service is implemented:
	@Autowired
	private UserSrv userSrv;
	
	@GetMapping("/")
	public String index(
			Model model
			, HttpSession session) {
		
//		String fruitx = "apple"; 
//		
//		model.addAttribute("fruity", fruitx); 
//		return "index.jsp"; 
		
		String customerName = "Buzz Aldrin"; 
		String itemName = "Rolled Flag"; 
		double itemPrice = 100000.00; 
		String itemDescription = "Symbol of freedom and prosperity"; 
		String vendorName = "Northrup Grummon"; 
		
		
		model.addAttribute("customerName", customerName); 
		model.addAttribute("itemName", itemName); 
		model.addAttribute("itemPrice", itemPrice); 
		model.addAttribute("itemDescription", itemDescription); 
		model.addAttribute("vendorName", vendorName); 
		
		ArrayList<Item> fruitsArrList = new ArrayList<Item>();
		fruitsArrList.add(new Item("Kiwi", 1.5));
		fruitsArrList.add(new Item("Mango", 2.0));
		fruitsArrList.add(new Item("Goji Berries", 4.0));
		fruitsArrList.add(new Item("Guava", .75));
		
        model.addAttribute("fruitsFromHomeController", fruitsArrList);
        
//        session.setAttribute("count", 0);
        Integer currentCount = 0; 
        if (session.getAttribute("count") == null) {
    		// Use setAttribute to initialize the count in session
        	session.setAttribute("count", 1);
    	} else {
    		// increment the count by 1 using getAttribute and setAttribute
//    		session.count = session.getAttribute("count") += 1; 
    		currentCount = (Integer) session.getAttribute("count");
    		currentCount ++; 
    		session.setAttribute("count", currentCount); 
    		}
         
        model.addAttribute("countToShow", currentCount);
        // GELD stuff
        
        if (session.getAttribute("currentCountGeld") == null) {
        	session.setAttribute("currentCountGeld", 0);
        }
        
//        if (session.getAttribute("geldActivityArrayList") == null) {
//        	session.setAttribute("geldActivityArrayList", 0);
//        }
        
        Integer currentCountGeld = (Integer) session.getAttribute("currentCountGeld");
        model.addAttribute("currentCountGeld", currentCountGeld);
        
        
        // login/reg form items: putting a new empty model (actually two mod) on the index page, so user can shove data onto using the form. 
        model.addAttribute("newUser", new UserMdl());
        model.addAttribute("newLogin", new LoginUserMdl());
		return "index.jsp"; 
	}

    @PostMapping("/register")
    public String register(
    		@Valid @ModelAttribute("newUser") UserMdl newUser
    		, BindingResult result
    		, Model model
    		, HttpSession session
//    		, @ModelAttribute("newLogin") LoginUserMdl newLogin // added with JLee
    		) {
        
    	UserMdl user = userSrv.register(newUser, result);
    	
    	// TO-DO Later -- call a register method in the service 
        // to do some extra validations and create a new user!
        
        if(result.hasErrors()) {
            // Be sure to send in the empty LoginUser before re-rendering the page.
            model.addAttribute("newLogin", new LoginUserMdl());
            return "index.jsp";
        }
        
        // no errors?  here we go... 
        //Store their ID from the DB in session, in other words, log them in.
        session.setAttribute("userId", user.getId());
   	 
	    return "redirect:/home";
    }
    
    @PostMapping("/login")
    public String login(
    		@Valid @ModelAttribute("newLogin") LoginUserMdl newLogin
    		, BindingResult result
    		, Model model
    		, HttpSession session) {
        
        // Add once service is implemented:
        // User user = userServ.login(newLogin, result);
    	
    	UserMdl user = userSrv.login(newLogin, result);
    	
        if(result.hasErrors() || user==null ) {
            model.addAttribute("newUser", new UserMdl());
            return "index.jsp";
        }
    
        // No errors! 
        // TO-DO Later: Store their ID from the DB in session, 
        // in other words, log them in.
    
        session.setAttribute("userId", user.getId());
   	 
	    return "redirect:/home";
        
        // JF NOTE: we need a home method and JSP!!
    }
    
    @GetMapping("/home")
	public String home(
			Model model
			, HttpSession session) {
	 
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}
		
		// We get the userId from our session (we need to cast the result to a Long as the 'session.getAttribute("userId")' returns an object
		Long userId = (Long) session.getAttribute("userId");
		model.addAttribute("user", userSrv.findById(userId));
		
	    return "home.jsp";
	    
	}
    
    @GetMapping("/logout")
	public String logout(HttpSession session) {
	 
		// Set userId to null and redirect to login/register page
		session.setAttribute("userId", null);
	     
	    return "redirect:/";
	}

//	@RequestMapping(value = "/processLogin", method=RequestMethod.POST)
//	public String processLogin(
//		@RequestParam(value="elNumero") String elNumero,
//	    @RequestParam(value="city") String city,
//	    @RequestParam(value="person") String person,
//	    @RequestParam(value="profHobb") String profHobb,
//	    @RequestParam(value="animal") String animal,
//	    @RequestParam(value="nicety") String nicety,
//		HttpSession session
//		, RedirectAttributes redirectAttributes) 
//		{
//	    	// CODE TO PROCESS FORM ie. check email and password
////			String emailSubmitted = email;
//			
//			session.setAttribute("elNumero", elNumero) ;
//			session.setAttribute("city", city) ;
//			session.setAttribute("person", person) ;
//			session.setAttribute("profHobb", profHobb) ;
//			session.setAttribute("animal", animal) ;
//			session.setAttribute("nicety", nicety) ;
//			
//			redirectAttributes.addFlashAttribute("displayMessage", "Thanks for the good submission!");
//	    	return "redirect:/dashboard"; // <-- we'll change this when we learn redirecting
//	}
//	
//	@RequestMapping("/dashboard") 
//	public String displayDashboard(Model model, HttpSession session) {
//		// get any info needed out of session and add to the view model to render on the page.
//        
////    	String elNumero = (String) session.getAttribute("elNumero");
////		model.addAttribute("elNumero", elNumero);
////		
////		String city = (String) session.getAttribute("city");
////		model.addAttribute("city", city);
////		
////		String person = (String) session.getAttribute("person");
////		model.addAttribute("person", person);
//		return "dashboard.jsp"; 
//	}
	
	@RequestMapping("/reset-counter/")
	public String resetCounter(HttpSession session, Model model, HttpServletRequest request) {
		
		session.setAttribute("count", 0);

		return "redirect:/";
	}
	
//	@RequestMapping("/counter")
//	public String counterApp(Model model, HttpSession session, HttpServletRequest request) {
//		Integer currentCount = 0; 
//		if (session.getAttribute("count") == null) {
//    		// Use setAttribute to initialize the count in session
//        	session.setAttribute("count", 0);
//    	} else {
//    		// increment the count by 1 using getAttribute and setAttribute
////    		session.count = session.getAttribute("count") += 1; 
//    		currentCount = (Integer) session.getAttribute("count");
//    	}
//		
//		// we can use the following line to get the URL of our page, in this case http://localhost:8080/counter/
////		String homeUrl = request.getRequestURL().toString();
//		
//		model.addAttribute("countToShow", currentCount);
//		
////		model.addAttribute("page", homeUrl.substring(0,homeUrl.length()-9)); 
//		
//		return "counter.jsp"; 
//	}
	
	@RequestMapping("/date")
	public String dateRoute(Model model) {
		

		// JRF: below my 1st attempt
		LocalDateTime myDateObj = LocalDateTime.now(); // Create a date object
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
		String formattedDate = myDateObj.format(myFormatObj);
		model.addAttribute("displayDate", formattedDate);
		// JRF end
		
		SimpleDateFormat dayName = new SimpleDateFormat("EEEE");
		SimpleDateFormat dayOfMonth = new SimpleDateFormat("dd");
		SimpleDateFormat spelledOutMonth = new SimpleDateFormat("MMMM");
		SimpleDateFormat yearFourNums = new SimpleDateFormat("Y");
		
		Date dateObj = new Date(); 
		
		String dayNameString = dayName.format(dateObj); 
		String dayOfMonthString = dayOfMonth.format(dateObj); 
		String spelledOutMonthString = spelledOutMonth.format(dateObj); 
		String yearFourNumsString = yearFourNums.format(dateObj); 
		
		String comprehensiveDateString = dayNameString + ", the " + dayOfMonthString + " of "  + spelledOutMonthString + ", " + yearFourNumsString + " "; 
		
		model.addAttribute("displayDate2", comprehensiveDateString);
		
		
		return "date.jsp"; 
	}

	@RequestMapping("/time")
	public String timeRoute(Model model) {
		
		SimpleDateFormat easyTime = new SimpleDateFormat("h:mm a");
		
		Date dateObj = new Date(); 
		
		String easyTimeString = easyTime.format(dateObj); 
		
//		String comprehensiveDateString = dayNameString + ", the " + dayOfMonthString + " of "  + spelledOutMonthString + ", " + yearFourNumsString + " "; 
		
		model.addAttribute("displayTime", easyTimeString);
		
		return "time.jsp"; 
	}
	
	@RequestMapping("/dojos")
    public String dojosRoute(Model model) {
        ArrayList<String> dojosArrList = new ArrayList<String>();
        dojosArrList.add("Burbank");
        dojosArrList.add("Chicago");
        dojosArrList.add("Bellevue");
        model.addAttribute("dojosFromMyController", dojosArrList);
        return "dojos.jsp";
    }
	
	//	@RequestMapping("")
//	public String hello() {
//		return "19 Welcome!"; 
//	}
	
//	@RequestMapping("/today")
//	public String today() {
//		return "19 Today you will find luck in all your endeavors!"; 
//	}
//	
//	@RequestMapping("/tomorrow")
//	public String tommy() {
//		return "19 Tomorrow, an opportunity will arise, so be sure to be open to new ideas!"; 
//	}
//	
//	@RequestMapping("/travel")
//	public String travvy0() {
//		return "19 You want to get away, I get it."; 
//	}
//	
//	@RequestMapping("/travel/{destination}")
//	public String travvy(@PathVariable("destination") String destination) {
//		return "19 Congrats!  You will soon travel to " + destination + "!" ; 
//	}
//	
//	@RequestMapping("/lotto")
//	public String lotty0() {
//		return "19 get some advice here, dude."; 
//	}
//	
//	@RequestMapping("/lotto/{intyInt}")
//	public String lotty(@PathVariable("intyInt") Integer intyInt) {
//		if (intyInt % 2 == 0) {
//			return "You will take a grand journey in the near future, but be weary of tempting offers."; 
//		} else {
//			return "You have enjoyed the fruits of your labor but now is a great time to spend time with family and friends."; 
//		}
//		
//		 
//	}

// end of methods
}
