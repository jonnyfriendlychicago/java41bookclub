package com.jonfriend.java41bookclub.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jonfriend.java41bookclub.models.PublicationMdl;
import com.jonfriend.java41bookclub.models.DojoMdl;
import com.jonfriend.java41bookclub.services.PublicationSrv;
import com.jonfriend.java41bookclub.services.UserSrv;
import com.jonfriend.java41bookclub.services.DojoSrv;

@Controller
public class PublicationCtl {
	
	@Autowired
	private PublicationSrv publicationSrv; 
	
	// JRF: below adding the AW for the pers serv
	
//	@Autowired
//	private DojoSrv dojoSrvIntVar; 
	
	// trying to get creatorId for publication working... 
	// Add once service is implemented:
	@Autowired
	private UserSrv userSrv;

// ******************************************************************************************
// *** publication methods below *** (publicationPlus methods below that) 
// ******************************************************************************************
	
//	// display list of all records
//	@RequestMapping("/publication")
//	public String displayAll(Model model) {
//		List<PublicationMdl> listy = publicationSrv.returnAll();
//		model.addAttribute("publicationList", listy); 
//		return "publication/list.jsp"; 
//		// list
//	}
//	
//	// view one record
//	@GetMapping("/publication/{publicationId}")
//	public String displayOne(Model model, @PathVariable("publicationId") Long publicationId ) {
//		PublicationMdl intVar = publicationSrv.findById(publicationId);
//		model.addAttribute("publication", intVar);
//		return "publication/record.jsp";
//		// record
//	}
//	
//	// new record: initiate it!
//	@RequestMapping("/publication/new")
//	public String initiateNewOne( @ModelAttribute("publication") PublicationMdl publicationMdl ) {
//		return "publication/new.jsp";
//		// new
//	}
//	
//	// new record: finalize/save it (or get kicked back b/c errors)
//	@PostMapping("/publication/new") 
//	public String processNewOne(
//		@Valid 
//		@ModelAttribute("publication") PublicationMdl publicationMdl 
//		, BindingResult result
//	) {
//		
//		if (result.hasErrors()) {
//            return "publication/new.jsp";
//        } else {
//        	publicationSrv.createNew(publicationMdl);
//            return "redirect:/publication";
//        }
//	}
//	
//	// edit record: initiate it!
//	@GetMapping("/publication/{publicationId}/edit")
//	public String editOne(@PathVariable("publicationId") Long publicationId, Model model) {
//		PublicationMdl intVar = publicationSrv.findById(publicationId); 
//		model.addAttribute("publication", intVar);  
//		return "publication/edit.jsp";
//		// edit
//	}
//	
//	// edit record: finalize/save it (or get kicked back b/c errors)
//	@PostMapping("/publication/{publicationId}/edit")
//	public String update(
//			@Valid 
//			@ModelAttribute("publication") PublicationMdl publicationMdl 
//			, BindingResult result) {
//		if (result.hasErrors()) {
//			return "publication/edit.jsp";
//		} else {
//			publicationSrv.update(publicationMdl);
//			return "redirect:/publication";
//		}
//	}
//	
//    @DeleteMapping("/publication/{publicationId}")
//    public String deleteOne(@PathVariable("publicationId") Long publicationId) {
//    	publicationSrv.delete(publicationId);
//        return "redirect:/publication";
//    }
    
// ******************************************************************************************
// *** publicationPlus methods below ***
//******************************************************************************************
	
    // display list of all records, with create form
	@RequestMapping("/publication")
	public String publication(
			@ModelAttribute("publication") PublicationMdl publicationMdl 
			, Model model
			, HttpSession session
			) {
        
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}
		
		// records in table
		List<PublicationMdl> intVar1 = publicationSrv.returnAll();
		model.addAttribute("publicationList", intVar1); 
		// records in 'add-new' dropdown
//		List<DojoMdl> intVar2 = dojoSrvIntVar.returnAll();
//		model.addAttribute("dojoList", intVar2); 
		
		String onErrorPath = "nope"; 
		model.addAttribute("onErrorPath", onErrorPath); 
		
		// We get the userId from our session (we need to cast the result to a Long as the 'session.getAttribute("userId")' returns an object
		Long userId = (Long) session.getAttribute("userId");
		model.addAttribute("user", userSrv.findById(userId));
		
