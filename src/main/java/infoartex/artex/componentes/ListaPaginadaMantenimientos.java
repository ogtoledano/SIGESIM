package infoartex.artex.componentes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import infoartex.artex.bundles.ConfirmDialog;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.ReporteMantenimiento;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class ListaPaginadaMantenimientos extends ListaPaginada<Mantenimiento>{

	public ListaPaginadaMantenimientos(CustomComponent view, int size, int tuplas, Class<?> clazz) {
		super(view, size, tuplas, clazz);
		// TODO Auto-generated constructor stub
	}
	
	public void cargarDatos(final CustomComponent view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (paginador.devolverElementosActuales().size() == 0) {
			paginador.pagAnterior();
		}
		
		List<Mantenimiento> aux=paginador.devolverElementosActuales();
		Field[] fields=clazz.getDeclaredFields();
		tabla.removeAllItems();
		for(final Mantenimiento objeto:aux){
			Object [] obj=new Object[campos.size()+1];
			int i=0;
			for(Field f:fields){
				if(campos.indexOf(f.getName())!=-1){
					if(f.getType()==java.util.Date.class){
						SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
						obj[i++]=format.format(f.get(objeto))+"";
					}else{
					obj[i++]=f.get(objeto)+"";
					}
				}
			}
			HorizontalLayout opciones=new HorizontalLayout();
			opciones.setWidthUndefined();
			NativeButton eliminar= new NativeButton("Eliminar", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
						ConfirmDialog dialogo=ConfirmDialog.show(getUI(), "Ejecutar acción",
				                "<h4>¿Desea realmente eliminar el elemento selecionado?</h4>",
				                "Aceptar", "Cancelar", new ConfirmDialog.Listener(
				                		) {
									
									@Override
									public void onClose(ConfirmDialog dialog) {
										if(dialog.isConfirmed()){
											try {
											fachada.eliminarEntidad(((Entidad)objeto).getId(),clazz);
											paginador.actualizarCuandoElimina();
											if(paginador.getElementos().size()!=0){
											cargarDatos(view);
											actualizarControles();
											}else{
												main.removeComponent(tabla);
												main.removeComponent(controles);
												main.addComponent(msg);
											}
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
								});	
						dialogo.setStyleName(Reindeer.WINDOW_LIGHT);
						dialogo.setContentMode(ConfirmDialog.ContentMode.HTML);
						dialogo.setHeight("16em");
						dialogo.getCancelButton().setStyleName("btn btn-danger");
						dialogo.getOkButton().setStyleName("btn btn-info");
				}
			});
			eliminar.setStyleName("botonesOpcionesTabla");
			NativeButton editar= new NativeButton("Editar", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Class<?>clase=editorEntidad.getClass();
					try {
						clase.getDeclaredMethod("setEntidad", Entidad.class).invoke(editorEntidad,(Entidad)objeto);
						clase.getDeclaredMethod("initComponents",Administrar.class).invoke(editorEntidad, (Administrar)view);
						((Administrar)view).nuevoContenido(editorEntidad);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			});

			editar.setStyleName("botonesOpcionesTabla");
			
			NativeButton ver= new NativeButton("Ver", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Class<?>clase=verEntidad.getClass();
					try {
						clase.getDeclaredMethod("setEntidad", Entidad.class).invoke(verEntidad,(Entidad)objeto);
						clase.getDeclaredMethod("initComponents",Administrar.class).invoke(verEntidad, (Administrar)view);
						Window win=new Window("Datos generales");
						win.setContent(verEntidad);
						win.center();
						win.setResizable(false);
						win.setImmediate(true);
						win.setModal(true);
						win.setWidth("650px");
						getUI().addWindow(win);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			});
			ver.setStyleName("botonesOpcionesTabla");
			ver.setIcon(FontAwesome.EYE);
			
			NativeButton cumplir= new NativeButton("Cumplir mantenimiento", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					RegistrarMantenimientoEfectuado manteniEf=new RegistrarMantenimientoEfectuado((Administrar) view);
					ReporteMantenimiento reporteNew=new ReporteMantenimiento();
					reporteNew.setInventario(objeto.getInventario());
					reporteNew.setFecha(new Date());
					reporteNew.setMantenimiento(objeto);
					reporteNew.setTipomedio(objeto.getEquipo());
					manteniEf.setCumplirMante(true);
					manteniEf.setMantenimiento(reporteNew,objeto);
					manteniEf.initComponents((Administrar) view);
					((Administrar) view).nuevoContenido(manteniEf);
				}
			});
			cumplir.setStyleName("botonesOpcionesTabla");
			cumplir.setIcon(FontAwesome.GEARS);
			editar.setIcon(FontAwesome.PENCIL);
			eliminar.setIcon(FontAwesome.TRASH);
			opciones.addComponents(ver,editar,cumplir,eliminar);
			if(objeto.getEstado().equals("Cumplido")){
				opciones.removeComponent(cumplir);
			}
			obj[i]=opciones;	
			tabla.addItem(obj,((Entidad)objeto).getId());
		}
		tabla.setPageLength(tabla.size());
	}

}
