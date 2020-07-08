package com.okta.UserAdminServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.application.AppUser;
import com.okta.sdk.resource.application.AppUserCredentials;
import com.okta.sdk.resource.application.Application;
import com.okta.sdk.resource.user.User;

@Component
public class AssignOrRevokeUsersToApplication {

	@Autowired
	private Client client;

	public ResponseEntity<String> assignUsersToApplication(String userId, String appId) {
		try {
			User user = client.getUser(userId);
			AppUser appUser = getAppUser(user);
			Application app = client.getApplication(appId);
			AppUser updatedUser = app.assignUserToApplication(appUser);
			return new ResponseEntity<String>(updatedUser.toString(), HttpStatus.OK);
		} catch (ResourceException re) {
			return new ResponseEntity<String>(re.getError().getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> revokeUserFromApplication(String userId, String appId) {
		try {
			Application app = client.getApplication(appId);
			AppUser appUser = app.getApplicationUser(userId);
			appUser.delete();
			return new ResponseEntity<String>("User Access revoked from application successfully", HttpStatus.OK);
		} catch (ResourceException re) {
			return new ResponseEntity<String>(re.getError().getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	private AppUser getAppUser(User user) {
		AppUser appUser = client.instantiate(AppUser.class).setScope("USER").setId(user.getId());
		AppUserCredentials appUserCreds = client.instantiate(AppUserCredentials.class)
				.setUserName(user.getProfile().getEmail());
		appUser.setCredentials(appUserCreds);
		return appUser;
	}

}
