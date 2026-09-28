package workout;

import workout.abstractions.CardioTracker;
import workout.abstractions.StrengthTracker;
import workout.abstractions.WorkoutTracker;
import workout.implementors.DynamicSyncFactory;
import workout.implementors.WorkoutSyncImplementor;
import workout.models.SyncException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== 1. Динамикалық бұлттық синхрондау (ONLINE) ===");
            WorkoutSyncImplementor cloudImpl = DynamicSyncFactory.createImplementor("ONLINE", null);
            WorkoutTracker cardio = new CardioTracker(cloudImpl, 150, 6.5);
            cardio.recordSession("ATHLETE-001");

            System.out.println("\n=== 2. Жергілікті базаға синхрондау (OFFLINE) ===");
            WorkoutSyncImplementor localImpl = DynamicSyncFactory.createImplementor("OFFLINE", null);
            WorkoutTracker boxing = new StrengthTracker(localImpl, 12, 36);
            boxing.recordSession("ATHLETE-001");

            System.out.println("\n=== 3. Адаптер арқылы Polar құрылғысына жіберу ===");
            WorkoutSyncImplementor polarImpl = DynamicSyncFactory.createImplementor("OFFLINE", "00:1A:7D:DA:71:13");
            cardio.setSyncImplementor(polarImpl); // Bridge арқылы орындаушыны ауыстыру
            cardio.recordSession("ATHLETE-001");

        } catch (SyncException e) {
            System.err.println("Қате орын алды: " + e.getMessage());
        }
    }
}