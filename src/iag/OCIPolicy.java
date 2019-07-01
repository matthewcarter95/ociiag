package iag;

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
import com.oracle.bmc.identity.requests.CreateCompartmentRequest;
import com.oracle.bmc.identity.requests.ListCompartmentsRequest;
import com.oracle.bmc.identity.requests.ListPoliciesRequest;
import com.oracle.bmc.identity.responses.ListCompartmentsResponse;
import com.oracle.bmc.identity.responses.ListPoliciesResponse;

public class OCIPolicy {
    public static ListPoliciesResponse list(String compartmentOcid) throws Exception {
    	ListPoliciesResponse lpResponse = null;
        // TODO: Fill in this value
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

    //    do {
        		lpResponse =
				identityClient.listPolicies(
                            ListPoliciesRequest.builder()
                                    .limit(3)
                                    .compartmentId(compartmentOcid)
                                    .page(nextPageToken)
                                    .build());

//            for (Policy policy : lpResponse.getItems()) {
//                System.out.println(policy);
//            }
//            nextPageToken = lpResponse.getOpcNextPage();
//        } while (nextPageToken != null);

     
        return lpResponse;

    }

}

