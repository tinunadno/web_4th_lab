package org.web_4th_lab.web_4th_lab.controllers;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.web_4th_lab.web_4th_lab.Beans.UserService;
import org.web_4th_lab.web_4th_lab.DTO.AuthenticationRequest;
import org.web_4th_lab.web_4th_lab.DTO.AuthenticationResponse;
import org.web_4th_lab.web_4th_lab.DTO.NoBodyRequest;

@Path("/userController")
public class AuthorizationController {
    @EJB
    UserService userService;

    @POST
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(@Valid AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse result = userService.authorizeUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            return Response.ok(result).build();
        }catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Valid AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse result = userService.registerUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            return Response.ok(result).build();
        }catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("deleteUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@Valid NoBodyRequest noBodyRequest) {
        if(!userService.validateAuthorizedUser(noBodyRequest.getUserId(), noBodyRequest.getToken())){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            userService.deleteUserById(noBodyRequest.getUserId());
        } catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }
}