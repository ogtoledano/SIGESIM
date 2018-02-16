package infoartex.artex.vistas;

import infoartex.artex.componentes.*;
import infoartex.artex.fachada.Facade;
import infoartex.artex.fachada.impl.FacadeImpl;

import java.util.Stack;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Button.ClickEvent;

@PreserveOnRefresh
@SuppressWarnings("serial")
public class Administrar extends CustomComponent implements View {

	public static String nombreVista = "";
	// CustomLayaout es una plantilla inicialmente vacia qie se le puede colocar
	// un htmml.
	private CustomLayout customLayout;
	// CustomComponent contenedores de GUI para vaadin( puedes meter un
	// TextField, Calendar Label, HorizontalLayout, etc)
	public Stack<CustomComponent> componentes;
	public CustomComponent contenidoActual;

	public Administrar() {
		super();
		modificarVista();
	}

	public void modificarVista() {
		componentes = new Stack<CustomComponent>();

		String rol = VaadinSession.getCurrent().getAttribute("rol").toString();

		switch (rol) {
		case "Administrador": {
			cargarDashboard();
			break;
		}
		case "Cliente": {
			cargarCliente();
			break;
		}
		case "Técnico": {
			cargarTecnico();
			break;
		}
		case "Almacenero": {
			cargarAlmacenero();
			break;
		}
		default: {
			customLayout = new CustomLayout("LoginForm");
			customLayout.addComponent(new LoginBackend(this), "login");
		}

		}
		setCompositionRoot(customLayout);
	}

	public void enter(ViewChangeEvent event) {

	}

	public void contenidoAnterior() {
		if (!componentes.isEmpty())
			customLayout.addComponent((contenidoActual = componentes.pop()),
					"contenido");
	}

	public void nuevoContenido(CustomComponent component) {
		componentes.push(contenidoActual);
		customLayout.addComponent(component, "contenido");
		contenidoActual = component;
	}

	public void reemplazarContenido(CustomComponent component) {
		componentes.pop();
		componentes.push(contenidoActual);
		customLayout.addComponent(component, "contenido");
		contenidoActual = component;
	}

	public Stack<CustomComponent> getComponentes() {
		return componentes;
	}

	public void setComponentes(Stack<CustomComponent> componentes) {
		this.componentes = componentes;
	}

	public CustomComponent getContenidoActual() {
		return contenidoActual;
	}

	public void setContenidoActual(CustomComponent contenidoActual) {
		this.contenidoActual = contenidoActual;
	}

	public void establecerPila(CustomComponent contenidoActual) {
		// componentes.clear();
		// customLayout.addComponent(contenidoActual, "contenido");
		// setContenidoActual(contenidoActual);
	}

	public CustomLayout getCustomLayout() {
		return customLayout;
	}

	public void setCustomLayout(CustomLayout customLayout) {
		this.customLayout = customLayout;
	}

