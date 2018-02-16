/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoartex.artex.componentes;

import java.util.List;

import infoartex.artex.bundles.MyPasswordEncrypt;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Rol;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author administrador
 */
@SuppressWarnings("serial")
public class RegistrarUsuario extends ComponenteGenerico {

	private final CustomLayout container;
	private final VerticalLayout main;
	private List<Rol> listAux;

	@SuppressWarnings("unchecked")
	public RegistrarUsuario(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		container = new CustomLayout("formulario");
		listAux = (List<Rol>) fachada.listarTodos(Rol.class);
		initComponents();
	}

	private void initComponents() {
		Panel datosBasico = new Panel("Datos del usuario");
		datosBasico.setSizeUndefined();
		FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		final TextField usuario = new TextField("Usuario");
		usuario.setNullRepresentation("");
		usuario.setImmediate(true);
		usuario.setSizeFull();
		usuario.setStyleName("form-control");
		final TextField nombre = new TextField("Nombre");
		nombre.setNullRepresentation("");
		nombre.setImmediate(true);
		nombre.setSizeFull();
		nombre.setStyleName("form-control");
		final TextField apellidos = new TextField("Apellidos");
		apellidos.setNullRepresentation("");
		apellidos.setImmediate(true);
		apellidos.setSizeFull();
		apellidos.setStyleName("form-control");
		final TextField correo = new TextField("Correo");
		correo.setNullRepresentation("");
		correo.setImmediate(true);
		correo.setSizeFull();
		correo.setStyleName("form-control");
		final PasswordField password = new PasswordField("Contraseña");
		password.setNullRepresentation("");
		password.setImmediate(true);
		password.setSizeFull();
		password.setStyleName("form-control");
		final PasswordField confPassword = new PasswordField("Confirmar contraseña");
		confPassword.setNullRepresentation("");
		confPassword.setImmediate(true);
		confPassword.setStyleName("form-control");
		confPassword.setSizeFull();
		final ComboBox tipoRol = new ComboBox("Seleccione el rol");
		tipoRol.addItem("Seleccione");
		tipoRol.setNullSelectionItemId("Seleccione");
		cargarRoles(tipoRol);

		final TextField cargo = new TextField("Cargo");
		cargo.setNullRepresentation("");
		cargo.setImmediate(true);
		cargo.setStyleName("form-control");
		cargo.setSizeFull();
		formularioBasico.addComponents(usuario, nombre, apellidos, correo, password, confPassword, tipoRol, cargo);
		datosBasico.setContent(formularioBasico);
		// Validación mediante el beanFieldGroup, el método bind mapea cada
		// campo con el atributo de la clase,
		// por defecto estos atributos tienen valor null
		final BeanFieldGroup<Usuario> validador = new BeanFieldGroup<Usuario>(Usuario.class);
		validador.setItemDataSource(new Usuario());
		validador.bind(usuario, "usuario");
		validador.bind(nombre, "nombre");
		validador.bind(apellidos, "apellidos");
		validador.bind(correo, "email");
		validador.bind(password, "password");
		validador.bind(cargo, "cargo");
		NativeButton registrar = new NativeButton("Registrar", new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					if (password.getValue().equals(confPassword.getValue())) {
						if (fachada.obtenerUsuario(usuario.getValue()) == null) {
							if (tipoRol.getValue() != null) {
								validador.commit();
								validador.getItemDataSource().getBean().setPassword(MyPasswordEncrypt.encrypt(password.getValue(), "MD5", "UTF-8"));
								Rol rol = obtenerRolXNombre(tipoRol.getValue().toString());
								validador.getItemDataSource().getBean().setRol(rol);
								rol.getUsuarios().add(validador.getItemDataSource().getBean());
								getFachada().actualizarEntidad(rol);
								Notificaciones.NotificarSubmit("Usuario registrado correctamente");
								cargo.setValue("");
								password.setValue("");
								confPassword.setValue("");
								correo.setValue("");
								apellidos.setValue("");
								nombre.setValue("");
								usuario.setValue("");
								actualizar(validador.getItemDataSource().getBean().getClass());
							} else {
								Notificaciones.NotificarError("Debe elegir un rol");
							}
						} else {
							Notificaciones.NotificarError("El usuario ya existe");
						}
					} else {
						Notificaciones.NotificarError("Las contraseñas no coinciden");
					}
				} catch (Exception ex) {
					Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
					//Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		registrar.setStyleName("btn btn-info botonControl");
		registrar.setIcon(FontAwesome.CHECK);
		HorizontalLayout reg = new HorizontalLayout();
		reg.addComponents(registrar);
		reg.setSpacing(true);
		reg.setStyleName("controles");
		datosBasico.setWidth("100%");
		main.addComponents(datosBasico, reg);
		container.addComponent(main, "formulario");
		Label nombreForm = new Label("Registrar usuario");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm, "nombreFormulario");
		actualizar(validador.getItemDataSource().getBean().getClass());
		setCompositionRoot(container);
	}

	public void actualizar(Class<?> clazz) {
		Label usuarios = new Label(fachada.cantidadElementos(clazz) + " usuarios registrados");
		usuarios.setStyleName("text-right");
		container.addComponent(usuarios, "datos");
	}

	public void cargarRoles(final ComboBox tipo) {
		for (Rol rol : listAux) {
			tipo.addItem(rol.getNombre());
		}
	}

	public Rol obtenerRolXNombre(String rol) {
		boolean encon = false;
		int i = 0;
		while (!encon && i < listAux.size()) {
			if (listAux.get(i).getNombre().equals(rol)) {
				encon = true;
			} else {
				i++;
			}
		}
		return encon ? listAux.get(i) : null;
	}

}
