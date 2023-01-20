package no.vegvesen.nvdbapi.client.model.transaction;

import java.time.LocalDateTime;

public class TransactionInfo {
    public final int taskId;
    public final LocalDateTime transactionDate;
    public final LocalDateTime transactionIndexedDate;

    public TransactionInfo(int taskId, LocalDateTime transactionDate, LocalDateTime transactionIndexedDate) {
        this.taskId = taskId;
        this.transactionDate = transactionDate;
        this.transactionIndexedDate = transactionIndexedDate;
    }
}
