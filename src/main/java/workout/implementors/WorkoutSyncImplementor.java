package workout.implementors;

import workout.models.SyncException;
import workout.models.WorkoutData;

public interface WorkoutSyncImplementor {
    void sendWorkoutData(String athleteId, WorkoutData data) throws SyncException;
}