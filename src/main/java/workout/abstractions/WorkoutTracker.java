package workout.abstractions;

import workout.implementors.WorkoutSyncImplementor;
import workout.models.SyncException;

public abstract class WorkoutTracker {
    // Bridge байланысы: интерфейс арқылы орындаушыға сілтеме жасайды
    protected WorkoutSyncImplementor syncImplementor;

    public WorkoutTracker(WorkoutSyncImplementor syncImplementor) {
        this.syncImplementor = syncImplementor;
    }

    // Орындаушыны динамикалық түрде өзгертуге мүмкіндік береді
    public void setSyncImplementor(WorkoutSyncImplementor syncImplementor) {
        this.syncImplementor = syncImplementor;
    }

    // Барлық жаттығу түрлері жүзеге асыруы тиіс негізгі әдіс
    public abstract void recordSession(String athleteId) throws SyncException;
}