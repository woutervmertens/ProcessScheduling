import java.util.HashMap;
import java.util.LinkedList;

public class DynamicPriority extends ProcessSchedulingAlgorithm{

    public DynamicPriority()
    {
        //preemptive: 40
        super(40);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
        _readyTime = new HashMap<ProcessControlBlock, Integer>();
    }

    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess) {
        //if process is ready, add it to hashmap
        if(readyProcess.getState() == ProcessState.Ready)
            _readyTime.put(readyProcess, info.getCurrentTime());

        //if process not yet in queue, add it to the end
        if(!_submittedQueue.contains(readyProcess))
            _submittedQueue.addLast(readyProcess);

        //activate highest priority process in queue
        info.setActive(highestPriorityProcess(info.getCurrentTime()));
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
            info.setActive(highestPriorityProcess(info.getCurrentTime()));
    }

    //returns highest priority process (highest = smallest)
    private ProcessControlBlock highestPriorityProcess(Integer time)
    {
        ProcessControlBlock highest = _submittedQueue.getFirst();
        for (ProcessControlBlock p : _submittedQueue) {
            if((p.getPriority() - getDynamicPriority(time,p)) < (highest.getPriority() - getDynamicPriority(time,highest)))
                highest = p;
        }
        return highest;
    }

    //Calculate dynamic priority: +1 every 100 timeunits
    private Integer getDynamicPriority(Integer currentTime, ProcessControlBlock process){
        Integer delta = currentTime - _readyTime.get(process);
        Integer dynamicPriority = 0;
        if(delta > 100)
        {
            dynamicPriority = delta / 100;
        }
        return dynamicPriority;
    }

    private LinkedList<ProcessControlBlock> _submittedQueue;
    private final HashMap<ProcessControlBlock, Integer> _readyTime;
}
