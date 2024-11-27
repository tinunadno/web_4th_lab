package org.web_4th_lab.web_4th_lab.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.web_4th_lab.web_4th_lab.Beans.PointService;
import org.web_4th_lab.web_4th_lab.Beans.UserService;
import org.web_4th_lab.web_4th_lab.Utils.BackendLogger;

@Path("/pointChecker")
public class PointCheckingController {

    @EJB
    UserService userService;
    @EJB
    PointService pointService;

    @GET
    @Path("checkPoint")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPoint(@QueryParam("x_cord") String x_cord,
                               @QueryParam("y_cord") String y_cord,
                               @QueryParam("radius") String radius,
                               @QueryParam("id") String id,
                               @QueryParam("token") String token) {
        if(!userService.validateAuthorizedUser(id, token)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        pointService.checkPoint(x_cord, y_cord, radius, id);
        String result = pointService.getUserHistory(id);
        return Response.ok(result).build();
    }

    @GET
    @Path("getPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints(@QueryParam("id") String id,
                              @QueryParam("token") String token) {
        if(!userService.validateAuthorizedUser(id, token)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = pointService.getUserHistory(id);
        return Response.ok(result).build();
    }

    @GET
    @Path("clearPointsHistory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearPointsHistory(@QueryParam("id") String id,
                              @QueryParam("token") String token) {
        if(!userService.validateAuthorizedUser(id, token)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        pointService.clearPointHistory(id);
        String result = pointService.getUserHistory(id);
        return Response.ok(result).build();
    }
}
