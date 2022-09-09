public class LamportClock {
    // 
    Integer LC = 0;

    public Integer get() {
        return LC;
    }

    public void Increment() {
        LC += 1;
    }

    public void Set(Integer LCa, Integer LCb) {
        LC = Math.max(LCa, LCb) + 1;
    }

    // only lamport clocks, not mutual exclusiomn 
}
