package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model;

public class StateData {

    private String stateId;
    private String stateName;

    public StateData(String stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
