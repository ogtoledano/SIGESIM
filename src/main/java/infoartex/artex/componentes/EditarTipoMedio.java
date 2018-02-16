package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
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
public class EditarTipoMedio extends ComponenteGenerico {

	private final CustomLayout container;
	private final VerticalLayout main;

	// entidad a modificar
	private TipoMedio tipomedio;
	private BeanFieldGroup<TipoMedio> validador;

	public EditarTipoMedio(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		// usar formulario de los layouts que tiene el tema
		container = new CustomLayout("formulario");
		tipomedio = new TipoMedio();
	}

	// debe llamarse asi setEntidad, en todos los que hagas
	public void setEntidad(Entidad entidad) {
		// lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la
		// entidad
		this.tipomedio = (TipoMedio) entidad;
		validador = new BeanFieldGroup<TipoMedio>(TipoMedio.class);
		validador.setItemDataSource(tipomedio);
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		container.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los campos");
		datosBasico.setSizeUndefined();
		FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		final TextField denominacion = new TextField("Denominación");
		denominacion.setNullRepresentation("");
		denominacion.setImmediate(true);
		denominacion.setSizeFull();
		// nombre.setRequired(true);
		denominacion.setStyleName("form-control");
		formularioBasico.addComponents(denominacion);
		datosBasico.setContent(formularioBasico);
		// Validación mediante el beanFieldGroup, el método bind mapea cada
		// campo con el atributo de la clase,
		// por defecto estos atributos tienen valor null
		final BeanFieldGroup<TipoMedio> validador = new BeanFieldGroup<>(TipoMedio.class);
		validador.setItemDataSource(tipomedio);
		validador.bind(denominacion, "denominacion");
		NativeButton registrar = new NativeButton("Actualizar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					// registrar en la BD
					// ejecutar validacion
					if (getFachada().obtenerTipoMedio(denominacion.getValue()) == null) {
						validador.commit();
						getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
						Notificaciones.NotificarSubmit("Tipo de medio actualizado correctamente");
						denominacion.setValue("");
						actualizar(validador.getItemDataSource().getBean().getClass());
					}else {
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
		registrar.setIcon(FontAwesome.EDIT);
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
