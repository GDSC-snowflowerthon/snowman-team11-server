package com.snowthon.snowman.dto.request;


import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VoteRequestDto {

    private ETopWear topWear;
    private EOuterWear outer;
    private EHeadWear headWear;
    private ENeckWear neckWear;

}
