package com.jijo.jerseyhouse.model;

import com.jijo.jerseyhouse.utilities.constants.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacement {
    @Id
    private String orderId;
    @NotNull
    private String userId;
    private int teamId;
    private int seasonCode;
    private String size;
    @Min(message = MessageConstants.ORDER_QUANTITY_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_ONE, value = 1)
    @Column(nullable = false)
    private int quantity;
    private Date orderDate;
    @CreationTimestamp
    private Date orderPlacedDate;
}
