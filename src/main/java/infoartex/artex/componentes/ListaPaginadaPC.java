package infoartex.artex.componentes;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import infoartex.artex.bundles.ConfirmDialog;
import infoartex.artex.bundles.EstructuraUnion;
import infoartex.artex.bundles.Paginador;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Laptop;
import infoartex.artex.dominio.PC;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class ListaPaginadaPC extends ListaPaginada<PC>{

	private ComboBox filtroDepartamento;
	private Label filtroEstadoCaption;
	
	private List<EstructuraUnion> columnasUnion;
	private HashMap<String, String> filtro;
	
	public ListaPaginadaPC(CustomComponent view, int size, int tuplas,
			Class<?> clazz) {
		super(view, size, tuplas, clazz);
		filtroDepartamento=new ComboBox();
		filtroEstadoCaption=new Label("Filtrar por departamento");
		columnasUnion=new LinkedList<>();
		filtro=new HashMap<>();
	}
	
	public void initComponent(final CustomComponent view) {
		super.initComponent(view);
		filtroDepartamento.addItem("Todos");
		cargarDepartamentos(filtroDepartamento);
		filtroDepartamento.setNullSelectionItemId("Todos");
		filtroDepartamento.setImmediate(true);
		filtroDepartamento.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				main.removeAllComponents();
				buscador.removeAllComponents();
				controles.removeAllComponents();
				String crite=criterio.getValue()!=null?criterio.getValue():"";
				if(filtroDepartamento.getValue()!=null){
				
				filtro.remove("departamento");
				filtro.put("departamento", filtroDepartamento.getValue().toString());
				EstructuraUnion estruct = new EstructuraUnion("departamento");
				estruct.adicionarAtributo("nombre");
				if (columnasUnion.indexOf(estruct) == -1) {
					columnasUnion.add(estruct);
				}
				paginador=new Paginador<>(Integer.parseInt(""+cantidadXPaginas.getValue()),tuplas,crite ,campos,filtro,columnasUnion, clazz);
				}else{
					filtro.remove("departamento");
					paginador=new Paginador<>(Integer.parseInt(""+cantidadXPaginas.getValue()),tuplas,crite ,campos, clazz);
				}
				initComponent(view);
			}
		});
		options.addComponent(filtroDepartamento, 1);
		options.addComponent(filtroEstadoCaption, 1);
		options.setComponentAlignment(filtroEstadoCaption, Alignment.MIDDLE_CENTER);
	}
	
	public void cargarDatos(final CustomComponent view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (paginador.devolverElementosActuales().size() == 0) {
			paginador.pagAnterior();
		}
		
		List<PC> aux=paginador.devolverElementosActuales();
		Field[] fields=clazz.getDeclaredFields();
		tabla.removeAllItems();
		for(final PC objeto:aux){
			Object [] obj=new Object[campos.size()+3];
			int i=0;
			for(Field f:fields){
				if(campos.indexOf(f.getName())!=-1){
					obj[i++]=f.get(objeto)+"";
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
			NativeButton verMantenimientos= new NativeButton("Ver mantenimientos planifiados", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						MantenimientosPC mante=new MantenimientosPC((Administrar)view, objeto);
						mante.initComponent();
						Window win=new Window("Datos generales");
						win.setContent(mante);
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
			verMantenimientos.setStyleName("botonesOpcionesTabla");
			verMantenimientos.setIcon(FontAwesome.GEAR);
			editar.setIcon(FontAwesome.PENCIL);
			eliminar.setIcon(FontAwesome.TRASH);
			opciones.addComponents(ver,editar,verMantenimientos,eliminar);
			Label tipoPC=new Label("");
			tipoPC.setStyleName("botonesOpcionesTabla");
			if(objeto instanceof Laptop){
				tipoPC.setValue("Laptop");
			}else{
				tipoPC.setValue("PC Escritorio");	
			}
			obj[i]=objeto.getDepartamento().getNombre();
			obj[i+1]=tipoPC;
			obj[i+2]=opciones;	
			tabla.addItem(obj,((Entidad)objeto).getId());
		}
		tabla.setPageLength(tabla.size());
	}
	
	private List<Departamento> cargarDepartamentos(ComboBox departamentos){
		@SuppressWarnings("unchecked")
		List<Departamento> lista=(List<Departamento>) getFachada().listarTodos(Departamento.class);
		for(Departamento d:lista){
			departamentos.addItem(d.getNombre());
		}
		return lista;
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
		tabla.addContainerProperty("Nombre del departamento", String.class,null);
		tabla.addContainerProperty("Tipo PC", Label.class,null);
		tabla.addContainerProperty("Opciones", HorizontalLayout.class,null);
	}

}
