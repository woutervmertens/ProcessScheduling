import java.util.LinkedList;

public class ShortestJobFirst extends ProcessSchedulingAlgorithm{

    public ShortestJobFirst()
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
        //if active process done or waiting, remove it, activate next-shortest or stop
        if(info.getActiveProcess().getState() == ProcessState.Finished
                || info.getActiveProcess().getState() == ProcessState.Waiting){
            _submittedQueue.remove(info.getActiveProcess());
            if(_submittedQueue.isEmpty())
                info.setActive(null);
            else
                info.setActive(shortestProcess());
        }
    }

    //Return the shortest process in the queue
    private ProcessControlBlock shortestProcess()
    {
        ProcessControlBlock shortest = _submittedQueue.getFirst();
        for(ProcessControlBlock p : _submittedQueue)
        {
            if(p.calculateNextBurstEstimate() < shortest.calculateNextBurstEstimate())
                shortest = p;
        }
        return shortest;
    }

    private LinkedList<ProcessControlBlock> _submittedQueue;
}
