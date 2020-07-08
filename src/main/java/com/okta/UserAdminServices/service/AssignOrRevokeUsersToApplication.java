package com.okta.UserAdminServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.application.AppUser;
import com.okta.sdk.resource.application.AppUserCredentials;
import com.okta.sdk.resource.application.Application;
import com.okta.sdk.resource.user.User;

@Component
public class AssignOrRevokeUsersToApplication {

	@Autowired
	private Client client;

	public String assignUsersToApplication(String userId, String appId) {
		User user = client.getUser(userId);
		AppUser appUser = getAppUser(user);
		Application app = client.getApplication(appId);
		AppUser updatedUser = app.assignUserToApplication(appUser);
		return updatedUser.toString();
	}

	public AppUser getAppUser(User user) {
		AppUser appUser = client.instantiate(AppUser.class)
			    .setScope("USER")
			    .setId(user.getId());
		AppUserCredentials appUserCreds = client.instantiate(AppUserCredentials.class)
			        .setUserName(user.getProfile().getEmail());
		appUser.setCredentials(appUserCreds);
		return appUser;
	}

	public String revokeUserFromApplication(String userId, String appId) {
		Application app = client.getApplication(appId);
		AppUser appUser = app.getApplicationUser(userId);
		appUser.delete();
		return "User Deleted Successfully";
	}
	
	
}
