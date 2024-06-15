package com.loscuchurrumines.step;
import org.junit.Before;
import org.mockito.Mockito;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.loscuchurrumines.controller.ParticiparController;
import com.loscuchurrumines.model.Persona;
import com.loscuchurrumines.model.Proyecto;
public class Paticipar2StepDefinitios {
    private static final Logger logger = Logger.getLogger(ParticiparStepDefinitions.class.getName());

    private ParticiparController participarController;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;
    private Persona persona;
    private Proyecto proyecto;
    private List<Integer> modalidades;

    @Before
    public void setUp() {
        participarController = new ParticiparController();

        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);

        // Ensure request returns the mock session
        Mockito.when(request.getSession()).thenReturn(session);
    }

    @Given("una persona con el id de usuario {int} y un proyecto con el id {int}")
    public void una_persona_con_el_id_de_usuario_y_un_proyecto_con_el_id(Integer userId, Integer proyectoId) {
        logger.info("Given: Datos de la persona y el proyecto");

        persona = new Persona();
        persona.setFkUser(userId);

        proyecto = new Proyecto();
        proyecto.setIdProyecto(proyectoId);

        modalidades = new ArrayList<>();
        modalidades.add(1);
        modalidades.add(2);

        // Configure session and request mocks
        Mockito.when(session.getAttribute("persona")).thenReturn(persona);
        Mockito.when(request.getParameter("id")).thenReturn(proyectoId.toString());
        Mockito.when(request.getParameter("modalidad")).thenReturn("1");
        Mockito.when(request.getRequestDispatcher("/Views/Proyecto/formularioParticipar.jsp")).thenReturn(requestDispatcher);
    }

    @When("el usuario solicita participar en el proyecto")
    public void el_usuario_solicita_participar_en_el_proyecto() throws ServletException, IOException {
        logger.info("When: El usuario solicita participar en el proyecto");
        participarController.doGet(request, response);
    }

    @When("el usuario envía la solicitud de participación")
    public void el_usuario_envía_la_solicitud_de_participación() throws ServletException, IOException {
        logger.info("When: El usuario envía la solicitud de participación");
        participarController.doPost(request, response);
    }

    @Then("el sistema debe redirigir al detalle del proyecto con id {int}")
    public void el_sistema_debe_redirigir_al_detalle_del_proyecto_con_id(Integer proyectoId) throws IOException {
        logger.info("Then: El sistema debe redirigir al detalle del proyecto");
        Mockito.verify(response).sendRedirect("proyecto?vista=detalleProyecto&id=" + proyectoId);
    }
}
