package workout.abstractions;

import workout.implementors.WorkoutSyncImplementor;
import workout.models.SyncException;
import workout.models.WorkoutData;

public class CardioTracker extends WorkoutTracker {
    private final int avgHeartRate;
    private final double distanceKm;

    public CardioTracker(WorkoutSyncImplementor syncImplementor, int avgHeartRate, double distanceKm) {
        super(syncImplementor);
        this.avgHeartRate = avgHeartRate;
        this.distanceKm = distanceKm;
    }

    @Override
    public void recordSession(String athleteId) throws SyncException {
        // Кардио ерекшелігі: қашықтыққа байланысты калория есептеу
        int estimatedCalories = (int) (distanceKm * 65);
        WorkoutData data = new WorkoutData("CARDIO (HR: " + avgHeartRate + " bpm)", 45, estimatedCalories);

        // Орындаушыға делегация жасау
        syncImplementor.sendWorkoutData(athleteId, data);
    }
}