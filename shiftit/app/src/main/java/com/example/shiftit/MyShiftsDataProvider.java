package com.example.shiftit;

import java.util.List;

public class MyShiftsDataProvider implements ShiftDataProvider {

    private Repository repository;
    private String uid;

    public MyShiftsDataProvider(Repository repository, String uid) {
        this.repository = repository;
        this.uid = uid;
    }

    @Override
    public List<Shift> getShifts() {
        return repository.getMyShifts(uid);
    }
}
