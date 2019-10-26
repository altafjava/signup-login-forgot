package com.slf.main.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {

	@GetMapping("/error")
	public void handleError(HttpServletRequest request) throws Throwable {
		if (request.getAttribute("javax.servlet.error.exception") != null) {
			throw (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}
