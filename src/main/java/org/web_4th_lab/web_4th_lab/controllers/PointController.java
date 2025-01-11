package org.web_4th_lab.web_4th_lab.controllers;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.web_4th_lab.web_4th_lab.Beans.PointService;
import org.web_4th_lab.web_4th_lab.Beans.UserService;
import org.web_4th_lab.web_4th_lab.DTO.CheckPointRequest;
import org.web_4th_lab.web_4th_lab.DTO.NoBodyRequest;
import org.web_4th_lab.web_4th_lab.DTO.ResultListResponse;
import org.web_4th_lab.web_4th_lab.Utils.BackendLogger;

@Path("/pointController")
public class PointController {

    @EJB
    UserService userService;
    @EJB
    PointService pointService;

    @POST
    @Path("checkPoint")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPoint(@Valid CheckPointRequest checkPointRequest) {
        if(!userService.validateAuthorizedUser(checkPointRequest.getId(), checkPointRequest.getToken())){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            pointService.checkPoint(checkPointRequest);
            ResultListResponse result = pointService.getUserHistory(checkPointRequest.getId());
            return Response.ok(result).build();
        }catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("getPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints(@QueryParam("userId") long id,
                              @QueryParam("token") String token) {
        if(!userService.validateAuthorizedUser(id, token)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            ResultListResponse result = pointService.getUserHistory(id);
            return Response.ok(result).build();
        }catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("clearPointsHistory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearPointsHistory(@Valid NoBodyRequest noBodyRequest) {
        if(!userService.validateAuthorizedUser(noBodyRequest.getUserId(), noBodyRequest.getToken())){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            pointService.clearPointHistory(noBodyRequest.getUserId());
        }catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.ok().build();
    }
}
