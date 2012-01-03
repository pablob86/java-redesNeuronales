/*
 * RedesNeuronalesApp.java
 */

package redesneuronales;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * La clase principal de la aplicación. Se encarga de "lanzar" la ventana principal
 */
public class RedesNeuronalesApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new RedesNeuronalesView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     * @param root
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of RedesNeuronalesApp
     */
    public static RedesNeuronalesApp getApplication() {
        return Application.getInstance(RedesNeuronalesApp.class);
    }

    /**
     * Main method launching the application.
     * @param args 
     */
    public static void main(String[] args) {
        launch(RedesNeuronalesApp.class, args);
    }
}
