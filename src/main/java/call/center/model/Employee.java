package call.center.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class Employee  {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class.getName());

    private EmployeeState state;

    private String name;

    public abstract EmployeeType getType();

    Employee(String name) {
        this.name = name;
        this.state = EmployeeState.AVAILABLE;
    }

    public EmployeeState getEmployeeState() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }


    /**
     *
     * Receives a call, answers it, then marks that the call was answered and the employee is enabled again
     *
     * @param call
     */
    public void attend(Call call){
        if (call != null) {
            logger.info("this employee: {} attending call with {} seconds", this.getName(), call.getDuration());
            try {
                TimeUnit.SECONDS.sleep(call.getDuration());
            } catch (InterruptedException e) {
            //nothing
            } finally {
                logger.info("call is attended for employed: " + this.getName());
                call.setState(CallState.ATTENDED);
                this.setState(EmployeeState.AVAILABLE);
            }
        }
    }

}
