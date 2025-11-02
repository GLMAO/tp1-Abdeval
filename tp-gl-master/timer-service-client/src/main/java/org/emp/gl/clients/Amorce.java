package org.emp.gl.clients ;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

import java.util.Random;


public class Amorce {

    public static void main(String[] args) throws InterruptedException {
        
        TimerService timer = new DummyTimeServiceImpl();

        // create some Horloges
        new Horloge("H1", timer);
        new Horloge("H2", timer);
        new Horloge("H3", timer);

        // un compte a regours a 5
        new CompteARebours("C5", 5, timer);

         // instancier 10 compteARebours aléatoires entre 10 et 20
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int start = 10 + rnd.nextInt(11); // 10..20
            new CompteARebours("C" + (i + 1), start, timer);
        }

        // laisser l'application tourner un certain temps pour observer
        Thread.sleep(30000); // 30s
        System.out.println("Fin Amorce");
    }
}