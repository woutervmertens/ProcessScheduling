import java.util.LinkedList;

public class Priority extends ProcessSchedulingAlgorithm{

    public Priority()
    {
        //preemptive: 40
        super(40);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
    }

    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess) {
        //if process not yet in queue, add it to the end
        if(!_submittedQueue.contains(readyProcess))
            _submittedQueue.addLast(readyProcess);

        //activate highest priority process in queue
        info.setActive(highestPriorityProcess());
    }


    public void processYield(ScheduleInformation info) {
        //remove active process from queue, if not finished or waiting add it to the end again
        _submittedQueue.remove(info.getActiveProcess());
        if(!(info.getActiveProcess().getState() == ProcessState.Finished
                || info.getActiveProcess().getState() == ProcessState.Waiting)) {
            _submittedQueue.addLast(info.getActiveProcess());
        }
        //if empty queue, stop, else next highest priority process
        if(_submittedQueue.isEmpty())
            info.setActive(null);
        else
            info.setActive(highestPriorityProcess());
    }

    //returns highest priority process (highest = smallest)
    private ProcessControlBlock highestPriorityProcess()
    {
        ProcessControlBlock highest = _submittedQueue.getFirst();
        for (ProcessControlBlock p : _submittedQueue) {
            if(p.getPriority() < highest.getPriority())
                highest = p;
        }
        return highest;
    }

    private LinkedList<ProcessControlBlock> _submittedQueue;

}
