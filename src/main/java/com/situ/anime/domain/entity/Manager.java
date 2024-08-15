package com.situ.anime.domain.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description 
 * @Author  liangyunfei
 * @Date 2024-08-15 21:00:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

  private Integer id;
  private Integer managerGroup;
  private String username;
  private String password;

}
