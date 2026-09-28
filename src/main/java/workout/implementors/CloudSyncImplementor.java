package workout.implementors;

import workout.models.SyncException;
import workout.models.WorkoutData;

public class CloudSyncImplementor implements WorkoutSyncImplementor {
    @Override
    public void sendWorkoutData(String athleteId, WorkoutData data) throws SyncException {
        if (athleteId == null || athleteId.isBlank()) {
            throw new SyncException("Спортшының ID-і бос болмауы керек!");
        }
        System.out.println("[CloudSync] Деректер бұлттық серверге сәтті жіберілді -> Спортшы: "
                + athleteId + ", Жүктеме: " + data.toPayload());
    }
}