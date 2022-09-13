public class QueueContent {
    public int priority;
    public String content;

    QueueContent(String content) {
        this.content = content;
        // calculate the priority based on the content
    }

    public Integer getPriority() {
        return priority;
    }
}

