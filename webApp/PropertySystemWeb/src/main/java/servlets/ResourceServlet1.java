package servlets;

import bo.Resource;
import common.Connection;
import common.StringFormat;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet( "/ResourceServlet1")
public class ResourceServlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String action = request.getParameter("action");

            if (action.equals("addResource") || action.equals("editResource")) {
                saveOrUpdate(request,response,action);
            }

            else if (action.equals("fillResourceTable")){
                fillResourceTable(request,response,action);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception:"+ex.getMessage());
        }

    }

    private void saveOrUpdate(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        try{
            String message="";
            String resourceId           = request.getParameter("resourceId");
            String resourceName         = request.getParameter("resourceName");
            String resourceOwner        = request.getParameter("resourceOwner");
            String resourceCurrentPlace = request.getParameter("resourceCurrentPlace");
            String floor                = request.getParameter("floor");
            String unitId               = request.getParameter("unitId");
            String status               = request.getParameter("status");
            String createdBy            = "1";
            String modifiedBy           = "2";

            // status = (status != null ? true : false);
            String by="";
            if (action.equals("addResource")){
                by = createdBy;
            }else if (action.equals("editResource")) {
                by = modifiedBy;
            }

            String apiMethod = "resourceInsertOrUpdate/" + resourceId + "/" + StringFormat.encoder(resourceName )+ "/"
                    +resourceOwner +"/"+ StringFormat.encoder(resourceCurrentPlace) + "/"
                    + floor + "/" + unitId+ "/" + by + "/" +  status;

            message = Connection.serviceConnection( apiMethod, "GET");

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println(message);
        }
        catch (Exception ex)
        {
            System.out.println("Exception is:"+ex.getMessage());
        }

    }

    private void fillResourceTable(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        try {
            /*int recordsPerPage = 5;
            int page = 1;*/
            System.out.println("fillResourceTable");
            int page = Integer.parseInt(request.getParameter("pageNumber"));
            int recordsPerPage = Integer.parseInt(request.getParameter("index"));

            /*if (request.getParameter("hdnCurrentPage") == null)
                request.setAttribute("currentPageValue", 1);
            else {
                page = Integer.parseInt(request.getParameter("hdnCurrentPage"));
                request.setAttribute("currentPageValue", page);
            }*/

            System.out.println("cpv=" + page);

            String output = Connection.serviceConnection("resourceList/" + page + "/" + recordsPerPage, "GET");

            System.out.println("OUT="+output);

            JSONArray jsonArray = new JSONArray(output);

            List<Resource> resources = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println("JsonArray=="+(Resource.ToResource((JSONObject) jsonArray.get(i))));
                resources.add((Resource.ToResource((JSONObject) jsonArray.get(i))));
            }
            System.out.println("testRES="+resources);
            //response.setContentType("");
            //request.setAttribute("resources", resources);
            request.setAttribute("pageCountValue", resources.get(0).getPageCount());
            request.setAttribute("recordPerPage", recordsPerPage);
            //response.setContentType("text/plain");
            response.setContentType("json");
            PrintWriter out = response.getWriter();
            out.println(output);
        }
        catch (Exception ex){
            System.out.println("fillException : " + ex.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/resource.jsp");
        requestDispatcher.forward(request, response);
    }



}
