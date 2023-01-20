/*
 * Copyright (c) 2015-2018, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.model.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Transaction {

    private final TransactionId transactionId;
    private final LocalDateTime indexedDateTime;
    private final Integer taskTypeId;
    private final String userId;
    private final List<RoadObject> roadObjects;

    public Transaction(TransactionId transactionId, LocalDateTime indexedDateTime, Integer taskTypeId, String userId, List<RoadObject> roadObjects) {
        this.transactionId = transactionId;
        this.indexedDateTime = indexedDateTime;
        this.taskTypeId = taskTypeId;
        this.userId = userId;
        this.roadObjects = roadObjects;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public List<RoadObject> getRoadObjects() {
        return roadObjects;
    }

    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public LocalDateTime getIndexedDateTime() {
        return indexedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getTransactionId(), that.getTransactionId()) &&
            Objects.equals(getTaskTypeId(), that.getTaskTypeId()) &&
            Objects.equals(getUserId(), that.getUserId()) &&
            Objects.equals(getRoadObjects(), that.getRoadObjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getTaskTypeId(), getUserId(), getRoadObjects());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "transactionId=" + transactionId +
            ", indexedDateTime=" + indexedDateTime +
            ", taskTypeId=" + taskTypeId +
            ", userId='" + userId + '\'' +
            ", roadObjects=" + roadObjects +
            '}';
    }
}
