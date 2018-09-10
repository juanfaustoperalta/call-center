package call.center.model;

public class EmployeeDirector extends Employee {

    public EmployeeDirector(String name) {
        super(name);
    }

    @Override
    public EmployeeType getType() {
        return EmployeeType.DIRECTOR;
    }


}
