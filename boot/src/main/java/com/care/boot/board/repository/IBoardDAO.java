package com.care.boot.board.repository;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.care.boot.board.dto.BoardDTO;

@Mapper
public interface IBoardDAO {
	public void writeProc(BoardDTO board);

	public ArrayList<BoardDTO> boardProc(@Param("b") int begin, @Param("e")int end,  @Param("sel")String sel, @Param("search") String search);

	public BoardDTO viewProc(int no);
	
	public void upHit(int no);

	public int boardCount(HashMap<String, Object> map);

	public int modifyProc(BoardDTO board);

	public int deleteProc(int no);

	public String getFileName(int no);
}
