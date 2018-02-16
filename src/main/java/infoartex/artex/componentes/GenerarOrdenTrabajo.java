package infoartex.artex.componentes;

import java.util.LinkedList;
import java.util.List;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.ConfiguracionGlobal;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.dominio.PiezaRecuperada;
import infoartex.artex.dominio.PiezaRepuesto;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class GenerarOrdenTrabajo extends ComponenteGenerico {

	private final CustomLayout container;
	private final VerticalLayout main;
	private Panel piezasPanel;
	private Panel recuperadasPanel;
	private List<PiezaRecuperada> recuperadasList;
	private List<PiezaRepuesto> repuestoList;
	private OrdenTrabajo orden;
	private Interrupcion interrupcion;
	private boolean isEditForm;

	public GenerarOrdenTrabajo(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		recuperadasList = new LinkedList<>();
		repuestoList = new LinkedList<>();
		isEditForm = false;
		// usar formulario de los layouts que tiene el tema
		container = new CustomLayout("formulario");
		// metodo que arranca los componentes de vaadin
		orden = new OrdenTrabajo();
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		recuperadasList.clear();
		repuestoList.clear();
		Panel datosBasico = new Panel("Rellene los campos");
		datosBasico.setSizeUndefined();
		FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		final TextField numero = new TextField("Número consecutivo");
		numero.setNullRepresentation("");
		numero.setImmediate(true);
		numero.setValue("" + (fachada.maximoID(OrdenTrabajo.class) + 1));
		numero.setStyleName("form-control");
		final DateField fecha = new DateField("Fecha");
		fecha.setImmediate(true);
		@SuppressWarnings("unchecked")
		List<ConfiguracionGlobal> config = (List<ConfiguracionGlobal>) fachada.listarTodos(ConfiguracionGlobal.class);
		String ccosto = config.isEmpty() ? "0" : config.get(0).getCentroCostoSucursal();
		final TextField inventario = new TextField("No. Inventario");
		inventario.setNullRepresentation("");
		inventario.setImmediate(true);
		inventario.setStyleName("form-control");
		inventario.setSizeFull();
		// inventario.setRequired(true);
		final ComboBox tipo = new ComboBox("Elija el tipo de medio");
		tipo.addItem("Seleccione");
		tipo.setNullSelectionItemId("Seleccione");
		tipo.addItem("Torre");
		tipo.addItem("Monitor");
		tipo.addItem("Mouse");
		tipo.addItem("Teclado");
		tipo.addItem("UPS");
		tipo.addItem("Impresora");
		tipo.addItem("Scanner");
		tipo.addItem("Switch");
		tipo.addItem("Modem Externo");
		tipo.addItem("Video BIN");
		final TextArea dictamen = new TextArea("Dictamen Técnico");
		dictamen.setNullRepresentation("");
		dictamen.setImmediate(true);
		dictamen.setStyleName("form-control");
		dictamen.setSizeFull();
		// dictamen.setRequired(true);
		// Si el dictamen es mucho mas grande, que creo yo, usa mejor un
		// texarea.
		final TextArea acciones = new TextArea("Acciones realizadas");
		acciones.setNullRepresentation("");
		acciones.setImmediate(true);
		acciones.setStyleName("form-control");
		acciones.setSizeFull();
		// acciones.setRequired(true);
		final DateField fechasalida = new DateField("Fecha de salida");
		fechasalida.setImmediate(true);
		final TextField trabajador = new TextField("Entregado al trabajador");
		trabajador.setNullRepresentation("");
		trabajador.setImmediate(true);
		trabajador.setStyleName("form-control");
		trabajador.setSizeFull();

		final CheckBox solucionada = new CheckBox("Solucionada");
		formularioBasico.addComponents(numero, fecha, inventario, tipo, dictamen, acciones, fechasalida, trabajador);
		if (isEditForm) {
			formularioBasico.addComponent(solucionada);
		}
		datosBasico.setContent(formularioBasico);
		// Validación mediante el beanFieldGroup, el método bind mapea cada
		// campo con el atributo de la clase,
		// por defecto estos atributos tienen valor null
		final BeanFieldGroup<OrdenTrabajo> validador = new BeanFieldGroup<>(OrdenTrabajo.class);
		if (!isEditForm) {
			orden.setNumero(Integer.parseInt(numero.getValue()));
		}
		validador.setItemDataSource(orden);
		validador.bind(numero, "numero");
		validador.bind(fecha, "fecha");
		validador.bind(inventario, "inventario");
		validador.bind(tipo, "tipomedio");
		validador.bind(dictamen, "dictamen");
		validador.bind(acciones, "acciones");
		validador.bind(fechasalida, "fechasalida");
		validador.bind(trabajador, "trabajadorentregado");
		if (!isEditForm) {
			inventario.setValue(ccosto + "-");
		}
		final ListaPaginadaPiezaRepuestoParaOrden piezas = new ListaPaginadaPiezaRepuestoParaOrden(view, 10, 200, PiezaRepuesto.class);
		final ListaPaginadaPiezaRecuperadaParaOrden piezasRecuperadas = new ListaPaginadaPiezaRecuperadaParaOrden(view, 10, 200, PiezaRecuperada.class);
		piezas.initComponent(this);
		piezasRecuperadas.initComponent(this);
		HorizontalLayout piezasElegir = new HorizontalLayout();
		final CheckBox repuesto = new CheckBox("Usar pieza de repuesto");
		final CheckBox recuperada = new CheckBox("Usar pieza de recuperada");
		piezasElegir.addComponents(repuesto, recuperada);
		piezasElegir.setSpacing(true);
		// panel de piesas repuesto
		piezasPanel = new Panel("Piezas de repuesto utilizadas");
		piezasPanel.setSizeFull();
		Label sinPiezas = new Label("No hay piezas elegidas");
		FormLayout formularioPiezas = new FormLayout();
		formularioPiezas.setMargin(true);
		formularioPiezas.setWidth("100%");
		formularioPiezas.addComponent(sinPiezas);
		piezasPanel.setContent(formularioPiezas);

		// panel recuperadas
		recuperadasPanel = new Panel("Piezas recuperadas utilizadas");
		recuperadasPanel.setSizeFull();
		Label sinPiezasRecuperadas = new Label("No hay piezas elegidas");
		FormLayout formularioRecuperadas = new FormLayout();
		formularioRecuperadas.setMargin(true);
		formularioRecuperadas.setWidth("100%");
		formularioRecuperadas.addComponent(sinPiezasRecuperadas);
		recuperadasPanel.setContent(formularioRecuperadas);
		repuesto.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (repuesto.getValue()) {
					main.addComponent(piezasPanel, main.getComponentCount() - 1);
					main.addComponent(piezas, main.getComponentCount() - 1);
				} else {
					main.removeComponent(piezasPanel);
					main.removeComponent(piezas);
				}
			}
		});

		recuperada.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (recuperada.getValue()) {
					main.addComponent(recuperadasPanel, main.getComponentCount() - 1);
					main.addComponent(piezasRecuperadas, main.getComponentCount() - 1);
				} else {
					main.removeComponent(recuperadasPanel);
					main.removeComponent(piezasRecuperadas);
				}
			}
		});

		NativeButton registrar = new NativeButton("Registrar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					if (isEditForm) {
						if (tipo.getValue() != null) {
							if (validarPiezasRepuesto()) {
								validador.commit();
								validador.getItemDataSource().getBean().setTipomedio(tipo.getValue().toString());
								validador.getItemDataSource().getBean().setEstado("Cerrada");
								interrupcion.setEstado(solucionada.getValue() ? "Solucionada" : "No solucionada");
								fachada.actualizarEntidad(interrupcion);
								actualizarAlmacenRepuesto(validador.getItemDataSource().getBean());
								view.reemplazarContenido(new ListarInterrupciones(view));
							} else {
								Notificaciones.NotificarError("Algunas de las cantidades en las piezas de repuesto elegidas excede la cantidad existente en el almacén o poseen un valor inválido");
							}
						} else {
							Notificaciones.NotificarError("Debe elegir el tipo de pieza");
						}
					} else {
						if (fachada.obtenerOrden(Integer.parseInt(numero.getValue() + "")) == null) {
							if (tipo.getValue() != null) {
								if (validarPiezasRepuesto()) {
									validador.commit();
									validador.getItemDataSource().getBean().setTipomedio(tipo.getValue().toString());
									validador.getItemDataSource().getBean().setEstado("Cerrada");
									actualizarAlmacenRepuesto(validador.getItemDataSource().getBean());
									Notificaciones.NotificarSubmit("Orden de trabajo registrada correctamente");
									GenerarOrdenTrabajo generar=new GenerarOrdenTrabajo(view);
									generar.initComponents(view);
									view.reemplazarContenido(generar);
								} else {
									Notificaciones.NotificarError("Algunas de las cantidades en las piezas de repuesto elegidas excede la cantidad existente en el almacén o poseen un valor inválido");
								}
							} else {
								Notificaciones.NotificarError("Debe elegir el tipo de pieza");
							}
						} else {
							Notificaciones.NotificarError("Ya existe una orden registrada con ese número");
						}
					}
				} catch (NumberFormatException ex) {
					Notificaciones.NotificarError("Errores en los valores de las cantidades");
				} catch (Exception ex) {
					Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
					// Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
			}
		});
		registrar.setStyleName("btn btn-info botonControl");
		if (isEditForm) {
			registrar.setCaption("Actualizar");
		}
		NativeButton cancelar = new NativeButton("Cancelar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				view.contenidoAnterior();
			}
		});
		cancelar.setStyleName("btn btn-danger botonControl");
		registrar.setIcon(FontAwesome.EDIT);
		cancelar.setIcon(FontAwesome.CLOSE);
		HorizontalLayout controles = new HorizontalLayout();
		controles.addComponents(registrar, cancelar);
		Label nombreForm = new Label("Entrada de nueva órden de trabajo");
		nombreForm.setStyleName("text-left encabezado");
		controles.setSpacing(true);
		controles.setStyleName("controles");
		datosBasico.setWidth("100%");
		main.addComponents(datosBasico, piezasElegir, controles);
		container.addComponent(main, "formulario");
		container.addComponent(nombreForm, "nombreFormulario");
		actualizar(validador.getItemDataSource().getBean().getClass());
		setCompositionRoot(container);
	}

	public void actualizar(Class<?> clazz) {
		Label medios = new Label(fachada.cantidadElementos(clazz) + " Ordenes de trabajo");
		medios.setStyleName("text-right");
		container.addComponent(medios, "datos");
	}

	public int cantidad(String cant) {
		String aux = "";
		for (int i = 0; i < cant.length(); i++) {
			if (cant.charAt(i) != '.') {
				aux += cant.charAt(i);
			}
		}
		return Integer.parseInt(aux);
	}

	public void llenarListaPiezasRepuesto(List<PiezaRepuesto> piezas) {
		FormLayout formularioPiezas = (FormLayout) piezasPanel.getContent();
		if (formularioPiezas.getComponent(0) instanceof Label) {
			formularioPiezas.removeComponent((formularioPiezas.getComponent(0)));
		}
		repuestoList.add(piezas.get(piezas.size() - 1));
		TextField cantidad = new TextField("Pieza: " + piezas.get(piezas.size() - 1).getTipo() + " Cantidad: ");
		formularioPiezas.addComponent(cantidad);
		piezasPanel.setWidth("100%");
	}

	public void eliminarElementoPanel(int pos) {
		FormLayout formularioPiezas = (FormLayout) piezasPanel.getContent();
		TextField aux = (TextField) formularioPiezas.getComponent(pos);
		formularioPiezas.removeComponent(aux);
		repuestoList.remove(pos);
		if (formularioPiezas.getComponentCount() == 0) {
			Label sinPiezas = new Label("No hay piezas elegidas");
			formularioPiezas.addComponent(sinPiezas);
		}
	}

	public void llenarListaPiezasRecuperadas(List<PiezaRecuperada> piezas) {
		FormLayout formularioPiezas = (FormLayout) recuperadasPanel.getContent();
		if (recuperadasList.isEmpty()) {
			formularioPiezas.removeComponent((formularioPiezas.getComponent(0)));
		}
		recuperadasList.add(piezas.get(piezas.size() - 1));
		Label cantidad = new Label("Pieza: " + piezas.get(piezas.size() - 1).getTipo());
		formularioPiezas.addComponent(cantidad);
		recuperadasPanel.setWidth("100%");
	}

	public void eliminarElementoPanelRecuperadas(int pos) {
		FormLayout formularioPiezas = (FormLayout) recuperadasPanel.getContent();
		Label aux = (Label) formularioPiezas.getComponent(pos);
		formularioPiezas.removeComponent(aux);
		recuperadasList.remove(pos);
		if (formularioPiezas.getComponentCount() == 0) {
			Label sinPiezas = new Label("No hay piezas elegidas");
			formularioPiezas.addComponent(sinPiezas);
		}
	}

	public boolean validarPiezasRepuesto() {
		FormLayout formularioPiezas = (FormLayout) piezasPanel.getContent();
		if (formularioPiezas.getComponent(0) instanceof Label == false) {
			boolean valido = true;
			int i = 0;
			while (valido && i < formularioPiezas.getComponentCount()) {
				int cantidad = Integer.parseInt(((TextField) formularioPiezas.getComponent(i)).getValue());
				if (cantidad > repuestoList.get(i).getCantidad() || cantidad < 0) {
					valido = false;
				} else {
					i++;
				}
			}
			return valido;
		}
		return true;
	}

	public void actualizarAlmacenRepuesto(OrdenTrabajo orden) throws Exception {
		if (!isEditForm) {
			fachada.registrarEntidad(orden);
		}
		if (!repuestoList.isEmpty()) {
			FormLayout formularioPiezas = (FormLayout) piezasPanel.getContent();
			int i = 0;
			for (Component c : formularioPiezas) {
				int cant = repuestoList.get(i).getCantidad();
				int nuevaCant = Integer.parseInt(((TextField) c).getValue());
				PiezaRepuesto aux = repuestoList.get(i);
				PiezaRepuesto pieza = new PiezaRepuesto(aux.getCodigo(), aux.getFactura(), aux.getTipo(), nuevaCant, aux.getDetalles());
				pieza.setCantidad(nuevaCant);
				pieza.setOrden(orden);
				pieza.setNoOrden(orden.getNumero() + "");
				orden.getPiezasRepuesto().add(pieza);
				if (cant - nuevaCant <= 0) {
					fachada.eliminarEntidad(aux.getId(), PiezaRepuesto.class);
				} else {
					aux.setCantidad(cant - nuevaCant);
					fachada.actualizarEntidad(aux);
				}
				i++;
			}
		}
		for (PiezaRecuperada p : recuperadasList) {
			p.setOrden(orden);
			p.setNoOrden(orden.getNumero() + "");
			fachada.actualizarEntidad(p);
			orden.getPiezasRecuperada().add(p);
		}
		fachada.actualizarEntidad(orden);
	}

	public void setOrdenTrabajo(OrdenTrabajo orden, Interrupcion interrupcion) {
		this.orden = orden;
		isEditForm = true;
		this.interrupcion = interrupcion;
	}

}