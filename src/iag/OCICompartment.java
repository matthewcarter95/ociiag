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
import com.oracle.bmc.identity.requests.CreateCompartmentRequest;
import com.oracle.bmc.identity.requests.ListCompartmentsRequest;
import com.oracle.bmc.identity.responses.ListCompartmentsResponse;

public class OCICompartment {
	
	
    public static ListCompartmentsResponse list() throws Exception {
    	ListCompartmentsResponse lcResponse = null;

        String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        AuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configurationFilePath, profile);

        String compartmentId = provider.getTenantId();
        final String tenantId = provider.getTenantId();
        Identity identityClient = new IdentityClient(provider);
        identityClient.setRegion(Region.US_ASHBURN_1);
        
        // List all compartments within tenancy with Accessible compartment filter
        String nextPageToken = null;
        
        		lcResponse =
				identityClient.listCompartments(
                            ListCompartmentsRequest.builder()
                                    .limit(3)
                                    .compartmentId(compartmentId)
                                    .accessLevel(ListCompartmentsRequest.AccessLevel.Any)
                                    .compartmentIdInSubtree(Boolean.TRUE)
                                    .page(nextPageToken)
                                    .build());


        
        return lcResponse;

    }

    public static List<String> listCompartmentOcids() throws Exception {
    	List<String> compartmentOcids = new ArrayList<String>();
        // TODO: Fill in this value
        String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        AuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configurationFilePath, profile);

        String compartmentId = provider.getTenantId();
        final String tenantId = provider.getTenantId();
        Identity identityClient = new IdentityClient(provider);
        identityClient.setRegion(Region.US_ASHBURN_1);
        
        // List all compartments within tenancy with Accessible compartment filter
        String nextPageToken = null;
        
    	int counter = 0;
        do {
        		ListCompartmentsResponse lcResponse = 
				identityClient.listCompartments(
                            ListCompartmentsRequest.builder()
                                    .limit(3)
                                    .compartmentId(compartmentId)
                                    .accessLevel(ListCompartmentsRequest.AccessLevel.Accessible)
                                    .compartmentIdInSubtree(Boolean.TRUE)
                                    .page(nextPageToken)
                                    .build());
        	
            for (Compartment compartment : lcResponse.getItems()) {
            	
            	counter++;
            	//System.out.println("Cmptmt Name is " + compartment.getName());
                compartmentOcids.add(compartment.getCompartmentId());
            }
            
            nextPageToken = lcResponse.getOpcNextPage();
        } while (nextPageToken != null);
        System.out.println("Number of compartments is " + counter);

        
        return compartmentOcids;

    }

}

