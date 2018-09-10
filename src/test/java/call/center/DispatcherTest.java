package call.center;

import call.center.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class DispatcherTest {

    private Dispatcher dispatcher;

    private List<Employee> employees;

    private static Integer MAX_CALL_DURATION = 10;

    private static Integer MIN_CALL_DURATION = 5;

    @Before
    public void setUp() {
        Employee employeeOperator1 = new EmployeeOperator("operator 1");
        Employee employeeOperator2 = new EmployeeOperator("operator 2");
        Employee employeeOperator3 = new EmployeeOperator("operator 3");
        Employee employeeOperator4 = new EmployeeOperator("operator 4");
        Employee employeeOperator5 = new EmployeeOperator("operator 5");
        Employee employeeOperator6 = new EmployeeOperator("operator 6");
        Employee employeeSupervisor1 = new EmployeeSupervisor("supervisor 1");
        Employee employeeSupervisor2 = new EmployeeSupervisor("supervisor 2");
        Employee employeeSupervisor3 = new EmployeeSupervisor("supervisor 3");
        Employee employeeDirector = new EmployeeDirector("director 1");

        employees = buildEmployees(employeeDirector, employeeSupervisor1, employeeSupervisor2, employeeSupervisor3, employeeOperator1, employeeOperator2, employeeOperator3, employeeOperator4, employeeOperator5, employeeOperator6);
    }

    @Test
    public void testDispatcherTenCalls() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        List<Call> calls = this.buildListOfRandomCalls(10);
        dispatcher = new Dispatcher(employees, calls);

        executor.execute(dispatcher);

        while(true){
            if(isAttendedAllCalls(calls)){
                break;
            }
        }

        Assert.assertTrue(isAttendedAllCalls(calls));
    }

    @Test
    public void testDispatcherTwentyCalls() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        List<Call> calls = this.buildListOfRandomCalls(20);

        dispatcher = new Dispatcher(employees, calls);

        executor.execute(dispatcher);

        while(true){
            if(isAttendedAllCalls(calls)){
                break;
            }
        }

        Assert.assertTrue(isAttendedAllCalls(calls));
    }

    private List<Employee> buildEmployees(Employee... employee) {
        return Arrays.asList(employee);
    }

    private Boolean isAttendedAllCalls(List<Call> calls){
        return calls.stream().allMatch(call -> call.getState().equals(CallState.ATTENDED));
    }

    private List<Call> buildListOfRandomCalls(int size) {
        List<Call> callList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            callList.add(new Call(ThreadLocalRandom.current().nextInt(MIN_CALL_DURATION, MAX_CALL_DURATION + 1)));
        }
        return callList;
    }

}
