package be.wouterversyck.slackintegration.repositories;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import be.wouterversyck.slackintegration.model.funFact.Vote;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

public class FunFactRepositoryImpl implements FunFactRepositoryCustom {

    private ReactiveMongoTemplate reactiveMongoTemplate;

    public FunFactRepositoryImpl(final ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<FunFact> getRandom() {
        SampleOperation sampleOperation = Aggregation.sample(1);
        Aggregation aggregation = Aggregation.newAggregation(sampleOperation);
        return reactiveMongoTemplate.aggregate(aggregation, "fun_facts", FunFact.class)
                .next();
    }

    @Override
    public Mono<Vote> getVote(String funFactId, String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(funFactId)).and("votes.user._id").is(userId));
        query.fields().include("name").include("id").position("votes", 1);
        return reactiveMongoTemplate.findOne(query, FunFact.class)
                .map(e -> e.getVotes().get(0));
    }
}
