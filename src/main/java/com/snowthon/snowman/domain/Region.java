package com.snowthon.snowman.domain;

import com.snowthon.snowman.dto.type.EBranchType;
import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "location", nullable = false)
    private String location; //지역 이름

    @Column(name = "code", nullable = false)
    private String code;    //지역 코드

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    //날씨 삭제시 브랜치 완전 삭제
    /**
     * 양방향 매핑
     */
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branchList = new ArrayList<>();


    @Builder
    public Region(String location, String code, List<Branch> branchList) {
        this.location = location;
        this.code = code;
        this.branchList = branchList;
        this.createdAt = LocalDateTime.now();
    }

    public static Region createRegion(String location, String code, List<Branch> branchList) {
        return Region.builder()
                .location(location)
                .code(code)
                .branchList(branchList)
                .build();
    }

    public Branch getMainBranch() {
        return branchList.stream()
                .filter(branch -> branch.getBranchType() == EBranchType.MAIN_BRANCH)
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BRANCH));
    }

    public Branch getFirstBranch(){
        return branchList.stream()
                .filter(branch -> branch.getBranchType() == EBranchType.FIRST_BRANCH)
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BRANCH));
    }

    public Branch getSecondBranch(){
        return branchList.stream()
                .filter(branch -> branch.getBranchType() == EBranchType.SECOND_BRANCH)
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BRANCH));
    }

    public Branch getThirdBranch(){
        return branchList.stream()
                .filter(branch -> branch.getBranchType() == EBranchType.THIRD_BRANCH)
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BRANCH));
    }
}
