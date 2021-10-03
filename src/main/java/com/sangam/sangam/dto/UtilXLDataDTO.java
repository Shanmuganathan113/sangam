package com.sangam.sangam.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UtilXLDataDTO {

	private String utilKey = "";
	private String utilUserId;
	private String utilFileName;
	private String latestStaticUpdatedTime;
	private String statusMessage;
	private LocalDateTime pLatestStaticUpdatedTime;
	private List<UtilTaskDTO> utilValue = new ArrayList<UtilTaskDTO>();
	private FilterTaskDTO filterTaskDTO = new FilterTaskDTO();
}
