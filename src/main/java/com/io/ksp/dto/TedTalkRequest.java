package com.io.ksp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TedTalkRequest {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMMM yyyy")
    private YearMonth date;
    private Long views;
    private Long likes;
    private String link;
}
