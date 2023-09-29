package com.example.student_portal.Service;

import com.example.student_portal.module.HpBoard;
import org.springframework.stereotype.Service;

import java.util.List;


public interface HpBoardService {
    List<HpBoard> findAllHpBoard();
    int addHpBoard(HpBoard hpBoard);

    int updateHpBoard(HpBoard hpBoard);
    int deleteHpBoard(int id );

}
