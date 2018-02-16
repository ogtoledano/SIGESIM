package infoartex.artex.componentes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import infoartex.artex.bundles.ConfirmDialog;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.bundles.Paginador;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ListaPaginada<T> extends ComponenteGenerico{

	protected VerticalLayout main;
	protected Paginador<T> paginador;
	protected Table tabla;
	protected Class<?> clazz;
	protected List<String> columnas;
	protected List<String> campos;
	protected HorizontalLayout controles;
	protected NativeButton ctrLeft1;
	protected NativeButton ctrLeft2;
	protected NativeButton ctrRigth1;
	protected NativeButton ctrRigth2;
	protected Label paginas;
	protected HorizontalLayout buscador;
	protected int size;
	protected int tuplas;
	protected Label msg;
	protected ComponenteGenerico editorEntidad;
	protected ComponenteGenerico verEntidad;
	protected final ComboBox cantidadXPaginas;
	protected final TextField criterio;
	protected HorizontalLayout options;
	protected HashMap<String, String> filtros;
	
	public ListaPaginada(final CustomComponent view,final int size,final int tuplas,final Class<?> clazz) {
		super(view);
		main=new VerticalLayout();
		paginador=new Paginador<>(size, tuplas, clazz);
		tabla=new Table();
		tabla.setWidth("100%");
		tabla.setColumnCollapsingAllowed(true);
		this.clazz=clazz;
		campos=new LinkedList<>();
		controles=new HorizontalLayout();
		criterio=new TextField();
		buscador=new HorizontalLayout();
		this.size=size;
		this.tuplas=tuplas;
		msg=new Label("No hay elementos en esta vista");
		msg.setStyleName("text text-danger");
		cantidadXPaginas=new ComboBox();
		cantidadXPaginas.addItem("10");
		cantidadXPaginas.addItem("20");
		cantidadXPaginas.addItem("40");
		cantidadXPaginas.addItem("80");
		cantidadXPaginas.addItem("100");
		cantidadXPaginas.setNullSelectionAllowed(false);
		cantidadXPaginas.setValue("10");
		cantidadXPaginas.setImmediate(true);
		cantidadXPaginas.setTextInputAllowed(false);
		this.filtros=new HashMap<>();
		cantidadXPaginas.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				main.removeAllComponents();
				buscador.removeAllComponents();
				controles.removeAllComponents();
				if(!paginador.isBuscar()){
				paginador=new Paginador<>(Integer.parseInt(""+cantidadXPaginas.getValue()), tuplas, clazz);
				}else{
					paginador=new Paginador<>(Integer.parseInt(""+cantidadXPaginas.getValue()),tuplas,criterio.getValue() ,campos, filtros,clazz);
				}
				setSize(Integer.parseInt(""+cantidadXPaginas.getValue()));
				initComponent(view);
			}
		});
		columnas=new LinkedList<String>();
	}
	
	public void addCampos(String campo){
		campos.add(campo);
	}
	
	public void setEditorEntidad(ComponenteGenerico editor){
		this.editorEntidad=editor;
	}
	
	public void setVerEntidad(ComponenteGenerico visor){
		this.verEntidad=visor;
	}
	
	

	public HashMap<String, String> getFiltros() {
		return filtros;
	}

	public void setFiltros(HashMap<String, String> filtros) {
		this.filtros = filtros;
	}

	public void initComponent(final CustomComponent view) {
		try {
			options=new HorizontalLayout();
			criterio.setStyleName("form-control");
			criterio.setInputPrompt("Criterio de búsqueda...");
			NativeButton buscar=new NativeButton("",new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						cambiarModoPaginador(criterio.getValue());
						if(paginador.getElementos().size()!=0){
							main.removeComponent(tabla);
							main.removeComponent(controles);
							main.removeComponent(msg);
							cargarDatos(view);
							actualizarControles();
							main.addComponents(tabla,controles);
							//main.setComponentAlignment(buscador, Alignment.MIDDLE_RIGHT);
							main.setComponentAlignment(controles, Alignment.MIDDLE_CENTER);
							main.setSpacing(true);
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
			});
			buscar.setClickShortcut(KeyCode.ENTER, null);
			buscar.setStyleName("btn btn-primary botonControl");
			buscar.setIcon(FontAwesome.SEARCH);
			buscador.addComponents(criterio,buscar);
			buscador.setComponentAlignment(criterio, Alignment.MIDDLE_RIGHT);
			buscador.setComponentAlignment(buscar, Alignment.MIDDLE_RIGHT);
			options.addComponents(cantidadXPaginas,buscador);
			options.setComponentAlignment(buscador, Alignment.MIDDLE_RIGHT);
			options.setComponentAlignment(cantidadXPaginas, Alignment.MIDDLE_LEFT);
			options.setSpacing(true);
			options.setWidth("100%");
			if(paginador.getElementos().size()!=0){
				cambiarHeaderTabla();
				cargarDatos(view);
				cargarControles();
				main.addComponents(options,tabla,controles);
				main.setComponentAlignment(controles, Alignment.MIDDLE_CENTER);
				main.setSpacing(true);
			}else{
				main.addComponents(options,msg);
				main.setSpacing(true);
			}
			setCompositionRoot(main);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cambiarHeaderTabla(){
		if(columnas.isEmpty()){
			for(Field reflect:clazz.getDeclaredFields()){
				if(campos.indexOf(reflect.getName())!=-1){
					tabla.addContainerProperty(reflect.getName(),String.class,null);
				}
			}
		}else{
			for(String columna:columnas){
				tabla.addContainerProperty(columna,String.class,null);
			}
		}
		tabla.addContainerProperty("Opciones", HorizontalLayout.class,null);
	}
	
	public void cargarDatos(final CustomComponent view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (paginador.devolverElementosActuales().size() == 0) {
			paginador.pagAnterior();
		}
		
		List<T> aux=paginador.devolverElementosActuales();
		Field[] fields=clazz.getDeclaredFields();
		tabla.removeAllItems();
		for(final T objeto:aux){
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
												Notificaciones.NotificarError("La entidad de tipo "+clazz.getSimpleName()+" no se puede eliminar porque está siendo utilizada por otra entidad");
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
			editar.setIcon(FontAwesome.PENCIL);
			eliminar.setIcon(FontAwesome.TRASH);
			opciones.addComponents(ver,editar,eliminar);
			obj[i]=opciones;	
			tabla.addItem(obj,((Entidad)objeto).getId());
		}
		tabla.setPageLength(tabla.size());
	}
	
	public void cargarControles(){
		paginas=new Label();
		
		ctrLeft1 = new NativeButton("");
		ctrLeft1.setIcon(FontAwesome.BACKWARD);
		ctrLeft1.setStyleName("botonesOpcionesTabla "+ValoTheme.BUTTON_BORDERLESS);

		ctrLeft1.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				paginador.irAlPrincipio();
				
				try {
					cargarDatos(view);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				actualizarControles();
			}
		});

		ctrLeft2 = new NativeButton("");
		ctrLeft2.setIcon(FontAwesome.CHEVRON_LEFT);
		ctrLeft2.setStyleName("botonesOpcionesTabla");
		ctrLeft2.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				paginador.pagAnterior();
				try {
					cargarDatos(view);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				actualizarControles();
			}
		});

		ctrRigth1 = new NativeButton("");
		ctrRigth1.setIcon(FontAwesome.FORWARD);
		ctrRigth1.setStyleName("botonesOpcionesTabla "+ValoTheme.BUTTON_BORDERLESS);
		ctrRigth1.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				paginador.irAlFinal();
				try {
					cargarDatos(view);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				actualizarControles();
			}
		});

		ctrRigth2 = new NativeButton("");
		ctrRigth2.setIcon(FontAwesome.CHEVRON_RIGHT);
		ctrRigth2.setStyleName("botonesOpcionesTabla");
		ctrRigth2.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				paginador.pagSiguiente();
				try {
					cargarDatos(view);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				actualizarControles();
			}
		});
		ctrLeft2.setClickShortcut(KeyCode.ARROW_LEFT, null);
		ctrRigth2.setClickShortcut(KeyCode.ARROW_RIGHT, null);
		controles.addComponent(ctrLeft1);
		controles.addComponent(ctrLeft2);
		controles.addComponent(paginas);
		controles.addComponent(ctrRigth2);
		controles.addComponent(ctrRigth1);

		actualizarControles();
	}
	
	public void actualizarControles(){
		paginas.setValue(paginador.getCurrentPage() + " de "
				+ paginador.getCantPaginas());
		
		if (paginador.getCurrentPage() == 1
				&& paginador.getCurrentPage() == paginador
						.getCantPaginas()||paginador.devolverElementosActuales().size()==0) {
			ctrLeft1.setEnabled(false);
			ctrLeft2.setEnabled(false);
			ctrRigth1.setEnabled(false);
			ctrRigth2.setEnabled(false);
		} else {
			if (paginador.getCurrentPage() == 1) {
				ctrLeft1.setEnabled(false);
				ctrLeft2.setEnabled(false);
				ctrRigth1.setEnabled(true);
				ctrRigth2.setEnabled(true);
			} else {
				if (paginador.getCurrentPage() ==paginador
						.getCantPaginas()) {
					ctrLeft1.setEnabled(true);
					ctrLeft2.setEnabled(true);
					ctrRigth1.setEnabled(false);
					ctrRigth2.setEnabled(false);
				} else {
					ctrLeft1.setEnabled(true);
					ctrLeft2.setEnabled(true);
					ctrRigth1.setEnabled(true);
					ctrRigth2.setEnabled(true);
				}
			}
		}
	}
	
	public void cambiarModoPaginador(String criterio){
		paginador=new Paginador<>(size, tuplas, criterio, campos,filtros, clazz);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void addColumnas(String... columna){
		for(String c:columna){
			columnas.add(c);
		}
	}
	
	
}