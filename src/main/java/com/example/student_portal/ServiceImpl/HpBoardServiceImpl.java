package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.HpBoardMapper;
import com.example.student_portal.Service.HpBoardService;
import com.example.student_portal.module.HpBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HpBoardServiceImpl implements HpBoardService {
    private final HpBoardMapper hpBoardMapper;

    @Autowired
    public HpBoardServiceImpl(HpBoardMapper hpBoardMapper) {
        this.hpBoardMapper = hpBoardMapper;
    }
    @Override
    public List<HpBoard> findAllHpBoard()
    {
        return hpBoardMapper.findAllHpBoard();
    }

    @Override
    public int addHpBoard(HpBoard hpBoard) {
        return hpBoardMapper.addHpBoard(hpBoard);
    }

    @Override
    public int updateHpBoard(HpBoard hpBoard) {
        return hpBoardMapper.updateHpBoard(hpBoard);
    }

    @Override
    public int deleteHpBoard(int id) {
        return hpBoardMapper.deleteHpBoard(id);
    }
}
