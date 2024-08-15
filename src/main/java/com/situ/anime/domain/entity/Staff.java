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
public class Staff {

  private Integer id;
  private String originalWork;
  private String chiefSupervisor;
  private String supervisor;
  private String seriesComposition;
  private String script;
  private String characterDesign;
  private String chiefAnimationDirector;
  private String artDirector;
  private String artDesign;
  private String colorDesign;
  private String dp;
  private String editing;
  private String audioDirector;
  private String musicProducer;
  private String animationStudio;
  private String productions;

}
