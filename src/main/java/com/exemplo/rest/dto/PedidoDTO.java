package com.exemplo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/*
{
	"cliente": 1,
	"total": 100,
	"items":[
		{
			"produto":1,
			"quandtidade":10
		}
	]
}
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;
}
