package com.situ.anime.domain.vo;

import com.situ.anime.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 注意，@EqualsAndHashCOde注解是用来解决子类与本身进行比较的问题的
 * 如果不加该注解，那么其仅根据子类自身的属性生成hashcode，那么只需要子类的属性相同即相同
 * 加上该注解后，会同时比较父类的属性
 * @author liangyunfei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo extends User {
    private String rePassword;
    private String oldPassword;

    @Override
    public String toString() {
        System.out.println("rePassword:"+rePassword+ " newPassword:" +oldPassword);
        return super.toString();
    }
}
