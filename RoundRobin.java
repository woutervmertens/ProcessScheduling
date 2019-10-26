import java.util.LinkedList;

public class RoundRobin extends ProcessSchedulingAlgorithm{

    public RoundRobin()
    {
        //preemptive: 40
        super(40);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
    }

    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess) {
        //if process not yet in queue, add it to the end
        if(!_submittedQueue.contains(readyProcess))
            _submittedQueue.addLast(readyProcess);

        //if no active process, activate first in queue
        if(info.getActiveProcess() == null)
            info.setActive(_submittedQueue.getFirst());
    }


    public void processYield(ScheduleInformation info) {
        //remove active process from queue, if not finished or waiting add it to the end again
        _submittedQueue.remove(info.getActiveProcess());
        if(!(info.getActiveProcess().getState() == ProcessState.Finished
                || info.getActiveProcess().getState() == ProcessState.Waiting)) {
            _submittedQueue.addLast(info.getActiveProcess());
        }
        //if empty queue, stop, else next process
        if(_submittedQueue.isEmpty())
            info.setActive(null);
        else
            info.setActive(_submittedQueue.getFirst());

    }

    private LinkedList<ProcessControlBlock> _submittedQueue;
}
