package com.bm12.chabra.model.enums;

public enum PriorityLevel {

    URGENT(0),
    HIGH(1),
    NORMAL(2),
    LOW(3);


    private Integer priorityLevel;

    PriorityLevel (Integer priorityLevel) {
        this.priorityLevel  = priorityLevel;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

}