	/**
	 * Vista del almacenero
	 */
	public void cargarAlmacenero() {
		final Facade fachada = new FacadeImpl();
		customLayout = new CustomLayout("almacenero");
		cargarOpcionesComun(fachada);
		nuevoContenido(new ListarPiezaRepuesto(getView()));
		NativeButton registrarInterrupcion = new NativeButton(
				"Registrar interrupción", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarInterrupcion(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarInterrupcion.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarInterrupcion, "nuevaInterrupcion");

		NativeButton listarInterrupciones = new NativeButton(
				"Listar interrupciones", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarInterrupciones(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarInterrupciones.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarInterrupciones, "listarInterrupciones");

		NativeButton registrarPiezaRepuesto = new NativeButton(
				"Registrar pieza de repuesto", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarPiezaRepuesto(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarPiezaRepuesto.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarPiezaRepuesto,
				"registrarPiezaRepuesto");

		NativeButton listarPiezaRepuesto = new NativeButton(
				"Listar pieza de repuesto", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarPiezaRepuesto(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarPiezaRepuesto.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarPiezaRepuesto,
				"listarPiezaRepuesto");

		NativeButton registrarPiezaRecuperada = new NativeButton(
				"Registrar pieza recuperada", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarPiezaRecuperada(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarPiezaRecuperada.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarPiezaRecuperada,
				"registrarPiezaRecuperada");

		NativeButton listarPiezaRecuperada = new NativeButton(
				"Listar pieza recuperada", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarPiezaRecuperada(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarPiezaRecuperada.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarPiezaRecuperada,
				"listarPiezaRecuperada");

	}

	/**
	 * Vista del tecnico
	 */
	public void cargarTecnico() {
		final Facade fachada = new FacadeImpl();
		customLayout = new CustomLayout("tecnico");
		nuevoContenido(new Dashboard(getView()));
		cargarOpcionesComun(fachada);
		NativeButton dashboard = new NativeButton("Estado del sistema",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new Dashboard(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		dashboard.setStyleName("botonesMenuLateral");
		customLayout.addComponent(dashboard, "dashboard");
		
		NativeButton registrarInterrupcion = new NativeButton(
				"Registrar interrupción", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarInterrupcion(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarInterrupcion.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarInterrupcion, "nuevaInterrupcion");

		NativeButton listarInterrupciones = new NativeButton(
				"Listar interrupciones", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarInterrupciones(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarInterrupciones.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarInterrupciones, "listarInterrupciones");

		NativeButton registrarMedioInformatico = new NativeButton(
				"Registrar medio", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarMedio(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarMedioInformatico.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarMedioInformatico,
				"registrarMedioInformatico");

		NativeButton listarMedioInfo = new NativeButton(
				"Listar medio informatico", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarMediosInformaticos(
									getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarMedioInfo.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarMedioInfo, "listarMedioInfo");
		
		NativeButton conformarPC = new NativeButton(
				"Conformar PC", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ConformarPC(
									getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		conformarPC.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(conformarPC, "conformarPC");

		NativeButton listarPC = new NativeButton(
				"Listar PC", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarPC(
									getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarPC.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarPC, "listarPC");


		NativeButton generarOrdenTrabajo = new NativeButton(
				"Generar orden de trabajo", new Button.ClickListener() {
					// Accion del boton o sea que componente va a incluir en el
					// location contenido
					@Override
					public void buttonClick(ClickEvent event) {
						try {
							GenerarOrdenTrabajo generar = new GenerarOrdenTrabajo(getView());
							generar.initComponents(getView());
							nuevoContenido(generar);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		generarOrdenTrabajo.setStyleName("botonesMenuOpciones");
		// adicionar el boton al menu
		customLayout.addComponent(generarOrdenTrabajo,
				"generarOrdenTrabajo");// este ultimo es el location
		NativeButton listarOrdenTrabajo = new NativeButton(
				"Listar ordenes de trabajo", new Button.ClickListener() {
					// Accion del boton o sea que componente va a incluir en el
					// location contenido
					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarOrdenesTrabajo(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarOrdenTrabajo.setStyleName("botonesMenuOpciones");
		// adicionar el boton al menu
		customLayout.addComponent(listarOrdenTrabajo,
				"listarOrdenesTrabajo");// este ultimo es el location
		
		NativeButton registrarMantenimientoPlanificado = new NativeButton(
				"Registrar mantenimiento", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarMantenimientoPlanificado(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarMantenimientoPlanificado.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarMantenimientoPlanificado,
				"registrarMantenimientoPlanificado");
		NativeButton listarMantenimientosPlanificados = new NativeButton(
				"Listar mantenimientos", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarMantenimientosProg(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarMantenimientosPlanificados.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarMantenimientosPlanificados, "listarMantenimientosPlanificados");

		NativeButton generarReporteMantenimiento = new NativeButton(
				"Generar reporte", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarMantenimientoEfectuado(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		generarReporteMantenimiento.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(generarReporteMantenimiento, "generarReporteMantenimiento");

		NativeButton listarOrdenesMantenimientos = new NativeButton(
				"Listar ordenes", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarMantenimientosEfectuados(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarOrdenesMantenimientos.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarOrdenesMantenimientos, "listarOrdenesMantenimientos");


	}

	/**
	 * Vista del Cliente
	 */
	public void cargarCliente() {
		final Facade fachada = new FacadeImpl();
		customLayout = new CustomLayout("custom");
		nuevoContenido(new ListarInterrupciones(this));
		cargarOpcionesComun(fachada);
		// menu lateral
		NativeButton registrarInterrupcion = new NativeButton(
				"Registrar interrupción", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarInterrupcion(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarInterrupcion.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarInterrupcion, "nuevaInterrupcion");

		NativeButton listarInterrupciones = new NativeButton(
				"Listar interrupciones", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarInterrupciones(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarInterrupciones.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarInterrupciones, "listarInterrupciones");
	}

	/**
	 * Vista de Administrador
	 */
	public void cargarDashboard() {
		final Facade fachada = new FacadeImpl();
		customLayout = new CustomLayout("index");
		nuevoContenido(new Dashboard(this));
		cargarOpcionesComun(fachada);
		// Menu lateral

		NativeButton dashboard = new NativeButton("Estado del sistema",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new Dashboard(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		dashboard.setStyleName("botonesMenuLateral");
		customLayout.addComponent(dashboard, "dashboard");

		NativeButton registrarInterrupcion = new NativeButton(
				"Registrar interrupción", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarInterrupcion(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarInterrupcion.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarInterrupcion, "nuevaInterrupcion");

		NativeButton registrarUsuario = new NativeButton("Registrar usuario",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarUsuario(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarUsuario.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarUsuario, "registrarUsuario");

		NativeButton listarUsuarios = new NativeButton("Listar usuarios",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarUsuario(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarUsuarios.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarUsuarios, "listarUsuarios");

		NativeButton listarInterrupciones = new NativeButton(
				"Listar interrupciones", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarInterrupciones(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarInterrupciones.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarInterrupciones, "listarInterrupciones");

		NativeButton registrarMedioInformatico = new NativeButton(
				"Registrar medio", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarMedio(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarMedioInformatico.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarMedioInformatico,
				"registrarMedioInformatico");

		NativeButton listarMedioInfo = new NativeButton(
				"Listar medios", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarMediosInformaticos(
									getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarMedioInfo.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarMedioInfo, "listarMedioInfo");

		NativeButton conformarPC = new NativeButton(
				"Conformar PC", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ConformarPC(
									getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		conformarPC.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(conformarPC, "conformarPC");

		NativeButton listarPC = new NativeButton(
				"Listar PC", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarPC(
									getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarPC.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarPC, "listarPC");

		NativeButton registrarPiezaRepuesto = new NativeButton(
				"Registrar pieza de repuesto", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarPiezaRepuesto(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarPiezaRepuesto.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarPiezaRepuesto,
				"registrarPiezaRepuesto");

		NativeButton listarPiezaRepuesto = new NativeButton(
				"Listar pieza de repuesto", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarPiezaRepuesto(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarPiezaRepuesto.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarPiezaRepuesto,
				"listarPiezaRepuesto");

		NativeButton registrarPiezaRecuperada = new NativeButton(
				"Registrar pieza recuperada", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarPiezaRecuperada(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarPiezaRecuperada.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarPiezaRecuperada,
				"registrarPiezaRecuperada");

		NativeButton listarPiezaRecuperada = new NativeButton(
				"Listar pieza recuperada", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarPiezaRecuperada(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarPiezaRecuperada.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarPiezaRecuperada,
				"listarPiezaRecuperada");

		NativeButton generarOrdenTrabajo = new NativeButton(
				"Generar orden de trabajo", new Button.ClickListener() {
					// Accion del boton o sea que componente va a incluir en el
					// location contenido
					@Override
					public void buttonClick(ClickEvent event) {
						try {
							GenerarOrdenTrabajo generar = new GenerarOrdenTrabajo(getView());
							generar.initComponents(getView());
							nuevoContenido(generar);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		generarOrdenTrabajo.setStyleName("botonesMenuOpciones");
		// adicionar el boton al menu
		customLayout.addComponent(generarOrdenTrabajo,
				"generarOrdenTrabajo");// este ultimo es el location
		NativeButton listarOrdenTrabajo = new NativeButton(
				"Listar ordenes de trabajo", new Button.ClickListener() {
					// Accion del boton o sea que componente va a incluir en el
					// location contenido
					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarOrdenesTrabajo(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarOrdenTrabajo.setStyleName("botonesMenuOpciones");
		// adicionar el boton al menu
		customLayout.addComponent(listarOrdenTrabajo,
				"listarOrdenesTrabajo");// este ultimo es el location

		// elementos del menu de administracion
		NativeButton registrarDepartamento = new NativeButton(
				"Registrar departamento", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarDepartamento(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarDepartamento.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarDepartamento,
				"registrarDepartamento");
		NativeButton listarDepartamento = new NativeButton(
				"Listar departamento", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarDepartamento(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarDepartamento.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarDepartamento, "listarDepartamento");

		NativeButton registrarTipoMedio = new NativeButton(
				"Registrar tipo de medio", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarTipoMedio(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarTipoMedio.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarTipoMedio, "registrarTipoMedio");

		NativeButton listarTipoMedio = new NativeButton(
				"Listar tipo de medio", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarTipoMedio(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarTipoMedio.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarTipoMedio, "listarTipoMedio");

		// Mantenimientos

		NativeButton registrarMantenimientoPlanificado = new NativeButton(
				"Registrar mantenimiento", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarMantenimientoPlanificado(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		registrarMantenimientoPlanificado.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(registrarMantenimientoPlanificado,
				"registrarMantenimientoPlanificado");
		NativeButton listarMantenimientosPlanificados = new NativeButton(
				"Listar mantenimientos", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarMantenimientosProg(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarMantenimientosPlanificados.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarMantenimientosPlanificados, "listarMantenimientosPlanificados");

		NativeButton generarReporteMantenimiento = new NativeButton(
				"Generar reporte", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new RegistrarMantenimientoEfectuado(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		generarReporteMantenimiento.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(generarReporteMantenimiento, "generarReporteMantenimiento");

		NativeButton listarOrdenesMantenimientos = new NativeButton(
				"Listar ordenes", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ListarMantenimientosEfectuados(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		listarOrdenesMantenimientos.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(listarOrdenesMantenimientos, "listarOrdenesMantenimientos");

		NativeButton configurarCorreo = new NativeButton(
				"Configurar correo", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							nuevoContenido(new ConfigurarCorreo(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		configurarCorreo.setStyleName("botonesMenuOpciones");
		customLayout.addComponent(configurarCorreo, "configurarCorreo");

	}

	/**
	 * Permite cargar opciones comunes en las vistas, cerrar sesion, editar
	 * perfil puedes annadir otras
	 * 
	 * @param fachada
	 */

	public void cargarOpcionesComun(final Facade fachada) {
		Label usuario = new Label(VaadinSession.getCurrent()
				.getAttribute("usuario").toString());
		usuario.setStyleName("usuario");
		customLayout.addComponent(usuario, "usuario");
		NativeButton logout = new NativeButton("Salir",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							fachada.cerrarSesion(VaadinSession.getCurrent()
									.getAttribute("usuario").toString());
							VaadinSession.getCurrent().setAttribute("rol",
									"Invitado");
							VaadinSession.getCurrent().setAttribute("usuario",
									"Invitado");
							modificarVista();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		NativeButton editarPerfil = new NativeButton("Perfil",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							EditarPerfil edit = new EditarPerfil(getView(),
									VaadinSession.getCurrent()
											.getAttribute("usuario").toString());
							edit.initComponents(getView());
							nuevoContenido(edit);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		logout.setStyleName("botonesMenu");
		editarPerfil.setStyleName("botonesMenu");

		NativeButton ajustes = new NativeButton("Ajustes",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {

							nuevoContenido(new Ajustes(getView()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		ajustes.setStyleName("botonesMenu");

		NativeButton logout2 = new NativeButton("Salir",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							fachada.cerrarSesion(VaadinSession.getCurrent()
									.getAttribute("usuario").toString());
							VaadinSession.getCurrent().setAttribute("rol",
									"Invitado");
							VaadinSession.getCurrent().setAttribute("usuario",
									"Invitado");
							modificarVista();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		logout2.setStyleName("botonesMenuLateral");
		customLayout.addComponent(logout, "logout");
		customLayout.addComponent(logout2, "logout2");
		customLayout.addComponent(editarPerfil, "perfil");
		customLayout.addComponent(ajustes, "ajustes");
	}

	public Administrar getView() {
		return this;
	}

}
