package in.coderarmy.servlet;

import in.coderarmy.model.User;
import in.coderarmy.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String i_d = request.getParameter("id");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobileNo");
        String emailid = request.getParameter("emailid");

        if (i_d == null || name == null || emailid == null || mobile == null) {

            response.setStatus(400);
            response.setContentType("application/json");

            response.getWriter().write("{\n" +
                    "    \"message\" : \"Some Fields are Missing\"\n" +
                    "}");

            return;
        }

        Integer id = Integer.parseInt(i_d);
        Long mobileNo = Long.parseLong(mobile);

        User us = new User(id, emailid, mobileNo, name);

        User createdUser = userService.createUser(us);

        response.setStatus(201);
        response.setContentType("application/json");

        response.getWriter().write("{\n" +
                "    \"message\" : \"User Added Successfully\"\n" +
                "}");
    }


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        String i_d = request.getParameter("id");

        if (i_d == null) {

            List<User> users = userService.getAllUsers();

            response.setStatus(201);
            response.setContentType("application/json");

            response.getWriter().write(usersToJson(users));

            return;
        }

        Integer id = Integer.parseInt(i_d);

        User userResponse = userService.getUserById(id);

        if (userResponse == null) {

            response.setStatus(400);
            response.setContentType("application/json");

            return;
        }

        response.setStatus(201);
        response.setContentType("application/json");

        response.getWriter().write(userToJson(userResponse));
    }


    @Override
    public void doPut(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) {

    }

    @Override
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String i_d = request.getParameter("id");

        if (i_d == null) {
            writeMessage(response, 400, "id is required");
            return;
        }

        Integer id = Integer.parseInt(i_d);
        User deleted = userService.deleteUser(id);

        if (deleted == null) {
            writeMessage(response, 404, "User Not Found");
            return;
        }

        writeMessage(response, 200, "User Deleted Successfully");
    }


    // helper to avoid repeating response code
    private void writeMessage(HttpServletResponse response,
                              int status,
                              String message) throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\n" +
                "    \"message\" : \"" + message + "\"\n" +
                "}");
    }


    private String userToJson(User user) {

        return "{\n" +
                "    \"id\": " + user.getId() + ",\n" +
                "    \"name\": \"" + user.getName() + "\",\n" +
                "    \"emailid\": \"" + user.getEmailid() + "\",\n" +
                "    \"mobileNo\": \"" + user.getMobileNo() + "\"\n" +
                "}";
    }


    private String usersToJson(List<User> users) {

        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i < users.size(); i++) {

            sb.append(userToJson(users.get(i)));

            if (i < users.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}