package infoartex.artex.componentes;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.ReporteMantenimiento;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
public class RegistrarMantenimientoEfectuado extends ComponenteGenerico {

	// obligado
	private final CustomLayout container;
	private final VerticalLayout main;
	private ReporteMantenimiento entidad;
	private Mantenimiento mantenimiento;
	private boolean isCumplirMante;

	public RegistrarMantenimientoEfectuado(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		// usar formulario de los layouts que tiene el tema
		container = new CustomLayout("formulario");
		// metodo que arranca los componentes de vaadin
		entidad = new ReporteMantenimiento();
		isCumplirMante = false;
		initComponents(view);
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los siguientes campos: ");
		datosBasico.setSizeUndefined();
		FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");

		final DateField fecha = new DateField("Fecha");
		fecha.setImmediate(true);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		fecha.setValue(gc.getTime());

		final ComboBox tipomedio = new ComboBox("Elija el tipo de medio");
		tipomedio.addItem("Seleccione");
		tipomedio.setNullSelectionItemId("Seleccione");
		tipomedio.addItem("Torre");
		tipomedio.addItem("Impresora");
		tipomedio.addItem("Lapto");
		tipomedio.setImmediate(true);
		tipomedio.setSizeFull();

		final TextField inventario = new TextField("Inventario");
		inventario.setNullRepresentation("");
		inventario.setImmediate(true);
		inventario.setSizeFull();
		inventario.setStyleName("form-control");

		final ComboBox departamento = new ComboBox("Departamento");
		departamento.addItem("Seleccione");
		departamento.setNullSelectionItemId("Seleccione");
		final List<Departamento> lista = cargarDepartamentos(departamento);
		departamento.setImmediate(true);

		final TextArea observacion = new TextArea("Observación");
		observacion.setNullRepresentation("");
		observacion.setImmediate(true);
		observacion.setSizeFull();
		observacion.setStyleName("form-control");

		formularioBasico.addComponents(fecha, tipomedio, inventario, departamento, observacion);
		datosBasico.setContent(formularioBasico);

		final BeanFieldGroup<ReporteMantenimiento> validador = new BeanFieldGroup<>(ReporteMantenimiento.class);
		validador.setItemDataSource(entidad);
		validador.bind(fecha, "fecha");
		validador.bind(tipomedio, "tipomedio");
		validador.bind(inventario, "inventario");
		validador.bind(observacion, "observacion");

		NativeButton registrar = new NativeButton("Registrar", new Button.ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					if (tipomedio.getValue() != null) {
						if (departamento.getValue() != null) {
							if (!isCumplirMante) {
								validador.commit();
								validador.getItemDataSource().getBean().setTipomedio(tipomedio.getValue().toString());
								validador.getItemDataSource().getBean().setUsuario(VaadinSession.getCurrent().getAttribute("usuario").toString());
								actualizarDepartamento(lista, departamento.getValue().toString(), validador.getItemDataSource().getBean());
							} else {
								validador.commit();
								validador.getItemDataSource().getBean().setTipomedio(tipomedio.getValue().toString());
								validador.getItemDataSource().getBean().setUsuario(VaadinSession.getCurrent().getAttribute("usuario").toString());
								ReporteMantenimiento repo = actualizarReporte(validador.getItemDataSource().getBean());
								actualizarDepartamento(lista, departamento.getValue().toString(), repo);
							}
							Notificaciones.NotificarSubmit("Reporte de mantenimiento registrado correctamente");
							validador.setItemDataSource(new ReporteMantenimiento());
							lista.clear();
							lista.addAll((List<Departamento>) getFachada().listarTodos(Departamento.class));
							actualizar(validador.getItemDataSource().getBean().getClass());
						} else {
							Notificaciones.NotificarError("Debe elegir un departamento");
						}
					} else {
						Notificaciones.NotificarError("Debe elegir un tipo de medio");
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
		Label nombreForm = new Label("Registrar mantenimiento efectuado");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm, "nombreFormulario");
		actualizar(validador.getItemDataSource().getBean().getClass());
		setCompositionRoot(container);

	}

	public void actualizar(Class<?> clazz) {
		Label medios = new Label(fachada.cantidadElementos(clazz) + " Mantenimientos efectuados");
		medios.setStyleName("text-right");
		container.addComponent(medios, "datos");
	}

	private List<Departamento> cargarDepartamentos(ComboBox departamentos) {
		@SuppressWarnings("unchecked")
		List<Departamento> lista = (List<Departamento>) getFachada().listarTodos(Departamento.class);
		for (Departamento d : lista) {
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}

	private void actualizarDepartamento(List<Departamento> lista, String departamento, ReporteMantenimiento i) throws Exception {
		boolean encontrado = false;
		int k = 0;
		while (!encontrado && k < lista.size()) {
			if (lista.get(k).getNombre().equals(departamento)) {
				encontrado = true;
			} else {
				k++;
			}
		}
		// No te olvides de actualizar y registrar aqui.
		Departamento dep = lista.get(k);
		i.setDepartamento(dep);
		if (isCumplirMante) {
			fachada.actualizarEntidad(i);
		} else {
			fachada.registrarEntidad(i);
		}
		dep.getRepomantenimiento().add(i);
		fachada.actualizarEntidad(dep);
	}

	public ReporteMantenimiento actualizarReporte(ReporteMantenimiento m) throws Exception {
		m.setMantenimiento(mantenimiento);
		mantenimiento.setReporte(m);
		mantenimiento.setEstado("Cumplido");
		Mantenimiento man = (Mantenimiento) fachada.actualizarEntidad(mantenimiento);
		return man.getReporte();
	}

	public void setMantenimiento(ReporteMantenimiento objeto, Mantenimiento mantenimiento) {
		this.entidad = objeto;
		this.mantenimiento = mantenimiento;
	}

	public boolean isCumplirMante() {
		return isCumplirMante;
	}

	public void setCumplirMante(boolean isCumplirMante) {
		this.isCumplirMante = isCumplirMante;
	}
	
	

}
