package com.okta.UserAdminServices.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.application.ApplicationList;
import com.okta.sdk.resource.user.UserList;

@Component
public class ReadOktaDataService {

	@Autowired
	private Client client;
	
	public List<String> getUsers() {
		List<String> users = new ArrayList<String>();
		UserList oktaUsers = client.listUsers();
		oktaUsers.forEach(user -> {
			System.out.println(user.toString());
			users.add(user.getProfile().getFirstName() + " " + user.getProfile().getLastName());
		});
		return users;		
	}
	
	public List<String> getApplictions() {
		List<String> applications = new ArrayList<String>();
		ApplicationList oktaApplications = client.listApplications();
		oktaApplications.forEach(oktaApplication -> {
			System.out.println(oktaApplication.toString());
			applications.add(oktaApplication.getLabel());
		});
		return applications;
		
		
	}

}
