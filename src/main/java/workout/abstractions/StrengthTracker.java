package workout.abstractions;

import workout.implementors.WorkoutSyncImplementor;
import workout.models.SyncException;
import workout.models.WorkoutData;

public class StrengthTracker extends WorkoutTracker {
    private final int roundsOrSets;
    private final int totalReps;

    public StrengthTracker(WorkoutSyncImplementor syncImplementor, int roundsOrSets, int totalReps) {
        super(syncImplementor);
        this.roundsOrSets = roundsOrSets;
        this.totalReps = totalReps;
    }

    @Override
    public void recordSession(String athleteId) throws SyncException {
        // Күш жаттығуының ерекшелігі: раундтарға байланысты калория есептеу
        int estimatedCalories = roundsOrSets * 45;
        WorkoutData data = new WorkoutData("STRENGTH (Sets: " + roundsOrSets + ", Reps: " + totalReps + ")", 60, estimatedCalories);

        // Орындаушыға делегация жасау
        syncImplementor.sendWorkoutData(athleteId, data);
    }
}