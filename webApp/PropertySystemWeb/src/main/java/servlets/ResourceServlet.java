package servlets;

import bo.Resource;
import common.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceServlet extends HttpServlet {

    protected void listResources(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException {

       /* int recordsPerPage = 5;
        int page = 1;

        if(request.getParameter("hdnCurrentPage")==null)
            request.setAttribute("currentPageValue", 1);
        else
        {
            page = Integer.parseInt(request.getParameter("hdnCurrentPage"));
            request.setAttribute("currentPageValue", page);
        }

        String output = Connection.serviceConnection("resourceList/"+page+"/"+recordsPerPage,"GET");

        JSONArray jsonArray = new JSONArray(output);

        List<Resource>  resources = new ArrayList<>();

        for (int i = 0 ; i < jsonArray.length() ; i++)
        {
            resources.add((Resource.ToResource((JSONObject) jsonArray.get(i))));
        }

        request.setAttribute("resources",resources);
        request.setAttribute("pageCountValue", resources.get(0).getPageCount());
        request.setAttribute("recordPerPage", recordsPerPage);*/

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/admin/resource.jsp");
        requestDispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        listResources(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        listResources(request, response);

        String action = request.getParameter("hdnButtonAction");
        System.out.println("action="+action);

        String resourceName = request.getParameter("txtResourceName");
        int resourceOwner = Integer.parseInt(request.getParameter("txtResourceOwner"));
//        int resourceLocation = Integer.parseInt(request.getParameter("txtResourceCurrentPlace"));
//        int resourceFloor = Integer.parseInt(request.getParameter("selectFloor"));
//        int resourceUnit = Integer.parseInt(request.getParameter("selectUnitId"));
       // Boolean resourceStatus = Boolean.valueOf(request.getParameter("selectStatus"));


        System.out.println("resourceName="+resourceName);
      //  System.out.println("resourceOwner="+resourceOwner);
//        System.out.println("resourceLocation="+resourceLocation);
//        System.out.println("resourceFloor="+resourceFloor);
//        System.out.println("resourceUnit="+resourceUnit);
       // System.out.println("resourceStatus="+resourceStatus);

        /*if (action.equals("edit")){
            edit(request,output);
        }*/
    }

    public void edit(HttpServletRequest request,String output) {
        System.out.println("output="+output);
        JSONObject jsonObject=new JSONObject(output);

        request.setAttribute("txtResourceName",jsonObject.getString("name"));
        request.setAttribute("txtResourceOwner",jsonObject.getString("owner"));
        request.setAttribute("txtResourceCurrentPlace",jsonObject.getString("unitId"));

    }
}
