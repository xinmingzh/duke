public class Deadline extends Task {
    protected String by;

    Deadline(String desc) {
        super(desc);
        this.by = "no idea :-p";
    }
    Deadline(String desc, boolean done) {
        super(desc, done);
    }
    Deadline(String desc, String by) {
        super(desc);
        this.by = by;
    }
    Deadline(String desc, String by, boolean done) {
        super(desc, done);
        this.by = by;
    }

    public String getDateBy() {
        return by;
    }

    public void setDateBy(String by) {
        this.by = by;
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDateBy() + ")";
    }
}
