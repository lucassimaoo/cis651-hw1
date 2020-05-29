package com.example.shiftit;

import java.util.List;

public interface ShiftDataProvider {

    List<Shift> getShifts();

    boolean showRequesterPicture();

}
