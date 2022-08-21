package bl.rest;

import bl.ResourceBL;
import bl.UserAccountBL;
import bo.Resource;
import bo.views.ResourceView;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/")
public class PropSysService {
    @GET
    @Path("/userAccountGetById/{userId}")
    public String userAccountGetById(@PathParam("userId") String userId) throws IOException {
        UserAccountBL userAccountBL = new UserAccountBL();
        return userAccountBL.getById(userId);
    }

    @GET
    @Path("/userAccountGetByUsernamePassword/{userName}/{password}")
    public String userAccountGetByUsernamePassword(@PathParam("userName") String userName, @PathParam("password") String password)
            throws IOException {
        UserAccountBL userAccountBL = new UserAccountBL();
        return userAccountBL.getByUsernamePassword(userName,password);
    }
    @GET
    @Path("/userAccountGetByWord/{word}")
    public String userAccountGetByWord(@PathParam("word") String word)
            throws IOException {
        UserAccountBL userAccountBL = new UserAccountBL();
        return userAccountBL.getByWord(word);
    }

    @GET
    @Path("/userAccountAddUser/{firstName}/{lastName}/{userName}/{password}/{managerId}/{userTypeId}/{active}/{createdBy}")
    public String userAccountAddUser(@PathParam("firstName") String firstName,
                                     @PathParam("lastName")  String lastName,
                                     @PathParam("userName")  String userName,
                                     @PathParam("password")  String password,
                                     @PathParam("managerId") Integer managerId,
                                     @PathParam("userTypeId") Integer userTypeId,
                                     @PathParam("active") Boolean active,
                                     @PathParam("createdBy") Integer createdBy)throws IOException {
        UserAccountBL userAccountBL = new UserAccountBL();
        String s=userAccountBL.getByUsernamePassword(userName,password);

        if(s=="null")

        {
            return "This user exist...PLZ enter another user name...";
        }
        else{
            String result= userAccountBL.addUser(firstName,lastName,userName,password,managerId,userTypeId,active,createdBy);
            return result;
        }

    }


    @GET
    @Path("/userAccountCheckDuplicateUserName/{userName}")
    public String userAccountAddUser(@PathParam("userName") String userName )throws IOException {
        UserAccountBL userAccountBL = new UserAccountBL();
        String result=userAccountBL.CheckDuplicateUserName(userName);
        return result;


    }

//---------<RESOURCES BLOCK>-----------//
    @GET
    @Path("/resourceList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resource> resourceList1() throws IOException{
        ResourceBL resourceBL = new ResourceBL();
        return resourceBL.GetAll1();
    }

    @GET
    @Path("/resourceList/{currentPage}/{recordPerPage}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResourceView> resourceList(@PathParam("currentPage") int currentPage, @PathParam("recordPerPage") int recordPerPage) throws IOException{
        ResourceBL resourceBL = new ResourceBL();
        return resourceBL.GetAll(currentPage,recordPerPage);
    }

    @GET
    @Path("/resourceGetById/{resourceId}")
    public String resourceGetById(@PathParam("resourceId") Integer resourceId) throws IOException {
        ResourceBL resourceBL = new ResourceBL();
        return resourceBL.getById(resourceId);
    }

    @GET
    @Path("/resourceInsertOrUpdate/{resourceId}/{resourceName}/{resourceOwner}/{resourceCurrentPlace}/{floor}/{unitId}/{by}/{status}")
    public String resourceInsertOrUpdate(@PathParam("resourceId") Integer resourceId,
                                       @PathParam("resourceName") String resourceName,
                                       @PathParam("resourceOwner") Integer resourceOwner,
                                       @PathParam("resourceCurrentPlace") String resourceCurrentPlace,
                                       @PathParam("floor") Byte floor,
                                       @PathParam("unitId") Integer unitId,
                                       @PathParam("by") Integer by,
                                       @PathParam("status") Boolean status)throws IOException {
        System.out.println("HI MY DEAR RAHIL...PROPSYS");

        ResourceBL resourceBL = new ResourceBL();
        String s=resourceBL.getById(resourceId);

        String result = resourceBL.insertOrUpdateResource(resourceId, resourceName, resourceOwner, resourceCurrentPlace, floor, unitId ,by , status);
        return result;


    }
}
