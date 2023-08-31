
package org.zerock.springex.mapper;

import java.util.HashMap;
import java.util.List;

import org.zerock.springex.domain.TodoVO;

public interface TodoMapper {

    String getTime();

    void insert(TodoVO todoVO);

    List<HashMap> selectAll();
    
    List<TodoVO> selectAll2();

    /*
    TodoVO selectOne(Long tno);

    void delete(Long tno);

    void update(TodoVO todoVO);

    List<TodoVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);
    */
}

