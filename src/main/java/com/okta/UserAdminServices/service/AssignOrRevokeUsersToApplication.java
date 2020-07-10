package com.okta.UserAdminServices.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.application.AppUser;
import com.okta.sdk.resource.application.AppUserCredentials;
import com.okta.sdk.resource.application.Application;
import com.okta.sdk.resource.user.User;

@Component
public class AssignOrRevokeUsersToApplication {

	private static final Logger logger = LoggerFactory.getLogger(AssignOrRevokeUsersToApplication.class);
	
	@Autowired
	private Client client;

	public ResponseEntity<String> assignUsersToApplication(String userId, String appId) throws JsonProcessingException {
		ObjectMapper Obj = new ObjectMapper();
		try {
			User user = client.getUser(userId);
			AppUser appUser = getAppUser(user);
			Application app = client.getApplication(appId);
			AppUser updatedUser = app.assignUserToApplication(appUser);
			logger.info("The User " + userId + " assigned with Application " + appId);
			return new ResponseEntity<String>(Obj.writeValueAsString(updatedUser), HttpStatus.OK);
		} catch (ResourceException re) {
			logger.error("Failed to assign the Application to the User", re);
			return new ResponseEntity<String>(Obj.writeValueAsString(getExceptionObject(re)), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> revokeUserFromApplication(String userId, String appId) throws JsonProcessingException {
		ObjectMapper Obj = new ObjectMapper();
		try {
			Application app = client.getApplication(appId);
			AppUser appUser = app.getApplicationUser(userId);
			appUser.delete();
			logger.info("The User " + userId + " access got revoked from the Application " + appId);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (ResourceException re) {
			logger.error("Failed to revoke the Application access for the User", re);
			return new ResponseEntity<String>(Obj.writeValueAsString(getExceptionObject(re)), HttpStatus.NOT_FOUND);
		}
	}

	private AppUser getAppUser(User user) {
		AppUser appUser = client.instantiate(AppUser.class).setScope("USER").setId(user.getId());
		AppUserCredentials appUserCreds = client.instantiate(AppUserCredentials.class)
				.setUserName(user.getProfile().getEmail());
		appUser.setCredentials(appUserCreds);
		return appUser;
	}
	
	private Map<String, String> getExceptionObject(ResourceException re) {
		Map<String, String> exception = new HashMap<String, String>();
		exception.put("Id", re.getId());
		exception.put("code", re.getCode());
		exception.put("message", re.getError().getMessage());
		exception.put("status", String.valueOf(re.getStatus()));
		return exception;
	}

}
