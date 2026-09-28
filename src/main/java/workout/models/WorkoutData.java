package workout.models;

public class WorkoutData {
    private final String workoutType;
    private final int durationMinutes;
    private final int caloriesBurned;

    public WorkoutData(String workoutType, int durationMinutes, int caloriesBurned) {
        this.workoutType = workoutType;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public String toPayload() {
        return workoutType + ";" + durationMinutes + ";" + caloriesBurned;
    }
}