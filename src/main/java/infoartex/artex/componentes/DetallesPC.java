package infoartex.artex.componentes;


import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.vistas.Administrar;

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

public class DetallesPC extends ComponenteGenerico {
	
	    //obligado
		private final CustomLayout container;
	    private final VerticalLayout main;

	public DetallesPC (final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
        initComponents(view);
	}
	
	private void initComponents(final Administrar view) {
		Panel datosBasico = new Panel("Rellene las propiedades de la Torre");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        
        final TextField marca = new TextField("Marca");
        marca.setNullRepresentation("");
        marca.setImmediate(true);
        marca.setWidth("50%");
        
        final TextField fuente = new TextField("Fuente (W)");
        fuente.setNullRepresentation("");
        fuente.setImmediate(true);
        fuente.setWidth("20%");
        
        final TextField board = new TextField("Motherboard Modelo");
        board.setNullRepresentation("");
        board.setImmediate(true);
        board.setWidth("50%");
        
        final TextField procesador = new TextField("Procesador Modelo + Velocidad");
        procesador.setNullRepresentation("");
        procesador.setImmediate(true);
        procesador.setWidth("50%");
        
        final TextField ram = new TextField("RAM (Mbytes Tipo + Cap)");
        ram.setNullRepresentation("");
        ram.setImmediate(true);
        ram.setWidth("20%");
  
        
        final TextField hdd = new TextField("HDD (Modelo + Cap)");
        hdd.setNullRepresentation("");
        hdd.setImmediate(true);
        hdd.setWidth("50%");
        
        final TextField optica = new TextField("Unidad Optica");
        optica.setNullRepresentation("");
        optica.setImmediate(true);
        optica.setWidth("50%");
        
        final TextField modeminterno = new TextField("Modem Interno");
        modeminterno.setNullRepresentation("");
        modeminterno.setImmediate(true);
        modeminterno.setWidth("50%");
        
        final TextField otros = new TextField("Otros dispositivos");
        otros.setNullRepresentation("");
        otros.setImmediate(true);
        otros.setWidth("50%");
        
        formularioBasico.addComponents(marca,fuente,board,procesador,ram,hdd,optica,modeminterno,otros);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
       // final BeanFieldGroup<DetallesTorre> validador=new BeanFieldGroup<>(DetallesTorre.class);
       // validador.setItemDataSource(new DetallesTorre());
      //  validador.bind(marca, "marca");
      //  validador.bind(fuente, "fuente");
      //  validador.bind(board, "board");
      //  validador.bind(ram, "ram");
       // validador.bind(hdd, "hdd");
       // validador.bind(optica, "optica");
       // validador.bind(modeminterno, "modeminterno");
      //  validador.bind(otros, "otros");
    
        HorizontalLayout controles=new HorizontalLayout();
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	//registrar en la BD
                	//ejecutar validacion
                   // validador.commit();	
                    
                    // validar select validador.getItemDataSource().getBean().setTipo(tipo.getValue().toString());
                   // getFachada().registrarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Medio informatico registrado correctamente");
                    marca.setValue("");
                    fuente.setValue("");
                    board.setValue("");
                    ram.setValue("");
                    hdd.setValue("");
                    optica.setValue("");
                    modeminterno.setValue("");
                    otros.setValue("");
                	//actualizar(validador.getItemDataSource().getBean().getClass());
                	
                } catch (Exception ex) {
                	Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                    //Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        
        registrar.setStyleName("btn btn-info");
        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                view.contenidoAnterior();
            }
        });
        cancelar.setStyleName("btn btn-danger");
        controles.addComponents(registrar,cancelar);
        main.addComponents(datosBasico,controles);
        container.addComponent(main,"formulario");
        setCompositionRoot(container);
        }
        
        public void actualizar(Class<?> clazz){
        	Label medios =new Label(fachada.cantidadElementos(clazz)+" PC Registrada");
        	medios.setStyleName("text-right");
            container.addComponent(medios, "datos");
        
	}
	
	

}
