package call.center.model;

public class Call {

    private static Integer MAX_CALL_DURATION = 10;

    private static Integer MIN_CALL_DURATION = 5;

    private Integer duration;

    private CallState state;

    public Call(Integer duration) {
        if (!(duration <= MAX_CALL_DURATION && duration >= MIN_CALL_DURATION)){
            throw new RuntimeException();
        }
        this.setState(CallState.ON_HOLD);
        this.setDuration(duration);
    }

    private void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public CallState getState() {
        return state;
    }

    public void setState(CallState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "{ \"duration:\""+ this.duration +
                 "\"state:\""+ this.state.name() +
                "}";
    }
}
