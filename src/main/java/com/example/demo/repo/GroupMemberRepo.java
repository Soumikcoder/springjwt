package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.GroupMember;

@Repository
public interface GroupMemberRepo extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findByUserUsername(String username);

    List<GroupMember> findByGroupGroupId(Long groupId);

}
