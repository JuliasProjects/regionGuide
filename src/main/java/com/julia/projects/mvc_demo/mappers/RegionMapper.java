package com.julia.projects.mvc_demo.mappers;

import com.julia.projects.mvc_demo.entities.Region;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RegionMapper {
    @Select("SELECT * FROM REGIONS WHERE id = #{id}")
    Region findRegionById(int id);

    @Update({"<script>",
            "update REGIONS",
            "  <set>",
            "    <if test='name != null'>name=#{name},</if>",
            "    <if test='shortname != null'>shortname=#{shortname},</if>",
            "  </set>",
            "where id=#{id}",
            "</script>"})
    void update(Region region);

    @Delete("DELETE FROM REGIONS WHERE id = #{id}")
    void delete(int id);

    @Insert("INSERT INTO REGIONS VALUES(DEFAULT, #{name}, #{shortname})")
    void insert(String name, String shortname);
}
