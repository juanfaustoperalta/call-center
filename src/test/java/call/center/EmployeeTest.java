package call.center;

import call.center.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {

    @Test
    public void testEmployeeAttendWhileAvailable() {
        Employee employee = new EmployeeOperator("operator 1");
        Call call = new Call(5);

        employee.attend(call);

        while (true){
            if (call.getState().equals(CallState.ATTENDED)){
                break;
            }
        }
        assertEquals(call.getState(), CallState.ATTENDED);
        assertEquals(employee.getEmployeeState(), EmployeeState.AVAILABLE);
    }

}
