package servlets;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/home.jsp");
        requestDispatcher.forward(req, resp);
    }
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //invalidate the session if exists
            HttpSession session = req.getSession(false);
            if(session != null){
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

