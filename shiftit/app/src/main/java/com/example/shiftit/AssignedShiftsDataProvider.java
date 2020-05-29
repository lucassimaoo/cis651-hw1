package com.example.shiftit;

import java.util.List;

public class AssignedShiftsDataProvider implements ShiftDataProvider {

    private Repository repository;
    private String uid;

    public AssignedShiftsDataProvider(Repository repository, String uid) {
        this.repository = repository;
        this.uid = uid;
    }

    @Override
    public List<Shift> getShifts() {
        return repository.getShiftsAssigned(uid);
    }

    @Override
    public boolean showRequesterPicture() {
        return true;
    }
}
