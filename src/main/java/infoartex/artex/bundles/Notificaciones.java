package infoartex.artex.bundles;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class Notificaciones {

    public Notificaciones() {
    }

    public static void NotificarError(String text) {
        Notification err = new Notification(text, Notification.Type.ERROR_MESSAGE);
        err.setPosition(Position.BOTTOM_RIGHT);
        err.setIcon(new ThemeResource("img/block.png"));
        err.setHtmlContentAllowed(true);        
        err.setStyleName("Error");
        err.show(Page.getCurrent());
    }

    public static void NotificarSubmit(String text) {
        Notification err = new Notification(text, Notification.Type.ERROR_MESSAGE);
        err.setPosition(Position.BOTTOM_RIGHT);
        err.setHtmlContentAllowed(true);
        err.setIcon(new ThemeResource("img/este.png"));
        err.setDelayMsec(1000);
        err.setStyleName("Submit");
        err.show(Page.getCurrent());
    }
    public static void NotificarAdvertencia(String text){
     Notification err = new Notification(text, Notification.Type.ERROR_MESSAGE);
        err.setPosition(Position.BOTTOM_RIGHT);
        err.setHtmlContentAllowed(true);
        err.setIcon(new ThemeResource("img/warn.png"));
        err.setDelayMsec(1000);
        err.setStyleName("Warning");
        err.show(Page.getCurrent());
    }
}
