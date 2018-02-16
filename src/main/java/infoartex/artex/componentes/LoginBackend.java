package infoartex.artex.componentes;
import java.util.logging.Level;
import java.util.logging.Logger;

import infoartex.artex.bundles.MyPasswordEncrypt;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Sesion;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.vistas.Administrar;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


@SuppressWarnings("serial")
public class LoginBackend extends ComponenteGenerico {
	private TextField usuario;
	private PasswordField contrasenna;
	private NativeButton acceder;

	public LoginBackend(final Administrar admin) {
		super(admin);
		usuario = new TextField();
		usuario.setStyleName("form-control");
		usuario.setInputPrompt("Usuario");
		usuario.setIcon(FontAwesome.USER);
		usuario.setWidth("100%");
		contrasenna = new PasswordField();
		contrasenna.setWidth("100%");
		contrasenna.setInputPrompt("Contraseña");
		contrasenna.setStyleName("form-control");
		contrasenna.setIcon(FontAwesome.ASTERISK);
		acceder = new NativeButton("Acceder", new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if (!sesionActiva(usuario.getValue())) {
						Usuario user = getFachada().obtenerUsuario(usuario.getValue());
						if (user != null) {
							if (user.getPassword().equals(MyPasswordEncrypt.encrypt(contrasenna.getValue(), "MD5", "UTF-8"))) {
								VaadinSession.getCurrent().setAttribute("usuario", usuario.getValue());
								VaadinSession.getCurrent().setAttribute("rol",user.getRol().getNombre());
								fachada.registrarEntidad(new Sesion(user.getUsuario()));
								admin.modificarVista();
							} else {
								Notificaciones
										.NotificarError("Usuario o clave incorrectos");
							}
						} else {
							Notificaciones
									.NotificarError("Usuario inexistente");
						}
					} else {
						Notificaciones.NotificarError("Sesión Activa");
					}
				} catch (Exception ex) {
					Logger.getLogger(LoginBackend.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		acceder.setStyleName("btn btn-primary controles botonControl");
		acceder.setIcon(FontAwesome.KEY,"");
		acceder.setClickShortcut(KeyCode.ENTER, null);
		VerticalLayout main = new VerticalLayout();
		FormLayout form = new FormLayout();
		form.addComponents(usuario, contrasenna,acceder);
		form.setWidth("100%");
		main.addComponents(form);
		main.setComponentAlignment(form, Alignment.MIDDLE_RIGHT);
		setCompositionRoot(main);

	}

	public boolean sesionActiva(String usuario) {
		Sesion sesion = fachada.obtenerSesion(usuario);
		return (sesion != null);
	}
}
