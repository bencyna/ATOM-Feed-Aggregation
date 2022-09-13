public class QueueContent {
    public int priority;
    public String content;

    QueueContent(String content) {
        this.content = content;
        String[] parts = content.split("<!endline!>;");

        System.out.println(content);
    }

    public Integer getPriority() {
        return priority;
    }
}

