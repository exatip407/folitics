package com.ohmuk.folitics.service.module.like;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLike;

public class ComponentUtils {

    public static Set<Opinion> getOpinionsFromOpinionLike(Set<OpinionLike> entities) {
        Set<Opinion> opinions = new TreeSet<Opinion>();
        Iterator<OpinionLike> iterator = entities.iterator();
        while (iterator.hasNext()) {
            /*not get*/
           /* opinions.add(iterator.next().getOpinion());*/
        }
        return opinions;
    }

}
