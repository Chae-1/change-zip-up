package com.kosa.chanzipup.api.member.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailDuplicationCheckResponse {
    private boolean isDuplicated;
}
