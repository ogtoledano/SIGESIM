package infoartex.artex.componentes;

import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import infoartex.artex.vistas.Administrar;
import infoartex.artex.bundles.ConfirmDialog;
import infoartex.artex.dominio.Usuario;



@SuppressWarnings("serial")
public class ListarUsuarios extends ComponenteGenerico{

	private final CustomLayout container;
    private final VerticalLayout main;
	
	public ListarUsuarios(final Administrar view) {
		super(view);
		main = new VerticalLayout();
        container=new CustomLayout("formulario");
        initComponents(view);
	}

	private void initComponents(final Administrar view) {
		Table tabla=new Table("Listado de usuarios");
		//columnas
		tabla.addContainerProperty("Usuario", String.class, null);
		tabla.addContainerProperty("Nombre y apellidos", String.class, null);
		tabla.addContainerProperty("Opciones", HorizontalLayout.class, null);
		cargarDatos(tabla);
		tabla.setSizeFull();
		tabla.setSelectable(true);
		//tabla.setImmediate(true);
		tabla.setPageLength(tabla.size());
		
		 NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

	            @Override
	            public void buttonClick(Button.ClickEvent event) {
	                view.contenidoAnterior();
	            }
	        });
		 cancelar.setStyleName("btn btn-danger");
		 HorizontalLayout reg=new HorizontalLayout();
	     reg.addComponents(cancelar);
	     reg.setSizeFull();
	     reg.setSpacing(true);
	     reg.setStyleName("controles");
		main.addComponents(tabla,reg);
		container.addComponent(main, "formulario");
		setCompositionRoot(container);
	}
	
	private void cargarDatos(final Table tabla){
		@SuppressWarnings("unchecked")
		List<Usuario> lista=(List<Usuario>)fachada.listarTodos(Usuario.class);
		for(final Usuario user:lista){
		HorizontalLayout hor=new HorizontalLayout();
		NativeButton eliminar= new NativeButton("Eliminar", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
					ConfirmDialog dialogo=ConfirmDialog.show(getUI(), "Ejecutar acción",
			                "<h4>¿Desea realmente eliminar el usuario?</h4>",
			                "Aceptar", "Cancelar", new ConfirmDialog.Listener(
			                		) {
								
								@Override
								public void onClose(ConfirmDialog dialog) {
									if(dialog.isConfirmed()){
										try {
										fachada.eliminarEntidad(user.getId(),Usuario.class);
										tabla.removeItem(user.getId());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							});	
					dialogo.setStyleName(Reindeer.WINDOW_LIGHT);
					dialogo.setContentMode(ConfirmDialog.ContentMode.HTML);
					dialogo.setHeight("16em");
					dialogo.getCancelButton().setStyleName("btn btn-danger");
					dialogo.getOkButton().setStyleName("btn btn-info");
			}
		});
		eliminar.setStyleName("btn btn-danger");
		hor.addComponents(eliminar);
		hor.setSizeFull();
		tabla.addItem(new Object[]{user.getUsuario(),user.getNombre()+" "+user.getApellidos(),hor}, user.getId());
		}
		
	}

}
