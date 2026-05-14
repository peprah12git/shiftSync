package org.example.shiftsync.enums;

public enum Role {
    EMPLOYEE,
    //can view own schdule, submit availability, request leave, request shift swaps, and read notification
    MANAGER,
    //create and manage shift, assign employees, manage schdules for assigned locations only, approve swap request ,view employees and availability for their locations
    HR_ADMIN
    //manage employee records,manage locations and departments,approve or reject leave, access reports
    //query audit log
}
