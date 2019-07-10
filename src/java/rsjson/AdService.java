/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsjson;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Maxim
 */
@Path("generic")
public class AdService {
    
    @Context
    private UriInfo context;
    private static List<Ad> alist;

    /**
     * Creates a new instance of AdService
     */
    public AdService() {
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public List<Ad> getAds() {
        return alist;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/post")
    public String postAd(Ad a) throws Exception {
        checkContext(context, a);
        alist.add(a);
        return "Added";
    }
    
    @PUT
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public String updateAd(Ad a) {
        Ad adput = find(a.getId());
        adput.setTitle(a.getTitle());
        adput.setDescription(a.getDescription());
        adput.setContact(a.getContact());
        return "Updated";
    }
    
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        Ad adelete = find(id);
        alist.remove(adelete);
        String msg = "Book " + id + " deleted.\n";
        return Response.ok(msg, "text/plain").build();
    }
    
    private void checkContext(UriInfo context, Ad a) {
        if (alist == null && context != null) {
            populate(a);
        }
    }
    
    private void populate(Ad a) {
        alist = new CopyOnWriteArrayList<Ad>();
        alist.add(a);
        
    }
    
    private Ad find(int id) {
        Ad ad = null;
        for (Ad a : alist) {
            if (a.getId() == id) {
                ad = a;
                break;
            }
        }
        return ad;
    }
}
