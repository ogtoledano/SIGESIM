package infoartex.artex.componentes;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class Ajustes extends ComponenteGenerico{
	
	private final CustomLayout container;
    private final VerticalLayout main;
    
	public Ajustes(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
        initComponents(view);
	}

	public void initComponents(final Administrar view) {
		WebBrowser brow=Page.getCurrent().getWebBrowser();
		Panel datosBasico = new Panel("Información del host");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        HorizontalLayout schema=new HorizontalLayout();
        Label nav=new Label("Nevegador web: ");
        Label navValor=new Label("Desconocido");
        Label navIcon=new Label();
		if(brow.isChrome()){
			navValor.setValue("Chrome");
			navIcon.setIcon(FontAwesome.CHROME);
		}
		if(brow.isFirefox()){
			navValor.setValue("Mozilla Firefox");
			navIcon.setIcon(FontAwesome.FIREFOX);
		}
		if(brow.isOpera()){
			navValor.setValue("Opera");
			navIcon.setIcon(FontAwesome.OPERA);
		}
		if(brow.isIE()){
			navValor.setValue("Internet Explorer");
			navIcon.setIcon(FontAwesome.INTERNET_EXPLORER);
		}
		if(brow.isSafari()){
			navValor.setValue("Safari");
			navIcon.setIcon(FontAwesome.SAFARI);   
		}
        
        nav.setStyleName("label label-info");
        schema.addComponents(nav,navIcon,navValor);
        
        Label ip=new Label("Dirección IP");
        ip.setStyleName("label label-info");
        Label ipValor=new Label(brow.getAddress());
        Label ipIcon=new Label();
        ipIcon.setIcon(FontAwesome.DESKTOP);
        HorizontalLayout schema2=new HorizontalLayout();
        schema2.addComponents(ip,ipIcon,ipValor);
        
        Label so=new Label("Sistema operativo: ");
        so.setStyleName("label label-info");
        Label soValor=new Label("Desconocido");
        Label soIcon=new Label();
        if(brow.isAndroid()){
			soValor.setValue("Android");
			soIcon.setIcon(FontAwesome.ANDROID);
		}
		if(brow.isLinux()){
			soValor.setValue("Linux");
			soIcon.setIcon(FontAwesome.LINUX);
		}
		if(brow.isWindows()){
			soValor.setValue("Windows");
			soIcon.setIcon(FontAwesome.WINDOWS);
		}
		if(brow.isMacOSX()){
			soValor.setValue("Max OSX");
			soIcon.setIcon(FontAwesome.APPLE);
		}

		HorizontalLayout schema3=new HorizontalLayout();
		schema3.addComponents(so,soIcon,soValor);
		schema3.setSpacing(true);
		schema2.setSpacing(true);
		schema.setSpacing(true);
        datosBasico.setWidth("100%");
        
        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                view.contenidoAnterior();
            }
        });
        cancelar.setIcon(FontAwesome.CLOSE);
        cancelar.setStyleName("btn btn-danger botonControl");
        formularioBasico.addComponents(schema2,schema,schema3);
        formularioBasico.setSpacing(true);
        datosBasico.setContent(formularioBasico);
        Label nombreForm=new Label("Ajustes");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        main.addComponents(datosBasico,new ConfigurarCorreo(view),new ConfigurarGlobal(view),new ListarUsuario(view),cancelar);
        main.setSpacing(true);
        container.addComponent(main, "formulario");
        setCompositionRoot(container);
	}

}
