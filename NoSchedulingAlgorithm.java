import java.util.*;

public final class NoSchedulingAlgorithm extends ProcessSchedulingAlgorithm {
    public NoSchedulingAlgorithm() {
    	super(0);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
    }
    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess) {
        if (!_submittedQueue.contains(readyProcess))
            _submittedQueue.addLast(readyProcess);
        if (info.getActiveProcess() == null) {
            info.setActive(_submittedQueue.getFirst());
        }
    }
    public void processYield(ScheduleInformation info) {
        if (info.getActiveProcess().getState() == ProcessState.Finished) {
            _submittedQueue.remove(info.getActiveProcess());
            if (_submittedQueue.isEmpty()) {
                info.setActive(null);
            } else {
                info.setActive(_submittedQueue.getFirst());
            }
        }
    }

    private LinkedList<ProcessControlBlock> _submittedQueue;
}