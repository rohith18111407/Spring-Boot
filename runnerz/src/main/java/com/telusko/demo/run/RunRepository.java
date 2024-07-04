package com.telusko.demo.run;

import java.util.List;

public class RunRepository {
	private List<Run> runs=new ArrayList<>();
	
	List<Run> findAll(){
		return runs;
	}
}
