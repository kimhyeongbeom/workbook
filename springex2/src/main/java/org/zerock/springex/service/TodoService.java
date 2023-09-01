package org.zerock.springex.service;

import java.util.List;

import org.zerock.springex.dto.TodoDTO;

public interface TodoService {

    void register(TodoDTO todoDTO);

    List<TodoDTO> getAll();

    TodoDTO getOne(Long tno);

//    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);
//
//
    void remove(Long tno);

    void modify(TodoDTO todoDTO);
}

