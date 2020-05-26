package com.example.shiftit;

import java.util.List;

public class OpenShiftsDataProvider implements ShiftDataProvider {

    private Repository repository;
    private String uid;

    public OpenShiftsDataProvider(Repository repository, String uid) {
        this.repository = repository;
        this.uid = uid;
    }

    @Override
    public List<Shift> getShifts() {
        return repository.getOpenShiftsByProfession(repository.getUser(uid).getProfession());
    }
}
