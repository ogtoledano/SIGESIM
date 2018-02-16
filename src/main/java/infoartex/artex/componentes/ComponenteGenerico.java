package infoartex.artex.componentes;

import infoartex.artex.fachada.Facade;
import infoartex.artex.fachada.impl.FacadeImpl;
import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public class ComponenteGenerico extends CustomComponent {

    protected Facade fachada;
    protected CustomComponent view;

    public ComponenteGenerico(CustomComponent view) {
        super();
        this.view = view;
        fachada = new FacadeImpl();
    }

    public Facade getFachada() {
        return fachada;
    }

    public void setFachada(Facade fachada) {
        this.fachada = fachada;
    }
}
