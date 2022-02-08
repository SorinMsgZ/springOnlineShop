package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.exceptions.MissingTokenException;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorMissingTokenDTO {
        private String type;
        private String message;

        public static ro.msg.learning.shop.dto.ErrorMissingTokenDTO of(MissingTokenException exception) {
            return new ro.msg.learning.shop.dto.ErrorMissingTokenDTO("no_token_found", exception.getMessage());
        }
    }
