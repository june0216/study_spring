package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet" , urlPatterns = "/hello") // 서블릿끼리 중복이 있으면 안된다.
public class HelloServlet extends HttpServlet { //상속해서 써야한다.

	@Override //서블릿이 호출되면 이 service가 호출된다. <컨트롤 + O , service 검색>
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//super.service(req, resp);
		System.out.println("HelloServlet.service");
		System.out.println("request = " + request); //HttpServletRequest request, HttpServletResponse response 은 인터페이스인데 was 서버가 HTTP 표준 스펙을 구현하기 위함이다.
		System.out.println("response = " + response);
		
		String username = request.getParameter("username");// 쿼리 파라미터 간단하게 조회
		System.out.println("username = " + username);

		response.setContentType("text/plain");//헤더에 들어갈 정보
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("hello" + username);
	}
}
