package com.sangam.sangam.web.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sangam.sangam.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
//    @Query(value="SELECT a.page_id, b.name FROM table_page_admins a JOIN table_page b ON a.page_id = b.t_p_id AND a.user_id = :param_user_id AND a.role_id = 1", nativeQuery = true)
//    List<Object[]> findMyPagesWithAdminRole( @Param("param_user_id") Long userId);
//
//    // Returns all admin details for a specific page
//    @Query(value="SELECT u.t_u_id as 'userId',CONCAT(u.name,'-',r.role_name) as 'userName',r.role_id as 'roleId', r.role_name as 'roleName' FROM  lookup_team_member_role l JOIN table_user u ON l.user_id = u.t_u_id JOIN static_role r ON r.role_id = l.team_role_id AND r.role_id <> '3' JOIN table_team t  ON l.team_id = t.t_t_id WHERE l.team_id = :param_team_id", nativeQuery = true)
//    List<Object[]> fetchTeamAdmins( @Param("param_team_id") Long teamId);
//   
//    //Returns task specific privileges user have for a specific page
//    @Query(value="SELECT * FROM static_role_privilege srp WHERE CASE 	WHEN '1' = :param_role THEN srp.team_lead = 'Y' WHEN '2' = :param_role THEN srp.team_member = 'Y' WHEN '3' = :param_role THEN srp.global_admin = 'Y' END AND segment_ind = 1", nativeQuery=true)
//    List<Object[]> fetchRolePrevileges(@Param("param_role") Integer roleId);
//   
    
    @Query(value="SELECT rp.* FROM view_role_privilege rp WHERE user_id = :param_user_id AND team_role_id <> 3", nativeQuery = true)
    List<Object[]> findMyTeamsRoleWithPrivilege( @Param("param_user_id") String userId);
    
    
    
//    // Returns all task status
//    @Query(value = "SELECT t_s_id,status_details FROM static_task_status", nativeQuery=true)
//    List<Object[]> fetchAllTaskStatus();
    
//    // Returns page details
//    @Query(value = "SELECT p.t_p_id,p.name,p.study_materials_link,p.description,p.detailed_description,p.keywords FROM table_page p WHERE p.name like %:param_page_name%", nativeQuery=true)
//    // @Query(value = "SELECT p.t_p_id,p.name,p.description,p.keywords FROM table_page p LIMIT 1", nativeQuery=true)
//    List<Object[]> fetchPageDetails(@Param("param_page_name") String pageName);
    
//    // Returns Members of a page
//    @Query(value = "SELECT u.name,u.image_url,CASE WHEN r.role_name='PAGE_ADMIN' THEN 'ADMIN' ELSE 'DEVELOPER' END AS 'role' FROM table_user u JOIN table_page_admins p ON u.t_u_id = p.user_id JOIN static_role r ON r.role_id = p.role_id WHERE p.page_id = :param_page_id", nativeQuery=true)
//    List<Object[]> fetchPageMembers(@Param("param_page_id") Integer pageId);
    
    // Returns My Pages
//    @Query(value = "SELECT p.name FROM table_page p JOIN table_page_admins a ON a.page_id = p.t_p_id WHERE a.user_id = :param_user_id", nativeQuery=true)
//    List<String> fetchMyTeams(@Param("param_user_id") Long userId);
    
//    // My tasks logs
//    @Query(value = "SELECT t.t_t_id, t.title, s.status_details, u.name, l.time  FROM table_task_log l JOIN table_task t ON t.t_t_id = l.task_id JOIN static_task_status s ON l.status_ind = s.t_s_id JOIN table_user u ON l.by_user_id = u.t_u_id  WHERE l.by_user_id = :param_my_user_id OR l.to_user_id = :param_my_user_id ORDER BY l.time DESC LIMIT 10", nativeQuery=true)
//    List<Object[]> fetchMyTasksQuickLog(@Param("param_my_user_id") Long userId);
//    
//    // My Page logs
//    @Query(value = "SELECT t.t_t_id, t.title, s.status_details, u.name, l.time  FROM table_task_log l JOIN table_task t ON t.t_t_id = l.task_id JOIN static_task_status s ON l.status_ind = s.t_s_id JOIN table_user u ON l.by_user_id = u.t_u_id  WHERE t.page_id IN :param_page_ids ORDER BY l.time DESC LIMIT 10", nativeQuery=true)
//    List<Object[]> fetchPageTasksQuickLog(@Param("param_page_ids") List<Long> pageId);
    
    // My Team permissions
    @Query(value = "SELECT tp.s_t_p_id, tp.task_status FROM static_task_privilege tp WHERE CASE WHEN param_role = 'team_lead' THEN team_lead='Y' WHEN param_role = 'team_member' THEN team_member='Y' WHEN param_role = 'global_admin' THEN global_admin='Y' END", nativeQuery=true)
    List<Object[]> fetchTeamPermissions(@Param("param_team_role") List<Long> pageId);
    
    @Query(value = "SELECT tp.s_t_p_id, tp.task_status FROM static_role_privilege tp WHERE CASE WHEN param_role = 'team_lead' THEN param_role='Y' WHEN param_role = 'team_member' THEN team_member='Y' WHEN param_role = 'global_admin' THEN global_admin='Y' END AND segment_ind = param_segment_ind", nativeQuery=true)
    List<Object[]> fetchMyRolePrivilege(@Param("param_role") String roleName, @Param("param_segment_ind") String segmentInd);
    
    // Get list of all teams 
    @Query(value = "SELECT GROUP_CONCAT(JSON_OBJECT('value' , t.t_t_id,'label', t.NAME)) FROM table_team t", nativeQuery=true)
    Object fetchTeamsList();
    
    @Query(value = "SELECT GROUP_CONCAT(JSON_OBJECT('value' , u.t_u_id,'label', CONCAT(u.name,'-',r.role_name))) FROM lookup_team_member_role l JOIN table_user u ON l.user_id = u.t_u_id JOIN static_role r ON r.role_id = l.team_role_id WHERE team_id = :param_team_id", nativeQuery=true)
    Object fetchTeamsMembers(@Param("param_team_id") String teamId);
    
    //Gets all available distinct status for segment task 
    @Query(value = "SELECT GROUP_CONCAT(JSON_OBJECT('value' , t.s_r_p_id,'label', t.task_status)) FROM static_role_privilege t WHERE t.segment_ind = 1", nativeQuery=true)
    Object fetchTaskStatus();
}
