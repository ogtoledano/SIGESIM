package infoartex.artex.componentes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
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
import infoartex.artex.bundles.Paginador;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class ListaPaginadaInterrupcion extends ListaPaginada<Interrupcion> {

	private ComboBox filtroEstado;
	private Label filtroEstadoCaption;

	public ListaPaginadaInterrupcion(CustomComponent view, int size,
			int tuplas, Class<?> clazz) {
		super(view, size, tuplas, clazz);
		filtroEstado = new ComboBox();
		filtroEstadoCaption = new Label("Filtrar por estado");
	}

	@Override
	public void initComponent(final CustomComponent view) {
		super.initComponent(view);
		filtroEstado.addItem("Todos");
		filtroEstado.addItem("Solucionada");
		filtroEstado.addItem("No solucionada");
		filtroEstado.addItem("Sin procesar");
		filtroEstado.addItem("Siendo procesada");
		filtroEstado.setNullSelectionItemId("Todos");
		filtroEstado.setImmediate(true);
		filtroEstado.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				main.removeAllComponents();
				buscador.removeAllComponents();
				controles.removeAllComponents();
				String crite = criterio.getValue() != null ? criterio.getValue() : "";
				if (filtroEstado.getValue() != null) {
					filtros.put("estado", filtroEstado.getValue().toString());
					paginador = new Paginador<>(Integer.parseInt("" + cantidadXPaginas.getValue()), tuplas, crite, campos, filtros, clazz);
				} else {
					paginador = new Paginador<>(Integer.parseInt("" + cantidadXPaginas.getValue()), tuplas, crite, campos, clazz);
				}
				paginador.setIsBuscar(true);
				initComponent(view);
			}
		});
		options.addComponent(filtroEstado, 1);
		options.addComponent(filtroEstadoCaption, 1);
		options.setComponentAlignment(filtroEstadoCaption, Alignment.MIDDLE_LEFT);
	}

	@Override
	public void cargarDatos(final CustomComponent view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (paginador.devolverElementosActuales().size() == 0) {
			paginador.pagAnterior();
		}

		List<Interrupcion> aux = paginador.devolverElementosActuales();
		Field[] fields = clazz.getDeclaredFields();
		tabla.removeAllItems();
		for (final Interrupcion objeto : aux) {
			Object[] obj = new Object[campos.size() + 2];
			int i = 0;
			for (Field f : fields) {
				if (campos.indexOf(f.getName()) != -1) {
					if (f.getType() == java.util.Date.class) {
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
						obj[i++] = format.format(f.get(objeto)) + "";
					} else {
						if (!f.getName().equals("estado")) {
							obj[i++] = f.get(objeto) + "";
						}
					}
				}
			}
			HorizontalLayout opciones = new HorizontalLayout();
			opciones.setWidthUndefined();
			NativeButton eliminar = new NativeButton("Eliminar", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {

					ConfirmDialog dialogo = ConfirmDialog.show(getUI(), "Ejecutar acción",
							"<h4>¿Desea realmente eliminar el elemento selecionado?</h4>",
							"Aceptar", "Cancelar", new ConfirmDialog.Listener(
							) {

								@Override
								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										try {
											fachada.eliminarEntidad(((Entidad) objeto).getId(), clazz);
											paginador.actualizarCuandoElimina();
											if (paginador.getElementos().size() != 0) {
												cargarDatos(view);
												actualizarControles();
											} else {
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
			NativeButton editar = new NativeButton("Editar", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					Class<?> clase = editorEntidad.getClass();
					try {
						clase.getDeclaredMethod("setEntidad", Entidad.class).invoke(editorEntidad, (Entidad) objeto);
						clase.getDeclaredMethod("initComponents", Administrar.class).invoke(editorEntidad, (Administrar) view);
						((Administrar) view).nuevoContenido(editorEntidad);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			editar.setStyleName("botonesOpcionesTabla");

			NativeButton ver = new NativeButton("Ver", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					Class<?> clase = verEntidad.getClass();
					try {
						clase.getDeclaredMethod("setEntidad", Entidad.class).invoke(verEntidad, (Entidad) objeto);
						clase.getDeclaredMethod("initComponents", Administrar.class).invoke(verEntidad, (Administrar) view);
						Window win = new Window("Datos generales");
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
			if (VaadinSession.getCurrent().getAttribute("rol").equals("Administrador")) {
				opciones.addComponents(ver, editar, eliminar);
			} else {
				if (((Interrupcion) objeto).getUsuario().equals(VaadinSession.getCurrent().getAttribute("usuario"))) {
					opciones.addComponents(ver, editar, eliminar);
				} else {
					opciones.addComponents(ver);
				}
			}
			/**
			 * Opciones adicionales de una interrupcion
			 */
			NativeButton procesar = new NativeButton("Procesar", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					int noOrden = getFachada().maximoID(OrdenTrabajo.class)+1;
					OrdenTrabajo orden = new OrdenTrabajo(noOrden, objeto.getFecha(), objeto.getInventario(), "No definido", "No definido", "No definidas", new Date(), objeto.getTrabajador());
					orden.setEstado("Abierta");
					objeto.setEstado("Siendo procesada");
					orden.setInterrupcion(objeto);
					getFachada().registrarEntidad(orden);
					objeto.setOrden(orden);
					try {
						getFachada().actualizarEntidad(objeto);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					((Administrar) view).nuevoContenido(new ListarInterrupciones((Administrar) view));
				}
			});
			NativeButton cambiarEstado = new NativeButton("Cambiar estado", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					Window win = new Window("Cambiar Estado");
					win.setContent(new CambiarEstadoInterrupcion((Administrar) view, win, objeto));
					win.center();
					win.setResizable(false);
					win.setImmediate(true);
					win.setModal(true);
					getUI().addWindow(win);
					((Administrar) view).nuevoContenido(new ListarInterrupciones((Administrar) view));
				}
			});
			NativeButton cerrar = new NativeButton("Cerrar interrupción", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					GenerarOrdenTrabajo generar = new GenerarOrdenTrabajo((Administrar) view);
					generar.setOrdenTrabajo(objeto.getOrden(), objeto);
					generar.initComponents((Administrar) view);
					((Administrar) view).nuevoContenido(generar);
				}
			});

			procesar.setStyleName("botonesOpcionesTabla");
			cambiarEstado.setStyleName("botonesOpcionesTabla");
			cerrar.setStyleName("botonesOpcionesTabla");
			procesar.setIcon(FontAwesome.GEARS);
			cambiarEstado.setIcon(FontAwesome.TOGGLE_ON);
			cerrar.setIcon(FontAwesome.CLOSE);
			if (VaadinSession.getCurrent().getAttribute("rol").equals("Técnico") || VaadinSession.getCurrent().getAttribute("rol").equals("Administrador")) {
				switch (objeto.getEstado()) {
				case "Sin procesar": {
					opciones.addComponent(procesar);
					break;
				}
				case "No solucionada": {
					opciones.addComponent(cambiarEstado);
					break;
				}
				case "Siendo procesada": {
					opciones.addComponent(cambiarEstado);
					opciones.addComponent(cerrar);
					break;
				}
				}
			}
			Label estado = new Label(objeto.getEstado());
			switch (objeto.getEstado()) {
			case "Sin procesar": {
				estado.setStyleName("label label-default");
				break;
			}

			case "Solucionada": {
				estado.setStyleName("label label-success");
				break;
			}

			case "No solucionada": {
				estado.setStyleName("label label-danger");
				break;
			}

			default: {
				estado.setStyleName("label label-info");
				break;
			}
			}
			obj[i] = objeto.getDepartamento().getNombre();
			obj[i + 1] = estado;
			obj[i + 2] = opciones;

			tabla.addItem(obj, ((Entidad) objeto).getId());
		}
		tabla.setPageLength(tabla.size());
	}

	@Override
	public void cambiarHeaderTabla() {
		if (columnas.isEmpty()) {
			for (Field reflect : clazz.getDeclaredFields()) {
				if (campos.indexOf(reflect.getName()) != -1) {
					if (!reflect.getName().equals("estado")) {
						tabla.addContainerProperty(reflect.getName(), String.class, null);
					}
				}
			}
		} else {
			for (String columna : columnas) {
				tabla.addContainerProperty(columna, String.class, null);
			}
		}
		tabla.addContainerProperty("Nombre del departamento", String.class, null);
		tabla.addContainerProperty("Estado", Label.class, null);
		tabla.addContainerProperty("Opciones", HorizontalLayout.class, null);
	}

}
