package com.okta.UserAdminServices.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okta.UserAdminServices.service.AssignOrRevokeUsersToApplication;
import com.okta.UserAdminServices.service.ReadOktaDataService;

@RestController()
@RequestMapping("/okta")
public class AdminServiceController {
	
	@Autowired
	private ReadOktaDataService listAppService;
	
	@Autowired
	private AssignOrRevokeUsersToApplication assignUsersToApplication;
	
	@GetMapping("/getUsers")
	public List<String> listUsers() throws IOException {		
		return listAppService.getUsers();
	}
	
	@GetMapping("/getApplications")
	public List<String> listApplications() throws IOException {		
		return listAppService.getApplictions();
	}
	
	@GetMapping("/assignUser")
	public ResponseEntity<String> assignAppToUser(@RequestParam String userId, @RequestParam String appId) throws IOException {		
		return assignUsersToApplication.assignUsersToApplication(userId, appId);
	}
	
	@GetMapping("/revokeUser")
	public ResponseEntity<String> revokeUserFromApp(@RequestParam String userId, @RequestParam String appId) throws IOException {		
		return assignUsersToApplication.revokeUserFromApplication(userId, appId);
	}
	
	

}