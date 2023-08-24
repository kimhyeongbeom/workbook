package w1.todo;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "todoRegisterController", urlPatterns = "/todo/register")
public class TodoRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("입력 화면을 볼 수 있도록 구성 ");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/todo/register.jsp");

        dispatcher.forward(req,resp);

    }


//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        System.out.println("입력을 처리하고 목록 페이지로 이동 ");
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("입력을 처리하고 목록 페이지로 이동 ");

        //브라우저가 호출해야 하는 주소
        resp.sendRedirect("/w1/todo/list");
    }

}

