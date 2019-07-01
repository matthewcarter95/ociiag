# ociiag
<b>Identity Access Governance for OCI</b>

Java class that goes through all of the compartments in a tenant and shows

  &lt;meta-verb&gt; &lt;resource&gt; in &lt;compartment&gt;

statement fragments for the groups that a specified user belongs to (including any-user)

Usage:

java -jar WhatUserHasAccessTo.jar ocid1.user.oc1..aaaaaaaaope4f3ocxezv4effbvknma4jjqxbdd4eyyzce4cgqo5nbpkyxswq

Output:

  User matt.carter@oracle.com belongs to groups:
   * grafana
   * InstanceLaunchers

Permissions:
  * read metrics in tenancy
  * read compartments in tenancy
  * manage health-check-family in tenancy
  * manage instance-family in compartment PrasadCompartment
  * use volume-family in compartment PrasadCompartment
  * use virtual-network-family in compartment PrasadCompartment

Known limitations:

   *  Doesn't report permissions for manage all-resources in tenancy
   *  Doesn't report conditions with access granted through request.user.id or request.groups.id
   *  Doesn't report access that comes through a service or instance principal
