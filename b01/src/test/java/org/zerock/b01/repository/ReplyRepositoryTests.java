package org.zerock.b01.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private ReplyRepository replyRepository;


    @Test
    public void testInsert() {

        //실제 DB에 있는 bno
        Long bno  = 100L;

        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글.....")
                .replyer("replyer1")
                .build();

        replyRepository.save(reply);

    }

    @Transactional
    @Test
    public void testBoardReplies() {

        Long bno = 100L;

        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply -> {
            log.info(reply);
        });
    }

    @Test
    public void testSearchReplyCount() {
    	String[] types = { "t", "c", "w" };
    	
    	String keyword = "1";
    	
    	Pageable pageable = PageRequest.of(0,  10, Sort.by("bno").descending());
    	
    	Page<BoardListReplyCountDTO> result = replyRepository.searchWithReplyCount(types, keyword, pageable);

    	log.info("result.getTotalPages : " + result.getTotalPages());
    	log.info("result.getSize : " + result.getSize());
    	log.info("result.getNumber : " + result.getNumber());
    	log.info("result.hasPrevious : " + result.hasPrevious() );
    	log.info("result.hasNext : " + result.hasNext() );
    	
    	result.getContent().forEach(board -> log.info(board));
    	
    }
    
    @Test
    @Transactional
    @Commit
    public void testRemoveAll() {
    	Long bno = 1L;
    	
    	replyRepository.deleteByBoard_Bno(bno);
    	boardRepository.deleteById(bno);
    }
}
