package org.zerock.springex.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoVO {
    private Long tno;

    private String title;

    private LocalDate dueDate;

    private String writer;

    private Boolean finished;
}
