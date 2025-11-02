[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)


## IASD (32)

# TP1 — Design Pattern Observer (résumé)

Ce dépôt contient un petit exercice sur le pattern Observer (Génie Logiciel — TP1).
Le but : utiliser une abstraction `TimerService` pour notifier des clients (horloges, compte-à-rebours) à chaque tick temporel.

## Ce que j'ai fait

- Corrigé et amélioré l'implémentation fournie du service de temps :

  - `time-service-impl/src/main/java/org/emp/gl/time/service/impl/DummyTimeServiceImpl.java`
  - Correction : les notifications pour les minutes et heures envoyaient la mauvaise valeur — corrigé.
  - Amélioration : les notifications sont envoyées en itérant sur une copie des listeners pour éviter les
    ConcurrentModificationException quand un listener se désinscrit pendant la notification.

- Modifié la classe `Horloge` pour respecter l'injection et l'abstraction :

  - `timer-service-client/src/main/java/org/emp/gl/clients/Horloge.java`
  - `Horloge` implémente `TimerChangeListener`, reçoit un `TimerService` via le constructeur,
    s'enregistre (`addTimeChangeListener(this)`) et affiche l'heure à chaque changement de seconde.

- Ajouté une classe `CompteARebours` :

  - `timer-service-client/src/main/java/org/emp/gl/clients/CompteARebours.java`
  - Décrémente son compteur à chaque seconde et se désinscrit automatiquement quand il atteint 0.

- Ajouté une classe d'amorce (`main`) pour tester facilement :

  - `timer-service-client/src/main/java/org/emp/gl/clients/Amorce.java`
  - Instancie `DummyTimeServiceImpl`, crée plusieurs `Horloge` et plusieurs `CompteARebours` (dont un à 5
    et dix compteurs aléatoires entre 10 et 20), puis laisse l'application tourner 30 s pour observer.

- Mis à jour `timer-service-client/pom.xml` pour ajouter une dépendance à `time-service-impl` — nécessaire
  pour compiler `Amorce` qui instancie `DummyTimeServiceImpl`.

## Pourquoi `DummyTimeServiceImpl` existe

- C'est une implémentation concrète de `TimerService` fournie pour le TP. Elle simule l'écoulement du temps
  (via `java.util.Timer`) et notifie les `TimerChangeListener` enregistrés sur les changements
  (dixième, seconde, minute, heure).
- Utilisation : instancier cette implémentation dans `main` et l'injecter dans les clients. Les clients
  ne doivent dépendre que de l'interface `TimerService` (principe d'inversion de dépendance).

## Pourquoi il y avait des bogues

- Si un listener s'enlève lui-même lors de la notification (par exemple `CompteARebours` se désinscrit
  quand il arrive à 0), l'itération sur la liste `listeners` provoque parfois une
  `ConcurrentModificationException`.
- Correction appliquée : itérer sur une copie (`new LinkedList<>(listeners)`) lors de la propagation des événements.
- Alternative recommandée (TP e) : utiliser `java.beans.PropertyChangeSupport` et faire hériter
  `TimerChangeListener` de `PropertyChangeListener` — c'est plus robuste et standard.

## Comment compiler et exécuter

Les commandes ci‑dessous sont à exécuter depuis la racine du projet (Windows PowerShell) :

1. clean the project and install 
```powershell
mvn clean install
```

2. Compiler le module client (et ses dépendances) :

```powershell
mvn -pl timer-service-client -am -DskipTests package
```

3. Lancer la classe `Amorce` pour observer les horloges et compte-à-rebours :

```powershell
mvn -pl timer-service-client exec:java "-Dexec.mainClass=org.emp.gl.clients.Amorce"
```

Ou ouvrir le module `timer-service-client` dans votre IDE et exécuter `org.emp.gl.clients.Amorce`.

## Suggestions / prochaines étapes

- Implémenter l'étape (e) du TP : refactorer pour utiliser `PropertyChangeSupport` (plus sûr, thread-safe).
- Ajouter des tests unitaires pour `Horloge` et `CompteARebours` (mock `TimerService` ou utilisation d'une
  implémentation de test).
- Bonus : créer une petite interface graphique (Swing/JavaFX) qui s'abonne au `TimerService` pour afficher l'heure.

---

Fichier généré automatiquement : ce README résume les modifications effectuées et comment reconstruire
et exécuter l'exemple d'amorce.
