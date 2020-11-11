package com.cxm.personal.wechat.mapper;

import com.cxm.personal.wechat.pojo.Sentence;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 15:59
 **/
public interface SentenceMapper {

    @Select("select id,content,note,translation,date,fenxiang_img as fenxiangImg from sentence where date = #{date}")
    Sentence selectByDate(String date);

    @Insert("insert into sentence(content,note,translation,date,fenxiang_img) values(#{content},#{note},#{translation},#{date},#{fenxiangImg})")
    Integer insert(Sentence sentence);

    @Delete("delete from sentence where id = #{id}")
    Integer delete(Long id);

}
