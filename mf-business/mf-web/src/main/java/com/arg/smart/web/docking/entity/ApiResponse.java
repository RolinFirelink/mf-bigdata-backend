package com.arg.smart.web.docking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {

    private int code;

    private String msg;

    private String traceId;

    private List<ProductPriceData> date;
}
