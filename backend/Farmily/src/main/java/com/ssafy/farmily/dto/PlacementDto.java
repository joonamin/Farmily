package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.AccessoryType;
import utils.Position;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlacementDto {
	private Position position;
	private String dtype;
	private Long recordId;
	private AccessoryType type;
	// 여기로 오면은요 이렇게 있는데용
	// 여기가 속살인가요? 존나 꼴궁속입니다요네리
	// 그래서 저는요
	// {
	//         "dtype":"F",
	//         "position":{
	//             "row":3,
	//             "col":3
	//         },
	//         "recordId":4
	//         }
	// placement를 이렇게 주면 expect 되어 있는 FruitPlacement로
}
