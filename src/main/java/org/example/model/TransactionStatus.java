package org.example.model;


public enum TransactionStatus {
    APPROVED(0),
    REVERSED(1),
    REFUNDED(2),
    ERROR(3);

    private final int number;

    TransactionStatus(int number) {
        this.number = number;
    }

    public static TransactionStatus checkIfExist(int status){
        for (TransactionStatus value: TransactionStatus.values()) {
            if(status== value.number){
                return value;
            }
        }
        return TransactionStatus.ERROR;
    }
}
