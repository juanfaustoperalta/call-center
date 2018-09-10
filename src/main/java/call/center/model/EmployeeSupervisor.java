package call.center.model;

import java.util.Optional;

public class EmployeeSupervisor extends Employee {

    public EmployeeSupervisor(String name) {
        super(name);
    }

    @Override
    public EmployeeType getType() {
        return EmployeeType.SUPERVISOR;
    }


}
