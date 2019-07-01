package iag;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
 */
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.identity.Identity;
import com.oracle.bmc.identity.IdentityClient;
import com.oracle.bmc.identity.model.Compartment;
import com.oracle.bmc.identity.model.CreateCompartmentDetails;
import com.oracle.bmc.identity.model.Policy;
import com.oracle.bmc.identity.model.UserGroupMembership;
import com.oracle.bmc.identity.requests.CreateCompartmentRequest;
import com.oracle.bmc.identity.requests.GetGroupRequest;
import com.oracle.bmc.identity.requests.GetUserGroupMembershipRequest;
import com.oracle.bmc.identity.requests.GetUserRequest;
import com.oracle.bmc.identity.requests.ListCompartmentsRequest;
import com.oracle.bmc.identity.requests.ListPoliciesRequest;
import com.oracle.bmc.identity.requests.ListUserGroupMembershipsRequest;
import com.oracle.bmc.identity.responses.GetGroupResponse;
import com.oracle.bmc.identity.responses.GetUserGroupMembershipResponse;
import com.oracle.bmc.identity.responses.GetUserResponse;
import com.oracle.bmc.identity.responses.ListCompartmentsResponse;
import com.oracle.bmc.identity.responses.ListPoliciesResponse;
import com.oracle.bmc.identity.responses.ListUserGroupMembershipsResponse;
import com.oracle.bmc.identity.responses.ListUsersResponse;

public class OCIMembership {
    public static List<String> list(String userOcid) throws Exception {
    	ListUserGroupMembershipsResponse membershipResponse = null;
    	GetUserResponse userResponse = null;
    	List<String> groupOcidList = new ArrayList<String>();
    	List<String> groupNameList = new ArrayList<String>();
       
        String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        AuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configurationFilePath, profile);

        //String compartmentId = provider.getTenantId();
        final String tenantId = provider.getTenantId();
        Identity identityClient = new IdentityClient(provider);
        identityClient.setRegion(Region.US_ASHBURN_1);
        
        // List all compartments within tenancy with Accessible compartment filter
        String nextPageToken = null;

        userResponse = 
        		identityClient.getUser(
        				GetUserRequest.builder()
        				.userId(userOcid)
        				.build());
        
        String userName = userResponse.getUser().getName();
        
//        do {
        	membershipResponse =
				identityClient.listUserGroupMemberships(
						ListUserGroupMembershipsRequest.builder()
						.compartmentId(tenantId)
						.userId(userOcid)
						                                    .build());

        	
            for (UserGroupMembership membership : membershipResponse.getItems()) {
                //System.out.println(membership.getGroupId());
                groupOcidList.add(membership.getGroupId());
            }
//            nextPageToken = membershipResponse.getOpcNextPage();
//        } while (nextPageToken != null);
            System.out.println("User " + userName + " belongs to groups: ");
            System.out.println("------------");
            for(String groupOcid : groupOcidList) {
            	GetGroupResponse groupResponse = 
                		identityClient.getGroup(
                				GetGroupRequest.builder()
                				.groupId(groupOcid)
                				.build());
            	String groupName = groupResponse.getGroup().getName(); 
            	System.out.println(groupName);
            	groupNameList.add(groupName);
            }
            
            
            
        return groupNameList;

    }
    
    

}

