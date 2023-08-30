package org.zerock.springex.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private Long tno;

//    @NotEmpty
    private String title;

//    @Future
    private LocalDate dueDate;

    private boolean finished;

 //   @NotEmpty
    private String writer;

}
