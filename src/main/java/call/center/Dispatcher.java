package call.center;

import call.center.error.FindEmployeeAvailableError;
import call.center.model.Call;
import call.center.model.Employee;

import java.util.List;
import java.util.concurrent.*;

public class Dispatcher implements Runnable {

    private ConcurrentLinkedDeque<Employee> employees;

    private ConcurrentLinkedDeque<Call> calls;

    private EmployeeAvailableStrategy employeeAvailableStrategy;

    private static ExecutorService executorService;

    private static Integer MAX_NUMBER_OF_CALLS = 10;

    Dispatcher(List<Employee> employees, List<Call> call) {
        this.employeeAvailableStrategy = new EmployeeAvailableStrategy();
        this.calls = new ConcurrentLinkedDeque<>(call);
        this.employees = new ConcurrentLinkedDeque<>(employees);
        executorService = Executors.newFixedThreadPool(MAX_NUMBER_OF_CALLS);
    }


    /**
     *
     * Receive a call, find a free employee, if you do not find it, stack the call again
     *
     * @param call
     */
    private synchronized void dispatchCall(Call call) {

        try {
            Employee employee = this.employeeAvailableStrategy.findEmployee(this.getEmployees());
            executorService.execute(() -> employee.attend(call));
        } catch (FindEmployeeAvailableError e) {
            this.addFirst(call);
        }
    }

    private synchronized Call getOneCall() {
        return this.calls.poll();
    }

    private synchronized void addFirst(Call call) {
        this.calls.addFirst(call);
    }

    private synchronized ConcurrentLinkedDeque<Employee> getEmployees() {
        return employees;
    }

    @Override
    public void run() {
        while (true) {
            if (!this.calls.isEmpty()) {
                Call call = this.getOneCall();
                this.dispatchCall(call);
            }
        }
    }
}