		return "publication/list.jsp";  
	}
	
	// display list, then process the new 
	@PostMapping("/publication") 
	public String publication(
		@Valid 
		@ModelAttribute("publication") PublicationMdl publicationMdl 
		, BindingResult result
		, Model model
		, HttpSession session
	) {
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}
		
		
		if (result.hasErrors()) { 
            // records in table
			List<PublicationMdl> intVar1 = publicationSrv.returnAll(); 
    		model.addAttribute("publicationList", intVar1);
    		// records in 'add-new' dropdown
//    		List<DojoMdl> intVar2 = dojoSrvIntVar.returnAll();
//    		model.addAttribute("dojoList", intVar2); 

    		String onErrorPath = "yep"; 
    		model.addAttribute("onErrorPath", onErrorPath); 
    		
    		return "publication/list.jsp";
    		
        } else {
//        	boolean onErrorPath = false; 
//    		model.addAttribute("onErrorPath", onErrorPath); 
    		
    		publicationSrv.createNew(publicationMdl);
            return "redirect:/publication";
        }
	}
	
	// display list while editting a record
	// edit record: initiate it!
	@GetMapping("/publication/{publicationId}/edit")
	public String publicationEdit(
			@PathVariable("publicationId") Long publicationId
			, Model model
			, HttpSession session
			) {
		
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}

        // records in table
		List<PublicationMdl> intVar1 = publicationSrv.returnAll();
		model.addAttribute("publicationList", intVar1); 
		
		// pre-populates the values in the 'manage-one' interface
		PublicationMdl intVar2 = publicationSrv.findById(publicationId); 
		model.addAttribute("publication", intVar2);  
		
		// records in 'manage-one' interface dropdown
//		List<DojoMdl> intVar3 = dojoSrvIntVar.returnAll();
//		model.addAttribute("dojoList", intVar3); 
		
		// We get the userId from our session (we need to cast the result to a Long as the 'session.getAttribute("userId")' returns an object
		Long userId = (Long) session.getAttribute("userId");
		model.addAttribute("user", userSrv.findById(userId));
		
		return "publication/edit.jsp";
	}
	
	// edit record: finalize/save it (or get kicked back b/c errors)
	@PostMapping("/publication/{publicationId}/edit")
	public String publicationEdit(
			@Valid 
			@ModelAttribute("publication") PublicationMdl publicationMdl 
			, BindingResult result
			, Model model
			, HttpSession session) {
		
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}
		
		
		
		// if someone hacky sacks the hidden fields... error this thing
		
		
		
		if (result.hasErrors()) {
			
//			List<PublicationMdl> intVar2 = publicationSrv.returnAll();
//			model.addAttribute("publicationList", intVar2); 
			
			List<PublicationMdl> intVar2 = publicationSrv.returnAll();
			model.addAttribute("publicationList", intVar2); 
			
//			PublicationMdl intVar = publicationSrv.findById(publicationId); 
//			model.addAttribute("publication", intVar);  
			
			// below provides drop-down that now works
//			List<DojoMdl> intVar3 = dojoSrvIntVar.returnAll();
//			model.addAttribute("dojoList", intVar3); 
			
			return "publication/edit.jsp";
		} else {
			publicationSrv.update(publicationMdl);
			return "redirect:/publication";
		}
	}
	
    @DeleteMapping("/publication/{publicationId}")
    public String displayAllDeleteOne(
    		@PathVariable("publicationId") Long publicationId
    		, HttpSession session
    		) {
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}
		
		publicationSrv.delete(publicationId);
        return "redirect:/publication";
    }
    
 // view one record
 	@GetMapping("/publication/{publicationId}")
 	public String fromDisplayAllJustDisplayOne(
 			@PathVariable("publicationId") Long publicationId
 			, Model model
 			, HttpSession session
 			) {
 		
		// If no userId is found in session, redirect to logout.  JRF: put this on basically all methods now, except the login/reg pages
		if(session.getAttribute("userId") == null) {return "redirect:/logout";}
		
		// We get the userId from our session (we need to cast the result to a Long as the 'session.getAttribute("userId")' returns an object
		Long userId = (Long) session.getAttribute("userId");
		model.addAttribute("user", userSrv.findById(userId));
		
		PublicationMdl intVar = publicationSrv.findById(publicationId);
 		model.addAttribute("publication", intVar);
 		
 		return "publication/record.jsp";
 	}

	// end of ctl
}
