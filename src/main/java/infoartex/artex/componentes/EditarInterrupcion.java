package infoartex.artex.componentes;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
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

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class EditarInterrupcion extends ComponenteGenerico {

	private final CustomLayout container;
	private final VerticalLayout main;

	// entidad a modificar
	private Interrupcion interrupcion;
	private BeanFieldGroup<Interrupcion> validador;

	public EditarInterrupcion(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		// usar formulario de los layouts que tiene el tema
		container = new CustomLayout("formulario");
		interrupcion = new Interrupcion();
	}

	// debe llamarse asi setEntidad, en todos los que hagas
	public void setEntidad(Entidad entidad) {
		// lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la
		// entidad
		this.interrupcion = (Interrupcion) entidad;
		validador = new BeanFieldGroup<Interrupcion>(Interrupcion.class);
		validador.setItemDataSource(interrupcion);
	}

	public void initComponents(final Administrar view) {
		// se crea una unica instancia y este metodo lo llama java reflection
		main.removeAllComponents();
		container.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los campos");
		datosBasico.setSizeUndefined();
		FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		final TextField inventario = new TextField("Inventario");
		inventario.setNullRepresentation("");
		inventario.setImmediate(true);
		inventario.setStyleName("form-control");
		inventario.setSizeFull();
		final DateField fecha = new DateField("Fecha");
		fecha.setImmediate(true);
		String horaInterrup = interrupcion.getHora();
		HorizontalLayout hora = new HorizontalLayout();
		final ComboBox horaCombo = new ComboBox("Hora");
		horaCombo.setImmediate(true);
		for (int i = 1; i < 13; i++) {
			horaCombo.addItem("" + i);
		}

		horaCombo.setNullSelectionAllowed(false);
		horaCombo.setValue(horaInterrup.split(":")[0] + "");
		horaCombo.setTextInputAllowed(false);
		final ComboBox minutosCombo = new ComboBox("Minutos");
		for (int i = 0; i < 60; i++) {
			if (i >= 0 && i < 10) {
				minutosCombo.addItem("0" + i);
			} else {
				minutosCombo.addItem("" + i);
			}
		}
		minutosCombo.setNullSelectionAllowed(false);
		minutosCombo.setValue(horaInterrup.split(":")[1].split(" ")[0]);
		minutosCombo.setImmediate(true);
		minutosCombo.setTextInputAllowed(false);
		final ComboBox am = new ComboBox("AM/PM");
		am.addItem("AM");
		am.addItem("PM");
		am.setNullSelectionAllowed(false);
		am.setImmediate(true);
		am.setValue(horaInterrup.split(":")[1].split(" ")[1]);
		am.setTextInputAllowed(false);
		hora.addComponents(horaCombo, minutosCombo, am);
		hora.setSpacing(true);
		final TextField trabajador = new TextField("Trabajador");
		trabajador.setNullRepresentation("");
		trabajador.setImmediate(true);
		trabajador.setStyleName("form-control");
		trabajador.setSizeFull();
		final ComboBox departamentos = new ComboBox("Departamento");
		departamentos.setEnabled(false);
		departamentos.setImmediate(true);
		departamentos.setSizeFull();
		final TextArea descripcion = new TextArea("Descripción");
		descripcion.setNullRepresentation("");
		descripcion.setImmediate(true);
		descripcion.setStyleName("form-control");
		descripcion.setSizeFull();
		formularioBasico.addComponents(inventario, fecha, hora, trabajador, departamentos, descripcion);
		datosBasico.setContent(formularioBasico);
		// Validación mediante el beanFieldGroup, el método bind mapea cada
		// campo con el atributo de la clase,
		// por defecto estos atributos tienen valor null
		// ojo asi se bindea para que se cargen los datos viejos
		validador.setItemDataSource(interrupcion);
		validador.bind(inventario, "inventario");
		validador.bind(fecha, "fecha");
		validador.bind(trabajador, "trabajador");
		validador.bind(descripcion, "descripcion");
		// poner el departamento de la entidad por defecto
		departamentos.addItem(validador.getItemDataSource().getBean().getDepartamento().getNombre());
		departamentos.setValue(validador.getItemDataSource().getBean().getDepartamento().getNombre());
		NativeButton registrar = new NativeButton("Actualizar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					if (departamentos.getValue() != null) {
						// registrar en la BD
						// ejecutar validacion
						validador.commit();
						// no se registra, se actualiza
						String horaSave = horaCombo.getValue() + ":" + minutosCombo.getValue() + " " + am.getValue();
						validador.getItemDataSource().getBean().setHora(horaSave);

						OrdenTrabajo orden = validador.getItemDataSource().getBean().getOrden();
						getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
						if (orden != null) {
							orden.setFecha(validador.getItemDataSource().getBean().getFecha());
							orden.setInventario(validador.getItemDataSource().getBean().getInventario());
							orden.setTrabajadorentregado(validador.getItemDataSource().getBean().getTrabajador());
							getFachada().actualizarEntidad(orden);
						}
						Notificaciones.NotificarSubmit("Interrupcion actualizada correctamente");
						view.nuevoContenido(new ListarInterrupciones(view));
					} else {
						Notificaciones.NotificarError("Debe elegir un departamento");
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
		registrar.setIcon(FontAwesome.EDIT);
		cancelar.setIcon(FontAwesome.CLOSE);
		HorizontalLayout reg = new HorizontalLayout();
		reg.addComponents(registrar, cancelar);
		reg.setSpacing(true);
		reg.setStyleName("controles");
		datosBasico.setWidth("100%");
		main.addComponents(datosBasico, reg);
		container.addComponent(main, "formulario");
		Label nombreForm = new Label("Modificar interrupción");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm, "nombreFormulario");
		setCompositionRoot(container);
	}

}
