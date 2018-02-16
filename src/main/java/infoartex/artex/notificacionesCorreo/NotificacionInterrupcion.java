package infoartex.artex.notificacionesCorreo;

import infoartex.artex.bundles.MyPasswordEncrypt;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.bundles.SMTPMailSender;
import infoartex.artex.dominio.ConfiguracionCorreo;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.fachada.Facade;
import infoartex.artex.fachada.impl.FacadeImpl;

import java.util.LinkedList;
import java.util.List;

public class NotificacionInterrupcion {
	private List<String> destinatarios;
	private Facade fachada;
	private ConfiguracionCorreo config;

	public NotificacionInterrupcion() {
		fachada = new FacadeImpl();
		destinatarios = new LinkedList<>();
		cargarUsuarios();
		try {
			config = (ConfiguracionCorreo) fachada.listarTodos(ConfiguracionCorreo.class).get(0);
		} catch (ArrayIndexOutOfBoundsException ex) {
			Notificaciones.NotificarError("No se ha gestionado una configuración de correo");
		}
	}

	public void cargarUsuarios() {
		List<Usuario> l1 = fachada.listarTecnicos();
		for (Usuario a : l1) {
			destinatarios.add(a.getEmail());
		}
	}

	public void Notificar() {
		if (config.isEnvioHabilitado()) {
			try {
				SMTPMailSender.setProperties(true, config.getServidorSaliente(), config.getPuerto(), config.getDireccion());
				SMTPMailSender.sendMail(config.getDireccion(), MyPasswordEncrypt.DesencriptarConDES(config.getPassword()), destinatarios, "Nueva interrupcion en el sistema", "Estimados técnicos, los clientes han registrado una nueva interrupción",
						null);
			} catch (Exception ex) {
				Notificaciones.NotificarError("Error de conexión, no se ha podido conectar con el servidor saliente");
			}
		}
	}
}
