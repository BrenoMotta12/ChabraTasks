package com.bm12.chabra.model.enums;

public enum PriorityLevel {

    URGENT(1),
    HIGH(2),
    NORMAL(3),
    LOW(4);


    private Integer priorityLevel;

    PriorityLevel (Integer priorityLevel) {
        this.priorityLevel  = priorityLevel;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

}
