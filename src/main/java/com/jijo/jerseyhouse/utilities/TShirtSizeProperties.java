package com.jijo.jerseyhouse.utilities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@NoArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "size-measures")
public class TShirtSizeProperties {
    char extraSmall;
    char small;
    char medium;
    char large;
    char extraLarge;

    @Bean
    public TShirtSizeProperties setTShirtSizeProperties(TShirtSizeProperties properties){
        return properties;
    }
}
