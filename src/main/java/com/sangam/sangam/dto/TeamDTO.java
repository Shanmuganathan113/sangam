package com.sangam.sangam.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TeamDTO {
	private String	teamId;
	private String	teamName;
	private String	summary;
	private String	detailedDescription;
	private String 	pMyRole;
	private Integer pRoleId;
	List<TeamUserRoleDTO> listTeamUser = new ArrayList<TeamUserRoleDTO>();
}
