package infoartex.artex.componentes;

import java.io.File;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.reportes.ControladorJasperReporter;
import infoartex.artex.reportes.ReporteOrdenMantenimiento;
import infoartex.artex.vistas.Administrar;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;



@SuppressWarnings("serial")
public class VerDatosMantenimientosPlanificado extends ComponenteGenerico {
	
private final CustomLayout main;
    
    //entidad a mostrar
    private Mantenimiento mantenimiento;

    public VerDatosMantenimientosPlanificado(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
        mantenimiento=new Mantenimiento();
	}
    public void initComponents(final Administrar view) {
    	main.removeAllComponents();
    	Label nombreFormulario=new Label("Ver datos del mantenimiento planificado");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos del mantenimiento programado");
        datosBasico.setSizeUndefined();
        final FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label numero=new Label("Número: "+ mantenimiento.getNo());
		Label mes=new Label("Mes: "+ mantenimiento.getMes());
		Label departamento=new Label("Departamento: "+ mantenimiento.getDepartamento().getNombre());
		Label inventario=new Label("Inventario: "+ mantenimiento.getInventario());
		Label equipo=new Label("Equipo: "+ mantenimiento.getEquipo());
		formularioBasico.addComponents(inventario,numero,mes,departamento,equipo);
		inventario.setStyleName("label label-info");
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		final NativeButton descargar = new NativeButton("Descargar");
		descargar.setIcon(FontAwesome.FILE_PDF_O);
		descargar.setStyleName("btn btn-primary");
		final FileDownloader down = new FileDownloader(new FileResource(new File("")));
		final NativeButton acta = new NativeButton("Generar Acta", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
						String urlReport = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/classes/infoartex/artex/reportes/";
						ControladorJasperReporter reporter = new ControladorJasperReporter("");
						ReporteOrdenMantenimiento acta1 = new ReporteOrdenMantenimiento(mantenimiento);
						reporter.generarReportePDF(urlReport + "OrdenMantenimiento-" + mantenimiento.getNo() + ".pdf", acta1.getJasperPrint());
						FileResource st = new FileResource(new File(urlReport + "OrdenMantenimiento-" + mantenimiento.getNo() + ".pdf"));
						down.setFileDownloadResource(st);
						down.extend(descargar);
						Label documento=new Label("OrdenMantenimiento-" + mantenimiento.getNo() + ".pdf");
						documento.setStyleName("text text-primary");
						HorizontalLayout hor=new HorizontalLayout();
						hor.setSpacing(true);
						hor.addComponents(descargar,documento);
						hor.setComponentAlignment(documento, Alignment.MIDDLE_LEFT);
						formularioBasico.addComponent(hor);
						formularioBasico.removeComponent(event.getButton());
					
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		acta.setStyleName("btn btn-primary");
		acta.setIcon(FontAwesome.DOWNLOAD);
		formularioBasico.addComponent(acta);
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.mantenimiento=(Mantenimiento)entidad;
	}
	
    
    
    
    
}
