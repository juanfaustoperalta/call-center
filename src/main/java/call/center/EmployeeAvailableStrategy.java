package call.center;

import call.center.error.FindEmployeeAvailableError;
import call.center.model.Employee;
import call.center.model.EmployeeState;
import call.center.model.EmployeeType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeAvailableStrategy {

    public synchronized Employee findEmployee(Collection<Employee> employees) {
        List<Employee> availableEmployees = employees.stream().filter(e -> e.getEmployeeState().equals(EmployeeState.AVAILABLE)).collect(Collectors.toList());
        Optional<Employee> optionalEmployee = this.getOptionalEmployee(availableEmployees, EmployeeType.OPERATOR);
        if (!optionalEmployee.isPresent()) {
            optionalEmployee = this.getOptionalEmployee(availableEmployees, EmployeeType.SUPERVISOR);
            if (!optionalEmployee.isPresent()) {
                optionalEmployee = this.getOptionalEmployee(availableEmployees, EmployeeType.DIRECTOR);
                if (!optionalEmployee.isPresent()) {
                    throw new FindEmployeeAvailableError();
                }
            }
        }
        Employee employee = optionalEmployee.get();
        employee.setState(EmployeeState.BUSY);
        return employee;
    }

    private Optional<Employee> getOptionalEmployee(List<Employee> employees, EmployeeType employeeType) {
        return employees.stream().filter(e -> e.getType().equals(employeeType)).findFirst();
    }
}