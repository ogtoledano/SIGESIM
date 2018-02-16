package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.TipoMedio;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RegistrarTipoMedio extends ComponenteGenerico {
	private final CustomLayout container;
	private final VerticalLayout main;

	public RegistrarTipoMedio(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		// usar formulario de los layouts que tiene el tema
		container = new CustomLayout("formulario");
		// metodo que arranca los componentes de vaadin
		initComponents(view);
	}

	private void initComponents(final Administrar view) {
		Panel datosBasico = new Panel("Rellene los campos");
		datosBasico.setSizeUndefined();
		FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		final TextField nombre = new TextField("Denominación");
		nombre.setNullRepresentation("");
		nombre.setImmediate(true);
		nombre.setSizeFull();
		// nombre.setRequired(true);
		nombre.setStyleName("form-control");
		formularioBasico.addComponents(nombre);
		datosBasico.setContent(formularioBasico);
		// Validación mediante el beanFieldGroup, el método bind mapea cada
		// campo con el atributo de la clase,
		// por defecto estos atributos tienen valor null
		final BeanFieldGroup<TipoMedio> validador = new BeanFieldGroup<>(TipoMedio.class);
		validador.setItemDataSource(new TipoMedio());
		validador.bind(nombre, "denominacion");
		NativeButton registrar = new NativeButton("Registrar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					// registrar en la BD
					// ejecutar validacion
					if (getFachada().obtenerTipoMedio(nombre.getValue()) == null) {
						validador.commit();
						getFachada().registrarEntidad(validador.getItemDataSource().getBean());
						Notificaciones.NotificarSubmit("Tipo de medio registrado correctamente");
						nombre.setValue("");
						actualizar(validador.getItemDataSource().getBean().getClass());
					} else {
						Notificaciones.NotificarError("El tipo de medio ya existe");
					}
				} catch (Exception ex) {
					Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
					// Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE,
					// null, ex);
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
		Label nombreForm = new Label("Registrar nuevo departamento o local");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm, "nombreFormulario");
		actualizar(validador.getItemDataSource().getBean().getClass());
		setCompositionRoot(container);

	}

	private void actualizar(Class<?> clazz) {
		Label tipoMedio = new Label(fachada.cantidadElementos(clazz) + " tipos de medios registrados");
		tipoMedio.setStyleName("text-right");
		container.addComponent(tipoMedio, "datos");

	}
}
