package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/events")
public class EventsController {

	
	@RequestMapping(value="dupa",  method = RequestMethod.GET)
	@ResponseBody
	public String dupa(HttpServletResponse response, ModelMap model) {

		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    return "createEvent";
	}

	
	@RequestMapping( value="help2", method = RequestMethod.GET)
	public String create2(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "help2";
	}

}