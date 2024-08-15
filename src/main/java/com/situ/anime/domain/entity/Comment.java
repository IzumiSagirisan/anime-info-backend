package com.situ.anime.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @Description
 * @Author liangyunfei
 * @Date: 2024-08-15 21:00:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Integer id;
    private Integer likeNumber;
    private Integer animeId;
    private String content;
    private Integer userId;
    private String ipAddress;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date sendTime;


}
