package org.web_4th_lab.web_4th_lab.rest;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/authorization")
public class AuthorizationController {
    @GET
    @Path("/process")
    @Produces(MediaType.APPLICATION_JSON)
    public Response processCredentials(@QueryParam("username") String username,
                                       @QueryParam("password") String password) {
        String result = "Received username: " + username + " and password: " + password;
        return Response.ok(result).build();
    }
}