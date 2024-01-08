package com.snowthon.snowman.domain;

import com.snowthon.snowman.domain.wear.HeadWear;
import com.snowthon.snowman.domain.wear.NeckWear;
import com.snowthon.snowman.domain.wear.OuterWear;
import com.snowthon.snowman.domain.wear.TopWear;
import com.snowthon.snowman.dto.type.EBranchType;
import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "weathers")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "location", nullable = false)
    private String location; //지역 이름

    @Column(name = "code", nullable = false)
    private String code;    //지역 코드

    @Column(name ="sky", nullable = false)
    private ESky sky; //눈 비 구분

    @Column(name = "temperature", nullable = false)
    private Integer temperature; //온도 메인브랜치에서 필요한 값

    @Column(name = "level", nullable = false)
    private ELevel level;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    //날씨 삭제시 브랜치 완전 삭제
    /**
     * 양방향 매핑
     */
    @OneToMany(mappedBy = "weather", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branchList = new ArrayList<>();

    @Builder
    public Weather(String location, String code, ESky sky, ELevel level) {
        this.location = location;
        this.code = code;
        this.sky = sky;
        this.level = level;
        this.createdAt = LocalDateTime.now();
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
