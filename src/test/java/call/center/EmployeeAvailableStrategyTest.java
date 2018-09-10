package call.center;

import call.center.error.FindEmployeeAvailableError;
import call.center.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class EmployeeAvailableStrategyTest {

    private EmployeeAvailableStrategy employeeAvailableStrategy;
    private Employee employeeOperator1, employeeOperator2, employeeOperator3, employeeSupervisor1, employeeSupervisor2, employeeDirector;

    @Before
    public void setUp() {
        employeeOperator1 = new EmployeeOperator("operator 1");
        employeeOperator2 = new EmployeeOperator("operator 2");
        employeeOperator3 = new EmployeeOperator("operator 3");
        employeeSupervisor1 = new EmployeeSupervisor("supervisor 1");
        employeeSupervisor2 = new EmployeeSupervisor("supervisor 2");
        employeeDirector = new EmployeeDirector("director 1");

        employeeAvailableStrategy = new EmployeeAvailableStrategy();
    }

    @Test(expected = FindEmployeeAvailableError.class)
    public void noFindEmployeeAvailableTest() {
        employeeOperator1.setState(EmployeeState.BUSY);
        employeeOperator2.setState(EmployeeState.BUSY);
        employeeOperator3.setState(EmployeeState.BUSY);
        employeeSupervisor1.setState(EmployeeState.BUSY);
        employeeSupervisor2.setState(EmployeeState.BUSY);
        employeeDirector.setState(EmployeeState.BUSY);

       this.employeeAvailableStrategy.findEmployee(buildEmployees(employeeOperator1, employeeOperator2, employeeOperator3, employeeSupervisor1, employeeSupervisor2, employeeDirector));

    }

    @Test
    public void findEmployeeDirectorTest() {
        employeeOperator1.setState(EmployeeState.BUSY);
        employeeOperator2.setState(EmployeeState.BUSY);
        employeeOperator3.setState(EmployeeState.BUSY);
        employeeSupervisor1.setState(EmployeeState.BUSY);
        employeeSupervisor2.setState(EmployeeState.BUSY);

        Employee employee = this.employeeAvailableStrategy.findEmployee(buildEmployees(employeeOperator1, employeeOperator2, employeeOperator3, employeeSupervisor1, employeeSupervisor2, employeeDirector));

        Assert.assertEquals(employee.getType(), employeeDirector.getType());
    }

    @Test
    public void findEmployeeSupervisorTest() {
        employeeOperator1.setState(EmployeeState.BUSY);
        employeeOperator2.setState(EmployeeState.BUSY);
        employeeOperator3.setState(EmployeeState.BUSY);
        employeeSupervisor1.setState(EmployeeState.BUSY);

        Employee employee = this.employeeAvailableStrategy.findEmployee(buildEmployees(employeeOperator1, employeeOperator2, employeeOperator3, employeeSupervisor1, employeeSupervisor2, employeeDirector));

        Assert.assertEquals(employee.getType(), employeeSupervisor2.getType());
        Assert.assertEquals(employee.getName(), employeeSupervisor2.getName());
    }

    @Test
    public void findEmployeeOperatorTest() {
        employeeOperator1.setState(EmployeeState.BUSY);
        employeeOperator2.setState(EmployeeState.BUSY);

        Employee employee = this.employeeAvailableStrategy.findEmployee(buildEmployees(employeeOperator1, employeeOperator2, employeeOperator3, employeeSupervisor1, employeeSupervisor2, employeeDirector));

        Assert.assertEquals(employee.getType(), employeeOperator3.getType());
        Assert.assertEquals(employee.getName(), employeeOperator3.getName());
    }

    private Collection<Employee> buildEmployees(Employee... employee) {
        return Arrays.asList(employee);
    }
}
