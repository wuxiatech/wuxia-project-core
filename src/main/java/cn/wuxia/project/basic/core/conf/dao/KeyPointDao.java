package cn.wuxia.project.basic.core.conf.dao;

import cn.wuxia.project.basic.core.conf.entity.KeyPoint;
import cn.wuxia.project.common.dao.CommonMongoDao;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class KeyPointDao extends CommonMongoDao<KeyPoint, String> {

    /**
     * 统计次数
     *
     * @param action
     * @param begin
     * @param end
     * @return
     */
    public long countAction(String action, Date begin, Date end) {
        return count(Query.query(Criteria.where("action").is(action).and("createdOn").gte(begin).lt(end)));
    }


    /**
     * 统计次数
     *
     * @param action
     * @param begin
     * @param end
     * @return
     */
//    public List<CountByDate> countAction2(String action, Date begin, Date end) {
//
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("action").is(action).and("createdOn").gte(begin).lte(end)),
//                Aggregation.project().and(StringOperators.Substr.valueOf("createdOn").substring(0, 10)).as("data"),
//                Aggregation.group("$data").count().as("count_value"),
//                Aggregation.project("count_value").and("$$_id").as("date_value"));
//        // Aggregation.sort(Sort.Direction.ASC, "date_value"));
//
//        AggregationResults ar = mongoTemplate.aggregate(aggregation, getMongoTemplate().getCollectionName(getEntityClass()), CountByDate.class);
//        List<CountByDate> list = ar.getMappedResults();
//        return list;
//    }
}
