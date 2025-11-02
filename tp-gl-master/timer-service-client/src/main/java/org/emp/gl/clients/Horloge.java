package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * Horloge dépend uniquement de l'abstraction TimerService.
 * Elle s'enregistre comme TimerChangeListener pour afficher l'heure
 * à chaque seconde.
 */
public class Horloge implements TimerChangeListener {

    private final String name;
    private final TimerService timerService;

    public Horloge(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;

        System.out.println("Horloge " + name + " initialized!");

        if (this.timerService != null) {
            this.timerService.addTimeChangeListener(this);
        }
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // n'afficher que sur le changement de seconde pour limiter le bruit
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            afficherHeure();
        }
    }

    public void afficherHeure() {
        if (timerService != null)
            System.out.println(name + " affiche " +
                    timerService.getHeures() + ":" +
                    timerService.getMinutes() + ":" +
                    timerService.getSecondes());
    }

}
