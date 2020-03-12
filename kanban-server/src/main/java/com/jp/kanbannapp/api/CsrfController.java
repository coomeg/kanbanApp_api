package com.jp.kanbannapp.api;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

	@GetMapping("/public/csrf")
	public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
