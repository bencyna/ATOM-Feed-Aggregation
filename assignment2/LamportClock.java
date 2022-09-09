import java.util.Collections;

public class LamportClock {
    // 
    Integer LC;

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


    // LCas = max(LCac, LCcs) + 1
    // this.lc = above


}
