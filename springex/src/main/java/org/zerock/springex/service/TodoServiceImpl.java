package org.zerock.springex.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.springex.domain.TodoVO;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.mapper.TodoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoMapper todoMapper;

    private final ModelMapper modelMapper;

    @Override
    public void register(TodoDTO todoDTO) {

        log.info(modelMapper);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class );

        log.info(todoVO);

        todoMapper.insert(todoVO);

    }

    @Override
    public List<TodoDTO> getAll() {

        List<HashMap> resultList = todoMapper.selectAll();
        List<TodoDTO> dtoList = new ArrayList<TodoDTO>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        
        for(HashMap item : resultList) {
        	System.out.println("item : " + item);
        	TodoDTO dd = new TodoDTO();
        	dd.setTno(Long.parseLong((String)item.get("TNO")));
        	dd.setTitle((String)item.get("TITLE"));
        	dd.setWriter((String)item.get("WRITER"));
        	dd.setFinished(!((String)item.get("FINISHED")).equals("0"));
        	dd.setDueDate(LocalDate.parse((String)item.get("DUEDATE"), formatter));
        	dtoList.add(dd);
        }
        
        return dtoList;
    }

    @Override
    public List<TodoDTO> getAll2() {

        List<TodoDTO> dtoList = todoMapper.selectAll2().stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }

//    @Override
//    public TodoDTO getOne(Long tno) {
//
//        TodoVO todoVO = todoMapper.selectOne(tno);
//
//        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
//
//        return todoDTO;
//    }
//
//    @Override
//    public void remove(Long tno) {
//
//        todoMapper.delete(tno);
//
//    }
//
//    @Override
//    public void modify(TodoDTO todoDTO) {
//
//        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class );
//
//        todoMapper.update(todoVO);
//
//    }
//
//    @Override
//    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
//
//
//        List<TodoVO> voList = todoMapper.selectList(pageRequestDTO);
//        List<TodoDTO> dtoList = voList.stream()
//                .map(vo -> modelMapper.map(vo, TodoDTO.class))
//                .collect(Collectors.toList());
//
//        int total = todoMapper.getCount(pageRequestDTO);
//
//        PageResponseDTO<TodoDTO> pageResponseDTO = PageResponseDTO.<TodoDTO>withAll()
//                .dtoList(dtoList)
//                .total(total)
//                .pageRequestDTO(pageRequestDTO)
//                .build();
//
//        return pageResponseDTO;
//
//    }


}
