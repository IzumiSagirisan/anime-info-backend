package com.situ.anime.domain.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description 
 * @Author  liangyunfei
 * @Date: 2024-08-15 21:00:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

  private Integer id;
  private String avatar;
  private String username;
  private String password;
  private String phone;
  private String ipAddress;
  private String email;
  private Integer userGroup;
  private Integer status;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
  private Date registerTime;

}
