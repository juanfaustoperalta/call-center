package call.center.model;

public class EmployeeOperator extends Employee {

    public EmployeeOperator(String name) {
        super(name);
    }

    @Override
    public EmployeeType getType() {
        return EmployeeType.OPERATOR;
    }


}
