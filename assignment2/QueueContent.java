public class QueueContent {
    public int priority;
    public String content;

    QueueContent(String content) {
        this.content = content;

        String head = content.split("<!endline!>;")[0];

        Integer LC = Integer.parseInt(head.split("lc:")[1]);
        this.priority = LC;
    }

    public Integer getPriority() {
        return priority;
    }
    public String getContent() {
        return content;
    }
}

