import java.util.LinkedList;

public final class FirstComeFirstServed extends ProcessSchedulingAlgorithm{

    public FirstComeFirstServed()
    {
        //non-preemptive: 0
        super(0);
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
        //if active process done or waiting, remove it, activate next or stop
        if(info.getActiveProcess().getState() == ProcessState.Finished
        || info.getActiveProcess().getState() == ProcessState.Waiting){
            _submittedQueue.remove(info.getActiveProcess());
            if(_submittedQueue.isEmpty())
                info.setActive(null);
            else
                info.setActive(_submittedQueue.getFirst());
        }
    }

    private LinkedList<ProcessControlBlock> _submittedQueue;
}
