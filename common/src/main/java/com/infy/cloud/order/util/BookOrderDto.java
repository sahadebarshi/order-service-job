package com.infy.cloud.order.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Getter
@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class BookOrderDto implements Serializable
{
    @JsonProperty("_id")
    private String orderId;
    private String bookName;
    private int noOfItem;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private String orderDate;
    private String deliveryAddress;

    public BookOrderDto()
    {
    }

}
