package com.sian.community_api.domain;

import org.springframework.data.annotation.Id;


@Entity
public class RefreshToken {
    @Id
    private Long userId;


}
