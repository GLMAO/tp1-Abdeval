package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * CompteARebours : décrémente un compteur à chaque seconde et se désinscrit quand il atteint 0
 */
public class CompteARebours implements TimerChangeListener {

    private final String name;
    private int remaining;
    private final TimerService timerService;

    public CompteARebours(String name, int startValue, TimerService timerService) {
        this.name = name;
        this.remaining = startValue;
        this.timerService = timerService;

        System.out.println("CompteARebours " + name + " initialisé à " + startValue);
        if (this.timerService != null) {
            this.timerService.addTimeChangeListener(this);
        }
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            tick();
        }
    }

    private synchronized void tick() {
        if (remaining <= 0) return;
        remaining--;
        System.out.println(name + " -> " + remaining);
        if (remaining <= 0) {
            System.out.println(name + " termine. Désinscription.");
            if (timerService != null) {
                timerService.removeTimeChangeListener(this);
            }
        }
    }
}
