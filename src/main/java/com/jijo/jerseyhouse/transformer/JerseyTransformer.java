package com.jijo.jerseyhouse.transformer;

import com.jijo.jerseyhouse.dto.JerseyViewDto;
import com.jijo.jerseyhouse.utilities.CommonMethods;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JerseyTransformer {
    public static JerseyViewDto toJerseyViewDto(Object[] object){
        JerseyViewDto response;
        try {
            response = JerseyViewDto.builder()
                    .teamId((Integer) object[0])
                    .teamName(CommonMethods.getStringIfNull(object[1]))
                    .SeasonCode((Integer) object[2])
                    .Season(CommonMethods.getStringIfNull(object[3]).concat("-").concat(CommonMethods.getStringIfNull(object[4])))
                    .stock((Long) object[5])
                    .hasStock((Long) object[5] > 0)
                    .imageUrl(CommonMethods.getStringIfNull(object[6]))
                    .build();
        }catch (Exception e){
            log.error("Unable to Parse view Response");
            return null;
        }
        log.info("successfully transformed to Jersey view response: {}",response.toString());
        return response;
    };
}
