package umk.zychu.inzynierka.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class JavaplannerController {

	@RequestMapping("/planner")
	public String planner(@RequestParam("orlik") Integer id, HttpServletRequest request, ModelMap model)
			throws Exception {
		model.addAttribute("orlik", id);
		return "javaplanner";
	}
}











