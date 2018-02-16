package infoartex.artex;

import infoartex.artex.bundles.MyPasswordEncrypt;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Rol;
import infoartex.artex.dominio.Sesion;
import infoartex.artex.dominio.TipoMedio;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.fachada.Facade;
import infoartex.artex.fachada.impl.FacadeImpl;
import infoartex.artex.vistas.Administrar;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@JavaScript({"../../VAADIN/themes/mytheme/js/lumino.glyphs.js",
	"../../VAADIN/themes/mytheme/js/jquery-2.0.0.min.js",
	"../../VAADIN/themes/mytheme/js/bootstrap.min.js"})

@Theme("mytheme")
@SuppressWarnings("serial")
@com.vaadin.annotations.PreserveOnRefresh

public class MyVaadinUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class)
    
    /**
     * Clase interna (innerClass) Servlet, se encarga de gestionar las sesiones
     * @author root
     *
     */
    
    public static class Servlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {

        @Override
        public void sessionInit(SessionInitEvent event) throws ServiceException {

        }

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(this);
            getService().addSessionDestroyListener(this);
        }

        @Override
        public void sessionDestroy(SessionDestroyEvent event) {
            String usuario = VaadinSession.getCurrent().getAttribute("usuario")
                    .toString();

            try {
               if (!usuario.equals("Invitado")) {
            	    Facade fachada=new FacadeImpl();
            	    fachada.cerrarSesion(usuario);
               }
            } catch (Exception e) {
               // e.printStackTrace();
            }
        }
    }
    /**
     * Metodo main, de vaadin
     */
    @Override
    protected void init(VaadinRequest request) {
        VaadinSession.getCurrent().getSession().setMaxInactiveInterval(300);
        setLocale(new Locale("es"));
        @SuppressWarnings("unused")
		Navigator navigator = new Navigator(this, this);
        VaadinSession.getCurrent().setAttribute("rol", "Invitado");
        VaadinSession.getCurrent().setAttribute("usuario", "Invitado");
        getNavigator().addView(Administrar.nombreVista, new Administrar());
        try {
			cargarDatos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
    }
    
    public void cargarDatos() throws Exception{
    	Facade fachada=new FacadeImpl();
    	if(fachada.cantidadElementos(Usuario.class)==0){
    		Rol adminRol=new Rol("Administrador","");
    		fachada.registrarEntidad(new Rol("Técnico",""));
    		fachada.registrarEntidad(new Rol("Almacenero",""));
    		Rol clienteRol=new Rol("Cliente","");
    		fachada.registrarEntidad(clienteRol);
    		Usuario admin=new Usuario("artex","artex","artex","artex@cuba.cu",MyPasswordEncrypt.encrypt("artex", "MD5", "UTF-8"),"Administrador",adminRol);
    		Usuario cliente=new Usuario("cliente","cliente","cliente","cliente@cuba.cu",MyPasswordEncrypt.encrypt("cliente", "MD5", "UTF-8"),"Cliente",clienteRol);
    		adminRol.getUsuarios().add(admin);
    		clienteRol.getUsuarios().add(cliente);
    		fachada.registrarEntidad(adminRol);
    		fachada.registrarEntidad(clienteRol);
    		fachada.registrarEntidad(new Sesion("artex"));
    		fachada.cerrarSesion("artex");
    		Departamento depTaller=new Departamento();
    		depTaller.setNombre("Taller de informática");
    		depTaller.setGerencia("Gerencia");
    		depTaller.setCcosto("00");
    		fachada.registrarEntidad(depTaller);
    		
    		//Tipos de medio
    		String [] tiposMedio={"Torre","Monitor","Mouse","Teclado","Bocinas","Scanner","Impresora","Webcam"};
    		for(String medio:tiposMedio){
    			fachada.registrarEntidad(new TipoMedio(medio));
    		}
    	}
    }

}
