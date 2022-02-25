package test.askForHelp;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import entities.Person;
import servelets.askForHelp.AskForHelpServlet;

import javax.servlet.http.*;

//before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class AskForHelpServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private AskForHelpServlet servlet;
	private SessionBean SessionBean;
	private Cookie[] requestCookies;
	private long id;

	@BeforeEach
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new AskForHelpServlet();
		SessionBean = new SessionBean();
		id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}

	// correct page request
	@Test
	public void testGet1() throws Exception {
		when(request.getCookies()).thenReturn(requestCookies);
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/askForHelp/ask_for_help.jsp");
	}
	
	// insert with no subject
	@Test
	public void testPost1() throws Exception {
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("Subject")).thenReturn("");
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/askForHelp/ask_for_help.jsp");
		
	}
	
	// good insert
	@Test
	public void testPost2() throws Exception {
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("Subject")).thenReturn("TestSubject");
		when(request.getParameter("Problems")).thenReturn("TestProblems");
		
		servlet.doPost(request, response);
		
		verify(response, times(1)).sendRedirect("home.do?goodInsert");
		
	}
}