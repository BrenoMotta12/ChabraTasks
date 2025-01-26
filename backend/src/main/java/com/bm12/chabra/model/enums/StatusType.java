package com.bm12.chabra.model.enums;

public enum StatusType {

    NOT_STARTED("Não iniciado"),
    IN_PROGRESS("Em progresso"),
    COMPLETED("Concluído");

    private String statusType;

    StatusType (String statusType) {
        this.statusType = statusType;
    }

    public String getStatusType() {
        return statusType;
    }

}
