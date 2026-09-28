package workout.implementors;

import workout.models.SyncException;
import workout.models.WorkoutData;

public class LocalDatabaseSyncImplementor implements WorkoutSyncImplementor {
    @Override
    public void sendWorkoutData(String athleteId, WorkoutData data) throws SyncException {
        if (athleteId == null || athleteId.isBlank()) {
            throw new SyncException("Спортшының ID-і бос болмауы керек!");
        }
        System.out.println("[LocalDbSync] Телефонның жергілікті базасына жазылды -> Спортшы: "
                + athleteId + ", Мәлімет: " + data.toPayload());
    }
}