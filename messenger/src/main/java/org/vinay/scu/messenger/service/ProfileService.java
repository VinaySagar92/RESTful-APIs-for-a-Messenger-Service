package org.vinay.scu.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.vinay.scu.messenger.databases.DatabaseClass;
import org.vinay.scu.messenger.model.Profile;

public class ProfileService {
	Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService()
	{
		profiles.put("vinay", new Profile(1L, "vinay", "Vinay Sagar", "Gonabavi"));
		profiles.put("admin", new Profile(2L, "admin", "Admin", "User"));
	}
	
	public List<Profile> getAllProfiles()
	{
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName)
	{
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile)
	{
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile)
	{
		if(profile.getProfileName().isEmpty()) return null;
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile deleteProfile(String profileName)
	{
		return profiles.remove(profileName);
	}
}
