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
        return repository.getOpenShifts(repository.getUser(uid));
    }

    @Override
    public boolean showRequesterPicture() {
        return true;
    }
}
