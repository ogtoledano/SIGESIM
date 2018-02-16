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
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import infoartex.artex.bundles.ConfirmDialog;
import infoartex.artex.bundles.EstructuraUnion;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.bundles.Paginador;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.TipoMedio;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class ListaPaginadaMediosInformaticos extends ListaPaginada<MedioInformatico> {

	private ComboBox filtroDepartamento;
	private Label filtroDepartamentoCaption;

	private ComboBox filtroTipoMedio;
	private Label filtroTipoMedioCaption;

	private TextField filtroMarca;

	private TextField filtroModelo;

	private List<EstructuraUnion> columnasUnion;
	
	private HashMap<String, String> filtro;

	public ListaPaginadaMediosInformaticos(CustomComponent view, int size,
			int tuplas, Class<?> clazz) {
		super(view, size, tuplas, clazz);
		filtroDepartamento = new ComboBox();
		filtroDepartamentoCaption = new Label("Filtrar por departamento");
		filtroTipoMedio = new ComboBox();
		filtroTipoMedioCaption = new Label("Filtrar por tipo de medio");
		filtroMarca = new TextField();
		filtroMarca.setInputPrompt("Marca");
		filtroModelo = new TextField();
		filtroModelo.setInputPrompt("Modelo");
		this.columnasUnion = new LinkedList<>();
		filtro = new HashMap<>();
	}

	public void initComponent(final CustomComponent view) {
		super.initComponent(view);
		filtroDepartamento.addItem("Todos");
		cargarDepartamentos(filtroDepartamento);
		filtroDepartamento.setNullSelectionItemId("Todos");
		filtroDepartamento.setImmediate(true);

		filtroTipoMedio.addItem("Todos");
		cargarTipomedio(filtroTipoMedio);
		filtroTipoMedio.setNullSelectionItemId("Todos");
		filtroTipoMedio.setImmediate(true);
		filtroDepartamento.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				main.removeAllComponents();
				buscador.removeAllComponents();
				controles.removeAllComponents();
				String crite = criterio.getValue() != null ? criterio.getValue() : "";
				if (filtroDepartamento.getValue() != null) {
					filtro.remove("departamento");
					filtro.put("departamento", filtroDepartamento.getValue().toString());
					EstructuraUnion estruct = new EstructuraUnion("departamento");
					estruct.adicionarAtributo("nombre");
					if (columnasUnion.indexOf(estruct) == -1) {
						columnasUnion.add(estruct);
					}
					paginador = new Paginador<>(Integer.parseInt("" + cantidadXPaginas.getValue()), tuplas, crite, campos, filtro, columnasUnion, clazz);
				} else {
					filtro.remove("departamento");
					paginador = new Paginador<>(Integer.parseInt("" + cantidadXPaginas.getValue()), tuplas, crite, campos, filtro,columnasUnion, clazz);
				}
				initComponent(view);
			}
		});

		filtroTipoMedio.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				main.removeAllComponents();
				buscador.removeAllComponents();
				controles.removeAllComponents();
				String crite = criterio.getValue() != null ? criterio.getValue() : "";
				if (filtroTipoMedio.getValue() != null) {
					filtro.remove("tipo");
					filtro.put("tipo", filtroTipoMedio.getValue().toString());
					paginador = new Paginador<>(Integer.parseInt("" + cantidadXPaginas.getValue()), tuplas, crite, campos, filtro,columnasUnion, clazz);
				} else {
					filtro.remove("tipo");
					paginador = new Paginador<>(Integer.parseInt("" + cantidadXPaginas.getValue()), tuplas, crite, campos, filtro,columnasUnion, clazz);
				}
				initComponent(view);
			}
		});

		final HorizontalLayout filtrosBus = new HorizontalLayout();
		filtrosBus.setSpacing(true);
		filtrosBus.addComponent(filtroDepartamento);
		filtrosBus.addComponent(filtroDepartamentoCaption, 0);
		filtrosBus.addComponent(filtroTipoMedio, 0);
		filtrosBus.addComponent(filtroTipoMedioCaption, 0);
		filtrosBus.setComponentAlignment(filtroDepartamentoCaption, Alignment.MIDDLE_LEFT);
		filtrosBus.setComponentAlignment(filtroTipoMedioCaption, Alignment.MIDDLE_LEFT);
		filtrosBus.setWidth("100%");
		main.addComponent(filtrosBus, 1);

	}

	public void cargarDatos(final CustomComponent view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (paginador.devolverElementosActuales().size() == 0) {
			paginador.pagAnterior();
		}

		List<MedioInformatico> aux = paginador.devolverElementosActuales();
		Field[] fields = clazz.getDeclaredFields();
		tabla.removeAllItems();
		for (final MedioInformatico objeto : aux) {
			Object[] obj = new Object[campos.size() + 2];
			int i = 0;
			for (Field f : fields) {
				if (campos.indexOf(f.getName()) != -1) {
					obj[i++] = f.get(objeto) + "";
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
										if (objeto.getComputadora() == null) {
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
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										} else {
											Notificaciones.NotificarError("No se puede eliminar un medio asociado a una PC,\ndebes editar primero la PC (" + objeto.getComputadora().getNombre() + ") para quitarle dicho medio");
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
			NativeButton trasladar = new NativeButton("Trasladar medio", new Button.ClickListener() {

				@Override
				public void buttonClick(Button.ClickEvent event) {
					TrasladarMedio trasladar = new TrasladarMedio((Administrar) view, objeto);
					((Administrar) view).nuevoContenido(trasladar);
				}
			});
			trasladar.setIcon(FontAwesome.ARROWS);
			trasladar.setStyleName("botonesOpcionesTabla");
			ver.setStyleName("botonesOpcionesTabla");
			ver.setIcon(FontAwesome.EYE);
			editar.setIcon(FontAwesome.PENCIL);
			eliminar.setIcon(FontAwesome.TRASH);
			opciones.addComponents(ver, editar, trasladar, eliminar);
			obj[i] = objeto.getDepartamento().getNombre();
			obj[i + 1] = opciones;
			tabla.addItem(obj, ((Entidad) objeto).getId());
		}
		tabla.setPageLength(tabla.size());
	}

	private List<Departamento> cargarDepartamentos(ComboBox departamentos) {
		@SuppressWarnings("unchecked")
		List<Departamento> lista = (List<Departamento>) getFachada().listarTodos(Departamento.class);
		for (Departamento d : lista) {
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}

	private List<TipoMedio> cargarTipomedio(ComboBox tipomedios) {
		@SuppressWarnings("unchecked")
		List<TipoMedio> lista = (List<TipoMedio>) getFachada().listarTodos(TipoMedio.class);
		for (TipoMedio d : lista) {
			tipomedios.addItem(d.getDenominacion());
		}
		return lista;
	}

	public void cambiarHeaderTabla() {
		if (columnas.isEmpty()) {
			for (Field reflect : clazz.getDeclaredFields()) {
				if (campos.indexOf(reflect.getName()) != -1) {
					tabla.addContainerProperty(reflect.getName(), String.class, null);
				}
			}
		} else {
			for (String columna : columnas) {
				tabla.addContainerProperty(columna, String.class, null);
			}
		}
		tabla.addContainerProperty("Nombre del departamento", String.class, null);
		tabla.addContainerProperty("Opciones", HorizontalLayout.class, null);
	}

}
