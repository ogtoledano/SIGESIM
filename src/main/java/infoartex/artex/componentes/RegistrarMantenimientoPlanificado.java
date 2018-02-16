package infoartex.artex.componentes;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.ConfiguracionGlobal;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Laptop;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.PC;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RegistrarMantenimientoPlanificado extends ComponenteGenerico {

	// obligado
	private final CustomLayout container;
	private final VerticalLayout main;
	private List<PC> listadoPC;
	public boolean semaforo;

	public RegistrarMantenimientoPlanificado(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		// usar formulario de los layouts que tiene el tema
		container = new CustomLayout("formulario");
		// metodo que arranca los componentes de vaadin
		listadoPC = new LinkedList<>();
		semaforo=true;
		initComponents(view);
	}

	private void initComponents(final Administrar view) {
		Panel datosBasico = new Panel("Rellene los siguientes campos: ");
		datosBasico.setSizeUndefined();
		final FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");

		final TextField numero = new TextField("Número");
		numero.setNullRepresentation("");
		numero.setImmediate(true);
		numero.setSizeFull();
		numero.setStyleName("form-control");

		final ComboBox mes = new ComboBox("Elija el mes");
		mes.addItem("Seleccione");
		mes.setNullSelectionItemId("Seleccione");
		mes.addItem("Enero");
		mes.addItem("Febrero");
		mes.addItem("Marzo");
		mes.addItem("Abril");
		mes.addItem("Mayo");
		mes.addItem("Junio");
		mes.addItem("Julio");
		mes.addItem("Agosto");
		mes.addItem("Septiembre");
		mes.addItem("Octubre");
		mes.addItem("Noviembre");
		mes.addItem("Diciembre");
		mes.setImmediate(true);

		final ComboBox departamentos = new ComboBox("Departamento");
		departamentos.addItem("Seleccione");
		departamentos.setNullSelectionItemId("Seleccione");
		final List<Departamento> lista = cargarDepartamentos(departamentos);
		departamentos.setImmediate(true);

		final ComboBox listadoPCLaptop = new ComboBox();
		listadoPCLaptop.addItem("Seleccione");
		listadoPCLaptop.setNullSelectionItemId("Seleccione");
		listadoPCLaptop.setImmediate(true);

		final ComboBox medio = new ComboBox("Elija el tipo de medio");
		medio.addItem("Seleccione");
		medio.setNullSelectionItemId("Seleccione");
		medio.addItem("PC");
		medio.addItem("Laptop");
		medio.addItem("Impresora");
		medio.setImmediate(true);

		medio.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (medio.getValue() != null) {
					if (medio.getValue().toString().equals("PC") || medio.getValue().toString().equals("Laptop")) {
						String departamento = departamentos.getValue() == null ? "Todos" : departamentos.getValue().toString();
						cargarPCLaptop(departamento, listadoPCLaptop, medio.getValue().toString().equals("PC") ? PC.class : Laptop.class);
						listadoPCLaptop.setCaption("Realizar mantenimiento a " + medio.getValue().toString());
						formularioBasico.addComponent(listadoPCLaptop);
					} else {
						formularioBasico.removeComponent(listadoPCLaptop);
					}
				} else {
					formularioBasico.removeComponent(listadoPCLaptop);
				}
			}
		});

		departamentos.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (medio.getValue() != null&&semaforo) {
					if (medio.getValue().toString().equals("PC") || medio.getValue().toString().equals("PC")) {
						String departamento = departamentos.getValue() == null ? "Todos" : departamentos.getValue().toString();
						cargarPCLaptop(departamento, listadoPCLaptop, medio.getValue().toString().equals("PC") ? PC.class : Laptop.class);
					}
				}
				semaforo=true;
			}
		});

		@SuppressWarnings("unchecked")
		List<ConfiguracionGlobal> config = (List<ConfiguracionGlobal>) fachada.listarTodos(ConfiguracionGlobal.class);
		String ccosto = config.isEmpty() ? "0" : config.get(0).getCentroCostoSucursal();
		final TextField inventario = new TextField("No.Inventario");
		inventario.setNullRepresentation("");
		inventario.setImmediate(true);
		inventario.setSizeFull();
		inventario.setStyleName("form-control");

		listadoPCLaptop.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (medio.getValue() != null&&listadoPCLaptop.getValue()!=null) {
					semaforo=false;
					departamentos.setValue(obtenerDeparatmentoListadoPC(listadoPCLaptop.getValue().toString()));
					inventario.setValue(obtenerInventarioPC(listadoPCLaptop.getValue().toString()));
					semaforo=true;
				}
			}
		});

		// metes pal formulario los campos
		// setear el contenido del panel
		formularioBasico.addComponents(numero, mes, departamentos, medio, inventario);
		datosBasico.setContent(formularioBasico);
		// Validación mediante el beanFieldGroup, el método bind mapea cada
		// campo con el atributo de la clase,
		// por defecto estos atributos tienen valor null

		final BeanFieldGroup<Mantenimiento> validador = new BeanFieldGroup<>(
				Mantenimiento.class);
		validador.setItemDataSource(new Mantenimiento());
		validador.bind(numero, "no");
		validador.bind(mes, "mes");
		validador.bind(medio, "equipo");
		validador.bind(inventario, "inventario");
		inventario.setValue(ccosto + "-");
		numero.setValue("" + (fachada.maximoID(Mantenimiento.class) + 1));
		NativeButton registrar = new NativeButton("Registrar", new Button.ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					if (mes.getValue() != null) {
						if (medio.getValue() != null) {
							if (departamentos.getValue() != null) {
								if (medio.getValue().toString().equals("PC") || medio.getValue().toString().equals("Laptop")) {
									if (listadoPCLaptop.getValue() != null) {
										if (obtenerDeparatmentoListadoPC(listadoPCLaptop.getValue().toString()).equals(departamentos.getValue().toString())) {
											if (inventario.getValue().toString().equals(obtenerInventarioPC(listadoPCLaptop.getValue().toString()))) {
												validador.commit();
												// Esto no valida esto lo que
												// hace es meter el valor del
												// combo box en la entidad
												validador.getItemDataSource().getBean().setMes(mes.getValue().toString());
												validador.getItemDataSource().getBean().setEquipo(medio.getValue().toString());
												validador.getItemDataSource().getBean().setUsuario(VaadinSession.getCurrent().getAttribute("usuario").toString());
												validador.getItemDataSource().getBean().setEstado("Programado");
												actualizarMantenimientoAPCLaptop(listadoPC, listadoPCLaptop.getValue().toString(), validador.getItemDataSource().getBean());
												Notificaciones.NotificarSubmit("Mantenimiento planificado registrado correctamente");
												validador.setItemDataSource(new Mantenimiento());
												lista.clear();
												lista.addAll((List<Departamento>) getFachada().listarTodos(Departamento.class));
												actualizar(validador.getItemDataSource().getBean().getClass());
												view.reemplazarContenido(new RegistrarMantenimientoPlanificado(view));
											} else {
												Notificaciones.NotificarError("El inventario debe coincidir con el del medio elegido");
											}
										} else {
											Notificaciones.NotificarError("El departamento elegido debe coincidir con el departamento que pertenece el medio");
										}
									} else {
										Notificaciones.NotificarError("Debe elegir una PC o Laptop para el mantenimiento");
									}
								} else {
									validador.commit();
									// Esto no valida esto lo que hace
									// es meter el valor del combo box
									// en la entidad
									validador.getItemDataSource().getBean().setMes(mes.getValue().toString());
									validador.getItemDataSource().getBean().setEquipo(medio.getValue().toString());
									validador.getItemDataSource().getBean().setUsuario(VaadinSession.getCurrent().getAttribute("usuario").toString());
									validador.getItemDataSource().getBean().setEstado("Programado");
									// arreglar duplicidad mediante jpa
									// annotations
									actualizarDepartamento(lista, departamentos.getValue().toString(), validador.getItemDataSource().getBean());
									Notificaciones.NotificarSubmit("Mantenimiento planificado registrado correctamente");
									actualizar(validador.getItemDataSource().getBean().getClass());
									view.reemplazarContenido(new RegistrarMantenimientoPlanificado(view));
								}

							} else {
								Notificaciones.NotificarError("Debe elegir un departamento");
							}
						} else {
							Notificaciones.NotificarError("Debe elegir un tipo de medio");
						}
					} else {
						Notificaciones.NotificarError("Debe elegir mes del año");
					}

				} catch (Exception ex) {
					Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
					Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});

		NativeButton cancelar = new NativeButton("Cancelar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				view.contenidoAnterior();
			}
		});

		registrar.setStyleName("btn btn-info botonControl");
		cancelar.setStyleName("btn btn-danger botonControl");
		registrar.setIcon(FontAwesome.CHECK);
		cancelar.setIcon(FontAwesome.CLOSE);
		HorizontalLayout reg = new HorizontalLayout();
		reg.addComponents(registrar, cancelar);
		reg.setSpacing(true);
		reg.setStyleName("controles");
		datosBasico.setWidth("100%");
		main.addComponents(datosBasico, reg);
		container.addComponent(main, "formulario");
		Label nombreForm = new Label("Planificar nuevo mantenimiento");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm, "nombreFormulario");
		actualizar(validador.getItemDataSource().getBean().getClass());
		setCompositionRoot(container);
	}

	private List<Departamento> cargarDepartamentos(ComboBox departamentos) {
		@SuppressWarnings("unchecked")
		List<Departamento> lista = (List<Departamento>) getFachada()
				.listarTodos(Departamento.class);
		for (Departamento d : lista) {
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}

	public void actualizar(Class<?> clazz) {
		Label medios = new Label(fachada.cantidadElementos(clazz)
				+ " Mantenimientos Programados");
		medios.setStyleName("text-right");
		container.addComponent(medios, "datos");
	}

	private void actualizarDepartamento(List<Departamento> lista, String departamento, Mantenimiento i) throws Exception {
		boolean encontrado = false;
		int k = 0;
		while (!encontrado && k < lista.size()) {
			if (lista.get(k).getNombre().equals(departamento)) {
				encontrado = true;
			} else {
				k++;
			}
		}
		Departamento dep = lista.get(k);
		i.setDepartamento(dep);
		dep.getMantenimientos().add(i);
		fachada.actualizarEntidad(dep);
	}

	private void actualizarMantenimientoAPCLaptop(List<PC> lista, String cadena, Mantenimiento i) throws Exception {
		boolean encontrado = false;
		int k = 0;
		while (!encontrado && k < lista.size()) {
			String inv = lista.get(k).getNombre() + "-" + ((lista.get(k) instanceof Laptop) ? ((Laptop) lista.get(k)).getInventario() : lista.get(k).obtenerTorre().getInventario());
			if (inv.equals(cadena)) {
				encontrado = true;
			} else {
				k++;
			}
		}
		PC pc = lista.get(k);
		i.setPcAsociada(pc);
		i.setDepartamento(pc.getDepartamento());
		pc.getMantenimientosEfectuados().add(i);
		pc = (PC) fachada.actualizarEntidad(pc);
		Departamento dep = pc.getDepartamento();
		dep.getMantenimientos().add(pc.getMantenimientosEfectuados().get(pc.getMantenimientosEfectuados().size() - 1));
		fachada.actualizarEntidad(dep);
	}

	@SuppressWarnings("unchecked")
	public List<PC> cargarPCLaptop(String departamento, ComboBox combo, Class<?> clazz) {
		listadoPC = (List<PC>) fachada.obtenerPCLaptop(clazz);
		combo.removeAllItems();
		combo.addItem("Seleccione");
		for (PC pc : listadoPC) {
			if (departamento.equals("Todos") || pc.getDepartamento().getNombre().equals(departamento)) {
				if (pc instanceof Laptop) {
					combo.addItem(pc.getNombre() + "-" + ((Laptop) pc).getInventario());
				} else {
					combo.addItem(pc.getNombre() + "-" + pc.obtenerTorre().getInventario());
				}
			}
		}
		return listadoPC;
	}

	public String obtenerDeparatmentoListadoPC(String cadena) {
		boolean encontrado = false;
		int k = 0;
		while (!encontrado && k < listadoPC.size()) {
			String inv = listadoPC.get(k).getNombre() + "-" + ((listadoPC.get(k) instanceof Laptop) ? ((Laptop) listadoPC.get(k)).getInventario() : listadoPC.get(k).obtenerTorre().getInventario());
			if (inv.equals(cadena)) {
				encontrado = true;
			} else {
				k++;
			}
		}
		return encontrado ? listadoPC.get(k).getDepartamento().getNombre() : null;
	}

	public String obtenerInventarioPC(String cadena) {
		boolean encontrado = false;
		int k = 0;
		while (!encontrado && k < listadoPC.size()) {
			String inv = listadoPC.get(k).getNombre() + "-" + ((listadoPC.get(k) instanceof Laptop) ? ((Laptop) listadoPC.get(k)).getInventario() : listadoPC.get(k).obtenerTorre().getInventario());
			if (inv.equals(cadena)) {
				encontrado = true;
			} else {
				k++;
			}
		}
		return encontrado ? ((listadoPC.get(k) instanceof Laptop) ? ((Laptop) listadoPC.get(k)).getInventario() : listadoPC.get(k).obtenerTorre().getInventario()) : null;
	}

}
