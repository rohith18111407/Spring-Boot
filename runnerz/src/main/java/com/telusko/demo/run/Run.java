package com.telusko.demo.run;

import java.lang.reflect.AccessFlag.Location;
import java.time.LocalDateTime;

public record Run(
		Integer id,
		String title,
		LocalDateTime startedOn,
		LocalDateTime completedOn,
		Integer miles,
		Location location) 
{

	public Run {
		// TODO Auto-generated constructor stub
	}

}
