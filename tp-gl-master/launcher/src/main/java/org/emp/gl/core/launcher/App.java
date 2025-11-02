package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge ;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        testDuTimeService();
    }

    private static void testDuTimeService() {
        // instancier le service de temps (implémentation fournie)
        TimerService timer = new DummyTimeServiceImpl();

        // fournir le service à l'horloge (injection de dépendance)
        Horloge horloge = new Horloge("Num 1", timer);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
