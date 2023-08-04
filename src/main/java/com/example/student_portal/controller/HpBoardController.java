package com.example.student_portal.controller;

import com.example.student_portal.Service.HpBoardService;
import com.example.student_portal.module.HpBoard;
import com.example.student_portal.module.SqlClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HpBoardController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private HpBoardService hpBoardService;
    @Autowired
    public HpBoardController(HpBoardService hpBoardService)
    {
        this.hpBoardService = hpBoardService;
    }

    //apis:
    @PostMapping("/api/getHpBoard")
    public ResponseEntity<HpBoard[]> getHpBoard(@RequestBody int type)
    {
        List<HpBoard> boards=hpBoardService.findAllHpBoard();
        List<HpBoard> hpSlides = new ArrayList<>();
        for(HpBoard b : boards)
        {
            if(b.getType() == type)
            {
                hpSlides.add(b);
            }
        }
        Collections.sort(hpSlides,(b1,b2)->b1.getNumber()- b2.getNumber());
        HpBoard[] hpArr =  hpSlides.toArray(new HpBoard[hpSlides.size()]);

        return ResponseEntity.ok(hpArr);
    }

    @PostMapping("/api/addHpBoard")
    int addHpBoard(@RequestBody HpBoard hpBoard)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        hpBoard.setId(sqlClass.idGeneration(4));

        return hpBoardService.addHpBoard(hpBoard);
    }
    @PostMapping("/api/updateHpBoard")
    int updateHpBoard(@RequestBody HpBoard hpBoard)
    {
        return hpBoardService.updateHpBoard(hpBoard);
    }
    @PostMapping("/api/deleteHpBoard")
    int deleteHpBoard(@RequestBody HpBoard hpBoard)
    {
        return hpBoardService.deleteHpBoard(hpBoard.getId());
    }

}
